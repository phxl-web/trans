package com.erp.trans.common.service.impl;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.erp.trans.common.annotation.BaseSql;
import com.erp.trans.common.dao.BaseMapper;
import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.service.IBaseService;


/**
 * 公共Service服务类
 * 提供：单主键查询、多主键查询、列表查询、分页查询、新增、修改、修改（覆盖）、单主键删除、多主键删除
 * service实现类可继承此类使用，不支持多表查询，不支持单表全表查询
 * 使用此方法还需要在上送实体类中增加相应注解@BaseSql(tableName="",resultName="")(必输)
 * 所有方法都会返回null或0 调用时需要做相应处理
 * date:2016.01.27
 *
 * @version	1.0
 * */
@Component
public class BaseService implements IBaseService {
	
	@Resource(name = "baseMapper")
	private BaseMapper baseMapper;

	/**
	 * 单主键查询
	 * @param	clazz	实体类型
	 * @param	id		主键ID
	 * @return	T		实体类型
	 * */
	public <T> T find(Class<T> clazz, Object id) {
		if(clazz == null || id == null){
			return null;
		}
		
		return baseMapper.find(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), id);
	}

	/**
	 * 多主键查询'
	 * @param	t	实体
	 * @return	T	实体类型
	 * */
	public <T> T findByIds(T t) {
		if(t == null){
			return null;
		}
		
		Class<?> clazz = t.getClass();
		return baseMapper.findByIds(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), t);
	}
	
	/**
	 * 多条件查询实体,如果结果超过一条会抛错
	 * @param	t	实体
	 * @return	T	实体类型
	 * @exception	TooManyResultsException
	 * */
	public <T> T searchEntity(T t){

		List<T> list = this.<T>searchList(t);
		
		if(list.size() == 1){
			return list.get(0);
		}else if(list.size() > 1){
			throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
		} else {
			return null;
		}
	}

	/**
	 * 列表查询
	 * @param	t		实体
	 * @return	List<T>	实体列表
	 * */
	public <T> List<T> searchList(T t) {
		if(t == null){
			return null;
		}
		
		Class<?> clazz = t.getClass();
		return baseMapper.searchList(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), t);
	}

	/**
	 * 分页查询
	 * @param	clazz		实体类型
	 * @param	parameter	pager实体
	 * @return	List<T>		实体列表
	 * */
	public <T> List<T> paginateList(Class<T> clazz, Pager parameter) {
		if(clazz == null || parameter == null){
			return null;
		}
		return baseMapper.paginateList(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), parameter);
	}

	/**
	 * 新增
	 * @param	t	实体
	 * @return
	 * */
	public <T> int insertInfo(T t) {
		if(t == null){
			return 0;
		}
		
		Class<?> clazz = t.getClass();
		return baseMapper.insertInfo(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), t);
	}

	/**
	 * 修改（只修改参数对应数据）
	 * @param	t	实体
	 * @return
	 * */
	public <T> int updateInfo(T t) {
		if(t == null){
			return 0;
		}
		
		Class<?> clazz = t.getClass();
		return baseMapper.updateInfo(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), t);
	}
	
	/**
	 * 修改（修改所有数据）
	 * @param	t	实体
	 * @return
	 * */
	public <T> int updateInfoCover(T t){
		if(t == null){
			return 0;
		}
		
		Class<?> clazz = t.getClass();
		return baseMapper.updateInfoCover(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), t);
	}
	
	/**
	 * 单主键删除
	 * @param	clazz	实体类型
	 * @param	id		主键ID
	 * */
	public <T> int deleteInfoById(Class<T> clazz, Object id){
		if(clazz == null || id == null){
			return 0;
		}
		
		return baseMapper.deleteInfoById(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), id);
	}

	/**
	 * 多主键删除
	 * @param	t	实体
	 * @return
	 * */
	public <T> int deleteInfoByIds(T t) {
		if(t == null){
			return 0;
		}
		
		Class<?> clazz = t.getClass();
		return baseMapper.deleteInfoByIds(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), t);
	}

	/**
	 * 多条件删除(可能会删除多条记录)
	 * @param	t	实体
	 * @return
	 * */
	public <T> int deleteInfo(T t) {
		if(t == null){
			return 0;
		}
		
		Class<?> clazz = t.getClass();
		return baseMapper.deleteInfo(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), t);
	}
	
	/**
	 * 获取某个字段最大值
	 * @param	clazz	实体类型
	 * @param	field	字段名
	 * @return
	 * */
	public <T> Long funcMax(Class<T> clazz, String field){
		if(clazz == null || StringUtils.isBlank(field)){
			return 0L;
		}
		
		return baseMapper.funcMax(clazz, (String)getAnnotation(clazz, BaseSql.class, "tableName"), (String)getAnnotation(clazz, BaseSql.class, "resultName"), field);
	}
	
	/**
	 * 获取某个注解中的属性
	 * @param	clazz				实体
	 * @param	clazzAnnotation		注解实体
	 * @param	name				注解属性
	 * @return
	 * */
	protected Object getAnnotation(Class<?> clazz, Class<? extends Annotation> clazzAnnotation, String name){
		if(clazz == null || clazzAnnotation == null || name == null){
			return null;
		}
		if(clazz.getAnnotation(clazzAnnotation) == null){
			throw new NullPointerException("[缺少注解] "+ name + " " + clazz.getName());
		}
		
		return AnnotationUtils.getValue(clazz.getAnnotation(clazzAnnotation), name);
	}
	
}
