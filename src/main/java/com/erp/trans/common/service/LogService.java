package com.erp.trans.common.service;

import org.aspectj.lang.JoinPoint;

/**
 * 系统日志服务层接口
 *
 *
 */
public interface LogService {
	
	public void logBefore(JoinPoint point);
	
	public void logArgAndReturn(JoinPoint point, Object returnObj);
	
	public void logThrowing(JoinPoint point,Exception exc);
}
