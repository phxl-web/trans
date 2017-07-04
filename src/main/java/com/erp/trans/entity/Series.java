package com.erp.trans.entity;

import java.util.Date;

import com.erp.trans.common.annotation.BaseSql;

@BaseSql(tableName="td_series", resultName="com.erp.trans.dao.SeriesMapper.BaseResultMap")
public class Series {
    private String seriesId;

    private String seriesName;

    private String seriesFirstLetter;

    private String brandId;

    private Date modifyDate;

    private String modifyUserId;
    
    private String seriesOrder;

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId == null ? null : seriesId.trim();
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName == null ? null : seriesName.trim();
    }

    public String getSeriesFirstLetter() {
        return seriesFirstLetter;
    }

    public void setSeriesFirstLetter(String seriesFirstLetter) {
        this.seriesFirstLetter = seriesFirstLetter == null ? null : seriesFirstLetter.trim();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
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

	public String getSeriesOrder() {
		return seriesOrder;
	}

	public void setSeriesOrder(String seriesOrder) {
		this.seriesOrder = seriesOrder;
	}
}