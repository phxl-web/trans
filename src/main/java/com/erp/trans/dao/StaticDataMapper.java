package com.erp.trans.dao;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.entity.StaticData;

public interface StaticDataMapper {
    int insert(StaticData record);

    int insertSelective(StaticData record);

	List<Map<String, Object>> searchLocationsForSelect(Pager pager);

	List<Map<String, Object>> carFindForSelect(Pager pager);
}