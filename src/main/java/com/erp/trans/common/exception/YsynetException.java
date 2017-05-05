package com.erp.trans.common.exception;

/**
 * 医商云同步服务器基本异常类
 * @date: 2016年8月29日 下午4:31:57
 * @author 黄文君
 * @version 1.0
 * @since JDK 1.6
 */
public class YsynetException extends PhxlException{
	private final String appName = "YSYNET";
	
	private String nestedException;

	public YsynetException() {
		super();
	}
	
	public YsynetException(String outerException, String message) {
		super(message);
		this.nestedException=outerException;
	}

	public YsynetException(String message, Throwable cause) {
		super(message, cause);
	}

	public YsynetException(String message) {
		super(message);
	}

	public YsynetException(Throwable cause) {
		super(cause);
	}

	public String getAppName() {
		return appName;
	}

	public String getNestedException() {
		return nestedException;
	}

	public void setNestedException(String nestedException) {
		this.nestedException = nestedException;
	}
}
