/**
 * 
 */
package com.joker.common.model;

/**
 * @author zhangfei
 * 
 */
public class AccountRole extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3795737178788385414L;
	// 商户
	private Client client;
	// 门店
	private Role role;
	// 用户名
	private Account account;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}