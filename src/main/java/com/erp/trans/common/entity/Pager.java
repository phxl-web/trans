/** 
 * Project Name:ysynet 
 * File Name:Pager.java 
 * Package Name:com.phxl.ysynet.common.entity 
 * Date:2015年9月24日下午1:03:39 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
*/  
  
package com.erp.trans.common.entity;  

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.erp.trans.common.constant.SystemConst;

/**
 * 分页信息实体Bean <br> 
 * @date	2015年9月24日 下午1:03:49 <br> 
 * @author	黄文君 
 * @version	1.0 
 * @since	JDK 1.6
 */
public class Pager<E> implements Serializable {
	
	/**总记录数*/
	@JsonProperty("records")
	private Integer total=0;
	
	/**总页数*/
	@JsonProperty("total")
	private Integer totalPage;
	
	/**每页记录数量（默认每页10条）*/
	@JsonIgnore
	private Integer pageSize = 10;
	
	/**当前页码（从1开始）*/
	@JsonIgnore
	private Integer pageNum = 1;
	
	/**查询结果集(列表)*/
	private List<E> rows;
	
	@JsonIgnore
	private Integer startIndex;//开始行号
	
	@JsonIgnore
	private Integer endIndex;//结束行号
	
	@JsonIgnore
	private boolean doneEval=Boolean.FALSE;//是否已经计算过分页行索引：startIndex/endIndex
	
	@JsonIgnore
	private boolean allowAutoPagable=Boolean.TRUE;//是否需要mybatis自动分页处理:默认自动分页，如果不要自动分页，用Pager(false)实例化！
	
	@JsonIgnore
	private boolean allowAutoTotal=Boolean.TRUE;//是否需要自动统计总记录数量，默认允许
	
	@JsonIgnore
	private int paginationMode=SystemConst.PaginationMode.DEFAULT_0;//分页实现模式，默认0
	
	/**查询参数*/
	@JsonIgnore
	private Map<String, Object> conditiions;
	
	/**动态拼接的字段名数组*/
	private String[] fieldName;
	
    public Pager() {
		super();
		this.eval();
	}
	
	public Pager(boolean allowAutoPagable) {
		this();
		this.allowAutoPagable = allowAutoPagable;
		if(!allowAutoPagable){
			this.allowAutoTotal=Boolean.FALSE;
		}
	}
	
	public Pager(boolean allowAutoPagable, boolean allowAutoTotal) {
		this(allowAutoPagable);
		this.allowAutoTotal = allowAutoTotal;
	}

	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
		this.eval();
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		if(pageSize==null || pageSize<=0){
			pageSize = 10;//每页记录数默认值
		}
		this.pageSize = pageSize;
		this.eval();
	}
	
	/**
	 * 设置每页记录数量（允许指定默认值）
	 * @author	黄文君
	 * @date	2017年2月8日 下午3:47:36
	 * @param	pageSize
	 * @param	defaultPageSize
	 * @return	void
	 */
	public void setPageSize(Integer pageSize, Integer defaultPageSize) {
		if(pageSize==null || pageSize<=0){
			pageSize = defaultPageSize==null ? 10 : defaultPageSize;//每页记录数默认值
		}
		this.pageSize = pageSize;
		this.eval();
	}
	
	public Integer getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(Integer pageNum) {
		if(pageNum==null || pageNum<=0){
			pageNum = 1;//页码默认值
		}
		this.pageNum = pageNum;
		this.eval();
	}
	
	public void eval(){
		if(this.pageSize!=null && this.pageNum!=null){
			this.startIndex = (pageNum-1)*pageSize+1;
			this.endIndex = (pageNum-1)*pageSize + pageSize;
			this.doneEval=Boolean.TRUE;//已计算开始行索引与结束行索引
		}
	}
	public List<E> getRows() {
		return rows;
	}
	
	public void setRows(List<E> rows) {
		this.rows = rows;
	}
	
	public Integer getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	
	public Integer getEndIndex() {
		return endIndex;
	}
	
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	
	public boolean isDoneEval() {
		return doneEval;
	}

	public void setDoneEval(boolean doneEval) {
		this.doneEval = doneEval;
	}

	public Map<String, Object> getConditiions() {
		return conditiions;
	}
	
	public void setConditiions(Map<String, Object> conditiions) {
		this.conditiions = conditiions;
	}
	
	public boolean isAllowAutoPagable() {
		return allowAutoPagable;
	}

	public void setAllowAutoPagable(boolean allowAutoPagable) {
		this.allowAutoPagable = allowAutoPagable;
	}

	/**
	 * 添加查询的过滤参数 <br>
	 * @param	paramName
	 * @param	paramValue
	 * @return	void
	 */
	public void addQueryParam(String paramName, Object paramValue){
		/*if(paramValue==null || (paramValue instanceof CharSequence && "".equals(paramValue.toString().trim()))){
			return;
		}*/
		if(this.conditiions==null){
			this.conditiions = new HashMap<String, Object>();
		}
		conditiions.put(paramName, paramValue);
	}
	
	/**
	 * 获取查询参数  <br>
	 * @author	黄文君
	 * @date	2016年4月20日 下午6:03:13 <br>
	 * @param	paramName
	 * @return	Object
	 */
	public Object getQueryParam(String paramName) {
		if(this.conditiions!=null) {
			return this.conditiions.get(paramName);
		}
		return null;
	}
	
	public String[] getFieldName() {
        return fieldName;
    }

    public void setFieldName(String[] fieldName) {
        this.fieldName = fieldName;
    }

	public boolean isAllowAutoTotal() {
		return allowAutoTotal;
	}

	public void setAllowAutoTotal(boolean allowAutoTotal) {
		this.allowAutoTotal = allowAutoTotal;
	}

	public int getPaginationMode() {
		return paginationMode;
	}

	public void setPaginationMode(int paginationMode) {
		this.paginationMode = paginationMode;
	}

	public Integer getTotalPage() {
		if(totalPage==null){
			if(total!=null&&pageSize!=null){
				totalPage=(this.total%this.pageSize==0)?this.total/this.pageSize:this.total/this.pageSize+1;
			}
		}
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}