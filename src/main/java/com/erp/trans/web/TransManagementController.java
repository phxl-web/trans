package com.erp.trans.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.erp.trans.common.constant.CustomConst.ConsignFstate;
import com.erp.trans.common.constant.CustomConst.LoginUser;
import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.ExcelUtils;
import com.erp.trans.common.util.ExcelUtils.EntityHandler;
import com.erp.trans.entity.Consign;
import com.erp.trans.entity.DespatchPlan;
import com.erp.trans.entity.UserInfo;
import com.erp.trans.service.TransManagementService;
import com.erp.trans.web.dto.ConsignDto;

/**
 * 运输管理Controller
 * 
 * */
@Controller
@RequestMapping("/trans")
public class TransManagementController {
	
	public final static Logger logger = LoggerFactory.getLogger(TransManagementController.class);

	@Autowired
	TransManagementService  transManagementService;
	
	/**
	 * 运单管理-查询运单信息
	 * @return
	 */
	@RequestMapping("findConsignList")
	@ResponseBody
	public Pager<Map<String, Object>> findConsignList(
			@RequestParam(value = "consignNo", required = false) String consignNo,
			@RequestParam(value = "carrierName", required = false) String carrierName,
			@RequestParam(value = "dispatchDateStart", required = false) String dispatchDateStart,
			@RequestParam(value = "dispatchDateEnd", required = false) String dispatchDateEnd,
			@RequestParam(value = "consignFstate", required = false) String consignFstate,
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
//			cal.add(Calendar.MONTH, -2);
//			pager.addQueryParam("dispatchDateStart", formatter.format(cal.getTime()));// 默认前两个月开始
//		}

		if (StringUtils.isNotBlank(dispatchDateEnd)) {
			pager.addQueryParam("dispatchDateEnd", dispatchDateEnd);
		} 
//		else {
//			Calendar cal1 = Calendar.getInstance();
//			pager.addQueryParam("dispatchDateEnd", formatter.format(cal1.getTime()));// 默认当前时间
//		}

		pager.addQueryParam("orgId", orgId);// 当前登录机构
		pager.addQueryParam("carrierName", carrierName);// 承运商名称，模糊搜索
		pager.addQueryParam("consignFstate", consignFstate);// 运单状态

		List<Map<String, Object>> deliveryList = transManagementService.findConsignList(pager);
		pager.setRows(deliveryList);

		return pager;
	}
	
