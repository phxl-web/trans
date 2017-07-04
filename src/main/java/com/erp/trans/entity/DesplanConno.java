package com.erp.trans.entity;

public class DesplanConno {
    private String consignNo;

    private String despatchPlanId;

    public String getConsignNo() {
		return consignNo;
	}

	public void setConsignNo(String consignNo) {
		this.consignNo = consignNo;
	}

	public String getDespatchPlanId() {
        return despatchPlanId;
    }

    public void setDespatchPlanId(String despatchPlanId) {
        this.despatchPlanId = despatchPlanId == null ? null : despatchPlanId.trim();
    }
}