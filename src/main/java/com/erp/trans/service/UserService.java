package com.erp.trans.service;


import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.service.IBaseService;
import com.erp.trans.entity.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserService extends IBaseService {
	
	/**
     * 
     * findUserNoExist:(验证用户登录账号的唯一性,根据用户信息表和待审核的用户注册表验证). <br/> 
     * 
     * @Title: findUserNoExist
     * @Description: TODO
     * @param userNo
     * @return    设定参数
     * @return int    返回类型
     * @throws
     */
    int findUserNoExist(String userNo);

	/**
	 */
	public List<UserInfo> findOrgUserList(Pager pager);

	/**
	 * 根据用户id查询用户信息
	 */
	public UserInfo findUserInfoById(String userId);
	
	/**
	 * 查看指定机构的机构类型
	 */
	public String findOrgTypeByOrgId(Long orgId);
	
	/**
	 * 查询还没有机构管理员的机构列表
	 */
	public List<Map<String, Object>> findWithoutAdminOrgList(Pager pager);
	
	/**
	 * 查询用户名（登录名）是否存在
	 */
	public boolean existedUserno(String userno, String excludeUserId);
	
	/**
	 * 判断指定机构是否运营商（服务商）
	 */
	public boolean assertServiceOrgByOrgId(Long orgId);
	
	/**
	 * 判断用户登录状态，并获取信息
	 */
    public Map<String,Object> checkLoginInfo(String userNo, String pwd) throws JsonProcessingException;
    
    /**
	 * 查看机构的状态
	 */
	public String findOrgFstateByOrgId(Long orgId);
    
    /**
     * 查询用户权限模块菜单信息，拼接成菜单树
     * @param userId
     */
    public List<Map<String,Object>> selectUserMenu(String userId);

    /**
	 * 添加用户
	 */
	public abstract void addUser(UserInfo user) throws ValidationException;
   
}
