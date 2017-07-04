package com.erp.trans.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.service.impl.BaseService;
import com.erp.trans.dao.ContractMapper;
import com.erp.trans.dao.RecordInfoMapper;
import com.erp.trans.service.ContractRecordsService;

@Service
public class ContractRecordsServiceImpl extends BaseService implements ContractRecordsService {
	
	@Autowired
	RecordInfoMapper recordInfoMapper;
	@Autowired
	ContractMapper contractMapper;

	@Override
	public List<Map<String, Object>> searchContractInfo(Pager<Map<String, Object>> pager) {
		// TODO Auto-generated method stub
		return contractMapper.searchContractInfo(pager);
	}

	@Override
	public List<Map<String, Object>> searchCarriersForSelectByOrg(Pager pager) {
		// TODO Auto-generated method stub	
		return recordInfoMapper.searchCarriersForSelectByOrg(pager);
	}

	@Override
	public List<Map<String, Object>> searchRecordsForSelectByOrg(Pager pager) {
		// TODO Auto-generated method stub
		return recordInfoMapper.searchRecordsForSelectByOrg(pager);
	}

	@Override
	public List<Map<String, Object>> searchRecordInfo(Pager<Map<String, Object>> pager) {
		// TODO Auto-generated method stub
		return recordInfoMapper.searchRecordInfo(pager);
	}

}
