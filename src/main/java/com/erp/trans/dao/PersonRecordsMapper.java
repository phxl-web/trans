package com.erp.trans.dao;

import com.erp.trans.entity.PersonRecords;

public interface PersonRecordsMapper {
    int insert(PersonRecords record);

    int insertSelective(PersonRecords record);
}