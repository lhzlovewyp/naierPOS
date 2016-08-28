/**
 * 
 */
package com.joker.common.model;

import java.math.BigDecimal;

/**
 * 商户支付方式.
 * 
 * @author lvhaizhen
 *
 */
public class ClientPayment extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4630508749294532611L;

	private Client client;
	
	private Payment payment;
	private BigDecimal amount;
	
	//支付积分数量.
	private BigDecimal points;
	
	private String selected;
	//支付流水号.
	private String transNo;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}
	
	
}
