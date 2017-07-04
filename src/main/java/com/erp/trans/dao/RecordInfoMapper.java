package com.erp.trans.dao;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.entity.RecordInfo;

public interface RecordInfoMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(RecordInfo record);

    int insertSelective(RecordInfo record);

    RecordInfo selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(RecordInfo record);

    int updateByPrimaryKey(RecordInfo record);

	List<Map<String, Object>> searchCarriersForSelectByOrg(Pager pager);

	List<Map<String, Object>> searchRecordsForSelectByOrg(Pager pager);

	List<Map<String, Object>> searchRecordInfo(Pager<Map<String, Object>> pager);
}