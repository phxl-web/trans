package com.erp.trans.dao;

import com.erp.trans.entity.Insurance;

public interface InsuranceMapper {
    int insert(Insurance record);

    int insertSelective(Insurance record);
}