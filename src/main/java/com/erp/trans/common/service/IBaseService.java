package com.erp.trans.common.service;

import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;

import com.erp.trans.common.entity.Pager;;


/**
 * 公共service接口
 * service接口需要继承
 * date:2016.01.27
 *
 * @version	1.0
 * */
public interface IBaseService {

	/**
	 * 单主键查询
	 * @param	clazz	实体类型
	 * @param	id		主键ID
	 * @return	T		实体类型
	 * */
	<T> T find(Class<T> clazz, Object id);
	
	/**
	 * 多主键查询'
	 * @param	t	实体
	 * @return	T	实体类型
	 * */
	<T> T findByIds(T t);
	
	/**
	 * 多条件查询实体,如果结果超过一条会抛错
	 * @param	t	实体
	 * @return	T	实体类型
	 * @exception	TooManyResultsException
	 * */
	<T> T searchEntity(T t);
	
	/**
	 * 列表查询
	 * @param	t		实体
	 * @return	List<T>	实体列表
	 * */
	<T> List<T> searchList(T t);
	
	/**
	 * 分页查询
	 * @param	clazz		实体类型
	 * @param	parameter	pager实体
	 * @return	List<T>		实体列表
	 * */
	<T> List<T> paginateList(Class<T> clazz, Pager parameter);
	
	/**
	 * 新增
	 * @param	t	实体
	 * @return
	 * */
	<T> int insertInfo(T t);
	
	/**
	 * 修改（只修改参数对应数据）
	 * @param	t	实体
	 * @return
	 * */
	<T> int updateInfo(T t);
	
	/**
	 * 修改（修改所有数据）
	 * @param	t	实体
	 * @return
	 * */
	<T> int updateInfoCover(T t);
	
	/**
	 * 单主键删除
	 * @param	clazz	实体类型
	 * @param	id		主键ID
	 * */
	<T> int deleteInfoById(Class<T> clazz, Object id);
	
	/**
	 * 多主键删除
	 * @param	t	实体
	 * @return
	 * */
	<T> int deleteInfoByIds(T t);
	
	/**
	 * 多条件删除(可能会删除多条记录)
	 * @param	t	实体
	 * @return
	 * */
	<T> int deleteInfo(T t);
	
	/**
	 * 获取某个字段最大值
	 * @param	clazz	实体类型
	 * @param	field	字段名
	 * @return
	 * */
	<T> Long funcMax(Class<T> clazz, String field);
}