	/**
	 * 运输管理-发运计划信息查询
	 */
	@RequestMapping("searchDispatchInfo")
	@ResponseBody
	public Pager<Map<String, Object>> searchDispatchInfo(
			@RequestParam(value = "consignNo", required = false) String consignNo,
			@RequestParam(value = "chassisNo", required = false) String chassisNo,
			@RequestParam(value = "createDateStart", required = false) String createDateStart,
			@RequestParam(value = "createDateEnd", required = false) String createDateEnd,
			@RequestParam(value = "locationFrom", required = false) String locationFrom,
			@RequestParam(value = "locationTo", required = false) String locationTo,
			@RequestParam(value = "dispatchFstate", required = false) String dispatchFstate,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, HttpServletRequest request) {
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>(true);
		pager.setPageSize(pagesize == null ? 15 : pagesize);
		pager.setPageNum(page == null ? 1 : page);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 年-月-日格式化
		// 当前登录用户的机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		if (StringUtils.isNotBlank(createDateStart)) {
			pager.addQueryParam("createDateStart", createDateStart);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -1);
			pager.addQueryParam("createDateStart", formatter.format(cal.getTime()));// 默认前一年开始
		}

		if (StringUtils.isNotBlank(createDateEnd)) {
			pager.addQueryParam("createDateEnd", createDateEnd);
		} else {
			Calendar cal1 = Calendar.getInstance();
			pager.addQueryParam("createDateEnd", formatter.format(cal1.getTime()));// 默认当前时间
		}

		pager.addQueryParam("orgId", orgId);// 当前登录机构
		pager.addQueryParam("consignNo", consignNo);//运单号
		pager.addQueryParam("chassisNo", chassisNo);//底盘号
		pager.addQueryParam("locationFrom", locationFrom);//起运地
		pager.addQueryParam("locationTo", locationTo);//目的地
		pager.addQueryParam("dispatchFstate", dispatchFstate);//编板状态


		List<Map<String, Object>> dispatchList = transManagementService.searchDispatchInfo(pager);
		pager.setRows(dispatchList);

		return pager;
	}
	
	/**
	 * 判断选择的运单号是否已被承运商携带
	 */
	@ResponseBody
	@RequestMapping(value = "isWithCarrier")
	public void isWithCarrier(
			@RequestParam(value = "consignNos", required = false) String[] consignNos,
			HttpServletRequest request) throws ValidationException {
		transManagementService.isWithCarrier(consignNos);		
	}
	
	/**
	 * 编板计划
	 */
	@ResponseBody
	@RequestMapping(value = "makePlan")
	public void makePlan(@RequestParam(value = "consignDetailIds", required = false) String[] consignDetailIds,
			@RequestParam(value = "recordId", required = false) String recordId,
			@RequestParam(value = "mainDrive", required = false) String mainDrive,
			@RequestParam(value = "mainDrivePhone", required = false) String mainDrivePhone,
			@RequestParam(value = "consignNos", required = false) String[] consignNos,
			HttpServletRequest request) throws ValidationException {
        if(consignDetailIds == null || consignDetailIds.length == 0){
        	throw new ValidationException("请选择运单明细");
        }
        if(StringUtils.isBlank(recordId)){
        	throw new ValidationException("请选择轿运车");
        }
        if(StringUtils.isBlank(mainDrive)){
        	throw new ValidationException("请输入主驾信息");
        }
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		// 当前登录用户的机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		
		DespatchPlan despatchPlan = new DespatchPlan();
		despatchPlan.setMainDrive(mainDrive);
		despatchPlan.setMainDrivePhone(mainDrivePhone);
		despatchPlan.setRecordId(recordId);
		despatchPlan.setOrgId(orgId);
		despatchPlan.setModifyUserId(userId);
		despatchPlan.setModifyDate(new Date());
		despatchPlan.setModifyUserName(userName);
		
		transManagementService.savePlan(despatchPlan,consignDetailIds,consignNos);
		
	}
	
	/**
	 * 新增运单
	 */
	@ResponseBody
	@RequestMapping(value = "insertConsignDto")
	public void insertConsignDetail(ConsignDto consigndto,
			HttpServletRequest request) throws ValidationException {
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		// 当前登录用户的机构
		String orgId = (String) request.getSession().getAttribute(LoginUser.SESSION_USER_ORGID);
		consigndto.setAmount(1);
		consigndto.setCamount(1);
		consigndto.setConsignFsate(ConsignFstate.UNRETURN);
		consigndto.setModifyDate(new Date());
		consigndto.setCreateDate(new Date());
		consigndto.setModifyUserId(userId);
		consigndto.setOrgId(orgId);
		
		transManagementService.insertConsignDto(consigndto);
	}
	
	/**
	 * 编辑运单
	 */
	@ResponseBody
	@RequestMapping(value = "updateConsignDto")
	public void updateConsignDto(ConsignDto consignDto,
			HttpServletRequest request) throws ValidationException {
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
	}
	
	/**
	 * 修改运单状态
	 */
	@ResponseBody
	@RequestMapping(value = "updateConsignFstate")
	public void updateConsignFstate(String[] consignIds,
			String fstate,
			HttpServletRequest request) throws ValidationException {
		if(consignIds == null || consignIds.length == 0){
			throw new ValidationException("请选择要更改状态的运单");
		}
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		List<Consign> consigns = new ArrayList<Consign>();
		for(String consignId:consignIds){
			Consign consign = new Consign();
			consign.setConsignId(consignId);
			consign.setConsignFsate(fstate);
			consign.setModifyUserId(userId);
			consign.setModifyDate(new Date());
			consigns.add(consign);
		}
		transManagementService.updateConsign(consigns);
	}
	
	/**
	 *  删除运单明细
	 */
	@ResponseBody
	@RequestMapping(value = "deleteConsignDetail")
	public void deleteConsignDetail(String[] consignDetailIds,
			HttpServletRequest request) throws ValidationException {
		// 操作人id
		String userId = (String) request.getSession().getAttribute(LoginUser.SESSION_USERID);
		// 操作人name
		String userName = (String) request.getSession().getAttribute(LoginUser.SESSION_USERNAME);
		transManagementService.deleteConsignDetail(consignDetailIds);
	}
	/**
	 * 导入运单excel
	 * */
	@RequestMapping("/importConsign")
	public void importConsign( 
		 	@RequestParam(value="consignFile", required=false)MultipartFile consignExcelFile, 
		 	@RequestParam(value="test", required=false)String test, 
			HttpServletRequest request, 
			HttpSession session) throws Exception {
			long startTime = System.currentTimeMillis();
			if(consignExcelFile==null || consignExcelFile.isEmpty()){
				throw new ValidationException("请上传excel文件");
			}
//			File file = new File("/Users/eva/Downloads/信息系统运单模板.xlsx");
//			InputStream consignExcelFile = new FileInputStream(file);
			final StringBuffer msg = new StringBuffer();
			List<Consign> consignList = new ArrayList<Consign>();
			List<ConsignDto> entityList = ExcelUtils.readExcel(
					consignExcelFile.getInputStream(),1, ConsignDto.class, 
					new EntityHandler<ConsignDto>(){
						@Override
						public ConsignDto process(String sheetName, int rownum, final ConsignDto entity) {
						//这里可以验证每行导入的数据的格式和正确性，目前没有做限制	
					    return entity;
					}},
					new String[]{"rownum","consignNo", "chassisNo", "carBrand", "carModel", "locationFrom","locationTo","consignOrgName","receiveOrgName","amount"}
					);

		//TASK 可以做一些校验。
		
		Assert.notEmpty(entityList, "列表:没有发现运单，请检查文档");
		//TASK Excel表格 => 导入excel中的运单
		transManagementService.importConsigns(entityList, (UserInfo)session.getAttribute(LoginUser.SESSION_USER_INFO));
		logger.info("运单管理->导入运单->导入成功: 共{}个运单明细，使用时间{}（秒）", entityList.size(),
		BigDecimal.valueOf((System.currentTimeMillis() - startTime)/1000d).setScale(3));
		}
		
	
			
			
}
