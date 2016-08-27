package com.joker.common.model;

/**
 * 品牌.
 * 
 * @author lvhaizhen
 *
 */
public class Brand  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7318250308645899667L;

	/**
	 * 商户
	 */
	private Client client;
	//品牌编号
	private String code;
	//品牌名称.
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
