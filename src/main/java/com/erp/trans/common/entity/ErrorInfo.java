package com.erp.trans.common.entity;

import java.util.Date;

/**
 * 异常信息类
 * @author Administrator
 *
 */
public class ErrorInfo {
	
	private String id;
	private String errLoca;
	private String errInfo;
	private Date errTime;
	private String operaId;
	private String logId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getErrInfo() {
		return errInfo;
	}
	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}
	public Date getErrTime() {
		return errTime;
	}
	public void setErrTime(Date errTime) {
		this.errTime = errTime;
	}
	public String getOperaId() {
		return operaId;
	}
	public void setOperaId(String operaId) {
		this.operaId = operaId;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getErrLoca() {
		return errLoca;
	}
	public void setErrLoca(String errLoca) {
		this.errLoca = errLoca;
	}
}
