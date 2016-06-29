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
	// 单元描述
	private String name;
	// 小数位
	private int decimal;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDecimal() {
		return decimal;
	}

	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}

}
