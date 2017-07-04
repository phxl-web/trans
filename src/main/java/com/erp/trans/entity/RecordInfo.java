package com.erp.trans.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.erp.trans.common.adapter.CustomDateSerializer;
import com.erp.trans.common.annotation.BaseSql;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@BaseSql(tableName="td_record_info", resultName="com.erp.trans.dao.RecordInfoMapper.BaseResultMap")
public class RecordInfo {
    private String recordId;

    private String orgId;

    private String recordType;

    private String transportTool;

    private String trailerNo;

    private Integer loadAmount;

    private String carrierId;

    private String carrierName;

    private String carrierType;

    private String mainDrive;

    private String mainDrivePhone;

    private String coPilot;

    private String coPilotPhone;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date recordStart;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date recordEnd;

    private Date modifyDate;

    private String modifyUserId;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date registerDate;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date insuranceDate;
    
    private String repaymentDay;
    private String repaymentAmount;


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType == null ? null : recordType.trim();
    }

    public String getTransportTool() {
        return transportTool;
    }

    public void setTransportTool(String transportTool) {
        this.transportTool = transportTool == null ? null : transportTool.trim();
    }

    public String getTrailerNo() {
        return trailerNo;
    }

    public void setTrailerNo(String trailerNo) {
        this.trailerNo = trailerNo == null ? null : trailerNo.trim();
    }

    public Integer getLoadAmount() {
        return loadAmount;
    }

    public void setLoadAmount(Integer loadAmount) {
        this.loadAmount = loadAmount;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId == null ? null : carrierId.trim();
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName == null ? null : carrierName.trim();
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType == null ? null : carrierType.trim();
    }

    public String getMainDrive() {
        return mainDrive;
    }

    public void setMainDrive(String mainDrive) {
        this.mainDrive = mainDrive == null ? null : mainDrive.trim();
    }

    public String getMainDrivePhone() {
        return mainDrivePhone;
    }

    public void setMainDrivePhone(String mainDrivePhone) {
        this.mainDrivePhone = mainDrivePhone == null ? null : mainDrivePhone.trim();
    }

    public String getCoPilot() {
        return coPilot;
    }

    public void setCoPilot(String coPilot) {
        this.coPilot = coPilot == null ? null : coPilot.trim();
    }

    public String getCoPilotPhone() {
        return coPilotPhone;
    }

    public void setCoPilotPhone(String coPilotPhone) {
        this.coPilotPhone = coPilotPhone == null ? null : coPilotPhone.trim();
    }

    public Date getRecordStart() {
        return recordStart;
    }

    public void setRecordStart(Date recordStart) {
        this.recordStart = recordStart;
    }

    public Date getRecordEnd() {
        return recordEnd;
    }

    public void setRecordEnd(Date recordEnd) {
        this.recordEnd = recordEnd;
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

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getInsuranceDate() {
		return insuranceDate;
	}

	public void setInsuranceDate(Date insuranceDate) {
		this.insuranceDate = insuranceDate;
	}

	public String getRepaymentDay() {
		return repaymentDay;
	}

	public void setRepaymentDay(String repaymentDay) {
		this.repaymentDay = repaymentDay;
	}

	public String getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(String repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
}