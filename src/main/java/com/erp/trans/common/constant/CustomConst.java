/** 
 * Project Name:ysynet 
 * File Name:CustomConst.java 
 * Package Name:com.phxl.ysynet.common.constant 
 * Date:2015年9月25日下午3:15:37 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
*/

package com.erp.trans.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义类: 用于定义系统中用到的常量值或者码值（建议使用）
 * 2017年6月4日 下午9:35:00
 * @author 陶悠
 *
 */
public class CustomConst {

	/**
	 * 用户级别
	 */
	public static final class UserLevel {
		/**01、系统管理员*/
		public static final String SUPER_ADMIN = "01";
		/**02、机构管理员*/
		public static final String ORG_ADMIN = "02";
		/**03、机构操作员*/
		public static final String ORG_USER = "03";
	}
	
	/**
	 * 机构类型
	 */
	public static final class OrgType {
		/**01、普通机构*/
		public static final String ORGNORMAL = "01";
		/**09、运维机构*/
		public static final String PLATFORM = "09";
	}
	
	/**
	 * 用户成功登录后存储在session里的信息
	 */
	public final static class LoginUser {
		/**用户对象*/
		public final static String SESSION_USER_INFO = "sessionUserInfo";
		/**用户ID*/
		public final static String SESSION_USERID = "sessionUserid";
		/**用户NO*/
		public final static String SESSION_USERNO = "sessionUserno";
		/**用户名称*/
		public final static String SESSION_USERNAME = "sessionUsername";
		/**用户密码*/
		public final static String SESSION_PASSWORD = "sessionPassword";
		/**用户机构ID*/
		public final static String SESSION_USER_ORGID = "sessionOrgid";
		/**用户机构名称*/
		public final static String SESSION_USER_ORGNAME = "sessionOrgname";
		/**用户机构类型*/
        public final static String SESSION_USER_ORG_TYPE = "sessionOrgType";
		/**用户级别*/
        public final static String SESSISON_USER_LEVEL = "sessisonUserLevel";
    	/**当前登录用户模块权限列表-会话*/
		public final static String CUR_USER_MENULIST = "curUserMenuList";
	}
	
	/**
	 * 启用|停用状态码（只适用: 1启用、0停用，按需使用）
	 */
	public static final class FlagCode {
		/** 启用 */
		public static final short usable = 1;
		/** 停用 */
		public static final short unusable = 0;
	}
	
	/**
	 * 状态码: 启用|停用|注销
	 */
	public static final class Fstate {
		/**01:表示启用*/
		public static final String USABLE = "01";
		/**00:表示禁用*/
		public static final String DISABLE = "00";
		/**02:注销（表示: 移除、物理删除），不可再变*/
		public static final String REMOVED = "02";
	}
	
	/**用户默认密码*/
	public static final String DEFAULT_PASSWORD = "00000";

	/**
	 * 邮件发送状态
	 */
	public static final class EmailFstate {
		/**发送成功*/
		public static final String SUCCESS = "01";
		/**发送失败*/
		public static final String FAIL = "02";
		/**终止发送*/
		public static final String CLOSED = "03";
	}
	
	/**
	 * 请求返回状态，本系统另外定义的（目前都是失败状态）
	 * @author 陶悠
	 */
	public static final class ResponseStatus {
		/**失败：未登录*/
		public static final int UNLOGIN = 999;
		/**失败：token验证失败*/
		public static final int TOKENFAIL = 998;
		/**失败：非法接口访问*/
		public static final int PERMISSIONREFUSE = 997;
	}
	
	/**
	 * 产品状态（00 到期 01正常 02异常）
	 * @since	JDK 1.6
	 */
	public static final class ProdCertFstate {
		/**00、到期*/
		public static final String EXPIRE = "00";
		/**01、正常*/
		public static final String USEFUL = "01";
		/**02、异常*/
		public static final String WRONG = "02";
	}
	/**
	 * 运单状态
	 * 2017年6月24日 下午8:29:37
	 * @author 陶悠
	 *
	 */
	public static final class ConsignFstate {
		/**00、未返单*/
		public static final String UNRETURN = "00";
		/**01、已返单*/
		public static final String RETURN = "01";
		/**02、异常*/
		public static final String WRONG = "02";
	}
	
	/**
	 * 运单明细状态
	 * 2017年7月2日 下午4:11:32
	 * @author 陶悠
	 *
	 */
	public static final class ConsignDetailFstate {
		/**00、未编板*/
		public static final String UNMAKE = "00";
		/**01、已编板*/
		public static final String MAKED = "01";
	}
	
	/**
	 *  对账状态
	 * 2017年7月2日 下午4:11:32
	 * @author 陶悠
	 *
	 */
	public static final class ChargeFstate {
		/**00、未对账*/
		public static final String UNBALANCE = "00";
		/**01、已对账*/
		public static final String BALANCE = "01";
	}
	
	/**
	 * 结费类型
	 * 2017年7月3日 下午11:36:26
	 * @author 陶悠
	 *
	 */
	public static final class ChargeType {
		/**01、托运方结费*/
		public static final String CONSIGN = "01";
		/**02、承运商结费*/
		public static final String CARRIER = "02";
		/**03、司机结费*/
		public static final String DRIVE = "03";
	}
	
	/**
	 * 文件导出Excel表对应字段名(结费信息)
	 */
	public final static Map<String,String> ChargeExcelMap = new HashMap<String, String>() {{
        put("consignOrgName", "托运单位");  
        put("createDate", "制单日期"); 
        put("despatchDate", "发运日期");  
        put("consignNo", "运单号");
        put("chassisNo", "底盘号");
        put("carBrand", "品牌"); 
        put("carModel", "车型"); 
        put("locationFrom", "起运地");
        put("locationTo", "目的地");
        put("freightRates", "运费");
        put("subsidy", "补贴");
        put("qualityLoss", "质损费");
        put("misCosts", "杂项扣款");
        put("gpsPayment", "GPS扣款");
        put("reverseCharge", "倒板费");
        put("advances", "预付款");
        put("prepaidOilCard", "预付油卡");
        put("chargeAmount", "结费金额");
        put("modifyUserName", "修改人");
        put("modifyDate", "修改时间");
        put("carrierName", "承运商");
        put("transportTool", "轿运车");
        put("mainDrive", "主驾");
        put("coPliot", "副驾驶");
    }};
	
}
