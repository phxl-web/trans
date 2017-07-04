package com.erp.trans.dao;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.entity.Contract;

public interface ContractMapper {
    int insert(Contract record);

    int insertSelective(Contract record);

	List<Map<String, Object>> searchContractInfo(Pager<Map<String, Object>> pager);
}