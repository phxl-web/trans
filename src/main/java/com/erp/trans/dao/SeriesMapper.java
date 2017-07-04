package com.erp.trans.dao;

import com.erp.trans.entity.Series;

public interface SeriesMapper {
    int insert(Series record);

    int insertSelective(Series record);
}