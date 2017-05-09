/**
 * like查询处理
 */

package com.erp.trans.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * ClassName: StringrepaceAll <br/> 
 * Function:  ADD FUNCTION. <br/> 
 * Reason:  ADD REASON(可选). <br/> 
 * date: 2015年11月11日 下午5:37:18 <br/> 
 * 模糊查询特殊字符处理
 *
 * @version  
 * @since JDK 1.6
 */

public class StringrepaceAll{

	
	public static String sharep(String value)  {
		if(StringUtils.isNotBlank(value)){
			
			 value=value.trim().replaceAll("%", " "+'%');
			 value=value.replaceAll("_", " "+'_');	
			 value="'"+'%'+value+'%'+"'"+"   "+"escape"+" "+"'"+' '+"'";
				return  value;
			}
		return null;
	}
	public static String shareplike(String value)  {
		if(StringUtils.isNotBlank(value)){
			
			 value=value.trim().replaceAll("%", " "+'%');
			 value=value.replaceAll("_", " "+'_');	
			 value="like"+"   "+"'"+'%'+value+'%'+"'"+"   "+"escape"+" "+"'"+' '+"'";
				return  value;
			}
		return null;
	}

}
