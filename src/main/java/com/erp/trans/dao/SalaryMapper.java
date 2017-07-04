package com.erp.trans.dao;

import com.erp.trans.entity.Salary;

public interface SalaryMapper {
    int insert(Salary record);

    int insertSelective(Salary record);
}