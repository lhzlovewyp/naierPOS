/**
 * 
 */
package com.joker.core.model;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;


/**
 * @author lvhaizhen
 *
 */
public class Account extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7785689455622734636L;

	//商户
	private Client client;
	//门店
	private Store store;
	//用户名
	private String name;
	//昵称
	private String nick;
	//密码
	private String password;
	//修改密码
	private String changePWD;
	//图片信息
	private String displayPhoto;
	//token
	private String token;
	
	private String loginPOS="0";
	
	private String loginAdmin="0";
	
	private String itemDISC="0";
	
	private String allDISC="0";
	
	//当前用户下所属的角色.
	private List<Role> roles;
	
	private List<Store> stores;
	
	

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChangePWD() {
		return changePWD;
	}

	public void setChangePWD(String changePWD) {
		this.changePWD = changePWD;
	}

	public String getDisplayPhoto() {
		return displayPhoto;
	}

	public void setDisplayPhoto(String displayPhoto) {
		this.displayPhoto = displayPhoto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
		
		if(CollectionUtils.isNotEmpty(roles)){
			for(Role role:roles){
				if(role.getLoginTerminal().equals("1")){
					this.setLoginPOS("1");
				}
				if(role.getLoginAdmin().equals("1")){
					this.setLoginAdmin("1");
				}
				if(role.getItemDISC().equals("1")){
					this.setItemDISC("1");
				}
				if(role.getAllDISC().equals("1")){
					this.setAllDISC("1");
				}
			}
		}
	}

	public String getLoginPOS() {
		return loginPOS;
	}

	public void setLoginPOS(String loginPOS) {
		this.loginPOS = loginPOS;
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
