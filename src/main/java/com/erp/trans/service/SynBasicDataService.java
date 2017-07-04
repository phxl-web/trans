package com.erp.trans.service;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.service.IBaseService;

public interface SynBasicDataService extends IBaseService{

	List<Map<String, Object>> searchOrgForSelect(Pager pager);

	List<Map<String, Object>> searchLocationsForSelect(Pager pager);

	List<Map<String, Object>> carFindForSelect(Pager pager);

}
