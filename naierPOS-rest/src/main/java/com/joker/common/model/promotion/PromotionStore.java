/**
 * 
 */
package com.joker.common.model.promotion;

import java.util.Date;

import com.joker.common.model.BaseModel;
import com.joker.common.model.Client;
import com.joker.common.model.Store;

/**
 * @author zhangfei
 *
 */
public class PromotionStore extends BaseModel{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7537458490366565066L;

	private Client client;
	
	private Promotion promotion;
	
	private Store store;
	
	private Date effDate;
	
	private Date expDate;

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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
}