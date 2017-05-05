package com.erp.trans.common.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SHA1Util {
	
	public static String SHA1Encrypt(String inStr) {  
		MessageDigest digest = null;  
	    String outStr = null;  
	    try {
	    	 digest = MessageDigest.getInstance("SHA-1");
	    	 digest.update(inStr.getBytes());
	    	 byte messageDigest[] = digest.digest();
	    	 // Create Hex String
	    	 StringBuffer hexString = new StringBuffer();
	    	 // 字节数组转换为 十六进制 数
	    	 for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
	    	outStr = hexString.toString();
	    }catch (NoSuchAlgorithmException nsae) {   
		     nsae.printStackTrace();  
	    }  
	    return outStr; 
	}
	
	/**
     * 排序方法
     *
     * @param strArray
     * @return
     */
    public static String sort(String[] strArray) {
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }
	 // 测试主函数  
    public static void main(String args[]) {  
//        String token = "9087654378908765432567890";  
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String nonce = String.valueOf(Math.random());
    	String pwd = "abcd";
    	pwd = MD5Util.MD5Encrypt(pwd);
    	System.out.println(pwd);
    }  
	
}
