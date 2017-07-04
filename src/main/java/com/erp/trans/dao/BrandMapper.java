package com.erp.trans.dao;

import com.erp.trans.entity.Brand;

public interface BrandMapper {
    int insert(Brand record);

    int insertSelective(Brand record);
}