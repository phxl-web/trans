package com.erp.trans.dao;

import org.apache.ibatis.annotations.Param;

import com.erp.trans.entity.DesplanConno;

public interface DesplanConnoMapper {
    int insert(DesplanConno record);

    int insertSelective(DesplanConno record);

	String[] isWithCarrier(@Param("consignNos")String[] consignNos);

	void batchDeleteByCnos(@Param("consignNos")String[] consignNos);

	void batchInsertByCnos(@Param("consignNos")String[] consignNos, @Param("despatchPlanId")String despatchPlanId);
}