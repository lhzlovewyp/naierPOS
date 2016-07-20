/**
 * 
 */
package com.joker.common.pay;

/**
 * 支付参数设置.
 * 
 * @author lvhaizhen
 * 
 */
public class PayParamsVo {

	/** 支付标题 */
	private String title;
	/** 订单描述. */
	private String body;
	/** 支付金额 */
	private String amount;
	/** 支付码 */
	private String authCode;
	
	private String storeId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	

}
