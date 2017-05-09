/** 
 * Project Name:ysynet 
 * File Name:ControllerSupport.java 
 * Package Name:com.phxl.ysynet.common.web 
 * Date:2015年9月22日下午5:27:28 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
*/  
  
package com.erp.trans.common.web;  

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erp.trans.common.entity.ResResult;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.interceptor.ResResultBindingInterceptor;
import com.erp.trans.common.util.JSONUtils;

/**
 * 控制器支持类, 包含控制器需要的公用性较强的一些方法
 * @date	2015年9月22日 下午5:27:59
 *
 * @version 1.0 
 * @since	JDK 1.6
 */
public class BaseControllerSupport {
	public static final Logger logger = LoggerFactory.getLogger(BaseControllerSupport.class);
	protected static ThreadLocal requestLocal = new ThreadLocal();
	protected static ThreadLocal responseLocal = new ThreadLocal();
	
	/**
	 * 获取当前线程的request
	 */
	public static HttpServletRequest getRequest() throws ValidationException {
		HttpServletRequest request=(HttpServletRequest)requestLocal.get();
		if(request==null){
			throw new ValidationException("当前线程未绑定request对象!");
		}
		return request;
	}
	
	/**
	 * 获取当前线程的response
	 */
	public static HttpServletResponse getResponse() throws ValidationException{
		HttpServletResponse response=(HttpServletResponse)responseLocal.get();
		if(response==null){
			throw new ValidationException("当前线程未绑定response对象!");
		}
		return response;
	}
	
	/**
	 * 绑定request、response对象到当前线程
	 * @param request
	 * @param response
	 */
	public static void bindingRequestAndResponse(HttpServletRequest request, HttpServletResponse response){
		requestLocal.set(request);
		responseLocal.set(response);
		logger.trace("当前线程已经绑定request与response对象");
	}
	
	/** 
	 * request参数设定
	 * 
	 * @date	2016年3月9日 上午11:07:46
	 * @param	name
	 * @param	value
	 * @throws	ValidationException
	 * @return	void
	 */
	public static void setRequestAttribute(String name, Object value) throws ValidationException{
		getRequest().setAttribute(name, value);
	}
	
	/** 
	 * request参数设定
	 * 
	 * @date	2016年3月9日 上午11:07:46
	 * @param	name
	 * @param	value
	 * @return	void
	 */
	public static void setRequestAttribute0(String name, Object value){
		HttpServletRequest request=(HttpServletRequest)requestLocal.get();
		request.setAttribute(name, value);
	}
	
	/**
	 * 初始化标准响应结构
	 *
	 * @date	2017年4月17日 下午2:01:47
	 * @param	request
	 * @throws	IOException
	 * @return	ResResult
	 */
	public static ResResult initStandardResult(HttpServletRequest request) throws IOException {
		ResResult resResult = (ResResult)request.getAttribute(ResResultBindingInterceptor.RESPONSE_RESULT_STANDARD);
		if(resResult==null){
			String requestURI = URLDecoder.decode(request.getRequestURI(), "UTF-8").replaceAll("[/\\\\]{2,}", "/");
			
			StringBuffer sb = new StringBuffer();
			if(logger.isDebugEnabled()){
				sb.append("┋requestURI=" + requestURI + "┋" + request.getContextPath());
			}
			if(logger.isTraceEnabled()){
				sb.append("┋params= " + JSONUtils.toJsonLoosely(request.getParameterMap()));
			}
			logger.debug(sb.toString());
			
			resResult = new ResResult(requestURI, Boolean.TRUE, new Date());
			//resResult.setReqParams(request.getParameterMap());
			request.setAttribute(ResResultBindingInterceptor.RESPONSE_RESULT_STANDARD, resResult);
			logger.trace("当前请求已经绑定ResResult");
		}
		return resResult;
	}
	
	/**
	 * 获取公共的响应结果实体 <p>
	 * 可以保证前端接收到的响应报文的数据结构一致，<br>
	 * 原则上建议大家都使用这个作为返回<p>
	 * @throws	IOException 
	 * @return	ResResult
	 */
	public static ResResult getStandardResResult() throws IOException {
		HttpServletRequest request = (HttpServletRequest)requestLocal.get();
		ResResult resResult = null;
		if(request != null){
			resResult = (ResResult)request.getAttribute(ResResultBindingInterceptor.RESPONSE_RESULT_STANDARD);
		}
		if(resResult == null){
			resResult = new ResResult(null, true, new Date());
		}
		return resResult;
	}
	
	/**
	 * 获取请求主机IP地址
	 * 
	 * @param	request
	 * @throws	IOException
	 */
	public static final String getClientIpAddress(HttpServletRequest request) throws IOException {
		String ip = request.getHeader("X-Forwarded-For");
		StringBuffer iptrace = new StringBuffer();
		iptrace.append("|X-Forwarded-For="	+ ip);

		if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip==null || ip.length()==0|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				iptrace.append("|Proxy-Client-IP="+ ip);
			}
			if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				iptrace.append("|WL-Proxy-Client-IP="+ ip);
			}
			if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				iptrace.append("|HTTP_CLIENT_IP="+ ip);
			}
			if (ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				iptrace.append("|HTTP_X_FORWARDED_FOR="+ ip);
			}
			if (ip==null || ip.length()==0|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				iptrace.append("|getRemoteAddr="+ ip);
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		logger.debug("client host ip router trace: "+iptrace.toString());
		logger.debug("获取请求主机IP地址: Client ip=" + ip);
		return ip;
	}
	
	/** 
	 * HttpServletResponse标准报文输出
	 * 
	 * @date	2016年3月9日 上午11:21:01
	 * @param	resResult
	 * @param	response
	 * @throws	IOException
	 * @return	void
	 */
	public static void writeInternalOfResResult(ResResult resResult, HttpServletResponse response, boolean prettyPrint) throws IOException{
		String jsonResResult="";
		if(prettyPrint){
			jsonResResult=JSONUtils.toPrettyJson(resResult);
		}else{
			jsonResResult=JSONUtils.toJson(resResult);
		}
		
		try {
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.print(jsonResResult);
			out.close();
			BaseControllerSupport.setRequestAttribute0(ResResultBindingInterceptor.RESPONSE_RESULT_STANDARD_WRITTEN_FLAG, true);//标准报文已经写入
			if(logger.isDebugEnabled()){
				logger.debug("■[ResponseBody]："+jsonResResult);
			}
		} catch (Exception e1) {
			logger.error("接口异常，响应带Exception的ResResult信息时出错！", e1);
			ServletOutputStream outstream=response.getOutputStream();
			IOUtils.write(jsonResResult, outstream);
			logger.info("接口异常，响应带Exception的ResResult信息，重试后成功。 ");
			BaseControllerSupport.setRequestAttribute0(ResResultBindingInterceptor.RESPONSE_RESULT_STANDARD_WRITTEN_FLAG, true);//标准报文已经写入
			if(logger.isDebugEnabled()){
				logger.debug("■[ResponseBody]："+jsonResResult);
			}
		}
	}
}
  