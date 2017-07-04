package com.erp.trans.web.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ServiceException;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.IdentifieUtil;
import com.erp.trans.common.util.LocalAssertUtils;
import com.erp.trans.common.util.MD5Util;
import com.erp.trans.common.constant.CustomConst;
import com.erp.trans.entity.UserInfo;

/**
 * 【用户管理】
 * 2017年6月4日 下午9:30:24
 * @author 陶悠
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
