/**
 * 
 */
package com.joker.common.model;

/**
 * 
 * 导购员
 * @author lvhaizhen
 *
 */
public class ShoppingGuide extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6643884883426319936L;

	private Client  client;
	
	private String code;
	
	private String name;
	
	private Store store;

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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	
}
