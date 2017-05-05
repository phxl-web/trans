package com.erp.trans.common.constant.sync;

/**
 * [功能说明]: 同步服务模块基本常量、码值资源定义  <br/>
 * @date: 2016年4月21日 下午2:15:27 <br/>
 * @author 黄文君
 * @version 1.0
 * @since JDK 1.6
 */
public class SyncConst {
	
	/**
	 * 记录同步标识
	 * */
	public static final class SyncStatus {
		/**1可同步*/
		public static final int ENABLE = 1;
		/**2同步中*/
		public static final int PROCESS_ING = 2;
		/**6同步成功*/
		public static final int SUCCESS = 6;
		/**7同步失败*/
		public static final int FAIL = 7;
		/**9同步终止（关闭）：重试多次后终止！ */
		public static final int CLOSED = 9;
	}
	
	/**
	 * 接口执行方式
	 * */
	public static final class ExecType {
		/**同步执行*/
		public static final String EXEC_SYNC = "SYNC";
		/**同步执行并下载*/
		public static final String SYNC_DOWN = "SYNC_DOWN";
	}
	

	/**
	 * 变量名称 <br/>
	 * @date: 2016年4月16日 上午8:33:19 <br/>
	 * @author 黄文君
	 * @version 1.0
	 * @since JDK 1.6
	 */
	public static final class ParameterName{
		/**数据列表（公用部分） */
		public static final String PARAM_REQ_TASKDATA_LIST = "taskData";
		/**指定具体主键（ID）值*/
		public static final String PARAM_REQ_SPECIFIED_ID = "specifiedId";
	}
}
