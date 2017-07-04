package com.erp.trans.entity;

import java.util.Date;

public class TravelAllowance {
    private String travelAllowanceId;

    private Date travelDate;

    private String travelType;

    private String travelers;

    private String travelAmount;

    private String reimbursePerson;

    private String remark;

    private Date modifyDate;

    private String modifyUserid;

    private String orgId;

    public String getTravelAllowanceId() {
        return travelAllowanceId;
    }

    public void setTravelAllowanceId(String travelAllowanceId) {
        this.travelAllowanceId = travelAllowanceId == null ? null : travelAllowanceId.trim();
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType == null ? null : travelType.trim();
    }

    public String getTravelers() {
        return travelers;
    }

    public void setTravelers(String travelers) {
        this.travelers = travelers == null ? null : travelers.trim();
    }

    public String getTravelAmount() {
        return travelAmount;
    }

    public void setTravelAmount(String travelAmount) {
        this.travelAmount = travelAmount == null ? null : travelAmount.trim();
    }

    public String getReimbursePerson() {
        return reimbursePerson;
    }

    public void setReimbursePerson(String reimbursePerson) {
        this.reimbursePerson = reimbursePerson == null ? null : reimbursePerson.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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