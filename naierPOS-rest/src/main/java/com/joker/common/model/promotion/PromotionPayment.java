/**
 * 
 */
package com.joker.common.model.promotion;

import com.joker.common.model.BaseModel;
import com.joker.common.model.Client;
import com.joker.common.model.ClientPayment;

/**
 * @author lvhaizhen
 *
 */
public class PromotionPayment extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3650871570099211285L;

	private Client client;
	
	private Promotion promotion;
	
	private ClientPayment clientPayment;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public ClientPayment getClientPayment() {
		return clientPayment;
	}

	public void setClientPayment(ClientPayment clientPayment) {
		this.clientPayment = clientPayment;
	}

	
	
	
}
