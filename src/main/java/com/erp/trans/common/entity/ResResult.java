package com.erp.trans.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.erp.trans.common.adapter.CustomDateTimeDeserializer;
import com.erp.trans.common.adapter.CustomDateTimeSerializer;

/**
 * Http请求返回报文模板
 * @author 黄文君 
 * @version 1.0
 * @since 2015-9-23
 * */
public class ResResult implements Serializable {
	/**接口地址*/
	private String interfaceUri;
	/** 请求状态：true成功， false失败 */
	private Boolean status;
	/** 返回结果 */
	private Object result;
	/**异常类型*/
	private String exception;
	/**异常消息*/
	private String msg;
	/**发起请求的时间 */
	private Date timestamp;
	/**耗时长度（单位:毫秒）*/
	private int during;
	
	public ResResult() {
		super();
	}
	
	public ResResult(Boolean status, Date timestamp) {
		super();
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public ResResult(String interfaceUri, Boolean status, Date timestamp) {
		super();
		this.interfaceUri = interfaceUri;
		this.status = status;
		this.timestamp = timestamp;
	}

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getTimestamp() {
		return timestamp;
	}
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public int getDuring() {
		return during;
	}
	public void setDuring(int during) {
		this.during = during;
	}
	/**
	 * 计算请求处理时长  <br/>
	 * @author 黄文君
	 * @date: 2016年6月16日 上午9:29:25
	 *
	 * @return void
	 */
	public void setDuring() {
		this.during = (int)(System.currentTimeMillis()-getTimestamp().getTime());
	}

	public String getInterfaceUri() {
		return interfaceUri;
	}

	public void setInterfaceUri(String interfaceUri) {
		this.interfaceUri = interfaceUri;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
