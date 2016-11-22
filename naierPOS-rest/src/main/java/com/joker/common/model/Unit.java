package com.joker.common.model;

/**
 * 计量单位.
 * 
 * @author lvhaizhen
 * 
 */
public class Unit extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -531461948045931568L;
	// 商户.
	private Client client;
	// 单元编码
	private String code;
	// 单元描述
	private String name;
	// 小数位
	private int unitNum;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(int unitNum) {
		this.unitNum = unitNum;
	}
}