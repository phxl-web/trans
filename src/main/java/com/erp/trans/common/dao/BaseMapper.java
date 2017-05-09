package com.erp.trans.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.erp.trans.common.entity.Pager;

/**
 * 公共mapper
 * 提供：单主键查询、多主键查询、列表查询、分页查询、新增、修改、修改（覆盖）、单主键删除、多主键删除
 * mapper类可继承此类使用
 * date:2016.01.27
 *
 * @version	1.0
 * */
public interface BaseMapper {
	
	/**
	 * 单主键查询
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		主键ID
	 * @return	T				泛型类型
	 * */
	<T> T find(Class<T> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") Object id);
	
	/**
	 * 多主键查询
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		实体
	 * @return	T				泛型类型
	 * */
	<T> T findByIds(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") T t);
	
	/**
	 * 列表查询
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		实体
	 * @return	List<T>			泛型类型列表
	 * */
	<T> List<T> searchList(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") T t);
	
	/**
	 * 分页查询
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		pager
	 * @return	List<T>			泛型类型列表
	 * */
	<T> List<T> paginateList(Class<T> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") Pager parameter);
	
	/**
	 * 新增
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		实体
	 * */
	<T> int insertInfo(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") T t);
	
	/**
	 * 修改（只修改参数对应数据）
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		实体
	 * */
	<T> int updateInfo(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") T t);
	
	/**
	 * 修改（修改所有数据）
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		实体
	 * */
	<T> int updateInfoCover(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") T t);
	
	/**
	 * 单主键删除
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		主键ID
	 * */
	<T> int deleteInfoById(Class<T> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") Object id);
	
	/**
	 * 多主键删除
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		实体
	 * */
	<T> int deleteInfoByIds(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") T t);
	
	/**
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		实体
	 * */
	<T> int deleteInfo(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") T t);
	
	/**
	 * 获取某个字段最大值
	 * @param	tableName		表名
	 * @param	resultMapName	查询配置文件中namespace + resultMap-id
	 * @param	parameter		字段名	
	 * */
	<T> Long funcMax(Class<?> clazz, @Param("tableName") String tableName, @Param("resultMapName") String resultMapName, @Param("parameter") String field);
}
