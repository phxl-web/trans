package com.erp.trans.service;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.service.IBaseService;
import com.erp.trans.entity.ChargeInfo;

public interface ChargeManagementService extends IBaseService{

	List<Map<String, Object>> searchChargeList(Pager<Map<String, Object>> pager);

	void updateChargeInfo(List<ChargeInfo> chargeInfos);

}
