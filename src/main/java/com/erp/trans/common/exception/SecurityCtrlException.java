package com.erp.trans.common.exception;

/**
 * [功能说明]: 安全与风险类异常   <br/>
 * @date: 2016年4月26日 上午11:43:01 <br/>
 *
 * @version 1.0
 * @since JDK 1.6
 */
public class SecurityCtrlException  extends BaseException{

	public SecurityCtrlException() {
		super();
	}


	public SecurityCtrlException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityCtrlException(String message) {
		super(message);
	}

	public SecurityCtrlException(Throwable cause) {
		super(cause);
	}
	
}