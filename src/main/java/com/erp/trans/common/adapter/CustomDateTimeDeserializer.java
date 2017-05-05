/** 
 * Project Name:hospital_sync 
 * File Name:CustomDateDeserializer.java 
 * Package Name:com.phxl.ysynet.common.adapter 
 * Date:2015年12月16日上午11:45:15 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
 */

package com.erp.trans.common.adapter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * jackson日期时间反序列化基本解析器 <br/>
 * date: 2015年12月16日 上午11:45:27 <br/>
 * 
 * @author 黄文君
 * @version
 * @since JDK 1.6
 */
public class CustomDateTimeDeserializer extends JsonDeserializer<Date> {
	
	/**
	 * ackson日期时间反序列化  </p>
	 * @param parser
	 * @param context
	 * @return
	 * @throws IOException
	 */
	@Override
	public Date deserialize(JsonParser parser,	DeserializationContext context) throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = parser.getText();
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
