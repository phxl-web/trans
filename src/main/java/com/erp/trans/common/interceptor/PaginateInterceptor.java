/**
 * Project Name:ysynet File Name:PageInterceptor.java Package Name:com.phxl.ysynet.common.test Date:2015年9月25日下午5:38:55 Copyright (c) 2015, PHXL All Rights Reserved.
 */
package com.erp.trans.common.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.erp.trans.common.annotation.BaseSql;
import com.erp.trans.common.annotation.OrderField;
import com.erp.trans.common.annotation.QueryType;
import com.erp.trans.common.annotation.SearchField;
import com.erp.trans.common.constant.SystemConst;
import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.OrderComparator;


/**
 * mybatis对oracle分页处理公共处理类（持续完善） <br/>
 * date: 2015年9月25日 下午5:39:03 <br/>
 *
 * @version 1.0
 * @since JDK 1.6
 */
@Intercepts({@Signature(type=StatementHandler.class, method = "prepare", args={Connection.class})})
public class PaginateInterceptor implements Interceptor{
	private static final Log logger	= LogFactory.getLog(PaginateInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static String pageSqlId	= "com\\.phxl\\..+";//需要拦截的ID(正则匹配):拦截医商去项目开发的dao~mapper
	private static final String baseNamespace = "com.erp.trans.common.dao.BaseMapper";
	private static String allowSqlId = PaginateInterceptor.baseNamespace + "[^\\s]*";//需要处理的sql
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		// 分离最后一个代理对象的目标类
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
//		ResultMap resultMap = configuration.getResultMap("BaseResultMap");
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		
		// 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
		String maperMethodId = mappedStatement.getId();
		
		if (maperMethodId.matches(pageSqlId)) {
			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			Object parameterObject = boundSql.getParameterObject();
			// 如果方法有入参，才进行有关分页的分析与处理
			if (parameterObject != null) {
				
				if (parameterObject instanceof Pager) {
					// if(!maperMethodId.matches(".+paginateQuery.*")){
					Pager page = (Pager) parameterObject;
					/** 分页处理 */
					pagitionProcess(invocation, metaStatementHandler, boundSql, mappedStatement, page);
				} else if (parameterObject instanceof Map) {
					Map<String, Object> multiParams = (Map<String, Object>) parameterObject;
					/**查询入参集，如果有分页对象，则返回，没有就返回null*/
					Pager page = lookupPagerOfMuitiParams(multiParams);
					
					//单表分页处理
					if(maperMethodId.matches(allowSqlId)){
						StringBuffer stringBuffer = new StringBuffer(boundSql.getSql());
						//命名空间
						String resultMapName = (String)multiParams.get("resultMapName");
						if(maperMethodId.equals(baseNamespace + ".paginateList")){
							ResultMap resultMap = configuration.getResultMap(resultMapName);
							BaseSqlInterceptor.localResultMap.set(resultMap);
							Map<String, String> fieldsMap = null;
							Map<String, String> queryTypeMap = new HashMap<String, String>();
							Class entity = Class.forName(resultMap.getType().getName());
							Field[] fields = entity.getDeclaredFields();
							for(Field f:fields){
								SearchField sf = f.getAnnotation(SearchField.class);
								if(sf != null){
									if(fieldsMap == null){
										fieldsMap = new HashMap<String, String>();
									}
									fieldsMap.put(f.getName(), (String)AnnotationUtils.getValue(sf, "value"));
								}
								QueryType qt = f.getAnnotation(QueryType.class);
								if(qt != null){
									queryTypeMap.put(f.getName(), (String)AnnotationUtils.getValue(qt, "value"));
								}
							}
							List<ResultMapping> mapping = resultMap.getResultMappings();
							String couplet = "";
							int i = 0;
							for(ResultMapping mp:mapping){
								if(page.getConditiions() != null && page.getConditiions().get(mp.getProperty()) != null && !(fieldsMap != null && fieldsMap.get(mp.getProperty()) != null)){
									if(i == 0){
										couplet = " where ";
									}else{
										couplet = " and ";
									}
									Object value = page.getConditiions().get(mp.getProperty());
									String queryType = queryTypeMap.get(mp.getProperty());
									if(value instanceof Date){
										Date date = (Date)value;
										String dateString = sdf.format(date);
										Calendar c = Calendar.getInstance();
										c.setTime(date);
										c.add(Calendar.DAY_OF_MONTH, 1);
										String tomDateString = sdf.format(c.getTime());
										
										stringBuffer.append(couplet + mp.getColumn() + ">= TO_DATE('" + dateString + "', 'yyyy/MM/dd') AND "
																+ mp.getColumn() + "< TO_DATE('" + tomDateString + "', 'yyyy/MM/dd')");
									}else if(value instanceof Number){
										String link = "";
										if(StringUtils.isNotBlank(queryType)){
											if("like".equals(queryType)){
												link = " like '%" + value + "%' ";
											}else if("=".equals(queryType)){
												link = " = " + value;
											}
										}else{
											link = " = " + value;
										}
										
										stringBuffer.append(couplet + mp.getColumn() + link);
									}else if(value instanceof String){
										String link = "";
										if(StringUtils.isNotBlank(queryType)){
											if("in".equals(queryType)){
												if(!((String) value).trim().startsWith("'")){
													value = "'" + value;
												}
												if(!((String) value).trim().endsWith("'")){
													value = value + "'";
												}
												link = mp.getColumn() + " in ( " + value + " )";
											}else if("like".equals(queryType)){
												link = "regexp_like(" + mp.getColumn() + ", '" + StringEscapeUtils.escapeSql(value.toString()) + "')";
											}else if("=".equals(queryType)){
												link = mp.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
											}
										}else{
											link = mp.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
										}
										
										stringBuffer.append(couplet + link);
									}else{
										String link = "";
										if(StringUtils.isNotBlank(queryType)){
											if("in".equals(queryType)){
												if(!((String) value).trim().startsWith("'")){
													value = "'" + value;
												}
												if(!((String) value).trim().endsWith("'")){
													value = value + "'";
												}
												link = mp.getColumn() + " in ( " + value + " )";
											}else if("like".equals(queryType)){
												link = "regexp_like(" + mp.getColumn() + ", '" + StringEscapeUtils.escapeSql(value.toString()) + "')";
											}else if("=".equals(queryType)){
												link = mp.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
											}
										}else{
											link = mp.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
										}
										
										stringBuffer.append(couplet + link);
									}
									
									i++;
								}
							}
							
							String searchCondition = page.getConditiions() == null ? "" : (String)page.getConditiions().get("searchCondition");
							if(StringUtils.isNotBlank(searchCondition) && fieldsMap != null){
								if(i == 0){
									couplet = " where ";
								}else{
									couplet = " and ";
								}
								stringBuffer.append(couplet + " (");
								
								int countSeach = 0;
								for(ResultMapping mp:mapping){
									if(fieldsMap.get(mp.getProperty()) != null){
										if(countSeach == 0){
											couplet = "";
										}else{
											couplet = " or ";
										}
										String serach = "";
										String value = fieldsMap.get(mp.getProperty());
										if("in".equals(value)){
											serach = mp.getColumn() + " "  + value + " (" + searchCondition + ") ";
										}else if("like".equals(value)){
											serach = "regexp_like(" + mp.getColumn() + ", '" + StringEscapeUtils.escapeSql(searchCondition) + "')";
										}else{
											serach = mp.getColumn() + " " + value + " '" + StringEscapeUtils.escapeSql(searchCondition) + "'";
										}
										
										stringBuffer.append(couplet  + " " + serach);
										
										countSeach++;
									}
								}
								
								i++;
								stringBuffer.append(")");
							}
							//设置排序
							setOrder(entity.newInstance(), resultMap.getResultMappings(), stringBuffer, page);
						}
						
						metaStatementHandler.setValue("delegate.boundSql.sql", stringBuffer.toString());
					}
					/** 分页处理 */
					pagitionProcess(invocation, metaStatementHandler, boundSql, mappedStatement, page);
				}
			}
		}
		//将执行权交给下一个拦截器
		return invocation.proceed();
	}

