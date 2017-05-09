/** 
 * Project Name:ysynet 
 * File Name:CustomConst.java 
 * Package Name:com.phxl.ysynet.common.constant 
 * Date:2015年9月25日下午3:15:37 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
*/

package com.erp.trans.common.constant.ysynet;

/**
 * 常量定义类: 用于定义系统中用到的常量值或者码值（建议使用） <br/>
 * @date: 2015年9月25日 下午3:15:42 <br/> 
 * 
 *
 * @version 1.0
 * @since JDK 1.6
 */
public class YsynetServiceConst {
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
		public static final short DeliveryWarehouseIn = 70;		
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
     * 发票状态  <br/> 
     * date: 2016年2月16日 下午5:31:59 <br/> 
     * 
     *
     * @version CustomConst 
     * @since JDK 1.6
     */
    public static final class InvoiceFstate {
		/** 0 待验收 */
		public static final short AWAIT_CHECK = 0;
		/** 1 验收通过 */
		public static final short PASS = 1;
		/** 9 验收未通过 */
		public static final short NO_PASS = 9;
	}
    
    /**
     * 机构类型
     * @date: 2016年7月17日 下午1:15:29
     *
     * @version 1.0
     * @since JDK 1.6
     */
    public static final class OrgType{
    	/**1医院*/
    	public static final String HOSPITAL = "1";
    	/**2供应商*/
    	public static final String SUPPLIER = "2";
    }
    
    /**
     * 用户类型（所属机构）
     * @date: 2016年7月14日 上午9:54:09
     *
     * @version 1.0
     * @since JDK 1.6
     */
    public static final class UserFtype{
    	/**1医院*/
    	public static final String HOSPITAL = "1";
    	/**2供应商*/
    	public static final String SUPPLIER = "2";
    	/**9运维*/
    	public static final String APP_OPERATION = "9";
    }
    
    /**
     * 资质证件类型
     * @date: 2016年7月17日 上午2:35:20
     *
     * @version 1.0
     * @since JDK 1.6
     */
    public static final class CertType{
    	/**1	企业法人营业执照*/
    	public static final short YYZZ = 1;
    	/**2	组织机构代码证*/
    	public static final short JGDMZ = 2;
    	/**3	医疗器械生产企业许可证*/
    	public static final short SCXK = 3;
    	/**4	医疗器械经营企业许可证*/
    	public static final short JYXK = 4;
    	/**5	医疗器械注册证*/
    	public static final short ZCZ = 5;
    	/**7	授权书*/
    	public static final short SQS = 7;
    	/**8	备案凭证*/
    	public static final short BAPZ = 8;
    	/**9	营业执照（三合一）*/
    	public static final short YYZZ_NEW = 9;
    	/**10	税务登记证*/
    	public static final short SWDJ = 10;
    	/**11  医疗机构执业许可证*/
        public static final short ZYXK = 11;
    }
    
    /**
     * 资质证件提醒类型
     * @date: 2016年7月29日 下午4:25:45
     *
     * @version 1.0
     * @since JDK 1.6
     */
    public static final class LicenseRemindType{
    	/**0	即将到期*/
    	public static final short JJDQ = 0;
    	/**1	已过期*/
    	public static final short YGQ = 1;
    	/**2	缺失营业执照或三证*/
    	public static final short NO_YYZZ = 2;
    	/**3	缺失许可证*/
    	public static final short NO_XKZ = 3;
    	/**4	缺失供应商产品资质*/
    	public static final short NO_PROD_CERT = 4;
    }
}