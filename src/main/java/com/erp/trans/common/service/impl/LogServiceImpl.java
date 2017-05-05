package com.erp.trans.common.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.erp.trans.common.annotation.LogInfo;
import com.erp.trans.common.dao.LogInfoMapper;
import com.erp.trans.common.entity.ErrorInfo;
import com.erp.trans.common.entity.LoginLogInfo;
import com.erp.trans.common.entity.LoginoperInfo;
import com.erp.trans.common.service.LogService;
import com.erp.trans.common.util.IdentifieUtil;
import com.erp.trans.common.util.SysContent;
import com.erp.trans.common.util.SystemConfig;

import net.sf.json.JSONObject;


/**
 * 系统操作日志
 * @author Administrator
 *
 */
public class LogServiceImpl implements LogService{
	private final static Logger logInfo = LoggerFactory.getLogger(LogServiceImpl.class);
	
	@Autowired
	private LogInfoMapper logInfoMapper;
	
	//前置方法
	public void logBefore(JoinPoint point) {
		try{
			HttpServletRequest request = SysContent.getRequest();
			HttpServletResponse response = SysContent.getResponse();
			if (request == null || response == null || point == null) return;
			
			//方法名
			String method = point.getSignature().getName();
			Logger logger = LoggerFactory.getLogger(point.getTarget().getClass());
			//获取到这个类上面的方法全名
		     Method meths[] = point.getSignature().getDeclaringType().getMethods();
		     //获取到这个类上面的方法上面的注释
		     LogInfo alogInfo = null;
			 for (Method m : meths) {
					if(m.getName().equals(method)){
						Annotation[] annotations = m.getAnnotations();
					    for(Annotation annotation : annotations){
					    	if(logger.isTraceEnabled()){
					    		logger.trace("正在执行方法上面的注释："+annotation);
					    	}
					        if(annotation.annotationType() == LogInfo.class){
					        	alogInfo = m.getAnnotation(LogInfo.class);
					        }
					    }
					    break;
					}
				}
			if(logger.isTraceEnabled()){
				logger.trace(method+"开始执行");
			}
			String argsString = "";
			for (Object arg : point.getArgs()) {
				if(arg instanceof DefaultMultipartHttpServletRequest){
					request = (HttpServletRequest)arg;					
				}else{
					if(arg != null){
						argsString +=arg.toString()+",";
					}	
				}
			}			
			String targetUrl = request.getRequestURI();
			if(logger.isTraceEnabled()){
				logger.trace("调用URL："+targetUrl);
			}
			String requestParam = request.getParameterMap().isEmpty()?"":JSONObject.fromObject(request.getParameterMap()).toString();
			if(logger.isTraceEnabled()){
				logger.trace("request参数："+requestParam);
				logger.trace("方法传入参数列表......");
				logger.trace(argsString);
			}
		}catch(Exception e){
			logInfo.error(point.toString()+"方法调用前出错："+e.getMessage());
		}
	}

