package com.erp.trans.entity;

import java.util.Date;

import com.erp.trans.common.annotation.BaseSql;

@BaseSql(tableName="tb_consign_detail", resultName="com.erp.trans.dao.ConsignDetailMapper.BaseResultMap")
public class ConsignDetail {
    private String consignDetailId;
    
    private String consignId;

    private String carBrand;

    private String carModel;

    private Integer amount;

    private String chassisNo;

    private String modifyUserId;

    private Date modifyDate;
    
    private String despatchPlanId;
    
    private String despatchFstate;
    
    private Date despatchDate;

    public String getConsignDetailId() {
        return consignDetailId;
    }

    public void setConsignDetailId(String consignDetailId) {
        this.consignDetailId = consignDetailId == null ? null : consignDetailId.trim();
    }

    public String getConsignId() {
		return consignId;
	}

	public void setConsignId(String consignId) {
		this.consignId = consignId;
	}

	public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand == null ? null : carBrand.trim();
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel == null ? null : carModel.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo == null ? null : chassisNo.trim();
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

	public String getDespatchPlanId() {
		return despatchPlanId;
	}

	public void setDespatchPlanId(String despatchPlanId) {
		this.despatchPlanId = despatchPlanId;
	}

	public String getDespatchFstate() {
		return despatchFstate;
	}

	public void setDespatchFstate(String despatchFstate) {
		this.despatchFstate = despatchFstate;
	}

	public Date getDespatchDate() {
		return despatchDate;
	}

	public void setDespatchDate(Date despatchDate) {
		this.despatchDate = despatchDate;
	}
}