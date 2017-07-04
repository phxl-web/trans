package com.erp.trans.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 基于jackson的json工具类
 * @date	2015年12月15日 下午6:45:57
 *
 * @version	1.1
 * @since	JDK 1.6
 */
public class JSONUtils {
	public final static Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	private static ObjectMapper objectMapper;
	
	static{
		if(objectMapper==null){
			objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));//配置项:默认日期格式
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//配置项:忽略未知属性
		}
		logger.debug("JSONUtils工具类初始化完成");
	}
	
	public static ObjectMapper getObjectMapper(){
		return objectMapper;
	}
	
	/**
	 * java Bean转json格式串
	 * @param 	obj
	 * @throws 	JsonProcessingException
	 * @return 	String
	 */
	public static String toJson(Object obj) throws JsonProcessingException{
		return objectMapper.writeValueAsString(obj);
	}
	
	/**
	 * java Bean转json格式串（已做缩进美化展现） 
	 * @param 	obj
	 * @throws 	JsonProcessingException
	 * @return 	String
	 */
	public static String toPrettyJson(Object obj) throws JsonProcessingException{
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}
	
	/**
	 * json格式串转换成java Bean
	 * @param 	jsonString
	 * @param 	clz
	 * @throws 	IOException
	 * @return 	String
	 */
	public static <T> T toBean(String jsonString, Class<T> clz) throws IOException{
		return objectMapper.readValue(jsonString, clz);
	}
	
	/**
	 * json格式串转换成java Bean
	 * @param 	jsonString
	 * @param 	valueTypeRef
	 * @throws 	IOException
	 * @return 	String
	 */
	public static <T> T toBean(String jsonString, TypeReference<T> valueTypeRef) throws IOException{
		return objectMapper.readValue(jsonString, valueTypeRef);
	}
	
	/**
	 * json转换成java Bean
	 *
	 * @date	2017年4月28日 上午9:43:46
	 * @param	jsonReader
	 * @param	clz
	 * @throws	IOException
	 * @return	T
	 */
	public static <T> T toBean(Reader jsonReader, Class<T> clz) throws IOException{
		return objectMapper.readValue(jsonReader, clz);
	}
	
	/**
	 * json转换成java Bean
	 *
	 * @date	2017年4月28日 上午9:44:34
	 * @param	jsonReader
	 * @param	valueTypeRef
	 * @throws	IOException
	 * @return	T
	 */
	public static <T> T toBean(Reader jsonReader, TypeReference<T> valueTypeRef) throws IOException{
		return objectMapper.readValue(jsonReader, valueTypeRef);
	}
	
	/**
	 * json转换成java Bean
	 *
	 * @date	2017年4月28日 上午9:44:48
	 * @param	jsonInputStream
	 * @param	clz
	 * @throws	IOException
	 * @return	T
	 */
	public static <T> T toBean(InputStream jsonInputStream, Class<T> clz) throws IOException{
		return objectMapper.readValue(jsonInputStream, clz);
	}
	
	/**
	 * json转换成java Bean
	 *
	 * @date	2017年4月28日 上午9:45:05
	 * @param	jsonInputStream
	 * @param	valueTypeRef
	 * @throws	IOException
	 * @return	T
	 */
	public static <T> T toBean(InputStream jsonInputStream, TypeReference<T> valueTypeRef) throws IOException{
		return objectMapper.readValue(jsonInputStream, valueTypeRef);
	}
	
	/**
	 * java Bean转json格式串（仅限打印日志等，非严谨场合使用）
	 * @param 	obj
	 * @return 	String
	 */
	public static String toJsonLoosely(Object obj){
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/**
	 * java Bean转已做缩进美化格式json串（仅限打印日志等，非严谨场合使用）
	 * @param 	obj
	 * @return 	String
	 */
	public static String toPrettyJsonLoosely(Object obj){
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/**   
     * 获取泛型的Collection Type  
     * @param collectionClass 泛型的Collection   
     * @param elementClasses 元素类   
     * @return JavaType Java类型   
     * @since 1.0   
     */   
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
	     return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}   
	
}
