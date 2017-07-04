package com.erp.trans.entity;

import java.util.Date;

public class PersonRecords {
    private String personRecordsId;

    private String pName;

    private String pGender;

    private Date pBirthdate;

    private String pIdno;

    private String pHomeAddress;

    private String pEmergencyPhone;

    private String pBankCard;

    private String remark;

    private String pFstate;

    private Date modifyDate;

    private String modifyUserid;

    private String orgId;

    public String getPersonRecordsId() {
        return personRecordsId;
    }

    public void setPersonRecordsId(String personRecordsId) {
        this.personRecordsId = personRecordsId == null ? null : personRecordsId.trim();
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public String getpGender() {
        return pGender;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender == null ? null : pGender.trim();
    }

    public Date getpBirthdate() {
        return pBirthdate;
    }

    public void setpBirthdate(Date pBirthdate) {
        this.pBirthdate = pBirthdate;
    }

    public String getpIdno() {
        return pIdno;
    }

    public void setpIdno(String pIdno) {
        this.pIdno = pIdno == null ? null : pIdno.trim();
    }

    public String getpHomeAddress() {
        return pHomeAddress;
    }

    public void setpHomeAddress(String pHomeAddress) {
        this.pHomeAddress = pHomeAddress == null ? null : pHomeAddress.trim();
    }

    public String getpEmergencyPhone() {
        return pEmergencyPhone;
    }

    public void setpEmergencyPhone(String pEmergencyPhone) {
        this.pEmergencyPhone = pEmergencyPhone == null ? null : pEmergencyPhone.trim();
    }

    public String getpBankCard() {
        return pBankCard;
    }

    public void setpBankCard(String pBankCard) {
        this.pBankCard = pBankCard == null ? null : pBankCard.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getpFstate() {
        return pFstate;
    }

    public void setpFstate(String pFstate) {
        this.pFstate = pFstate == null ? null : pFstate.trim();
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