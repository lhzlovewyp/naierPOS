package com.joker.core.model;


public class Client extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -568166096773997323L;

	//编码
	private String code;
	//名称
	private String name;
	//地址
	private String address;
	//联系人
	private String contacts;
	//最大店铺数量，无限制，默认为0
	private int maxStore;
	//最大终端数,无限制默认为0
	private int maxTerminal;

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

	public int getMaxStore() {
		return maxStore;
	}

	public void setMaxStore(int maxStore) {
		this.maxStore = maxStore;
	}

	public int getMaxTerminal() {
		return maxTerminal;
	}

	public void setMaxTerminal(int maxTerminal) {
		this.maxTerminal = maxTerminal;
	}
	
	
}
