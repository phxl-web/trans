package com.erp.trans.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.trans.common.adapter.CustomDateTransfer;
import com.erp.trans.common.constant.CustomConst.LoginUser;
import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.IdentifieUtil;
import com.erp.trans.common.util.LocalAssertUtils;
import com.erp.trans.entity.Contract;
import com.erp.trans.entity.RecordInfo;
import com.erp.trans.service.ContractRecordsService;

/**
 * 合同档案Controller
 * 
 * */
@Controller
@RequestMapping("/contractRecords")
public class ContractRecordsController {
    @Autowired
    ContractRecordsService contractRecordsService;
    
    /**
	 * 合同档案-合同信息查询
	 */
	@RequestMapping("searchContractInfo")
	@ResponseBody
	public Pager<Map<String, Object>> searchContractInfo(
			@RequestParam(value = "consignOrgName", required = false) String consignOrgName,
			@RequestParam(value = "carrierName", required = false) String carrierName,
			@RequestParam(value = "contractStart", required = false) String contractStart,
			@RequestParam(value = "contractEnd", required = false) String contractEnd,
			@RequestParam(value = "locationFrom", required = false) String locationFrom,
			@RequestParam(value = "locationTo", required = false) String locationTo,
			@RequestParam(value = "contractType", required = false) String contractType,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, HttpServletRequest request) {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		// 当前登录用户的机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(contractStart)) {
			pager.addQueryParam("contractStart", contractStart);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -1);
			pager.addQueryParam("contractStart", formatter.format(cal.getTime()));// 默认上一年开始
		}

		if (StringUtils.isNotBlank(contractEnd)) {
			pager.addQueryParam("contractEnd", contractEnd);
		} 
//		else {
//			Calendar cal1 = Calendar.getInstance();
//			pager.addQueryParam("contractEnd", formatter.format(cal1.getTime()));// 默认当前时间
//		}

		pager.addQueryParam("orgId", orgId);// 当前登录机构
		pager.addQueryParam("locationFrom", locationFrom);//起运地
		pager.addQueryParam("locationTo", locationTo);//目的地
		pager.addQueryParam("consignOrgName", consignOrgName);//托运单位
		pager.addQueryParam("carrierName", carrierName);//承运商
		pager.addQueryParam("contractType", contractType);//合同类型，02：成本合同，01：收入合同。

		List<Map<String, Object>> contractList = contractRecordsService.searchContractInfo(pager);
		pager.setRows(contractList);

		return pager;
	}
	
	/**
	 * 新增／更新合同信息
	 */
	@ResponseBody
	@RequestMapping(value = "addUpdateContractInfo")
	public void addUpdateContractInfo(Contract contract,
			HttpServletRequest request) throws ValidationException {
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		// 操作人机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if(StringUtils.isBlank(contract.getContractId())){
			contract.setContractId(IdentifieUtil.getGuId());
			contract.setOrgId(orgId);
			contract.setModifyUserId(userId);
			contract.setModifyUserName(userName);
			contract.setModifyDate(new Date());
			contractRecordsService.insertInfo(contract);
		}else{
			contract.setModifyUserId(userId);
			contract.setModifyUserName(userName);
			contract.setModifyDate(new Date());
			contractRecordsService.updateInfoCover(contract);
		}
	}
	
	/**
	 * 删除合同信息
	 */
	@ResponseBody
	@RequestMapping(value = "deleteContract")
	public void deleteContract(Contract contract,
			HttpServletRequest request) throws ValidationException {
		if(contract == null){
			throw new ValidationException("请选择合同");
		}
		LocalAssertUtils.notBlank(contract.getContractId(), "请先选择一条系统中的合同");
		contractRecordsService.deleteInfo(contract);
	}
	
	/**
	 * 合同档案-轿运车档案信息查询
	 */
	@RequestMapping("searchRecordInfo")
	@ResponseBody
	public Pager<Map<String, Object>> searchRecordInfo(
			@RequestParam(value = "trailerNo", required = false) String trailerNo,
			@RequestParam(value = "transportTool", required = false) String transportTool,
			@RequestParam(value = "recordStart", required = false) String recordStart,
			@RequestParam(value = "recordEnd", required = false) String recordEnd,
			@RequestParam(value = "mainDrive", required = false) String mainDrive,
			@RequestParam(value = "coPilot", required = false) String coPilot,
			@RequestParam(value = "carrierName", required = false) String carrierName,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, HttpServletRequest request) {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		// 当前登录用户的机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(recordStart)) {
			pager.addQueryParam("recordStart", recordStart);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -2);
			pager.addQueryParam("recordStart", formatter.format(cal.getTime()));// 默认上一年开始
		}

		if (StringUtils.isNotBlank(recordEnd)) {
			pager.addQueryParam("recordEnd", recordEnd);
		} 
