package com.erp.trans.common.constant;

/**
 * 公用常量、码值定义（项目间可以通用） <br/>
 * @date: 2016年4月16日 下午12:06:49 <br/>
 *
 * @version 1.0
 * @since JDK 1.6
 */
public class CommonConst {
	/**
	 * 实体更新模式（） <br/>
	 * @date: 2016年4月16日 下午12:01:37 <br/>
	 *
	 * @version 1.0
	 * @since JDK 1.6
	 */
	public static final class EntityUpdateMode {
		/**ONE_BY_ONE:逐个（遍历）实体更新*/
		public static final String ONE_BY_ONE = "ONE_BY_ONE";
		/**BATCH:单个实体更新*/
		public static final String BATCH = "BATCH";
	}
	
	/**
	 * 数据表的更新模式（） <br/>
	 * @date: 2016年4月16日 下午12:01:37 <br/>
	 *
	 * @version 1.0
	 * @since JDK 1.6
	 */
	public static final class TableUpdateMode {
		/**新增*/
		public static final String INSERT = "INSERT";
		/**更新*/
		public static final String UPDATE = "UPDATE";
		/**删除*/
		public static final String DELETE = "DELETE";
	}
}
