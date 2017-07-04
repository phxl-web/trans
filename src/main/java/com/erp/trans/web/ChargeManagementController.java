package com.erp.trans.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.trans.common.constant.CustomConst;
import com.erp.trans.common.constant.CustomConst.LoginUser;
import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.DateUtils;
import com.erp.trans.common.util.ExportUtil;
import com.erp.trans.common.util.LocalAssertUtils;
import com.erp.trans.entity.ChargeInfo;
import com.erp.trans.service.ChargeManagementService;

/**
 * 计费Controller
 * 
 * */
@Controller
@RequestMapping("/charge")
public class ChargeManagementController {
	@Autowired
	ChargeManagementService chargeManagementService;
    
	/**
	 * 结费管理-待结费列表查询
	 */
	@RequestMapping("searchChargeList")
	@ResponseBody
	public Pager<Map<String, Object>> searchChargeList(
			@RequestParam(value = "consignOrgName", required = false) String consignOrgName,
			@RequestParam(value = "carrierName", required = false) String carrierName,
			@RequestParam(value = "dispatchDateStart", required = false) String dispatchDateStart,
			@RequestParam(value = "dispatchDateEnd", required = false) String dispatchDateEnd,
			@RequestParam(value = "transportTool", required = false) String transportTool,
			@RequestParam(value = "chargeFstate", required = false) String chargeFstate,
			@RequestParam(value = "chargeType", required = false) String chargeType,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, HttpServletRequest request) {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		// 当前登录用户的机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(dispatchDateStart)) {
			pager.addQueryParam("dispatchDateStart", dispatchDateStart);
		} 
//		else {
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.YEAR, -1);
//			pager.addQueryParam("dispatchDateStart", formatter.format(cal.getTime()));// 默认前一年开始
//		}

		if (StringUtils.isNotBlank(dispatchDateEnd)) {
			pager.addQueryParam("dispatchDateEnd", dispatchDateEnd);
		} 
