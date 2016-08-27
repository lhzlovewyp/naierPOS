package com.joker.common.model.promotion;

import java.math.BigDecimal;
import java.util.List;

import com.joker.common.model.BaseModel;
import com.joker.common.model.Client;

public class PromotionCondition extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928345299626281790L;

	//商户
	private Client client;
	//促销优惠
	private PromotionOffer promotionOffer;
	//条件类型 MATQTY-商品数量  MATAMT-商品金额 TTLAMT-整单金额
	private String conditionType;
	//条件内容 达成方式=MATQTY，此处是商品数量 达成方式=MATAMT，此处是商品金额 达成方式=TTLAMT，此处是整单金额
	private BigDecimal condition;
	//条件匹配方式 
	//达成方式=MATQTY/MATAMT，此处才有值
	//ANY-任意
	//SAME-相同
	//DIFF-不同
	private String match;
	//条件匹配类型 达成方式=MATQTY/MATAMT，此处才有值
	//MATCAT-品类
	//BRAND-品牌
	//MAT-物料
	private String matchType;
	
	//促销条件匹配内容
	private List<PromotionConditionMatchContent> promotionConditionMatchContents;

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

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public BigDecimal getCondition() {
		return condition;
	}

	public void setCondition(BigDecimal condition) {
		this.condition = condition;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public List<PromotionConditionMatchContent> getPromotionConditionMatchContents() {
		return promotionConditionMatchContents;
	}

	public void setPromotionConditionMatchContents(
			List<PromotionConditionMatchContent> promotionConditionMatchContents) {
		this.promotionConditionMatchContents = promotionConditionMatchContents;
	}
	
	
}
