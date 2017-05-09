/** 
 * Project Name:ysynet 
 * File Name:IdentifieUtil.java 
 * Package Name:com.phxl.ysynet.common.util 
 * Date:2015年9月24日上午11:32:03 
 * Copyright (c) 2015, PHXL All Rights Reserved. 
 * 
*/  
  
package com.erp.trans.common.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 上传附件、删除附件常用工具类
 * date: 2015年10月8日 下午3:44:37 <br/> 
 *  
 *
 * @version  
 * @since JDK 1.6
 */
public class FileUploadUtil {
	
	/**
	 * 上传附件
	 * @return String
	 */
	public static boolean saveFile(MultipartFile file,String path,String filename,HttpServletRequest request){
	    String filePath = fileReplace(path,request);//将原路径替换成windows和Linux通用路径
	 // 判断文件是否为空  
        if (file.getSize() > 0) {
        	File root = new File(filePath);
            if(!root.exists()){//如果路径不存在就创建一个
                new File(filePath).mkdirs();
            }
            try {  
                // 转存文件  
                File f = new File(filePath+File.separator+filename
                        + file.getOriginalFilename().substring(
                                file.getOriginalFilename().lastIndexOf(
                                        ".")));
                file.transferTo(f);
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
		return false;
	}
	
	/**
     * 删除附件
     * @return String
     */
    public static boolean deleteFile(String path,String uploadName,HttpServletRequest request){
     // 判断文件名是否为空  
        if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(uploadName)) {
            String filePath = fileReplace(path,request);//将原路径替换成windows和Linux通用路径
            File f = new File(filePath+File.separator+uploadName);
            if(!f.isDirectory()){//判断是否属于文件,如果属于文件就删除
                return f.delete();
            }
            return false; 
        }  
        return false;
    }
    
    /**
     * 
     * fileExist:(判断文件是否存在). <br/> 
     * 
     * @Title: fileExist
     * @Description: TODO
     * @param path 文件绝对路径
     * @param uploadName 文件名(带后缀名)
     * @param request
     * @return    设定参数
     * @return boolean    返回类型
     * @throws
     */
    public static boolean fileExist(String path,String uploadName,HttpServletRequest request){
        // 判断文件名是否为空  
           if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(uploadName)) {
               String filePath = fileReplace(path,request);//将原路径替换成windows和Linux通用路径
               File f = new File(filePath+File.separator+uploadName);
               if(f .exists()  && !f.isDirectory()){//判断路径存在并且属于文件
                   return true;
               }
               return false; 
           }  
           return false;
       }
    
    /**
     * 
     * fileReplace:(将原路径替换成windows和Linux通用路径). <br/> 
     * 
     * @Title: fileReplace
     * @Description: TODO
     * @param path
     * @param uploadName
     * @param request
     * @return    设定参数
     * @return String    返回类型
     * @throws
     */
    public static String fileReplace(String path,HttpServletRequest request){
        StringBuffer newPath = new StringBuffer();
        String[] pathArray = path.split("#");
        for(int i=0;i<pathArray.length;i++){
            newPath.append(pathArray[i]+File.separator);//将原路径替换成windows和Linux通用路径
        }
        String s = request.getSession().getServletContext().getRealPath(newPath.toString());
        if (!s.endsWith(File.separator)) {
            s = s + File.separator;
            }
        return s;
    }
}
  