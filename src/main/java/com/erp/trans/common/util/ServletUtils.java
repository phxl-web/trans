package com.erp.trans.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.apache.bcel.classfile.Constant;

/**
 * Servlet向頁面输出的几种字符格式
 * @author Administrator 
 *
 */
public class ServletUtils {

	/**
	 * 打印xml数据
	 * 
	 */
	public static void printXmlData(String xml,HttpServletResponse response){
		response.setContentType("application/xml;charset=UTF-8"); 
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(xml);   
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	/**
	 * 打印text数据
	 *
	 */
	public static void printTextData(String text,HttpServletResponse response){
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(text);   
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	/**
	 * 打印html数据
	 *
	 */
	public static void printHtmlData(String text,HttpServletResponse response){
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(text);   
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	/**
	 * 打印json数据
	 * 
	 */
	public static void printJsonData(String json,HttpServletResponse response){
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);   
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	/**
	 * 打印请求参数
	 * 
	 */
	public static void printRequestParams(HttpServletRequest request){
		System.out.println("=====" + DateUtils.DateToStr(new Date(),DateUtils.TIME_FORMAT) + "=====print request parameters=====" + request.getServletPath() + "=====");
		Enumeration<String> params = request.getParameterNames();
		List<String> names = new ArrayList<String>();
		while (params.hasMoreElements()) {
			names.add(params.nextElement());
		}
		java.util.Collections.sort(names);
		for (Iterator<String> iterator = names.iterator(); iterator.hasNext();) {
			String prarmName = iterator.next();
			String [] values = request.getParameterValues(prarmName);
			if(values.length == 1){
				Object value = request.getParameter(prarmName);
				System.out.println(prarmName + "[" + value.getClass().getName() + "]: " + value);
			}else{
				String arrayValues = "";
				for (String value : values) {
					arrayValues += value + ",";
				}
				System.out.println(prarmName + ": " + arrayValues.substring(0,arrayValues.lastIndexOf(",")));
			}
		}
	}
}
