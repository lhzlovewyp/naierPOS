/**
 * 
 */
package com.joker.common.model;

import java.math.BigDecimal;

/**
 * 
 * 支付方式.
 * @author lvhaizhen
 *
 */
public class Payment extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8783755983556830188L;

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
