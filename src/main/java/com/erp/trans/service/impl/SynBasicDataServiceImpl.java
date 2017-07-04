package com.erp.trans.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.service.impl.BaseService;
import com.erp.trans.dao.OrgInfoMapper;
import com.erp.trans.dao.StaticDataMapper;
import com.erp.trans.service.SynBasicDataService;

@Service
public class SynBasicDataServiceImpl extends BaseService implements SynBasicDataService {

	@Autowired
	OrgInfoMapper orgInfoMapper;
	@Autowired
	StaticDataMapper staticDataMapper;
	
	@Override
	public List<Map<String, Object>> searchOrgForSelect(Pager pager) {
		// TODO Auto-generated method stub
		return orgInfoMapper.searchOrgForSelect(pager);
	}

	@Override
	public List<Map<String, Object>> searchLocationsForSelect(Pager pager) {
		// TODO Auto-generated method stub
		return staticDataMapper.searchLocationsForSelect(pager);
	}

	@Override
	public List<Map<String, Object>> carFindForSelect(Pager pager) {
		// TODO Auto-generated method stub
		return staticDataMapper.carFindForSelect(pager);
	}


}
