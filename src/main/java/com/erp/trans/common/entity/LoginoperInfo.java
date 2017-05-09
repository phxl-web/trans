package com.erp.trans.common.entity;

import java.util.Date;

/**
 * 系统操作类
 *
 *
 */
public class LoginoperInfo {
	
	private String id;
	private String reqMethod;
	private String reqUrl;
	private String reqParam;
	private String status;
	private Date reqTime;
	private String logId;
	private String reqName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReqMethod() {
		return reqMethod;
	}
	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}
	public String getReqUrl() {
		return reqUrl;
	}
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	public String getReqParam() {
		return reqParam;
	}
	public void setReqParam(String reqParam) {
		this.reqParam = reqParam;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getReqName() {
		return reqName;
	}
	public void setReqName(String reqName) {
		this.reqName = reqName;
	}	
}
