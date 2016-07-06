package com.joker.core.model;

public class Role extends BaseModel{

	//商户号
	private Client client;
	//编码
	private String code;
	//角色名称
	private String name;
	//允许访问前台
	private String loginTerminal;
	//允许访问后台
	private String loginAdmin;
	//允许单项打折
	private String itemDISC;
	//允许整单打折
	private String allDISC;
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
	
	public String getLoginTerminal() {
		return loginTerminal;
	}
	public void setLoginTerminal(String loginTerminal) {
		this.loginTerminal = loginTerminal;
	}
	public String getLoginAdmin() {
		return loginAdmin;
	}
	public void setLoginAdmin(String loginAdmin) {
		this.loginAdmin = loginAdmin;
	}
	public String getItemDISC() {
		return itemDISC;
	}
	public void setItemDISC(String itemDISC) {
		this.itemDISC = itemDISC;
	}
	public String getAllDISC() {
		return allDISC;
	}
	public void setAllDISC(String allDISC) {
		this.allDISC = allDISC;
	}
	
	
	
	
	
}
