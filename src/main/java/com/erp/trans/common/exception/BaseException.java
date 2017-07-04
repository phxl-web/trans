package com.erp.trans.common.exception;

/**
 * 基本异常类定义 
 * */
public class BaseException extends Exception{

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
	
	
}
