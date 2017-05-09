package com.erp.trans.common.converter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;

import com.erp.trans.common.entity.ResResult;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.interceptor.ResResultBindingInterceptor;
import com.erp.trans.common.util.JSONUtils;
import com.erp.trans.common.web.BaseControllerSupport;

/**
 * 接口响应返回Json格式消息定义器重写扩展
 * @date	2016年2月3日 下午5:57:14
 *
 * @version	1.0
 * @since	JDK 1.6
 */
public class CommonResResult2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**是否使用格式化json输出，默认否*/
	private boolean prettyPrint = false;
	
	@Override
	protected void writeInternal(Object result, HttpOutputMessage outputMessage) 
			throws IOException, HttpMessageNotWritableException {
		try {
			HttpServletRequest request = BaseControllerSupport.getRequest();
			Assert.notNull(request, "request，未获取到请求上下文对象");
			
			Boolean ignoreStdResult = (Boolean)request.getAttribute(ResResultBindingInterceptor.IGNORE_STD_RESULT);
			if (!Boolean.TRUE.equals(ignoreStdResult) && !(result instanceof ResResult)) {
				ResResult response=BaseControllerSupport.getStandardResResult();
				response.setResult(result);
				response.setDuring();//计算请求处理时长
				result=response;
			}
			
			if(logger.isTraceEnabled()){
				if(this.prettyPrint){
					logger.trace("■[ResponseBody]："+JSONUtils.toPrettyJsonLoosely(result));
				}else{
					logger.trace("■[ResponseBody]："+JSONUtils.toJsonLoosely(result));
				}
			}
			super.writeInternal(result, outputMessage);
			BaseControllerSupport.setRequestAttribute0(ResResultBindingInterceptor.RESPONSE_RESULT_STANDARD_WRITTEN_FLAG, true);//标准报文已经写入
		} catch (ValidationException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setPrettyPrint(boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
	}
	
}