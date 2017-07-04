package com.erp.trans.entity;

import java.util.Date;
import java.util.List;

import com.erp.trans.common.annotation.BaseSql;

@BaseSql(tableName="tb_consign", resultName="com.erp.trans.dao.ConsignMapper.BaseResultMap")
public class Consign {
    private String consignId;

    private String consignNo;

    private Integer camount;

    private String locationTo;

    private String receiveOrgId;

    private String receiveOrgName;

    private String consignFsate;

    private String consignOrgId;

    private String consignOrgName;

    private String locationFrom;

    private Date createDate;

    private Date modifyDate;

    private String createUserId;

    private String modifyUserId;

    private String orgId;
    
    List<ConsignDetail> consignDetails;

    public String getConsignId() {
        return consignId;
    }

    public void setConsignId(String consignId) {
        this.consignId = consignId == null ? null : consignId.trim();
    }

    public String getConsignNo() {
        return consignNo;
    }

    public void setConsignNo(String consignNo) {
        this.consignNo = consignNo == null ? null : consignNo.trim();
    }

    public Integer getCamount() {
        return camount;
    }

    public void setCamount(Integer camount) {
        this.camount = camount;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo == null ? null : locationTo.trim();
    }

    public String getReceiveOrgId() {
        return receiveOrgId;
    }

    public void setReceiveOrgId(String receiveOrgId) {
        this.receiveOrgId = receiveOrgId == null ? null : receiveOrgId.trim();
    }

    public String getReceiveOrgName() {
        return receiveOrgName;
    }

    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName == null ? null : receiveOrgName.trim();
    }

    public String getConsignFsate() {
        return consignFsate;
    }

    public void setConsignFsate(String consignFsate) {
        this.consignFsate = consignFsate == null ? null : consignFsate.trim();
    }

    public String getConsignOrgId() {
        return consignOrgId;
    }

    public void setConsignOrgId(String consignOrgId) {
        this.consignOrgId = consignOrgId == null ? null : consignOrgId.trim();
    }

    public String getConsignOrgName() {
        return consignOrgName;
    }

    public void setConsignOrgName(String consignOrgName) {
        this.consignOrgName = consignOrgName == null ? null : consignOrgName.trim();
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom == null ? null : locationFrom.trim();
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

	public List<ConsignDetail> getConsignDetails() {
		return consignDetails;
	}

	public void setConsignDetails(List<ConsignDetail> consignDetails) {
		this.consignDetails = consignDetails;
	}
}