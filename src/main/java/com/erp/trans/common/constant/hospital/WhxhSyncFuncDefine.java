package com.erp.trans.common.constant.hospital;

/**
 * [协和医院]: 同步功能定义 <br/>
 * @date	2016年4月12日 下午2:47:41 <br/>
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class WhxhSyncFuncDefine {
	
	/**骨科申请信息:		GA				[院内]->[院外]骨科申请信息同步  */
	public static final String GKAPPLY = "GA";
	
	/**订单: 		 	OD				[院内]->[院外]订单同步  */
	public static final String ORDER = "OD";
	
	/**送货单->状态: 		DL1_{fstate}	[院内]->[院外]送货单状态（fstate）值同步 */
	public static final class DELIVERY_DL1 {
		
		public static String getFuncType(int fstate) {
			return "DL1_"+fstate;
		}
		
		public static final String DELIVERY_DL1_APPROVED = "DL1_60";//验收通过(fstate=60)，状态同步
		public static final String DELIVERY_DL1_NOPASS= "DL1_90";//验收不通过(fstate=90)，状态同步
		//public static final String DELIVERY_DL1_IMPORT = "DL1_80";//已入库，状态同步
	}
	
	/**订单->状态:	 OD1_{fstate}	[院内]->[院外]订单状态（fstate）值同步  */
	public static final class ORDER_OD1 {
		public static String getFuncType(int fstate) {
			return "OD1_"+fstate;
		}
	}
	
}
