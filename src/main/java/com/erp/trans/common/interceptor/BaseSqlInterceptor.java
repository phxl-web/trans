package com.erp.trans.common.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
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
import org.apache.ibatis.session.Configuration;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.annotation.AnnotationUtils;

import com.erp.trans.common.annotation.BaseSql;
import com.erp.trans.common.annotation.OrderField;
import com.erp.trans.common.annotation.QueryType;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.CollectionsUtils;
import com.erp.trans.common.util.DateUtils;
import com.erp.trans.common.util.OrderComparator;



@Intercepts({@Signature(type=StatementHandler.class, method = "prepare", args={Connection.class}),
		@Signature(method="handleResultSets", type=ResultSetHandler.class, args={Statement.class})})
public class BaseSqlInterceptor implements Interceptor {
	private static final Log logger	= LogFactory.getLog(BaseSqlInterceptor.class);
	public static ThreadLocal<ResultMap> localResultMap = new ThreadLocal<ResultMap>();
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final String baseNamespace = "com.erp.trans.common.dao.BaseMapper";
	private static String allowSqlId = BaseSqlInterceptor.baseNamespace + "[^\\s]*";//需要处理的sql
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//查询时间格式
	
	public Object intercept(Invocation invocation) throws Throwable {
		//上送请求拦截
		if(invocation.getTarget() instanceof StatementHandler){
			StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
			MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
			while (metaStatementHandler.hasGetter("h")) {
				Object object = metaStatementHandler.getValue("h");
				metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			}
			//分离最后一个代理对象的目标类
			while (metaStatementHandler.hasGetter("target")) {
				Object object = metaStatementHandler.getValue("target");
				metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			}
			Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			String maperMethodId = mappedStatement.getId();

			//只处理基础数据类型
			if(maperMethodId.matches(allowSqlId)){
				BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
				StringBuffer stringBuffer = new StringBuffer(boundSql.getSql());
				Map<String, Object> parameterObject = (Map<String, Object>)boundSql.getParameterObject();
				//命名空间
				String resultMapName = (String)parameterObject.get("resultMapName");
				//参数
				Object parameter = parameterObject.get("parameter");
				if(parameter == null){
					throw new ValidationException("上送参数parameter: null");
				}
				ResultMap resultMap = configuration.getResultMap(resultMapName);
				
				if(maperMethodId.equals(baseNamespace + ".find") || maperMethodId.equals(baseNamespace + ".findByIds")){//查询-返回实体
					//获取全部主键
					List<ResultMapping> resultMapping = resultMap.getIdResultMappings();
					if(resultMapping != null && !resultMapping.isEmpty()){
						if(resultMapping.size() == 1){
							if(!(parameter instanceof String || parameter instanceof Number)){
								throw new ValidationException("单主键查询，上送参数有误! " + parameter.getClass().getName());
							}
							ResultMapping rm = resultMapping.get(0);
							stringBuffer.append(" where " + rm.getColumn() + " = " + " '" + parameter + "' ");
						}else{
							String couplet = "";
							Map<String, Object> paramMap = classMethodsData(parameter);
							for(int i=0;i<resultMapping.size();i++){
								if(i == 0){
									couplet = " where ";
								}else{
									couplet = " and ";
								}
								ResultMapping rm = resultMapping.get(i);
								Object value = (Object)paramMap.get(rm.getProperty());
								if(value == null){
									throw new ValidationException("多主键查询，上送参数 " + rm.getProperty() + ": null");
								}
								
								stringBuffer.append(couplet + rm.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ");
							}
						}
					}
					BaseSqlInterceptor.localResultMap.set(resultMap);
				}else if(maperMethodId.equals(baseNamespace + ".searchList")){//查询-返回list<Class<?>>
					Map classData = classMethodsData(parameter);
					Map<String, String> fielfsData = classFieldsData(parameter);
					selectSqlSplice(stringBuffer, resultMap.getResultMappings(), classData, fielfsData);
					setOrder(parameter, resultMap.getResultMappings(), stringBuffer);
					BaseSqlInterceptor.localResultMap.set(resultMap);
				}else if(maperMethodId.equals(baseNamespace + ".insertInfo")){//新增
					Map classData = classMethodsData(parameter);
					insertSqlSplice(stringBuffer, resultMap.getResultMappings(), classData);
				}else if(maperMethodId.equals(baseNamespace + ".updateInfo")){//修改
					Map classData = classMethodsData(parameter);
					updateSqlSplice(stringBuffer, resultMap.getResultMappings(), resultMap.getIdResultMappings(), classData, "updateInfo");
				}else if(maperMethodId.equals(baseNamespace + ".updateInfoCover")){//覆盖修改
					Map classData = classMethodsData(parameter);
					updateSqlSplice(stringBuffer, resultMap.getResultMappings(), resultMap.getIdResultMappings(), classData, "updateInfoCover");
				}else if(maperMethodId.equals(baseNamespace + ".deleteInfoById") || maperMethodId.equals(baseNamespace + ".deleteInfoByIds")){//删除
					//获取全部主键
					List<ResultMapping> resultMapping = resultMap.getIdResultMappings();
					if(resultMapping != null && !resultMapping.isEmpty()){
						if(resultMapping.size() == 1){
							if(!(parameter instanceof String || parameter instanceof Number)){
								throw new ValidationException("单主键删除，上送参数有误! " + parameter.getClass().getName());
							}
							ResultMapping rm = resultMapping.get(0);
							
							//重新设置sql
							String sql = stringBuffer.toString().replace("keys", rm.getColumn() + " = " + " '" + parameter + "' ");
							stringBuffer.delete(0, stringBuffer.length()).append(sql);
						}else{
							String couplet = "";
							Map<String, Object> paramMap = classMethodsData(parameter);
							StringBuffer keys = new StringBuffer();
							for(int i=0;i<resultMapping.size();i++){
								if(i == 0){
									couplet = "";
								}else{
									couplet = " and ";
								}
								ResultMapping rm = resultMapping.get(i);
								Object value = (Object)paramMap.get(rm.getProperty());
								if(value == null){
									throw new ValidationException("多主键删除，上送参数 " + rm.getProperty() + ": null");
								}
								
								keys.append(couplet + rm.getColumn() + " = " + " '" + value + "' ");
							}
							
							//重新设置sql
							String sql = stringBuffer.toString().replace("keys", keys);
							stringBuffer.delete(0, stringBuffer.length()).append(sql);
						}
					}
				}else if(maperMethodId.equals(baseNamespace + ".deleteInfo")){//多条件删除
					Map classData = classMethodsData(parameter);
					Map<String, String> fielfsData = classFieldsData(parameter);
					deleteSqlSplice(stringBuffer, resultMap.getResultMappings(), classData, fielfsData);
				}else if(maperMethodId.equals(baseNamespace + ".funcMax")){
					String field = (String)parameter;
					for(ResultMapping rm:resultMap.getResultMappings()){
						if(rm.getProperty().equals(field)){
							String sql = stringBuffer.toString().replace("field", rm.getColumn());
							stringBuffer.delete(0, stringBuffer.length()).append(sql);
							break;
						}
					}
				}

				metaStatementHandler.setValue("delegate.boundSql.sql", stringBuffer.toString());//重写sql语句
			}
			
			return invocation.proceed();
		}else if(invocation.getTarget() instanceof ResultSetHandler){//返回请求拦截
			ResultMap resultMap = BaseSqlInterceptor.localResultMap.get();
			Object result = invocation.proceed();
			if(resultMap == null){
				return result;
			}
			if(result instanceof List){
				List list = (List)result;
				List<Object> resultList = new ArrayList<Object>();
				for(int i=0;i<list.size();i++){
					Object obj = CollectionsUtils.MapToBean((Class<T>)resultMap.getType(), columnToProperty(resultMap, (Map)list.get(i)));
					resultList.add(obj);
				}
				localResultMap.remove();
				return resultList;
			}
			
			localResultMap.remove();
			return result; 
		}
		
		return invocation.proceed();
	}
	
	/**
	 * 将上送实体转成Map
	 * @param	parameter	接口上送参数
	 * @return
	 * */
	private Map<String, Object> classMethodsData(Object parameter) throws Exception{
		Map<String, Object> fieldValue = new HashMap<String, Object>();
		Method[] methods = parameter.getClass().getMethods();
		for(Method method:methods){
			if(method.getName().startsWith("get")){
				Object value = method.invoke(parameter, null);
				if(value != null){
					String first = method.getName().substring(method.getName().indexOf("get") + 3).substring(0, 1);
					fieldValue.put(method.getName().substring(method.getName().indexOf("get") + 3).replaceFirst(first, first.toLowerCase()), value);
				}
			}
		}
		
		return fieldValue;
	}
	
	/**
	 * 获取上送实体中需要指定查询方式的字段
	 * @param	parameter	接口上送参数
	 * @return
	 * */
	private Map<String, String> classFieldsData(Object parameter){
		Map<String, String> fieldValue = null;
		Field[] fields = parameter.getClass().getDeclaredFields();
		for(Field f:fields){
			QueryType ct = f.getAnnotation(QueryType.class);
			if(ct != null){
				if(fieldValue == null){
					fieldValue = new HashMap<String, String>();
				}
				fieldValue.put(f.getName(), (String)AnnotationUtils.getValue(ct, "value"));
			}
		}
		
		return fieldValue;
	}
	
	/**
	 * 设置排序结果
	 * @param	parameter		参数
	 * @param	resultMapping	映射关系
	 * @param	stringBuffer	sql
	 * */
	private void setOrder(Object parameter, List<ResultMapping> resultMapping, StringBuffer stringBuffer){
		if(parameter != null && resultMapping != null && !resultMapping.isEmpty()){
			String fieldValue = classOrderField(parameter, resultMapping);
			if(fieldValue != null && !"".equals(fieldValue)){
				stringBuffer.append(fieldValue + " " + classOrderType(parameter));
			}
			
		}
	}
	
	/**
	 * 设置排序方式
	 * @param	parameter	参数
	 * */
	private String classOrderType(Object parameter){
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
	private String classOrderField(Object parameter, List<ResultMapping> resultMapping){
		StringBuffer stringBuffer = new StringBuffer();
		Map<String, String> rmMap = new HashMap<String, String>();
		for(ResultMapping rm:resultMapping){
			rmMap.put(rm.getProperty(), rm.getColumn());
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
	
	/**
	 * 单表查询构造sql语句方法
	 * @param	stringBuffer	sql(包含原sql)
	 * @param	resultMapping	resultMap
	 * @param	values			上送参数
	 * */
	private void selectSqlSplice(StringBuffer stringBuffer, List<ResultMapping> resultMapping, Map values, Map<String, String> fielfsData){
		if(resultMapping != null && !resultMapping.isEmpty()){
			if(resultMapping.size() == 1){
				ResultMapping rm = resultMapping.get(0);
				if(values.get(rm.getProperty()) != null){
					Object value = values.get(rm.getProperty());
					stringBuffer.append(" where " + rm.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ");
				}
			}else{
				String couplet = "";
				int i = 0;
				for(ResultMapping rm:resultMapping){
					if(values.get(rm.getProperty()) != null){
						if(i == 0){
							couplet = " where ";
						}else{
							couplet = " and ";
						}
						Object value = values.get(rm.getProperty());
						String queryType = "";
						if(fielfsData != null && !fielfsData.isEmpty()){
							queryType = fielfsData.get(rm.getProperty());
						}
						if(value instanceof Date){
							Date date = (Date)value;
							String dateString = sdf.format(date);
							Calendar c = Calendar.getInstance();
							c.setTime(date);
							c.add(Calendar.DAY_OF_MONTH, 1);
							String tomDateString = sdf.format(c.getTime());
							
							stringBuffer.append(couplet + rm.getColumn() + ">= str_to_date('" + dateString + "', '%Y-%m-%d') AND "
													+ rm.getColumn() + "< str_to_date('" + tomDateString + "', '%Y-%m-%d')");
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
							
							stringBuffer.append(couplet + rm.getColumn() + link);
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
									link = rm.getColumn() + " in ( " + value + " )";
								}else if("like".equals(queryType)){
									link = "regexp_like(" + rm.getColumn() + ", '" + StringEscapeUtils.escapeSql(value.toString()) + "')";
								}else if("=".equals(queryType)){
									link = rm.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
								}
							}else{
								link = rm.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
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
									link = rm.getColumn() + " in ( " + value + " )";
								}else if("like".equals(queryType)){
									link = "regexp_like(" + rm.getColumn() + ", '" + StringEscapeUtils.escapeSql(value.toString()) + "')";
								}else if("=".equals(queryType)){
									link = rm.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
								}
							}else{
								link = rm.getColumn() + " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
							}
							
							stringBuffer.append(couplet + link);
						}
						
						i++;
					}
				}
				
			}
		}
	}
	
	/**
	 * 单表新增构造sql语句方法
	 * @param	stringBuffer	sql(包含原sql)
	 * @param	resultMapping	resultMap
	 * @param	values			上送参数
	 * */
	private void insertSqlSplice(StringBuffer stringBuffer, List<ResultMapping> resultMapping, Map values){
		if(resultMapping != null && !resultMapping.isEmpty() && values != null){
			//字段列表
			StringBuffer fields = new StringBuffer();
			//参数列表
			StringBuffer parameter = new StringBuffer();
			int count = 0;
			for(ResultMapping rm:resultMapping){
				if(values.get(rm.getProperty()) != null){
					String sym = ", ";
					if(count == 0){
						sym = "";
					}
					Object value = values.get(rm.getProperty());
					fields.append(sym + rm.getColumn());
					
					if(value instanceof Date){
						String dateString = DateUtils.DateToStr((Date)value, "yyyy-MM-dd HH:mm:ss");
						parameter.append(sym + "str_to_date('" + dateString + "', '%Y-%m-%d %T')");
					}else if(value instanceof Number){
						parameter.append(sym + value);
					}else if(value instanceof String){
						parameter.append(sym + "'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
					}else{
						parameter.append(sym + "'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
					}

					count++;
				}
			}
			//重新设置sql
			String sql = stringBuffer.toString().replace("fields", fields.toString()).replace("parameters", parameter.toString());
			stringBuffer.delete(0, stringBuffer.length()).append(sql);
		}
	}
	
	/**
	 * 单表更新构造sql语句方法
	 * @param	stringBuffer	sql(包含原sql)
	 * @param	resultMapping	resultMap
	 * @param	idResultMapping	resultMap中的主键
	 * @param	values			上送参数
	 * */
	private void updateSqlSplice(StringBuffer stringBuffer, List<ResultMapping> resultMapping, List<ResultMapping> idResultMapping, Map values, String nameFlag) throws ValidationException{
		if(idResultMapping != null && !idResultMapping.isEmpty() && resultMapping != null && !resultMapping.isEmpty() && values != null){
			//更新参数
			StringBuffer fields = new StringBuffer();
			//主键
			StringBuffer keys = new StringBuffer();
			int count = 0;
			
			//封装主键
			for(ResultMapping irm:idResultMapping){
				String sym = " AND ";
				if(count == 0){
					sym = "";
				}
				Object value = values.get(irm.getProperty());
				if(value == null){
					throw new ValidationException("update : 主键值为空 " + irm.getProperty());
				}
				
				if(value instanceof Date){
					String dateString = DateUtils.DateToStr((Date)value, "yyyy-MM-dd HH:mm:ss");
					keys.append(sym + irm.getColumn() + " = str_to_date('" + dateString + "', '%Y-%m-%d %T')");
				}else if(value instanceof Number){
					keys.append(sym + irm.getColumn() + " = " + value);
				}else if(value instanceof String){
					keys.append(sym + irm.getColumn() + " = " + "'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
				}else{
					keys.append(sym + irm.getColumn() + " = " + "'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
				}
				
				count++;
			}
			
			count = 0;
			//封装参数
			if("updateInfo".equals(nameFlag)){//只更新上送参数
				for(ResultMapping rm:resultMapping){
					if(values.get(rm.getProperty()) != null && !"".equals(values.get(rm.getProperty())) && keys.indexOf(rm.getColumn()) == -1){
						String sym = ", ";
						if(count == 0){
							sym = "";
						}
						Object value = values.get(rm.getProperty());
						fields.append(sym + rm.getColumn() + " = ");
						
						if(value instanceof Date){
							String dateString = DateUtils.DateToStr((Date)value, "yyyy-MM-dd HH:mm:ss");
							fields.append("str_to_date('" + dateString + "', '%Y-%m-%d %T')");
						}else if(value instanceof Number){
							fields.append(value);
						}else if(value instanceof String){
							fields.append("'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
						}else{
							fields.append("'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
						}

						count++;
					}
				}
			}else{//更新所有参数
				for(ResultMapping rm:resultMapping){
					String sym = ", ";
					if(count == 0){
						sym = "";
					}
					fields.append(sym + rm.getColumn() + " = ");
					Object value = values.get(rm.getProperty());
					if(value == null){
						fields.append("''");
					}else{
						if(value instanceof Date){
							String dateString = DateUtils.DateToStr((Date)value, "yyyy-MM-dd HH:mm:ss");
							fields.append("str_to_date('" + dateString + "', '%Y-%m-%d %T')");
						}else if(value instanceof Number){
							fields.append(value);
						}else if(value instanceof String){
							fields.append("'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
						}else{
							fields.append("'" + StringEscapeUtils.escapeSql(value.toString()) + "'");
						}
					}
					
					count++;
				}
			}
			
			//重新设置sql
			String sql = stringBuffer.toString().replace("fields", fields.toString()).replace("keys", keys.toString());
			stringBuffer.delete(0, stringBuffer.length()).append(sql);
		}
	}
	
	/**
	 * 单表删除构造sql语句方法
	 * @param	stringBuffer	sql(包含原sql)
	 * @param	resultMapping	resultMap
	 * @param	values			上送参数
	 * @param	fielfsData		
	 * */
	private void deleteSqlSplice(StringBuffer stringBuffer, List<ResultMapping> resultMapping, Map values, Map<String, String> fielfsData){
		if(resultMapping != null && !resultMapping.isEmpty() && values != null){
			String couplet = "";
			int i = 0;
			StringBuffer keys = new StringBuffer();
			for(ResultMapping rm:resultMapping){
				if(values.get(rm.getProperty()) != null){
					if(i == 0){
						couplet = "";
					}else{
						couplet = " and ";
					}
					Object value = values.get(rm.getProperty());
					String queryType = "";
					if(fielfsData != null && !fielfsData.isEmpty()){
						queryType = fielfsData.get(rm.getProperty());
					}
					if(value instanceof Date){
						Date date = (Date)value;
						String dateString = sdf.format(date);
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.add(Calendar.DAY_OF_MONTH, 1);
						String tomDateString = sdf.format(c.getTime());
						
						keys.append(couplet + rm.getColumn() + ">= str_to_date('" + dateString + "', 'yyyy-MM-dd') AND "
												+ rm.getColumn() + "< str_to_date('" + tomDateString + "', '%Y-%m-%d')");
					}else if(value instanceof Number){
						keys.append(couplet + rm.getColumn() + " = " + value);
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
								link = " in ( " + value + " )";
							}else if("=".equals(queryType)){
								link = " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
							}else{
								link = " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
							}
						}else{
							link = " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
						}
						
						keys.append(couplet + rm.getColumn() + link);
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
								link = " in ( " + value + " )";
							}else if("=".equals(queryType)){
								link = " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
							}else{
								link = " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
							}
						}else{
							link = " = " + " '" + StringEscapeUtils.escapeSql(value.toString()) + "' ";
						}
						
						keys.append(couplet + rm.getColumn() + link);
					}
					
					i++;
				}
			}
			
			//重新设置sql
			String sql = stringBuffer.toString().replace("keys", keys);
			stringBuffer.delete(0, stringBuffer.length()).append(sql);
		}
	}
	
	/**
	 * 将Map中key值从数据库字段转为实体字段
	 * @param	resultMap	resultMap
	 * @param	columnMap	数据库字段名
	 * @return	propertyMap	实体字段名
	 * @throws SQLException 
	 * */
	private Map columnToProperty(ResultMap resultMap, Map<String, Object> columnMap) throws SQLException{
		Map<String, Object> propertyMap = new HashMap();
		List<ResultMapping> resultList = resultMap.getResultMappings();
		for(ResultMapping rm:resultList){
			Object value=columnMap.get(rm.getColumn());
			if(value != null){
				if(value instanceof oracle.sql.TIMESTAMP){
					value=new Date(((oracle.sql.TIMESTAMP)value).dateValue().getTime());
				}
				propertyMap.put(rm.getProperty(), value);
			}
		}
		
		return propertyMap;
	}

	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
		if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	public void setProperties(Properties properties) {

	}

}
