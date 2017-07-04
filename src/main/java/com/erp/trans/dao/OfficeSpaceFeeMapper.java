package com.erp.trans.dao;

import com.erp.trans.entity.OfficeSpaceFee;

public interface OfficeSpaceFeeMapper {
    int insert(OfficeSpaceFee record);

    int insertSelective(OfficeSpaceFee record);
}