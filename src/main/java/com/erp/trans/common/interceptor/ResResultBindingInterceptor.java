package com.erp.trans.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.erp.trans.common.entity.ResResult;
import com.erp.trans.common.util.BaseUtils;
import com.erp.trans.common.web.BaseControllerSupport;

/**
 * 请求响应标准报文数据结构处理
 * 
 * @date	2016年2月18日 下午2:42:48
 *
 * @version	1.1
 * @since	JDK 1.6
 */
public class ResResultBindingInterceptor extends HandlerInterceptorAdapter {
	public final Logger logger = LoggerFactory.getLogger(ResResultBindingInterceptor.class);

	public static final String RESPONSE_RESULT_STANDARD = "_ResResult";

	/**标识名称: 标准报文是否已经写入*/
	public static final String RESPONSE_RESULT_STANDARD_WRITTEN_FLAG = "RESPONSE_RESULT_STANDARD_WRITTEN_FLAG";
	
	/**标识名称: 是否忽略标准结果*/
	public static final String IGNORE_STD_RESULT = "IGNORE_STD_RESULT";

	/**是否使用格式化json输出，默认否*/
	private boolean prettyPrint=false;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		BaseControllerSupport.bindingRequestAndResponse(request, response);
		BaseControllerSupport.initStandardResult(request);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
		//是否已经响应写入
		if(response.isCommitted()){
			return;
		}
		if(handler.getClass().equals(HandlerMethod.class)){
			//接口方法信息
			HandlerMethod handlermethod=(HandlerMethod)handler;
			//标准报文已经写入：是|否
			Boolean isWrittenFlag=(Boolean)request.getAttribute(RESPONSE_RESULT_STANDARD_WRITTEN_FLAG);
			//是否忽略标准结果：是|否
			Boolean ignoreStdResult = (Boolean)request.getAttribute(IGNORE_STD_RESULT);
			//如果ResponseBody类型的请求
			if(handlermethod.getMethodAnnotation(ResponseBody.class)!=null){
				if(!Boolean.TRUE.equals(isWrittenFlag) && !Boolean.TRUE.equals(ignoreStdResult)) {
					ResResult resResult=BaseControllerSupport.getStandardResResult();
					if(e!=null) {
						resResult.setStatus(Boolean.FALSE);//请求状态：失败
						resResult.setException(BaseUtils.getRootCause(e).getClass().getCanonicalName());
						resResult.setMsg(BaseUtils.getRootCauseMsg(e));
						logger.error("请求处理出现异常", e);
					}
					resResult.setDuring();
					//HttpServletResponse标准报文输出
					BaseControllerSupport.writeInternalOfResResult(resResult, response, this.prettyPrint);
				}
			}
		}
	}

	/**
	 * 是否使用格式化json输出
	 * 
	 *
	 * @param	prettyPrint
	 * @return	void
	 */
	public void setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}

}
