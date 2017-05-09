package com.erp.trans.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Processor;

import org.apache.commons.collections.CollectionUtils;

/**
 * 集合处理工具类（内部扩展）
 * @date: 2016年8月30日 下午5:37:02
 *
 * @version 1.0
 * @since JDK 1.6
 */
public class LocalCollectionUtils {
	
	public interface Processer<T>{
		public Object process(T o) throws Exception;
	}

	/**
	 * 列表数据逐页进行处理
	 *
	 * @date: 2016年8月30日 下午5:33:49
	 *
	 * @param	entityList	数据列表
	 * @param	pageSize	每页记录数量
	 * @param	process		数据处理过程
	 * @throws	Exception
	 * @return	void
	 */
	public static <T> void paginationProcess(List<T> entityList, int pageSize, Processer<List<T>> process) throws Exception{
		if(CollectionUtils.isNotEmpty(entityList)) {
			int count=entityList.size();
			int totalPage = (count%pageSize==0?count/pageSize : count/pageSize+1);//总页数
			for(int pageNum=1; pageNum<=totalPage; pageNum++){
				List<T> subList = new ArrayList<T>();
				int start=(pageNum-1)*pageSize;//起
				int end=start+pageSize-1;//止
				for(int i=start; i<=end; i++){
					if(i>=count){break;}
					subList.add(entityList.get(i));
				}
				//数据列表处理
				process.process(subList);
			}
		}
	}
}
