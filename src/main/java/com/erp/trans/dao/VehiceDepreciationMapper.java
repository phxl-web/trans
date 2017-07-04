package com.erp.trans.dao;

import com.erp.trans.entity.VehiceDepreciation;

public interface VehiceDepreciationMapper {
    int insert(VehiceDepreciation record);

    int insertSelective(VehiceDepreciation record);
}