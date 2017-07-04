package com.erp.trans.dao;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.entity.DespatchPlan;

public interface DespatchPlanMapper {
    int insert(DespatchPlan record);

    int insertSelective(DespatchPlan record);

	List<Map<String, Object>> searchDispatchInfo(Pager<Map<String, Object>> pager);
}