//		else {
//			Calendar cal1 = Calendar.getInstance();
//			pager.addQueryParam("recordEnd", formatter.format(cal1.getTime()));// 默认当前时间
//		}

		pager.addQueryParam("orgId", orgId);// 当前登录机构
		pager.addQueryParam("trailerNo", trailerNo);//挂车号
		pager.addQueryParam("transportTool", transportTool);//轿运车号
		pager.addQueryParam("mainDrive", mainDrive);//主驾
		pager.addQueryParam("coPilot", coPilot);//副驾
		pager.addQueryParam("carrierName", carrierName);//承运商

		List<Map<String, Object>> contractList = contractRecordsService.searchRecordInfo(pager);
		pager.setRows(contractList);

		return pager;
	}
    
	/**
	 * 新增/更新轿运车档案
	 */
	@ResponseBody
	@RequestMapping(value = "addUpdateRecordInfo")
	public void addUpdateRecordInfo(RecordInfo recordInfo,
			HttpServletRequest request) throws ValidationException {
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		// 操作人机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if(StringUtils.isBlank(recordInfo.getRecordId())){
			recordInfo.setRecordId(IdentifieUtil.getGuId());
			recordInfo.setOrgId(orgId);
			recordInfo.setModifyUserId(userId);
			recordInfo.setModifyDate(new Date());
			contractRecordsService.insertInfo(recordInfo);
		}else{
			recordInfo.setModifyUserId(userId);
			recordInfo.setModifyDate(new Date());
			contractRecordsService.updateInfoCover(recordInfo);
		}
	}
	
	/**
	 * 删除轿运车档案
	 */
	@ResponseBody
	@RequestMapping(value = "deleteRecordInfo")
	public void deleteRecordInfo(RecordInfo recordInfo,
			HttpServletRequest request) throws ValidationException {
		if(recordInfo == null){
			throw new ValidationException("请选择档案");
		}
		LocalAssertUtils.notBlank(recordInfo.getRecordId(), "请先选择一条系统中的档案");
		contractRecordsService.deleteInfo(recordInfo);
	}
	
	/**
	 * 查询系统中在某个机构的轿运车档案信息
	 * @param searchParams
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCarriersForSelectByOrg")
	public List<Map<String,Object>> searchCarriersForSelectByOrg(String searchParams, HttpSession session) {
		Pager pager = new Pager(false);
		
		pager.addQueryParam("searchParams", searchParams);
		// 操作人机构
		String orgId = (String) session.getAttribute(LoginUser.SESSION_USER_ORGID);
		pager.addQueryParam("orgId", orgId);

		return contractRecordsService.searchCarriersForSelectByOrg(pager);
	}
	
	/**
	 * 查询系统中在某个机构的某个承运商的所有轿运车，下拉框选项
	 * @param searchParams
	 * @throws ValidationException 
	 */
	@ResponseBody
	@RequestMapping(value = "/searchRecordsForSelectByCarrierId")
	public List<Map<String,Object>> searchRecordsForSelectByOrg(String searchParams, 
			String carrierId,
			HttpSession session) throws ValidationException {
		LocalAssertUtils.notBlank(carrierId, "请先选择承运商");
		Pager pager = new Pager(false);
		pager.addQueryParam("searchParams", searchParams);
		pager.addQueryParam("carrierId", carrierId);
		// 操作人机构
		String orgId = (String) session.getAttribute(LoginUser.SESSION_USER_ORGID);
		pager.addQueryParam("orgId", orgId);

		return contractRecordsService.searchRecordsForSelectByOrg(pager);
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateTransfer("yyyy-MM-dd", true));
	}
	
}
