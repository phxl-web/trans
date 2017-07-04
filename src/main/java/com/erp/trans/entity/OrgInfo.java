package com.erp.trans.entity;

import java.util.Date;

public class OrgInfo {
    private String orgId;

    private String orgName;

    private String orgAlias;
    
    private String fstate;

    private String fqun;

    private String parentId;

    private String tfProvince;

    private String tfCity;

    private String tfDistrict;

    private String tfAddress;

    private Date createDate;

    private Date modifyDate;
    
    private String orgType;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgAlias() {
        return orgAlias;
    }

    public void setOrgAlias(String orgAlias) {
        this.orgAlias = orgAlias == null ? null : orgAlias.trim();
    }

    public String getFqun() {
        return fqun;
    }

    public void setFqun(String fqun) {
        this.fqun = fqun == null ? null : fqun.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getTfProvince() {
        return tfProvince;
    }

    public void setTfProvince(String tfProvince) {
        this.tfProvince = tfProvince == null ? null : tfProvince.trim();
    }

    public String getTfCity() {
        return tfCity;
    }

    public void setTfCity(String tfCity) {
        this.tfCity = tfCity == null ? null : tfCity.trim();
    }

    public String getTfDistrict() {
        return tfDistrict;
    }

    public void setTfDistrict(String tfDistrict) {
        this.tfDistrict = tfDistrict == null ? null : tfDistrict.trim();
    }

    public String getTfAddress() {
        return tfAddress;
    }

    public void setTfAddress(String tfAddress) {
        this.tfAddress = tfAddress == null ? null : tfAddress.trim();
    }
    
    public String getFstate() {
		return fstate;
	}

	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
}