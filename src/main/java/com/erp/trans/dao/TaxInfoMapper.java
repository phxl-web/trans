package com.erp.trans.dao;

import com.erp.trans.entity.TaxInfo;

public interface TaxInfoMapper {
    int insert(TaxInfo record);

    int insertSelective(TaxInfo record);
}