	//后置方法
	public void logArgAndReturn(JoinPoint point, Object returnObj) {
		try {
			HttpServletRequest request = SysContent.getRequest();
			HttpServletResponse response = SysContent.getResponse();
			if (request == null || response == null || point == null) return;
			
			//获得访问目标方法的字符串访问形式
			String p = point.toString();
			//获得类的字符串形式
			String clazz = p.substring(p.indexOf(" ")+1,p.lastIndexOf("."));
			//获得方法的字符串形式
			String s = p.substring(p.indexOf(" ")+1,p.lastIndexOf("("));
			//方法名
			String method = s.substring(s.lastIndexOf(".")+1);
			//能过反射获得方法是否带RequestMapping注解
			Class<?> c = Class.forName(clazz);
			//添加调试代码
			Logger logger = LoggerFactory.getLogger(point.getTarget().getClass());
			if(logger.isTraceEnabled()){
				logger.trace("返回对象："+returnObj);
			}
			
			Method [] methods = c.getDeclaredMethods();
			boolean flag = false;
			LogInfo alogInfo = null;
			for (Method m : methods) {
				if(m.getName().equals(method)){
					Annotation[] annotations = m.getAnnotations();
				    for(Annotation annotation : annotations){
				        if(annotation.annotationType() == LogInfo.class){
				        	alogInfo = m.getAnnotation(LogInfo.class);
				        }
				    }
				    break;
				}
			}
			//没有找到方法或不带RequestMapping注解
			//if(!flag) return ;
			//通过循环代理目标 得到request
			for (Object o : point.getArgs()) {
				if(o instanceof DefaultMultipartHttpServletRequest){
					request = (HttpServletRequest)o;
					break;
				}
			}
			
			String targetUrl = request.getRequestURI();
			//访问登录的,这里创建一个登录日志
			if(targetUrl != null && (targetUrl.contains("/login/userLogin")||
					targetUrl.endsWith("/login/login"))){
				String login_userid = (String)request.getSession().getAttribute("sessionUserid");
				String login_username = (String)request.getSession().getAttribute("sessionUsername");
				if(StringUtils.isNotBlank(login_userid) && 
						"true".equals(SystemConfig.getProperty("accessLogmethod.isvalid"))){//session里有用户ID 说明已经登录了创建一个登录日志 创建访问日志打开时
					String userAgent = request.getHeader("User-Agent");
					LoginLogInfo logInfo = new LoginLogInfo();
					logInfo.setId(IdentifieUtil.getGuId());
					logInfo.setUserId(login_userid);
					logInfo.setUserName(login_username);
					logInfo.setLogIP(request.getRemoteAddr());
					logInfo.setLogBowser(getLogBowser(userAgent));
					logInfo.setLogOS(getLogOS(userAgent));
					logInfo.setLogTime(new Date());
					logInfoMapper.saveLoginLog(logInfo);
					request.getSession().setAttribute("loginLogid", logInfo.getId());
				}	
			}
			method = method.toLowerCase();
			if("true".equals(SystemConfig.getProperty("operateLogmethod.isvalid")) && (method.contains("insert")||method.contains("add")||method.contains("save")||method.contains("create")||method.contains("update")||
					method.contains("delete")||method.contains("remove") || alogInfo!=null)){//满足条件并且日志记录打开
			/*if(true){*/
				String loginLogid = (String)request.getSession().getAttribute("loginLogid");
				//创建一个操作日志
				LoginoperInfo operaInfo = new LoginoperInfo();
				operaInfo.setId(IdentifieUtil.getGuId());
				operaInfo.setReqMethod(s);
				String requestParam = request.getParameterMap().isEmpty()?"":JSONObject.fromObject(request.getParameterMap()).toString();
				if(requestParam.length() > 500){
					operaInfo.setReqParam(requestParam.substring(0,500));
				}else{
					operaInfo.setReqParam(requestParam);
				}
				operaInfo.setReqUrl(targetUrl);
				//operaInfo.setStatus(response.getStatus()+"");
				//operaInfo.setStatus("1");
				operaInfo.setStatus("200");
				operaInfo.setReqTime(new Date());
				operaInfo.setLogId(loginLogid);
				operaInfo.setReqName(alogInfo==null?"":alogInfo.logFName());
				logInfoMapper.saveOperaInfo(operaInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//后置方法出现异常
	public void logThrowing(JoinPoint point,Exception exc) {
		//System.out.println("\n***"+point+"********** error *******************");
		try {
			HttpServletRequest request = SysContent.getRequest();
			HttpServletResponse response = SysContent.getResponse();
			if (request == null || response == null || point == null) return;			
			
			//获得访问目标方法的字符串访问形式
			String p = point.toString();
			//获得类的字符串形式
			String clazz = p.substring(p.indexOf(" ")+1,p.lastIndexOf("."));
			//获得方法的字符串形式
			String s = p.substring(p.indexOf(" ")+1,p.lastIndexOf("("));
			//方法名
			String method = s.substring(s.lastIndexOf(".")+1);
			//能过反射获得方法是否带RequestMapping注解
			Class<?> c = Class.forName(clazz);
			Logger logger = LoggerFactory.getLogger(c);
			Method [] methods = c.getDeclaredMethods();
			boolean flag = false;
			for (Method m : methods) {
				if(m.getName().equals(method)){
					Annotation[] annotations = m.getAnnotations();
				    for(Annotation annotation : annotations){
				        if(annotation.annotationType() == RequestMapping.class){
				        	flag = true;
				        	break;
				        }
				    }
				    break;
				}
			}
			//没有找到方法或不带RequestMapping注解
//			if(!flag) return ;
			
			//对增删改的操作记录日志
			method = method.toLowerCase();
			if(method.contains("insert")||method.contains("add")||method.contains("save")||method.contains("create")||method.contains("update")||
					method.contains("delete")||method.contains("remove")){
			/*if(true){*/
				//通过循环代理目标 得到request
				for (Object o : point.getArgs()) {
					if(o instanceof DefaultMultipartHttpServletRequest){
						request = (HttpServletRequest)o;
						break;
					}
				}
				String operaInfoId = IdentifieUtil.getGuId();
				String loginLogid = (String)request.getSession().getAttribute("loginLogid");
				//创建一个操作日志
				{
					LoginoperInfo operaInfo = new LoginoperInfo();
					operaInfo.setId(operaInfoId);
					operaInfo.setReqMethod(s);
					String requestParam = request.getParameterMap().isEmpty()?"":JSONObject.fromObject(request.getParameterMap()).toString();
					if(requestParam.length() > 500){
						operaInfo.setReqParam(requestParam.substring(0,500));
					}else{
						operaInfo.setReqParam(requestParam);
					}
					operaInfo.setReqUrl(request.getRequestURI());
					//operaInfo.setStatus(response.getStatus()+"");
					//operaInfo.setStatus("0");
					operaInfo.setStatus("500");
					operaInfo.setReqTime(new Date());
					operaInfo.setLogId(loginLogid);
					logInfoMapper.saveOperaInfo(operaInfo);
				}
				logger.error(p+":"+request.getRequestURI()+"\n"+exc);
				//创建一个异常日志H
				{
					ErrorInfo errInfo = new ErrorInfo();
					errInfo.setId(IdentifieUtil.getGuId());
					StackTraceElement [] ste = exc.getStackTrace();
					if(ste != null){
						for(StackTraceElement se:ste){
								errInfo.setErrLoca(se.toString());
								break;
						}
					}
					
					String errorInfo = exc.toString().replace("\r", "").replace("\n", " ");
					if(errorInfo.length() > 500){
						errInfo.setErrInfo(errorInfo.substring(0,500));
					}else{
						errInfo.setErrInfo(errorInfo);
					}
					errInfo.setErrTime(new Date());
					errInfo.setOperaId(operaInfoId);
					errInfo.setLogId(loginLogid);
					logInfoMapper.saveErrorInfo(errInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取浏览器
	public String getLogBowser(String userAgent){
		String logBowser = null;
		String firefox = "FIREFOX";
		String chrome = "CHROME";
		String ie11 = "LIKE GECKO";
		String ie = "MSIE";
		String edge = "EDGE";
		userAgent = userAgent.toUpperCase();
		if(userAgent == null || userAgent.trim().equals("")){
			logBowser = "未知浏览器";
		}else if(userAgent.contains(firefox)){
			logBowser = userAgent.substring(userAgent.indexOf(firefox));
		}else if(userAgent.contains(chrome)){
			logBowser = userAgent.substring(userAgent.indexOf(chrome));
			if(logBowser.contains(edge)){
				logBowser = "Microsoft "+userAgent.substring(userAgent.indexOf(edge));
			}
		}else if(userAgent.contains(ie11)){
			logBowser = "Internet Explorer 11";
		}else if(userAgent.contains(ie)){
			logBowser = "Internet Explorer"+userAgent.substring(userAgent.indexOf(ie)+4,userAgent.indexOf(ie)+8);
		}else{
			logBowser = "未知浏览器";
		}
		return logBowser;
	}
	
	//获取当前使用系统
	public String getLogOS(String userAgent){
		
		String logOS = null;
		String windows_xp  = "WINDOWS NT 5.1";
		String windows_7   = "WINDOWS NT 6.1";
		String windows_8   = "WINDOWS NT 6.2";
		String windows_8_1 = "WINDOWS NT 6.3";
		String windows_10  = "WINDOWS NT 10";
		userAgent = userAgent.toUpperCase();
		
		if(userAgent == null || userAgent.trim().equals("")){
			logOS = "未知系统";
		}else if(userAgent.contains(windows_xp)){
			logOS = "windows xp";
		}else if(userAgent.contains(windows_7)){
			logOS = "windows 7";
		}else if(userAgent.contains(windows_8)){
			logOS = "windows 8";
		}else if(userAgent.contains(windows_8_1)){
			logOS = "windows 8.1";
		}else if(userAgent.contains(windows_10)){
			logOS = "windows 10";
		}else {
			logOS = "未知系统";
		}
		return logOS;
	}
}
