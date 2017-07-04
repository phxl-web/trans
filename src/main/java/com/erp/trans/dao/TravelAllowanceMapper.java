package com.erp.trans.dao;

import com.erp.trans.entity.TravelAllowance;

public interface TravelAllowanceMapper {
    int insert(TravelAllowance record);

    int insertSelective(TravelAllowance record);
}