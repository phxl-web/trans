package com.erp.trans.entity;

import java.util.Date;

import com.erp.trans.common.adapter.CustomDateSerializer;
import com.erp.trans.common.annotation.BaseSql;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@BaseSql(tableName="tb_contract", resultName="com.erp.trans.dao.ContractMapper.BaseResultMap")
public class Contract {
    private String contractId;

    private String contractType;

    private String consignOrgId;

    private String consignOrgName;

    private String locationConsign;

    private String carrierId;

    private String carrierName;

    private String locationFrom;

    private String locationTo;

    private String freightRates;

    private String chargePeriod;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date contractStart;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date contractEnd;

    private String contractFstate;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date modifyDate;

    private String modifyUserId;
    
    private String modifyUserName;

    private String orgId;
    
    private String carBrand;
    
    private String carModel;
    
    private String tempConprice;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
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

    public String getLocationConsign() {
        return locationConsign;
    }

    public void setLocationConsign(String locationConsign) {
        this.locationConsign = locationConsign == null ? null : locationConsign.trim();
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

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom == null ? null : locationFrom.trim();
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo == null ? null : locationTo.trim();
    }

    public String getFreightRates() {
        return freightRates;
    }

    public void setFreightRates(String freightRates) {
        this.freightRates = freightRates == null ? null : freightRates.trim();
    }

    public String getChargePeriod() {
        return chargePeriod;
    }

    public void setChargePeriod(String chargePeriod) {
        this.chargePeriod = chargePeriod == null ? null : chargePeriod.trim();
    }

    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getContractFstate() {
        return contractFstate;
    }

    public void setContractFstate(String contractFstate) {
        this.contractFstate = contractFstate == null ? null : contractFstate.trim();
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getTempConprice() {
		return tempConprice;
	}

	public void setTempConprice(String tempConprice) {
		this.tempConprice = tempConprice;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
    
}