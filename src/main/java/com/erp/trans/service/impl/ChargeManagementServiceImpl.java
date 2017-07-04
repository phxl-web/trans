package com.erp.trans.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.service.impl.BaseService;
import com.erp.trans.dao.ChargeInfoMapper;
import com.erp.trans.entity.ChargeInfo;
import com.erp.trans.service.ChargeManagementService;

@Service
public class ChargeManagementServiceImpl extends BaseService implements ChargeManagementService {

	@Autowired
	ChargeInfoMapper chargeInfoMapper;
	
	@Override
	public List<Map<String, Object>> searchChargeList(Pager<Map<String, Object>> pager) {
		// TODO Auto-generated method stub
		return chargeInfoMapper.searchChargeList(pager);
	}

	@Override
	public void updateChargeInfo(List<ChargeInfo> chargeInfos) {
		// TODO Auto-generated method stub
		chargeInfoMapper.updateChargeInfo(chargeInfos);
	}

}
