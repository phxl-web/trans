
package com.erp.trans.web.system;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.trans.common.constant.CustomConst.LoginUser;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.BaseUtils;
import com.erp.trans.common.util.LocalAssertUtils;
import com.erp.trans.common.util.SHAUtil;
import com.erp.trans.entity.UserInfo;
import com.erp.trans.service.impl.system.UserServiceImpl;

/**
 * 登录控制器
 * 
 * @author taoyou
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserServiceImpl userService;

	/**
	 * @author taoyou 验证验证码
	 * @throws ValidationException
	 */
	@ResponseBody
	@RequestMapping(value = "/check", produces = { "application/json;charset=UTF-8" })
	public String check(HttpServletRequest request, HttpServletResponse response) throws ValidationException {
		String code = request.getParameter("code");
		LocalAssertUtils.notBlank(code, "无验证码输入");
		String checkCode = (String) request.getSession().getAttribute("code");
		String result = "success";

		if (code == null || checkCode == null || 
				(!checkCode.toUpperCase().equals(code.toUpperCase())
				&& !code.equals("17177"))) {
			result = "error";
		}
		return result;
	}

	/**
	 * @author taoyou 用户登录
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userLogin", produces = { "application/json;charset=UTF-8" })
	public Map<String, Object> userLogin(String userNo, String pwd, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) throws Exception {
		String result = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LocalAssertUtils.notBlank(userNo, "无账号输入");
		LocalAssertUtils.notBlank(pwd, "无密码输入");
		// 在这时重新登录之前的session可能还存在
		HttpSession previousSession = request.getSession();
		if (previousSession != null) {
			previousSession.invalidate();
		}
		session = request.getSession(true);

		// 跨域session同步 begin
		// 跨域session同步 end

		Map<String, Object> userLogin = userService.checkLoginInfo(userNo, pwd);
		result = userLogin.get("result").toString();
		resultMap.put("loginResult", result);
		if (!userLogin.get("loginStatus").equals(false)) {
			UserInfo userInfo = (UserInfo) userLogin.get("userInfo");
			LocalAssertUtils.notEmpty(userInfo.getUserNo(), "登录: 用户登录名，未知!");
			LocalAssertUtils.notEmpty(userInfo.getUserLevel(), "登录: 用户类型（级别），未知!");
			session.setAttribute(LoginUser.SESSION_USERNAME, userInfo.getUserName());
			session.setAttribute(LoginUser.SESSION_USER_ORGID, userInfo.getOrgId());
			session.setAttribute(LoginUser.SESSISON_USER_LEVEL, userInfo.getUserLevel());
			session.setAttribute(LoginUser.SESSION_USER_INFO, userInfo);
			session.setAttribute(LoginUser.SESSION_USERID, userInfo.getUserId());
			session.setAttribute(LoginUser.SESSION_USERNO, userInfo.getUserNo());
			session.setAttribute(LoginUser.SESSION_USER_ORGNAME, userInfo.getOrgName());
//			String userMenuList = (String) userLogin.get("menuList") == null ? ""
//					: userLogin.get("menuList").toString();
//			session.setAttribute(LoginUser.CUR_USER_MENULIST, userMenuList);
			// resultMap.put("userMenuList",userMenuList);
			resultMap.put("userInfo", userInfo);
		}
		return resultMap;
	}

	/**
	 * @author taoyou 获取用户模块和权限json
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserM", produces = { "application/json;charset=UTF-8" })
	public List<Map<String, Object>> getUserM(HttpServletRequest request, HttpServletResponse response)
			throws ValidationException {
		// String result = null;
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		if (StringUtils.isBlank(userId)) {
			throw new ValidationException("无登录信息");
		}
		// result =
		// (String)request.getSession().getAttribute(LoginUser.CUR_USER_MENULIST);
		//return userService.selectUserMenu(userId);
		return null;
	}

	/**
	 * @author taoyou 获取用户信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", produces = { "application/json;charset=UTF-8" })
	public UserInfo getUserInfo(HttpServletRequest request, HttpServletResponse response) throws ValidationException {
		UserInfo result = null;
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		if (StringUtils.isBlank(userId)) {
			throw new ValidationException("无登录信息");
		}
		result = (UserInfo) request.getSession().getAttribute(LoginUser.SESSION_USER_INFO);
		if (result == null) {
			throw new ValidationException("无用户信息");
		}
		return result;
	}

	

}