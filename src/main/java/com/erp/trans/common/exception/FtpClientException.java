package com.erp.trans.common.exception;

/**
 * FTP客户端请求类异常
 * @date: 2016年7月11日 上午9:54:03
 *
 * @version 1.0
 * @since JDK 1.6
 */
public class FtpClientException extends BaseException {

	public FtpClientException() {
		super();
	}

	public FtpClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public FtpClientException(String message) {
		super(message);
	}

	public FtpClientException(Throwable cause) {
		super(cause);
	}
}
