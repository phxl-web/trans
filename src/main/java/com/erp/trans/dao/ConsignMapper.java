package com.erp.trans.dao;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.entity.Consign;
import com.erp.trans.web.dto.ConsignDto;

public interface ConsignMapper {
    int insert(Consign record);

    int insertSelective(Consign record);

	int importConsigns(List<ConsignDto> list);

	List<Map<String, Object>> findConsignList(Pager<Map<String, Object>> pager);

	void updateConsign(List<Consign> consigns);

	void clearNoUseConsign();
}