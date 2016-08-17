/**
 * 
 */
package com.joker.common.model;

/**
 * @author lvhaizhen
 *
 */
public class ItemClass extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8586378499780426634L;

	//MAT:商品；ITEMDISC:整单折扣；TRANSDISC:整单折扣；PROMDISC:促销折扣
	private String code;
	
	private String name;

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
