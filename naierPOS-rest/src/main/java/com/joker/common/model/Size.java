/**
 * 
 */
package com.joker.common.model;

/**
 * @author lvhaizhen
 *
 */
public class Size extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3339387649503215670L;

	//商户
	private Client client;
	//编号
	private String code;
	//名称
	private String name;
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
	
	
}
