package com.erp.trans.entity;

import java.util.Date;

import com.erp.trans.common.annotation.BaseSql;

@BaseSql(tableName="td_brand", resultName="com.erp.trans.dao.BrandMapper.BaseResultMap")
public class Brand {
    private String brandId;

    private String brandName;

    private String brandFirstLetter;

    private Date modifyDate;

    private String modifyUserId;
    
    private String brandParent;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrandFirstLetter() {
        return brandFirstLetter;
    }

    public void setBrandFirstLetter(String brandFirstLetter) {
        this.brandFirstLetter = brandFirstLetter == null ? null : brandFirstLetter.trim();
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
    }

	public String getBrandParent() {
		return brandParent;
	}

	public void setBrandParent(String brandParent) {
		this.brandParent = brandParent;
	}
}