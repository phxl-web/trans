package com.erp.trans.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.erp.trans.common.exception.ValidationException;

/**
 * 本地化扩展StringUtils
 * @date	2016年10月28日 下午4:01:16
 *
 * @version 1.0
 * @since	JDK 1.6
 */
public class LocalStringUtils {
	
	/**
	 * 判断入参的每个字符串，都是空
	 *
	 * @date	2016年12月26日 上午11:49:34
	 * @param	value
	 * @param	moreValue
	 * @return	boolean
	 */
	public static boolean isEmptyOfAll(String value, String... moreValue){
		if(StringUtils.isNotEmpty(value)){
			return false;
		}
		if(ArrayUtils.isNotEmpty(moreValue)){
			for(String val: moreValue){
				if(StringUtils.isNotEmpty(val)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断入参的每个字符串，都是非空
	 *
	 * @date	2016年12月26日 上午11:49:34
	 * @param	value
	 * @param	moreValue
	 * @return	boolean
	 */
	public static boolean isNotEmptyOfAll(String value, String... moreValue){
		if(StringUtils.isEmpty(value)){
			return false;
		}
		if(ArrayUtils.isNotEmpty(moreValue)){
			for(String val: moreValue){
				if(StringUtils.isEmpty(val)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断入参的每个字符串，都是空串
	 *
	 * @date	2016年12月26日 上午11:49:34
	 * @param	value
	 * @param	moreValue
	 * @return	boolean
	 */
	public static boolean isBlankOfAll(String value, String... moreValue){
		if(StringUtils.isNotBlank(value)){
			return false;
		}
		if(ArrayUtils.isNotEmpty(moreValue)){
			for(String val: moreValue){
				if(StringUtils.isNotBlank(val)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断入参的每个字符串，都是非空串
	 *
	 * @date	2016年12月26日 上午11:49:34
	 * @param	value
	 * @param	moreValue
	 * @return	boolean
	 */
	public static boolean isNotBlankOfAll(String value, String... moreValue){
		if(StringUtils.isBlank(value)){
			return false;
		}
		if(ArrayUtils.isNotEmpty(moreValue)){
			for(String val: moreValue){
				if(StringUtils.isBlank(val)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 如果字符串是null, 默认处理成""
	 *
	 * @date	2016年12月8日 下午4:50:40
	 * @param	str		字符串值
	 * @return	String
	 */
	public static String defaultEmptyIfNull(String str){
		return StringUtils.defaultString(str);
	}
	
	/**
	 * 将对象串化以后，trim处理，如果对象是NULL，返回空串
	 *
	 * @date	2016年10月28日 下午3:57:56
	 * @param	object
	 * @return	String
	 */
	public static String trimToEmpty(Object object){
		if(object==null){
			return "";//如果对象是NULL，返回空串
		}
		return StringUtils.trimToEmpty(object.toString());
	}
	
	/**
	 * 将对象串化以后，trim处理，如果对象是NULL，返回空串（指定默认值）
	 *
	 * @date	2016年10月28日 下午4:16:18
	 * @param	object
	 * @param	defaultValue
	 * @return	String
	 */
	public static String trimToEmpty(Object object, String defaultValue){
		object = object==null ? defaultValue: object;
		return trimToEmpty(object);
	}
	
	/**
	 * 将对象串化以后，trim处理，如果对象是NULL，返回NULL
	 *
	 * @date	2016年10月28日 下午3:57:59
	 * @param	object
	 * @return	String
	 */
	public static String trimToNull(Object object){
		if(object==null){
			return null;//如果对象是NULL，返回NULL
		}
		return StringUtils.trimToNull(object.toString());
	}
	
	/**
	 * 将对象串化以后，trim处理，如果对象是NULL，返回NULL（指定默认值）
	 *
	 * @date	2016年10月28日 下午4:19:02
	 * @param	object
	 * @param	defaultValue
	 * @return	String
	 */
	public static String trimToNull(Object object, String defaultValue){
		object = object==null ? defaultValue: object;
		return trimToNull(object);
	}

	/**
	 * 使用指定的字符，拼接指定长度的字符串
	 *
	 * @date	2016年9月27日 上午9:51:51
	 * @param 	word
	 * @param 	count
	 * @return	String
	 */
	public static String concatCharSequeue(Character word, int count){
		if(word == null){
			throw new IllegalArgumentException("入参值不能为空值");
		}
		if(count==0){
			return null;
		}
		StringBuffer baseSequeue = new StringBuffer();
		concatCharSequeue(baseSequeue, word, count);
		return baseSequeue.toString();
	}
	
	/**
	 * 使用指定的字符，拼接指定长度的字符串
	 *
	 * @date	2016年9月27日 上午9:51:51
	 * @param 	word
	 * @param 	count
	 * @return	String
	 */
	public static void concatCharSequeue(StringBuffer baseSequeue, Character word, int count){
		if(baseSequeue==null || word == null){
			throw new IllegalArgumentException("入参值不能为空值!");
		}
		if(count<0){
			throw new IllegalArgumentException("参数count不能是负值!");
		}
		for(int i=1; i<=count; i++){
			baseSequeue.append(word);
		}
	}
	
	/**
	 * 全角字符串转换半角字符串<p>
	 * <pre>1.半角字符是从33开始到126结束</pre>
	 * <pre>2.与半角字符对应的全角字符是从65281开始到65374结束</pre>
	 * <pre>3.其中半角的空格是32.对应的全角空格是12288</pre>
	 * <pre>半角和全角的关系很明显,除空格外的字符偏移量是65248(65281-33 = 65248)</pre>
	 * 
	 *
	 * @date	2016年11月7日 上午11:43:10
	 * @param	inputString	源字符串
	 * @return	String			半角字符串
	 */
	public static String toHalfWidth(String inputString) {
		if (null == inputString) {
			return null;
		}
		if(inputString.length()==0){
			return "";
		}
		char[] charArray = inputString.toCharArray();
		//对全角字符转换的char数组遍历
		for (int i = 0; i < charArray.length; ++i) {
			int charValue = (int)charArray[i];
			//如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
			if (charValue >= 65281 && charValue <= 65374) {
				charArray[i] = (char)(charValue - 65248);
			} else if (charValue == 12288) {//全角空格
				charArray[i] = (char)32;//半角空格
			}
		}
		return new String(charArray);
	}

	/**
	 * 半角字符串转全角字符串<p>
	 * <pre>1.半角字符是从33开始到126结束</pre>
	 * <pre>2.与半角字符对应的全角字符是从65281开始到65374结束</pre>
	 * <pre>3.其中半角的空格是32.对应的全角空格是12288</pre>
	 * <pre>半角和全角的关系很明显,除空格外的字符偏移量是65248(65281-33 = 65248)</pre>
	 * 
	 *
	 * @date	2016年11月7日 上午11:43:10
	 *
	 * @param	inputString		源字符串
	 * @return	String			全角字符串
	 */
	public static String toFullWidth(String inputString) {
		if (null == inputString) {
			return null;
		}
		if(inputString.length()==0){
			return "";
		}
		char charArray[] = inputString.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			int charValue = (int)charArray[i];
			if (charValue == 32) {//半角空格
				charArray[i] = 12288;//全角空格
			} else if (charValue > 32 && charValue < 127) {
				charArray[i] = (char)(charArray[i] + 65248);
			}
		}
		return new String(charArray);
	}
	
	/**
	 * 字符器切割生成列表
	 *
	 * @date	2017年2月8日 上午11:42:50
	 * @param	sequeue
	 * @param	splitToken
	 * @throws	ValidationException
	 * @return	List<String>
	 */
	public static List<String> splitToList(String sequeue, String splitToken) throws ValidationException{
		LocalAssertUtils.notEmpty(sequeue, "sequeue，参数不能空!");
		LocalAssertUtils.notEmpty(splitToken, "splitToken，参数不能空!");
		String[] array = sequeue.split(splitToken);
		if(array!=null && array.length>0){
			List<String> list = new ArrayList<String>();
			for(String e: array){
				list.add(e);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 驼峰命名法转下划线命名法
	 *
	 * @date	2017年2月20日 上午11:22:05
	 * @param	camelCaseName
	 * @return	String
	 */
	public static String namedFromCamelCase(String camelCaseName) {
		if(StringUtils.isNotBlank(camelCaseName)) {
			StringBuffer sequeue = new StringBuffer();
			for(int i=0, length=camelCaseName.length(); i<length; i++){
				char c = camelCaseName.charAt(i);
				Boolean addSplit = (i>0 && c>='A' && c<='Z' ? true : false);
				sequeue.append(addSplit ? "_" + c : c);
			}
			return sequeue.toString().toUpperCase();
		}
		return camelCaseName;
	}
	
}