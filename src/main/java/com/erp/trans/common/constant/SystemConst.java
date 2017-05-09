/** 
 * Project Name:ysynet 
 * File Name:SystemConst.java 
 * Package Name:com.phxl.ysynet.common.constant 
 * Date:2016年2月2日上午10:23:05 
 * Copyright (c) 2016, PHXL All Rights Reserved. 
 * 
*/  
  
package com.erp.trans.common.constant;  
/**
 * 开发框架、系统常量定义（非业务类） <br/>
 * date: 2016年2月2日 上午10:23:16 <br/> 
 * 
 *
 * @version 1.0 
 * @since JDK 1.6
 */
public class SystemConst {
	
	/**
	 * mybatis对oracle自动分页处理：分页语句模式 
	 */
	public static final class PaginationMode{
		/**0，默认*/
		public static final int DEFAULT_0 = 0;
		/**1*/
		public static final int MODE_1 = 1;
	}
	
	/**
	 * 功能开放标识  <br/>
	 * @date: 2016年5月17日 上午11:08:26 <br/>
	 *
	 * @version 1.0
	 * @since JDK 1.6
	 */
	public static final class OpenFlag{
		/**1，开放 */
		public static final String OPEN = "1";
		/**0，关闭 */
		public static final String CLOSE = "0";
	}
}