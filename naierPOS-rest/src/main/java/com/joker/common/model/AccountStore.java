/**
 * 
 */
package com.joker.common.model;

/**
 * @author zhangfei
 * 
 */
public class AccountStore extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5185167257259996743L;
	// 商户
	private Client client;
	// 门店
	private Store store;
	// 用户名
	private Account account;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}