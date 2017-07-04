package com.erp.trans.entity;

import java.math.BigDecimal;
import java.util.Date;

public class VehiceDepreciation {
    private String vehicleDepreciationId;

    private String trailerNo;

    private String handCarNo;

    private BigDecimal vehicleAssets;

    private String serviceLife;

    private String monthlyDepreciation;

    private Date modifyDate;

    private String modifyUserid;

    private String orgId;

    public String getVehicleDepreciationId() {
        return vehicleDepreciationId;
    }

    public void setVehicleDepreciationId(String vehicleDepreciationId) {
        this.vehicleDepreciationId = vehicleDepreciationId == null ? null : vehicleDepreciationId.trim();
    }

    public String getTrailerNo() {
        return trailerNo;
    }

    public void setTrailerNo(String trailerNo) {
        this.trailerNo = trailerNo == null ? null : trailerNo.trim();
    }

    public String getHandCarNo() {
        return handCarNo;
    }

    public void setHandCarNo(String handCarNo) {
        this.handCarNo = handCarNo == null ? null : handCarNo.trim();
    }

    public BigDecimal getVehicleAssets() {
        return vehicleAssets;
    }

    public void setVehicleAssets(BigDecimal vehicleAssets) {
        this.vehicleAssets = vehicleAssets;
    }

    public String getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(String serviceLife) {
        this.serviceLife = serviceLife == null ? null : serviceLife.trim();
    }

    public String getMonthlyDepreciation() {
        return monthlyDepreciation;
    }

    public void setMonthlyDepreciation(String monthlyDepreciation) {
        this.monthlyDepreciation = monthlyDepreciation == null ? null : monthlyDepreciation.trim();
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