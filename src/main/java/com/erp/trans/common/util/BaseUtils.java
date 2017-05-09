/** 
 * Project Name:HSCM_SYNC 
 * File Name:BaseUtils.java 
 * Package Name:com.phxl.hscm.common.util 
 * Date:2015年7月29日下午3:53:24 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
*/  
  
package com.erp.trans.common.util;  

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.erp.trans.common.exception.PhxlException;
import com.erp.trans.common.exception.YsynetException;

/** 
 * 基本工具方法类
 * @Date:     2015年9月23日 下午3:53:24
 *
 * @version  1.0
 * @since    JDK 1.6      
 */
public class BaseUtils {

	/**
	 * 从异常堆栈中追朔根本异常
	 *
	 * @date: 2016年8月29日 上午10:22:37
	 *
	 * @param e
	 * @return Throwable
	 */
	public static Throwable getRootCause(Exception e){
		Throwable cause = e.getCause();
		if(cause!=null){
			while(cause.getCause()!=null){
				Throwable nextcause = cause.getCause();
				cause = nextcause;
			}
		}else{
			cause = e;
		}
		return cause;
	}
	
	/**
	 * 从异常堆栈中追朔根本原因（消息）
	 *
	 * @date: 2016年8月29日 上午10:23:01
	 *
	 * @param e
	 * @return String
	 */
	public static String getRootCauseMsg(Exception e){
		return getRootCause(e).getMessage();
	}
	
	/**
	 * 从异常堆栈中追朔根本原因（消息）
	 *
	 * @date: 2016年8月29日 上午10:22:16
	 *
	 * @param e
	 * @return String
	 */
	public static String getRootCauseWithMsg(Exception e){
		Throwable cause = getRootCause(e);
		StringBuffer exceptionName = new StringBuffer("[");
		if(cause instanceof YsynetException){
			YsynetException ysynetException = (YsynetException)cause;
			String nestedException = ysynetException.getNestedException();
			exceptionName.append(cause.getClass().getSimpleName());
			if(StringUtils.isNotBlank(nestedException)){
				exceptionName.append("[").append(nestedException).append("]");
			}
		}else if(cause instanceof PhxlException){
			exceptionName.append(cause.getClass().getSimpleName());
		}else{
			exceptionName.append(cause.getClass().getCanonicalName());
		}
		exceptionName.append("]");
		return exceptionName.toString()+cause.getMessage();
	}
	
	/**
	 * 空值指定默认值方法
	 *
	 * @date: 2016年8月7日 下午5:00:52
	 *
	 * @param value
	 * @param defaultValue
	 * @return
	 * @return T
	 */
	public static <T> T nvl(T value, T defaultValue){
		Assert.notNull(defaultValue);
		return value==null ? defaultValue : value;
	}
	
	/**
     * 排序方法
     *
     * @param strArray
     * @return
     */
    public static String sort(String[] strArray) {
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }
}
  