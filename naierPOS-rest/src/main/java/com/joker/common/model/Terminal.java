/**
 * 
 */
package com.joker.common.model;

/**
 * @author lvhaizhen
 *
 */
public class Terminal extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3826957374314053077L;

	private Client client;
	
	private Store store;
	
	private String code;
	
	private String name;

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
	
}
