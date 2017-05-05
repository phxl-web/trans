/**
 * Project Name:ysynet File Name:SHAUtil.java Package Name:com.phxl.ysynet.common.util Date:2015年10月16日下午5:48:01 Copyright (c) 2015, PHXL All Rights Reserved.
 */

package com.erp.trans.common.util;

import java.security.MessageDigest;

/**
 * SHAA加密工具类 ClassName:SHAUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/> 
 * Date: 2015年10月16日 下午5:48:01 <br/>
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class SHAUtil{

	/**
	 * 
	 * shaEncode:针对传入的字符串进行加密 <br/> 
	 * 
	 * @Title: shaEncode
	 * @Description: TODO
	 * @param inStr
	 * @throws Exception    设定参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String shaEncode(String inStr) throws Exception {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = sha.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static void main(String[] args) throws Exception {
		String str = new String("12345");
		System.out.println("原始：" + str);
		System.out.println("SHA后：" + shaEncode(str));
	}
}
