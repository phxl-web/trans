package com.erp.trans.common.entity;

import java.util.Date;

/**
 * 系统登录信息类
 * @author Administrator
 *
 */
public class LoginLogInfo {
	
	private String id;
	private String userId;
	private String userName;
	private String logIP;
	private String logBowser;
	private String logOS;
	private Date logTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogIP() {
		return logIP;
	}
	public void setLogIP(String logIP) {
		this.logIP = logIP;
	}
	public String getLogBowser() {
		return logBowser;
	}
	public void setLogBowser(String logBowser) {
		this.logBowser = logBowser;
	}
	public String getLogOS() {
		return logOS;
	}
	public void setLogOS(String logOS) {
		this.logOS = logOS;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
}
