package com.joker.core.model;

import java.util.Date;


public class Store extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8122085715146450766L;
	private Client client;
	//编码
	private String code;
	//名称
	private String name;
	//开店日期
	private Date opened;
	//地址
	private String address;
	//联系人
	private String contacts;
	
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
	public Date getOpened() {
		return opened;
	}
	public void setOpened(Date opened) {
		this.opened = opened;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
}