	/**
	 * 分页处理 <br/>
	 * @param invocation
	 * @param metaStatementHandler
	 * @param boundSql
	 * @param mappedStatement
	 * @param page
	 * @return void
	 * @throws ValidationException
	 * @throws SQLException 
	 */
	private void pagitionProcess(Invocation invocation,
								 MetaObject metaStatementHandler,
								 BoundSql boundSql,
								 MappedStatement mappedStatement,
								 Pager page) throws ValidationException, SQLException {
		/** 如果没有分页信息，则不做分页处理 */
		if (page == null) {
			return;
		}
		// 如果有分页信息，检查分页必要的属性性：开始行索引与结束行索引
		if (!page.isDoneEval()) {
			logger.debug("pageNum=" + page.getPageNum());
			logger.debug("pageSize=" + page.getPageSize());
			throw new ValidationException("请检查分页信息是否指定“pageNum”及“pageSize”属性值！");
		}
		Map<String,Object> params = page.getConditiions();
		String sql = boundSql.getSql();
		//将排序中实体字段转成数据库字段
		if(params != null && params.get("orderMark") != null && params.get("orderField") != null){
			String resultMapName = (String)params.get("resultMapName");
			String orderField = (String)params.get("orderField");
			
			if(StringUtils.isNotBlank(resultMapName)){
				Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
				ResultMap resultMap = configuration.getResultMap(resultMapName);
				List<ResultMapping> mapping = resultMap.getResultMappings();
				boolean flag = true;
				for(ResultMapping m:mapping){
					if(m.getProperty().equals(orderField)){
						sql = sql.replace("[orderField]", m.getColumn());
						flag = false;
						break;
					}
				}
				if(flag){
					sql = sql.replace("[orderField]", orderField);
				}
			}else{
				sql = sql.replace("[orderField]", orderField);
			}
		}
		//转换正则like
		if(sql != null && sql.toUpperCase().contains("REGEXP_LIKE")){
//			Map<String,Object> param = page.getConditiions();
			for(String k : params.keySet()){
				if(params.get(k) instanceof String){
					params.put(k,replaceChars((String)params.get(k)));
				}
			}
		}
		// logger.debug("boundSql.sql= " + sql);
		Connection connection = (Connection) invocation.getArgs()[0];
		/** 设定总记录数  */
		if(page.isAllowAutoTotal()){
			page.setTotal(getPagerTotal(sql, connection, mappedStatement, boundSql));
			page.setTotalPage(page.getTotalPage());//计算总页数
		}
		/** 自动分页  */
		if(page.isAllowAutoPagable()){
			// 自动生成分页sql
			sql = buildPageSqlForOracle(sql, page).toString();
			// 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		}
		//处理工作完成以后，更新目标sql语句
		metaStatementHandler.setValue("delegate.boundSql.sql", sql);
	}

	/**
	 * 查询入参集，如果有分页对象，则返回，没有就返回null <br/>
	 * @param multiParams
	 * @return
	 * @return Pager
	 * @throws ValidationException 
	 */
	private Pager lookupPagerOfMuitiParams(Map multiParams) throws ValidationException {
		Collection<?> multiParamVals = multiParams.values();
		Pager page=null;
		for (Object param : multiParamVals) {
			if (param instanceof Pager) {
				page=(Pager)param;
				break;
			}
		}
		//如果mapper.java的方法入参里没有Pager信息，则查看是否指定起始行与截止行，并封装Pager信息
		if (page == null) {
			// 开始行索引
			Object originStartIndex = null;
			if (multiParams.containsKey("START_INDEX")) {
				originStartIndex = multiParams.get("START_INDEX");
			}
			// 结束行索引
			Object originEndIndex = null;
			if (multiParams.containsKey("END_INDEX")) {
				originEndIndex = multiParams.get("END_INDEX");
			}

			if (originStartIndex != null && originEndIndex == null) {
				throw new ValidationException("分页参数‘endIndex’必需指定！");
			} else if (originStartIndex == null && originEndIndex != null) {
				throw new ValidationException("分页参数‘startIndex’必需指定！");
			}
			// 如果设置分页参数，‘开始行索引’与‘结束行索引’两个都要有值
			if (originStartIndex != null && originEndIndex != null) {
				page = new Pager(true, false);
				if (!(originStartIndex instanceof Number) && !(originStartIndex instanceof String)) {
					throw new ValidationException("分页参数‘startIndex’的值类型必需是Integer或者String！");
				} else {
					String originStartIndexStr = originStartIndex.toString();
					if (!originStartIndexStr.matches("\\d+")) {
						throw new ValidationException("分页：“开始行索引”必须是整数！");
					}
					page.setStartIndex(Integer.parseInt(originStartIndexStr));// startIndex: 开始行索引
				}
				if (!(originEndIndex instanceof Number) && !(originEndIndex instanceof String)) {
					throw new ValidationException("分页参数‘endIndex’的值类型必需是Integer或者String！");
				} else {
					String originEndIndexStr = originEndIndex.toString();
					if (!originEndIndexStr.matches("\\d+")) {
						throw new ValidationException("分页：“结束行索引”必须是整数！");
					}
					page.setEndIndex(Integer.parseInt(originEndIndexStr));// endIndex: 结束行索引
				}
				page.setDoneEval(Boolean.TRUE);// 已经计算出“开始行索引”和“结束行索引”
			}
		}
		
		return page;
	}

	/**
	 * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用者就可用通过 分页参数 <code>PageParameter</code>获得相关信息。
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 * @throws SQLException
	 */
	private int getPagerTotal(String sql, Connection connection, MappedStatement mappedStatement,
		BoundSql boundSql) throws SQLException {
		//总记录数
		String countSql = "SELECT COUNT(*) FROM (" + sql + ") T_";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
			setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
			rs = countStmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);//总记录数量
				logger.debug("查询总记录数量: " + count);
			}
		}finally {
			try {
				if(rs != null)rs.close();
				if(countStmt != null)countStmt.close();
			} catch (SQLException e) {
				logger.error("关闭数据库连接资源时错误提示(ResultSet、PreparedStatement)", e);
			}
		}
		return count;
	}

	/**
	 * 对SQL参数(?)设值
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
		Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	/**
	 * 参考hibernate的实现完成oracle的分页
	 * @param sql
	 * @param page
	 * @return String
	 */
	public StringBuilder buildPageSqlForOracle(String sql, Pager page) {
		StringBuilder pageSql = new StringBuilder(100);
		Integer startIndex = page.getStartIndex();// 开始行索引
		Integer endIndex = page.getEndIndex();// 结束行索引
		Assert.notNull(startIndex, "必需指定‘开始行索引’！");
		Assert.notNull(endIndex, "必需指定‘结束行索引’！");

		switch(page.getPaginationMode()){
		case SystemConst.PaginationMode.MODE_1:
			pageSql.append("SELECT TTT_.* FROM ( SELECT TT_.*, ROWNUM RN FROM(");
			pageSql.append(sql);
			pageSql.append(") TT_ WHERE ROWNUM <= ").append(endIndex)
			   .append(") TTT_ WHERE RN >= ").append(startIndex);
			break;
		default:
			pageSql.append("SELECT TTT_.* FROM ( SELECT TT_.*, ROWNUM RN FROM(");
			pageSql.append(sql);
			pageSql.append(") TT_ ) TTT_ WHERE RN >= ").append(startIndex);
			pageSql.append(" AND RN <= ").append(endIndex);
			break;
		}
		
		return pageSql;
	}
	
	/**
	 * 设置排序结果
	 * @param	parameter		参数
	 * @param	resultMapping	映射关系
	 * @param	stringBuffer	sql
	 * */
	private void setOrder(Object parameter, List<ResultMapping> resultMapping, StringBuffer stringBuffer, Pager page){
		if(parameter != null && resultMapping != null && !resultMapping.isEmpty()){
			String fieldValue = classOrderField(parameter, resultMapping, page);
			if(fieldValue != null && !"".equals(fieldValue)){
				stringBuffer.append(fieldValue + " " + classOrderType(parameter, page));
			}
			
		}
	}
	
	/**
	 * 设置排序方式
	 * @param	parameter	参数
	 * */
	private String classOrderType(Object parameter, Pager page){
		if(page != null && page.getConditiions() != null && page.getConditiions().get("orderMark") != null && page.getConditiions().get("orderField") != null){
			return page.getConditiions().get("orderMark").toString();
		}
		if(parameter != null){
			BaseSql bs = parameter.getClass().getAnnotation(BaseSql.class);
			if(bs != null){
				return AnnotationUtils.getValue(bs, "type") == null ? "DESC" : AnnotationUtils.getValue(bs, "type").toString();
			}
		}
		
		return "DESC";
	}
	
	/**
	 * 设置排序字段
	 * @param	parameter		参数
	 * @param	resultMapping	映射关系
	 * @return
	 * */
	private String classOrderField(Object parameter, List<ResultMapping> resultMapping, Pager page){
		StringBuffer stringBuffer = new StringBuffer();
		Map<String, String> rmMap = new HashMap<String, String>();
		for(ResultMapping rm:resultMapping){
			rmMap.put(rm.getProperty(), rm.getColumn());
		}
		if(page != null && page.getConditiions() != null && page.getConditiions().get("orderMark") != null && page.getConditiions().get("orderField") != null){
			stringBuffer.append(" ORDER BY ");
			stringBuffer.append(rmMap.get(page.getConditiions().get("orderField")));
			return stringBuffer.toString();
		}
		List<Map> orderList = new ArrayList<Map>();
		Field[] fields = parameter.getClass().getDeclaredFields();
		for(Field f:fields){
			OrderField of = f.getAnnotation(OrderField.class);
			if(of != null){
				Integer order = (Integer)AnnotationUtils.getValue(of, "order");
				String spare = (String)AnnotationUtils.getValue(of, "spare");
				
				if(rmMap.get(f.getName()) != null){
					Map<String, Object> orderMap = new HashMap<String, Object>();
					
					orderMap.put("name", rmMap.get(f.getName()));
					orderMap.put("order", order);
					orderMap.put("spare", spare);
					
					orderList.add(orderMap);
				}
			}
		}
		
		if(orderList != null && !orderList.isEmpty()){
			stringBuffer.append(" ORDER BY ");
			Collections.sort(orderList, new OrderComparator());
			int count = 1;
			for(Map m:orderList){
				if(m.get("spare") != null && !"".equals(m.get("spare")) && rmMap.get(m.get("spare")) != null && !"".equals(rmMap.get(m.get("spare")))){
						stringBuffer.append(" NVL(" + m.get("name") + ", " 
					+ rmMap.get(m.get("spare")) + ") " + (count == orderList.size() ? "" : ", "));
				}else{
					stringBuffer.append(m.get("name") + (count == orderList.size() ? "" : ", "));
				}
				count++;
			}
		}else{
			if(rmMap.get("createtime") != null && rmMap.get("lastmodify") != null){
				stringBuffer.append(" ORDER BY ");
				stringBuffer.append(" NVL(" + rmMap.get("lastmodify") + ", " + rmMap.get("createtime") + ") ");
			}else if(rmMap.get("createtime") != null){
				stringBuffer.append(" ORDER BY ");
				stringBuffer.append(rmMap.get("createtime"));
			}else if(rmMap.get("lastmodify") != null){
				stringBuffer.append(" ORDER BY ");
				stringBuffer.append(rmMap.get("lastmodify"));
			}
		}
		
		return stringBuffer.toString();
	}
	
	private String replaceChars(String str){
		String s = "";
		char [] c = str.toCharArray();
		for (char d : c) {
			if(d == '^' || d == '$' || d == '.' || d == '?' || d == '+' || d == 92 || 
				d == '*' || d == '|' || d == '(' || d == ')' || d == '[' || d == ']'){
				s += "\\"+d;
				continue;
			}
			s += d;
		}
		return  s;
	}

	@Override
	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {}
}