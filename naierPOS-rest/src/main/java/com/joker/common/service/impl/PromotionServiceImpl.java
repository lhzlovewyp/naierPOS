package com.joker.common.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.PromotionMapper;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.common.model.promotion.PromotionPayment;
import com.joker.common.service.PromotionService;

@Service("promotionService")
public class PromotionServiceImpl implements PromotionService{

	@Autowired
	PromotionMapper mapper;
	
	@Override
	public List<Promotion> getPromotionsByStore(String clientId,
			String storeId, Date saleDate) {
		
		List<Promotion> promotions=mapper.getPromotionsByStore(clientId, storeId, saleDate);
		//设置促销活动.
		if(CollectionUtils.isNotEmpty(promotions)){
			for(Promotion promotion:promotions){
				//设置限制支付.
				List<PromotionPayment> payments=this.getPromoPaymentByPromoId(promotion.getId());
				if(CollectionUtils.isNotEmpty(payments)){
					promotion.setPromotionPayments(payments);
				}
				
				//设置促销活动.
				List<PromotionOffer> offers=this.getPromoOfferbyPromoId(promotion.getId());
				if(CollectionUtils.isNotEmpty(offers)){
					promotion.setPromotionOffers(offers);
				}
			}
		}
		return promotions;
	}

	@Override
	public List<PromotionPayment> getPromoPaymentByPromoId(String promotionId) {
		return mapper.getPromoPaymentByPromoId(promotionId);
	}

	@Override
	public List<PromotionOffer> getPromoOfferbyPromoId(String promotionId) {
		List<PromotionOffer> offers=mapper.getPromoOfferbyPromoId(promotionId);
		if(CollectionUtils.isNotEmpty(offers)){
			for(PromotionOffer offer:offers){
				List<PromotionOfferMatchContent> contents=this.getMatchContentByOfferId(offer.getId());
				if(CollectionUtils.isNotEmpty(contents)){
					offer.setPromotionOfferMatchContents(contents);
				}
				
				List<PromotionCondition> conditions=this.getConditionByOfferId(offer.getId());
				if(CollectionUtils.isNotEmpty(conditions)){
					offer.setPromotionCondition(conditions);
				}
			}
		}
		return offers;
	}

	@Override
	public List<PromotionOfferMatchContent> getMatchContentByOfferId(
			String offerId) {
		return mapper.getMatchContentByOfferId(offerId);
	}

	@Override
	public List<PromotionCondition> getConditionByOfferId(String offerId) {
		List<PromotionCondition> conditions=mapper.getConditionByOfferId(offerId);
		if(CollectionUtils.isNotEmpty(conditions)){
			for(PromotionCondition condition:conditions){
				List<PromotionConditionMatchContent> contents=this.getMatchContentByConditionId(condition.getId());
				if(CollectionUtils.isNotEmpty(contents)){
					condition.setPromotionConditionMatchContents(contents);
				}
			}
		}
		return conditions;
	}

	@Override
	public List<PromotionConditionMatchContent> getMatchContentByConditionId(
			String conditionId) {
		return mapper.getMatchContentByConditionId(conditionId);
	}

}
