package com.erp.trans.common.entity;

import java.io.Serializable;

import org.springframework.util.Assert;

/**
 * 复合主键结构实体定义（通用）
 * @author 黄文君
 * @version 1.0
 */
public class CompoundID implements Serializable {
	/**复合主键__字段1*/
	private String compoundId1;
	/**复合主键__字段2*/
	private String compoundId2;
	/**复合主键__字段3*/
	private String compoundId3;

	public CompoundID() {
		super();
	}

	public CompoundID(String compoundId1, String compoundId2) {
		super();
		Assert.notNull(compoundId1, "‘复合主键__字段1’的值不能为空！");
		Assert.notNull(compoundId2, "‘复合主键__字段2’的值不能为空！");
		this.compoundId1 = compoundId1;
		this.compoundId2 = compoundId2;
	}
	
	public CompoundID(String compoundId1, String compoundId2, String compoundId3) {
		super();
		Assert.notNull(compoundId1, "‘复合主键__字段1’的值不能为空！");
		Assert.notNull(compoundId2, "‘复合主键__字段2’的值不能为空！");
		Assert.notNull(compoundId3, "‘复合主键__字段3’的值不能为空！");
		this.compoundId1 = compoundId1;
		this.compoundId2 = compoundId2;
		this.compoundId3 = compoundId3;
	}
	
	public String getCompoundId1() {
		return compoundId1;
	}
	public void setCompoundId1(String compoundId1) {
		this.compoundId1 = compoundId1;
	}
	public String getCompoundId2() {
		return compoundId2;
	}
	public void setCompoundId2(String compoundId2) {
		this.compoundId2 = compoundId2;
	}
	public String getCompoundId3() {
		return compoundId3;
	}
	public void setCompoundId3(String compoundId3) {
		this.compoundId3 = compoundId3;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compoundId1 == null) ? 0 : compoundId1.hashCode());
		result = prime * result + ((compoundId2 == null) ? 0 : compoundId2.hashCode());
		result = prime * result + ((compoundId3 == null) ? 0 : compoundId3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CompoundID)) {
			return false;
		}
		CompoundID other = (CompoundID) obj;
		if (compoundId1 == null) {
			if (other.compoundId1 != null) {
				return false;
			}
		} else if (!compoundId1.equals(other.compoundId1)) {
			return false;
		}
		if (compoundId2 == null) {
			if (other.compoundId2 != null) {
				return false;
			}
		} else if (!compoundId2.equals(other.compoundId2)) {
			return false;
		}
		if (compoundId3 == null) {
			if (other.compoundId3 != null) {
				return false;
			}
		} else if (!compoundId3.equals(other.compoundId3)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CompoundID [compoundId1=" + compoundId1 + ", compoundId2=" + compoundId2 + ", compoundId3=" + compoundId3 + "]";
	}
}
