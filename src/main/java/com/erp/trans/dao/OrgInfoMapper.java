package com.erp.trans.dao;

import java.util.List;
import java.util.Map;

import com.erp.trans.common.entity.Pager;
import com.erp.trans.entity.OrgInfo;

public interface OrgInfoMapper {
    int deleteByPrimaryKey(String orgId);

    int insert(OrgInfo record);

    int insertSelective(OrgInfo record);

    OrgInfo selectByPrimaryKey(String orgId);

    int updateByPrimaryKeySelective(OrgInfo record);

    int updateByPrimaryKey(OrgInfo record);

	List<Map<String, Object>> searchOrgForSelect(Pager pager);
}