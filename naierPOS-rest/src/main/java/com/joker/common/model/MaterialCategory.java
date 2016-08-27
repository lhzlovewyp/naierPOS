/**
 * 
 */
package com.joker.common.model;

/**
 * @author lvhaizhen
 *
 */
public class MaterialCategory  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8988000617760986320L;

	//商户
	private Client client;
	//编号
	private String code;
	//名称
	private String name;
	//上级类目
	private MaterialCategory parent;

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

	public MaterialCategory getParent() {
		return parent;
	}

	public void setParent(MaterialCategory parent) {
		this.parent = parent;
	}	
}