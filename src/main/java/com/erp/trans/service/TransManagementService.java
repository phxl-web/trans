package com.erp.trans.service;


import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.service.IBaseService;
import com.erp.trans.entity.Consign;
import com.erp.trans.entity.DespatchPlan;
import com.erp.trans.entity.UserInfo;
import com.erp.trans.web.dto.ConsignDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TransManagementService extends IBaseService {
	
	
	public List<UserInfo> findOrgUserList(Pager pager);

	public void importConsigns(List<ConsignDto> entityList, UserInfo attribute) throws Exception;

	public List<Map<String, Object>> findConsignList(Pager<Map<String, Object>> pager);

	public List<Map<String, Object>> searchDispatchInfo(Pager<Map<String, Object>> pager);

	public void isWithCarrier(String[] consignNos) throws ValidationException;

	public void savePlan(DespatchPlan despatchPlan, String[] consignDetailIds, String[] consignNos);

	public void deleteConsignDetail(String[] consignDetailIds);

	public void updateConsign(List<Consign> consignse);

	public void insertConsignDto(ConsignDto consigndto) throws ValidationException;

	
}
