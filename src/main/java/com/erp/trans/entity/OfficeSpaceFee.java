package com.erp.trans.entity;

import java.util.Date;

public class OfficeSpaceFee {
    private String officeSpaceId;

    private String officeSpaceName;

    private Long monthRent;

    private String lease;

    private Date modifyDate;

    private String modifyUserid;

    private String orgId;

    public String getOfficeSpaceId() {
        return officeSpaceId;
    }

    public void setOfficeSpaceId(String officeSpaceId) {
        this.officeSpaceId = officeSpaceId == null ? null : officeSpaceId.trim();
    }

    public String getOfficeSpaceName() {
        return officeSpaceName;
    }

    public void setOfficeSpaceName(String officeSpaceName) {
        this.officeSpaceName = officeSpaceName == null ? null : officeSpaceName.trim();
    }

    public Long getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(Long monthRent) {
        this.monthRent = monthRent;
    }

    public String getLease() {
        return lease;
    }

    public void setLease(String lease) {
        this.lease = lease == null ? null : lease.trim();
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUserid() {
        return modifyUserid;
    }

    public void setModifyUserid(String modifyUserid) {
        this.modifyUserid = modifyUserid == null ? null : modifyUserid.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}