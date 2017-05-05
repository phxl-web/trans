package com.erp.trans.common.exception;

/**
 * 数据校验异常定义 
 * */
public class ValidationException extends PhxlException {

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

}
