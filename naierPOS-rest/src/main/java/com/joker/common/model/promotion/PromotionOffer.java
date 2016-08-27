/**
 * 
 */
package com.joker.common.model.promotion;

import java.math.BigDecimal;
import java.util.List;

import com.joker.common.model.BaseModel;
import com.joker.common.model.Client;

/**
 * @author lvhaizhen
 *
 */
public class PromotionOffer extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5170549533722865356L;

	//商户
	private Client client;
	//促销活动
	private Promotion promotion;
	//优惠方式
	private String offerType;
	//优惠值
	private BigDecimal offerContent;
	//优惠匹配类型
	private String matchType;
	
	//促销优惠匹配内容
	private List<PromotionOfferMatchContent> promotionOfferMatchContents;
	//促销条件.
	private List<PromotionCondition> promotionCondition;

	public List<PromotionOfferMatchContent> getPromotionOfferMatchContents() {
		return promotionOfferMatchContents;
	}

	public void setPromotionOfferMatchContents(
			List<PromotionOfferMatchContent> promotionOfferMatchContents) {
		this.promotionOfferMatchContents = promotionOfferMatchContents;
	}

	public List<PromotionCondition> getPromotionCondition() {
		return promotionCondition;
	}

	public void setPromotionCondition(List<PromotionCondition> promotionCondition) {
		this.promotionCondition = promotionCondition;
	}

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

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}
	public BigDecimal getOfferContent() {
		return offerContent;
	}

	public void setOfferContent(BigDecimal offerContent) {
		this.offerContent = offerContent;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	
	
}
