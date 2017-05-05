package com.erp.trans.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网站跳转Controller
 * 
 * */
@Controller
@RequestMapping("/home")
public class HomeController {
    
	/**
	 * 首页
	 * */
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	/**
	 * home
	 * */
	@RequestMapping("/home")
	public String home(HttpServletRequest request){
		return "home";
	}
	
	/**
	 * 客户案例
	 * */
	@RequestMapping("/form")
	public String form(){
		return "form";
	}
	
	/**
	 * 医商云服务
	 * */
	@RequestMapping("/category_add")
	public String category_add(){
		return "category_add";
	}
	
	@RequestMapping("/category_list")
	public String category_list(){
		return "category_list";
	}
	/**
	 * 关于我们
	 * */
	@RequestMapping("/feedback_edit")
	public String feedback_edit(){
		return "feedback_edit";
	}
	
	/**
	 * 微信跳转
	 * */
	@RequestMapping("/feedback_list")
	public String feedback_list(){
		return "feedback_list";
	}
	
	
}
