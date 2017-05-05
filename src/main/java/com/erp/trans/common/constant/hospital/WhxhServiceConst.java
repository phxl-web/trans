/** 
 * Project Name:ysynet 
 * File Name:CustomConst.java 
 * Package Name:com.phxl.hospital.common.constant 
 * Date:2015年9月25日下午3:15:37 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
*/

package com.erp.trans.common.constant.hospital;


/**
 * 协和医院，业务常量与码值对照
 * @date	2015年9月25日 下午3:15:42
 * 
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class WhxhServiceConst {

	/**
	 * 订单状态
	 */
	public final static class OrderState {
		/** 20.待提交（待发送） */
		public static final short OrderUnSend = 20;
		/** 30.待确认 */
		public static final short OrderUnConfirmed = 30;
		/** 40.确认、备货中 */
		public static final short OrderConfirmed = 40;
		/** 80.交易完成 */
		public static final short OrderCompleted = 80;
		/** 90.撤消 */
		public static final short OrderCanceled = 90;
	}

	/**
	 * 单据类别
	 * @date	2016年6月3日 下午4:30:46
	 * @author	黄文君
	 * @version 1.0
	 * @since	JDK 1.6
	 */
	public final static class OrderType{
		/**20长期备货计划 */
		public static final short CQBH = 20;
		/**30临时备货计划 */
		public static final short LSBH = 30;
		/**50快捷通道 */
		public static final short KJTD = 50;
		/**70结算计划 */
		public static final short JSJH = 70;
		/**90库管(大库房)计划 */
		public static final short KFJH = 90;
		/**91科室申请单 */
		public static final short KSSQ = 91;
		/**92消毒采购(实物库房) */
		public static final short XDCG = 92;
	}

	/**
	 * 送货单状态
	 */
	public final static class DeliveryState {
		/** 30.待确认 */
		public static final short DeliveryUnConfirmed = 30;
		/** 40.确认、待发货 */
		public static final short DeliveryConfirmed = 40;
		/** 50.已发货\待签收*/
		public static final short DeliverySended = 50;
		/** 60.验收通过*/
		public static final short DeliveryThrough = 60;
		/** 70.送货单入库*/
		//public static final short DeliveryWarehouseIn = 70;
		/** 80.结束*/
		public static final short DeliveryOver = 80;
		/** 90.验收不通过*/
		public static final short DeliveryNotThrough = 90;
		/** 99.作废*/
		public static final short DeliveryInvalid = 99;
	}

	/**
	 * 启用|停用状态码
	 */
	public static final class FlagCode {
		/** 启用 */
		public static final short usable = 1;
		/** 停用 */
		public static final short unusable = 0;
	}

	/**
     * 发票标志  <br> 
     * @date	2016年2月16日 下午5:31:59
     * 
     * @author	黄文君 
     * @version CustomConst 
     * @since	JDK 1.6
     */
    public static final class InvoiceFlag {
    	/** 50待换发票 */
		public static final String AWAIT_EXCHANE = "50";
    	/** 55已换发票 */
		public static final String DONE_EXCHANE = "55";
		/** 60已验收发票 */
		public static final String CHECK_ACCEPT = "60";
		/** 70已审核发票 */
		public static final String AUDIT_PASSED = "70";
		/** 80已结转发票 */
		public static final String CARRY_OVER = "80";
	}

    /**
     * 库房类型   <br>
     * @date	2016年4月28日 上午10:49:10
     * @author	黄文君
     * @version 1.0
     * @since	JDK 1.6
     */
    public static final class StorageType{
    	/**01、大库房 */
    	public static final String CENTER_STORAGE = "01";
    	/**02、虚拟库房 */
    	public static final String VIRTUAL_STORAGE = "02";
    	/**03、实物库房 */
    	public static final String REALITY_STORAGE = "03";
    	/**04、二级虚库房 */
    	public static final String SECOND_VIRTUAL_STORAGE = "04";
    }

    /**
     * 库房编号字典
     * @date	2017年3月10日 上午9:32:38
     * @author	黄文君
     * @version	1.0
     * @since	JDK 1.6
     */
    public static final class StorageIdDict {
    	/**10	采购中心库房*/
    	public static final String CENTRE = "10";
    	/**1011	卫材库库房*/
    	public static final String WCK = "1011";
    	/**1012	消毒供应室库房*/
    	public static final String SDGYS = "1012";
    	/**1021	手术室二级库房*/
    	public static final String SSS_OF_XH = "1021";
    	/**1022	心导管二级库房*/
    	public static final String XDG = "1022";
    	/**1023	介入二级库房*/
    	public static final String JR = "1023";
    	/**1024	骨科（代二级库房）*/
    	public static final String GK = "1024";
    	/**1025	肿瘤手术室二级库房*/
    	public static final String SSS_OF_ZL = "1025";
    }

    /**
	 * 二维码状态标识
	 * */
	public final static class QrcodeFlag {
		/**0: 生成二维码（待验收）*/
		public final static String WAIT_ACCEP = "0";
		/**1: 已入库（待出库、可出库）*/
		public final static String IMPORTED = "1";
		/**5: 已出库（待登记、待计费）*/
		public final static String OUTTED = "5";
		/**3: 入库退库（已废弃）*/
		public final static String DISABLED = "3";
		/**6: 已登记*/
		public final static String RECORD = "6";
		/**7: 已退费*/
		public final static String REFUNDED = "7";
		/**2: 已计费*/
		public final static String CHARGED = "2";
	}

	/**
	 * 患者耗材使用登记主信息：登记状态
	 * @date	2016年12月20日 下午3:53:05
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public final static class HzxxFstate {
		/**1新建登记*/
		public final static String CREATE = "1";
		/**2已计费*/
		public final static String CHARGED = "2";
		/**3废除*/
		public final static String THROW_DOWN = "3";
		/**4计费异常*/
		public final static String CHARGING_ERROR = "4";
		/**5已退费*/
		public final static String REFUNDED = "5";
	}

	/**
	 * 患者耗材使用登记明细记录: 退费标识
	 * @date	2016年12月20日 下午3:53:05
	 * @author	黄文君
	 * @version	1.0
	 * @since	JDK 1.6
	 */
	public final static class HzDetailTFlag {
		/**0没有退费*/
		public final static short CREATE = 0;
		/**1已退费*/
		public final static short REFUNDED = 1;
		/**-1退费记录*/
		public final static short REFUNDED_RECORD = -1;
		/**9计费失败*/
		public final static short CHARGING_ERROR = 9;
	}

}
