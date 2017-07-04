package com.erp.trans.web;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * 网站跳转Controller
 * 
 */
@Controller
@RequestMapping("/home")
public class HomeController {

	/**
	 * 首页
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * home
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request) {
		return "home";
	}

	/**
	 * 客户案例
	 */
	@RequestMapping("/form")
	public String form() {
		return "form";
	}

	/**
	 * 医商云服务
	 */
	@RequestMapping("/category_add")
	public String category_add() {
		return "category_add";
	}

	@RequestMapping("/category_list")
	public String category_list() {
		return "category_list";
	}

	/**
	 * 关于我们
	 */
	@RequestMapping("/feedback_edit")
	public String feedback_edit() {
		return "feedback_edit";
	}

	/**
	 * 微信跳转
	 */
	@RequestMapping("/feedback_list")
	public String feedback_list() {
		return "feedback_list";
	}

	public static void main(String[] args) {
		 String url = "jdbc:mysql://localhost:3306/TRANSPORTATION" ;    
	     String username = "trans_1" ;   
	     String password = "trans_1" ;   
		try {
			// 加载MySql的驱动类
			Class.forName("com.mysql.jdbc.Driver");
			try {
				Connection con =    
				         (Connection) DriverManager.getConnection(url , username , password ) ;
				   Statement stmt = (Statement) con.createStatement() ;   
				  ResultSet rs = stmt.executeQuery("SELECT * FROM ts_user_info") ;  
				  while(rs.next()){     
				    String pass = rs.getString(1) ; // 此方法比较高效   
				    System.out.println(pass);
				     }   
				  if(rs != null){   // 关闭记录集   
				        try{   
				            rs.close() ;   
				        }catch(SQLException e){   
				            e.printStackTrace() ;   
				        }   
				          }   
				          if(stmt != null){   // 关闭声明   
				        try{   
				            stmt.close() ;   
				        }catch(SQLException e){   
				            e.printStackTrace() ;   
				        }   
				          }   
				          if(con != null){  // 关闭连接对象   
				         try{   
				            con.close() ;   
				         }catch(SQLException e){   
				            e.printStackTrace() ;   
				         }   
				          }  
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("数据库连接失败！");   
			}   
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}
	}

}
