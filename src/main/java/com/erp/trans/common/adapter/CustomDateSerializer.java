package com.erp.trans.common.adapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * ClassName: CustomDateSerializer <br/> 
 * Function:Jackson 时间格式转化 
 * 需要在java bean中做时间get方法中加注解<br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2016年2月20日 13:53:37 <br/> 
 * 
 *
 * @version  
 * @since JDK 1.6
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

	/**
	 * <p>Title: serialize</p>
	 * <p>Description: 自定义时间格式.</p>
	 * @param value
	 * @param gen
	 * @param serializers
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String formattedDate = formatter.format(value);  
         gen.writeString(formattedDate);  
	}







	
	
}
