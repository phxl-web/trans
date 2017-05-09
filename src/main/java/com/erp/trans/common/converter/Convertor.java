package com.erp.trans.common.converter;

/**
 * 数据、对象转换器   <br/>
 * @date: 2016年4月27日 下午2:50:10 <br/>
 *
 * @version 1.0
 * @since JDK 1.6
 */
public interface Convertor<T> {
	
	/**
	 * 数据转换为目标对象  <br/>
	 *
	 * @date: 2016年4月27日 下午2:51:28 <br/>
	 * @param source
	 * @return T
	 */
	public T convert(String source);
}
