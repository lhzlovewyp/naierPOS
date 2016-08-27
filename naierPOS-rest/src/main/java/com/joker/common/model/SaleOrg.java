/**
 * 
 */
package com.joker.common.model;

import java.io.Serializable;

/**
 * @author lvhaizhen
 *
 */
public class SaleOrg implements Serializable{

	private Long orgId;
	private String orgCode;
	private String orgName;
	private String parentOrgId;
	private String orgType;
	private String orgAddress;
	private String orgTel;
	private String orgOwner;
	private String orgOwnerId;
	
	
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public String getOrgTel() {
		return orgTel;
	}
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}
	public String getOrgOwner() {
		return orgOwner;
	}
	public void setOrgOwner(String orgOwner) {
		this.orgOwner = orgOwner;
	}
	public String getOrgOwnerId() {
		return orgOwnerId;
	}
	public void setOrgOwnerId(String orgOwnerId) {
		this.orgOwnerId = orgOwnerId;
	}
	
	
	
}
