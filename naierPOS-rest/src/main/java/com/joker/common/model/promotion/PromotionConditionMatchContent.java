/**
 * 
 */
package com.joker.common.model.promotion;

import com.joker.common.model.BaseModel;
import com.joker.common.model.Client;

/**
 * @author lvhaizhen
 *
 */
public class PromotionConditionMatchContent extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9101484680253100392L;

	private Client client;
	
	private PromotionCondition promotionCondition;
	
	private String matchContent;
	
	private String matchContentDesc;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public PromotionCondition getPromotionCondition() {
		return promotionCondition;
	}

	public void setPromotionCondition(PromotionCondition promotionCondition) {
		this.promotionCondition = promotionCondition;
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
