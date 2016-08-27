package com.joker.common.model.promotion;

import com.joker.common.model.BaseModel;
import com.joker.common.model.Client;

public class PromotionOfferMatchContent extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -327733808659261197L;

	private Client client;
	
	private PromotionOffer promotionOffer;
	
	private String matchContent;
	
	private String matchContentDesc;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public PromotionOffer getPromotionOffer() {
		return promotionOffer;
	}

	public void setPromotionOffer(PromotionOffer promotionOffer) {
		this.promotionOffer = promotionOffer;
	}

	public String getMatchContent() {
		return matchContent;
	}

	public void setMatchContent(String matchContent) {
		this.matchContent = matchContent;
	}

	public String getMatchContentDesc() {
		return matchContentDesc;
	}

	public void setMatchContentDesc(String matchContentDesc) {
		this.matchContentDesc = matchContentDesc;
	}
	
}
