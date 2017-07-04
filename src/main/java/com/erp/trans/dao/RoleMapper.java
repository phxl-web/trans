package com.erp.trans.dao;

import com.erp.trans.entity.Role;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);
}