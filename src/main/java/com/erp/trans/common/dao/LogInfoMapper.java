package com.erp.trans.common.dao;

import com.erp.trans.common.entity.ErrorInfo;
import com.erp.trans.common.entity.LoginLogInfo;
import com.erp.trans.common.entity.LoginoperInfo;



/**
 * 日志DAO接口
 *
 *
 */
public interface LogInfoMapper {
	
	//保存登录信息
	void saveLoginLog(LoginLogInfo logInfo);
	//保存操作信息
	void saveOperaInfo(LoginoperInfo operaInfo);
	//保存异常信息
	void saveErrorInfo(ErrorInfo errInfo);
}
