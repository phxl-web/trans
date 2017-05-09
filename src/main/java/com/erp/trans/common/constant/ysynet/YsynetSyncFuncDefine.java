package com.erp.trans.common.constant.ysynet;

/**
 * [医商云]: 同步功能定义
 * @date	2016年4月12日 下午2:44:54
 *
 * @version	1.0
 * @since	JDK 1.6
 */
public class YsynetSyncFuncDefine {
	
	/**
	 * ---------------- [基础数据] ------------------
	 */
	
	/**供应商信息:		SI		[院外]->[院内]供应商信息同步  */
	public static final String SUPPLIER_INFO = "SI";
	
	/**产品信息:		MI		[院外]->[院内]产品信息同步  */
	public static final String MATERIAL_INFO = "MI";
	
	/**规格型号信息:	MM		[院外]->[院内]规格型号信息同步  */
	public static final String MATERIAL_MODEL = "MM";
	
	/**规格型号区域扩展属性:	MMA		[院外]->[院内] */
	public static final String MATERIAL_MODEL_AREA = "MMA";
	
	/**注册证信息:			RI		[院外]->[院内]注册证信息同步  */
	public static final String REGISTRATION_INFO = "RI";
	
	/**库房信息:				SG		[院外]->[院内]库房信息同步*/
	public static final String STORAGE_INFO="SG";
	
	/**库房规格型号扩展属性: 	SGV		[院外]->[院内]库房规格型号扩展属性表同步 */
	public static final String STORAGE_VARIABLE="SGV";
	
	/**物资类别:				TY  	[院外]->[院内] */
	public static final String TYPE_INFO="TY";
	
	/**账务类别:				TSI  	[院外]->[院内] */
	public static final String TYPE_SUM_INFO="TSI";
	
	/**招标产品信息:	TD	将外网（院外）招标产品信息同步到院内  */
	public static final String TENDER_DETAIL = "TD";
	
	/**供应商信息（Delete）:		SI_D	[院外]->[院内]供应商字典信息被物理删除（Delete）同步  */
	public static final String SUPPLIER_INFO_FOR_DELETE = "SI_D";
	
	/**产品信息（Delete）:		MD_D	[院外]->[院内]产品信息被物理删除（Delete）同步  */
	public static final String MATERIAL_INFO_FOR_DELETE = "MI_D";
	
	/**规格型号信息（Delete）:		MD_D	[院外]->[院内]规格型号信息被物理删除（Delete）同步  */
	public static final String MATERIAL_MODEL_FOR_DELETE = "MM_D";
	
	/**规格型号区域扩展属性（Delete）:	MMA_D		[院外]->[院内] */
	public static final String MATERIAL_MODEL_AREA_FOR_DELETE = "MMA_D";
	
	/**注册证信息（Delete）:		RI_D	[院外]->[院内]注册证信息被物理删除（Delete）同步  */
	public static final String REGISTRATION_INFO_FOR_DELETE = "RI_D";
	
	/**库房信息（Delete）:		SG_D	[院外]->[院内]库房信息被物理删除（Delete）同步*/
	public static final String STORAGE_INFO_FOR_DELETE="SG_D";
	
	/**库房规格型号扩展属性（Delete）:		SGV_D	[院外]->[院内]库房产品扩展属性物理删除（Delete）同步*/
	public static final String STORAGE_VARIABLE_FOR_DELETE="SGV_D";
	
	/**物资类别（Delete）:				TY_D  	[院外]->[院内]物理删除（Delete）同步 */
	public static final String TYPE_INFO_FOR_DELETE="TY_D";
	
	/**账务类别（Delete）:				TSI_D  	[院外]->[院内]物理删除（Delete）同步 */
	public static final String TYPE_SUM_INFO_FOR_DELETE="TSI_D";
	
	/**产品招标信息（Delete）:				TD_D	[院外]->[院内]产品招标明细信息被物理删除（Delete）同步  */
	public static final String TENDER_DETAIL_FOR_DELETE = "TD_D";
	
	/**产品变更		TD_C	[院外]->[院内] */
	public static final String TENDER_DETAIL_CHANGE = "TD_C";
	
	/**产品转移		TD_S	[院外]->[院内] */
	public static final String TENDER_DETAIL_SHIFT = "TD_S";
	
	/**产品九位码（新增）		P9CODE_INSERT*/
	public static final String PRODUCT_9BIT_CODE_INSERT = "P9CODE_INSERT";
	
	/**产品九位码（更新）		P9CODE_UPDATE*/
	public static final String PRODUCT_9BIT_CODE_UPDATE = "P9CODE_UPDATE";
	
	/**产品九位码（停用、作废）		P9CODE_DISABLE*/
	public static final String PRODUCT_9BIT_CODE_DISABLE = "P9CODE_DISABLE";
	
	/**
	 * ---------------- [订单业务] -----------------
	 */
	
	/**订单信息->状态:	OD1_{fstate}	[院外]->[院内]订单状态（fstate）值同步  */
	public static final class ORDER_OD1 {
		public static String getFuncType(int fstate) {
			return "OD1_"+fstate;
		}
		public static final String ORDER_OD1_90="OD1_90";//90已撤销订单同步到内网
	}
	
	/**送货单信息:		DL				[院外]->[院内]送货单同步  */
	public static final String DELIVERY = "DL";
	
	/**送货单信息->状态: 	DL1_{fstate}	[院外]->[院内]送货单状态（fstate）值同步 */
	public static final class DELIVERY_DL1 {
		public static String getFuncType(int fstate) {
			return "DL1_"+fstate;
		}
	}
	
	/**发票信息: 				IV			[院外]->[院内]发票信息  */
	public static final String INVOICE = "IV";
	
	/**发票信息->更换发票号: 	IV_EOI		[院外]->[院内] */
	public static final String INVOICE_EXCHANGE_OF_INVIOCENO="IV_EOI";
	
	/**订单（Delete）:	 	OD_D		[院外]->[院内]删除快捷通道订单 */
	public static final String ORDER_FOR_DELETE="OD_D";

}
