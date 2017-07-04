package com.erp.trans.service;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.service.IBaseService;

public interface ContractRecordsService extends IBaseService{

	List<Map<String, Object>> searchContractInfo(Pager<Map<String, Object>> pager);

	List<Map<String, Object>> searchCarriersForSelectByOrg(Pager pager);

	List<Map<String, Object>> searchRecordsForSelectByOrg(Pager pager);

	List<Map<String, Object>> searchRecordInfo(Pager<Map<String, Object>> pager);

}
