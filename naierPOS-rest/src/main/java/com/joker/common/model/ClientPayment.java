/**
 * 
 */
package com.joker.common.model;

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
	
	
}