//		else {
//			Calendar cal1 = Calendar.getInstance();
//			pager.addQueryParam("createDateEnd", formatter.format(cal1.getTime()));// 默认当前时间
//		}

		pager.addQueryParam("orgId", orgId);// 当前登录机构
		pager.addQueryParam("consignOrgName", consignOrgName);//托运单位
		pager.addQueryParam("carrierName", carrierName);//承运商
		pager.addQueryParam("transportTool", transportTool);//轿运车
		pager.addQueryParam("chargeFstate", chargeFstate);//结费状态
		pager.addQueryParam("chargeType", chargeType);//结费类型



		List<Map<String, Object>> consignDepatchList = chargeManagementService.searchChargeList(pager);
		pager.setRows(consignDepatchList);

		return pager;
	}
	

	/**
	 * 单据对账
	 */
	@ResponseBody
	@RequestMapping(value = "balanceOfAccount")
	public void balanceOfAccount(String[] chargeIds,
			HttpServletRequest request) throws ValidationException {
		if(chargeIds == null || chargeIds.length == 0){
			throw new ValidationException("请选择要对账的车辆信息");
		}
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		List<ChargeInfo> chargeInfos = new ArrayList<ChargeInfo>();
		for(String chargeId:chargeIds){
			ChargeInfo chargeInfo = new ChargeInfo();
			chargeInfo.setChargeId(chargeId);
			chargeInfo.setChargeFstate(CustomConst.ChargeFstate.BALANCE);
			chargeInfo.setModifyUserId(userId);
			chargeInfo.setModifyUserName(userName);
			chargeInfo.setModifyDate(new Date());
			chargeInfos.add(chargeInfo);
		}
		chargeManagementService.updateChargeInfo(chargeInfos);
	}
	
	/**
	 * 编辑对账
	 */
	@ResponseBody
	@RequestMapping(value = "editAccount")
	public void editAccount(ChargeInfo chargeInfo,
			HttpServletRequest request) throws ValidationException {
		if(chargeInfo == null || StringUtils.isBlank(chargeInfo.getChargeId())){
			throw new ValidationException("请选择要编辑结费信息的车辆");
		}
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		
		chargeInfo.setModifyUserId(userId);
		chargeInfo.setModifyUserName(userName);
		chargeInfo.setModifyDate(new Date());
		
		chargeManagementService.updateInfo(chargeInfo);
	}
	
	/**
	 * 新增对账，司机结费
	 */
	@ResponseBody
	@RequestMapping(value = "insertDriveAccount")
	public void insertDriveAccount(ChargeInfo chargeInfo,
			HttpServletRequest request) throws ValidationException {
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		
		chargeInfo.setModifyUserId(userId);
		chargeInfo.setModifyUserName(userName);
		chargeInfo.setModifyDate(new Date());
		chargeInfo.setKiloPrice("0");
		chargeInfo.setKilometers("0");
		
		chargeManagementService.insertInfo(chargeInfo);
	}
	
	//导出结费信息列表
	@RequestMapping("exportChargeInfoList")
	@ResponseBody
	public void rExportDeliveryList(
			@RequestParam(value = "consignOrgName", required = false) String consignOrgName,
			@RequestParam(value = "carrierName", required = false) String carrierName,
			@RequestParam(value = "dispatchDateStart", required = false) String dispatchDateStart,
			@RequestParam(value = "dispatchDateEnd", required = false) String dispatchDateEnd,
			@RequestParam(value = "transportTool", required = false) String transportTool,
			@RequestParam(value = "chargeFstate", required = false) String chargeFstate,
			@RequestParam(value = "chargeType", required = false) String chargeType,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(false);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		LocalAssertUtils.notBlank(chargeType, "缺少结费类型参数");
		List<String> fieldName = null;
	      
		if(chargeType.equals(CustomConst.ChargeType.CONSIGN)){//托运方结费导出
			fieldName = Arrays.asList("consignOrgName","createDate","despatchDate", "consignNo", "chassisNo", "carBrand",
					"carModel", "amount","locationFrom", "locationTo",
					"freightRates", "subsidy","qualityLoss","misCosts","gpsPayment","reverseCharge","advances","prepaidOilCard","chargeAmount",
					"modifyUserName","modifyDate");
		}
		if(chargeType.equals(CustomConst.ChargeType.CONSIGN)){//承运商结费导出
			fieldName = Arrays.asList("carrierName", "transportTool","despatchDate", "consignNo", "chassisNo", "carBrand",
					"carModel", "amount","locationFrom", "locationTo","mainDrive", "coPliot",
					"freightRates", "subsidy","qualityLoss","misCosts","gpsPayment","reverseCharge","advances","prepaidOilCard","chargeAmount",
					"modifyUserName","modifyDate");
		}
		
		// 当前登录用户的机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(dispatchDateStart)) {
			pager.addQueryParam("dispatchDateStart", dispatchDateStart);
		} 
//		else {
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.YEAR, -1);
//			pager.addQueryParam("dispatchDateStart", formatter.format(cal.getTime()));// 默认前一年开始
//		}

		if (StringUtils.isNotBlank(dispatchDateEnd)) {
			pager.addQueryParam("dispatchDateEnd", dispatchDateEnd);
		} 
//		else {
//			Calendar cal1 = Calendar.getInstance();
//			pager.addQueryParam("createDateEnd", formatter.format(cal1.getTime()));// 默认当前时间
//		}

		pager.addQueryParam("orgId", orgId);// 当前登录机构
		pager.addQueryParam("consignOrgName", consignOrgName);//托运单位
		pager.addQueryParam("carrierName", carrierName);//承运商
		pager.addQueryParam("transportTool", transportTool);//轿运车
		pager.addQueryParam("chargeFstate", chargeFstate);//结费状态
		pager.addQueryParam("chargeType", chargeType);//结费类型



		List<Map<String, Object>> consignDepatchList = chargeManagementService.searchChargeList(pager);

		String nowDay = DateUtils.DateToStr(new Date(), "yyyy-MM-dd");
		String fileName = nowDay + "-结费信息表";
//		List<String> conditionBefore = Arrays.asList(
//				"库房：" + (rStorageName == null ? "全部" : rStorageName) + ",制单时间：" + sendStartDate + " 至 " + sendEndDate
//						+ ",导出时间：" + DateUtils.DateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),
//				"送货单总数量：" + (delivery == null ? "" : delivery.size()));

		ExportUtil.exportExcel(response, fieldName, consignDepatchList, null, "结   费   信   息   表", null, null, fileName
				,CustomConst.ChargeExcelMap
				);
	}
	
}
