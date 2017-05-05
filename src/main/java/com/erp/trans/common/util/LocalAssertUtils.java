package com.erp.trans.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.erp.trans.common.exception.ValidationException;

/**
 * 断言工具（内部扩展）
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class LocalAssertUtils {
	
	/**
	 * 断言字符串值非空，并且非空白字符串
	 * @author	黄文君
	 * @date	2016年10月31日 下午4:09:36
	 *
	 * @param	value
	 * @param	message
	 * @throws	ValidationException
	 * @return	void
	 */
	public static void notBlank(String value, String message) throws ValidationException{
		if(StringUtils.isBlank(value)){
			throw new ValidationException(message);
		}
	}
	
	/**
	 * 断言字符串值为空或空串
	 * @author	黄文君
	 * @date	2017年4月7日 上午9:52:15
	 * @param	value
	 * @param	message
	 * @throws	ValidationException
	 * @return	void
	 */
	public static void isBlank(String value, String message) throws ValidationException{
		if(StringUtils.isNotBlank(value)){
			throw new ValidationException(message);
		}
	}
	
	/**
	 * 断言字符串值非空
	 * @author	黄文君
	 * @date	2016年10月31日 下午4:09:36
	 *
	 * @param	value
	 * @param	message
	 * @throws	ValidationException
	 * @return	void
	 */
	public static void notEmpty(String value, String message) throws ValidationException{
		if(StringUtils.isEmpty(value)){
			throw new ValidationException(message);
		}
	}
	
	/**
	 * 断言字符串值空
	 * @author	黄文君
	 * @date	2017年4月7日 上午9:53:28
	 * @param	value
	 * @param	message
	 * @throws	ValidationException
	 * @return	void
	 */
	public static void isEmpty(String value, String message) throws ValidationException{
		if(StringUtils.isNotEmpty(value)){
			throw new ValidationException(message);
		}
	}
	
	/**
	 * 断言包含指定的值
	 * @author	黄文君
	 * @date	2017年3月24日 上午11:45:27
	 * @param	array
	 * @param	valueOfFind
	 * @return	void
	 * @throws	ValidationException
	 */
	public static void contain(Object[] array, Object valueOfFind, String message) throws ValidationException{
		if(!ArrayUtils.contains(array, valueOfFind)){
			throw new ValidationException(message);
		}
	}
	
	/**
	 * 断言不包含指定的值
	 * @author	黄文君
	 * @date	2017年4月25日 下午5:51:51
	 * @param	array
	 * @param	valueOfFind
	 * @param	message
	 * @throws	ValidationException
	 * @return	void
	 */
	public static void notContain(Object[] array, Object valueOfFind, String message) throws ValidationException{
		if(ArrayUtils.contains(array, valueOfFind)){
			throw new ValidationException(message);
		}
	}
	
}
