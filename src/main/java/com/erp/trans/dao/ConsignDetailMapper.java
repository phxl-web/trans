package com.erp.trans.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.erp.trans.entity.ConsignDetail;
import com.erp.trans.web.dto.ConsignDto;

public interface ConsignDetailMapper {
    int insert(ConsignDetail record);

    int insertSelective(ConsignDetail record);

	int importConsignDetails(List<ConsignDto> list);

	void batchUpdatePlan(@Param("consignDetailIds")String[] consignDetailIds, @Param("despatchPlanId")String despatchPlanId);

	void batchDeleteByIds(String[] consignDetailIds);
}