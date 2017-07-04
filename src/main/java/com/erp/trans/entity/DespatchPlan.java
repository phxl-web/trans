package com.erp.trans.entity;

import java.util.Date;

import com.erp.trans.common.annotation.BaseSql;

@BaseSql(tableName="tb_despatch_plan", resultName="com.erp.trans.dao.DespatchPlanMapper.BaseResultMap")
public class DespatchPlan {
    private String despatchPlanId;

    private Integer mount;

    private String recordId;

    private Date modifyDate;

    private String modifyUserId;
    
    private String modifyUserName;

    private String orgId;
    
    private String mainDrive;
    
    private String mainDrivePhone;

    public String getDespatchPlanId() {
        return despatchPlanId;
    }

    public void setDespatchPlanId(String despatchPlanId) {
        this.despatchPlanId = despatchPlanId == null ? null : despatchPlanId.trim();
    }

    public Integer getMount() {
        return mount;
    }

    public void setMount(Integer mount) {
        this.mount = mount;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
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

	public String getMainDrive() {
		return mainDrive;
	}

	public void setMainDrive(String mainDrive) {
		this.mainDrive = mainDrive;
	}

	public String getMainDrivePhone() {
		return mainDrivePhone;
	}

	public void setMainDrivePhone(String mainDrivePhone) {
		this.mainDrivePhone = mainDrivePhone;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
}