package com.joker.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.PromotionMapper;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.common.model.promotion.PromotionPayment;
import com.joker.common.service.PromotionService;
import com.joker.core.dto.Page;

@Service("promotionService")
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	PromotionMapper mapper;

	@Override
	public List<Promotion> getPromotionsByStore(String clientId,
			String storeId, Date saleDate) {

		List<Promotion> promotions = mapper.getPromotionsByStore(clientId,
				storeId, saleDate);
		// 设置促销活动.
		if (CollectionUtils.isNotEmpty(promotions)) {
			for (Promotion promotion : promotions) {
				// 设置限制支付.
				List<PromotionPayment> payments = this
						.getPromoPaymentByPromoId(promotion.getId());
				if (CollectionUtils.isNotEmpty(payments)) {
					promotion.setPromotionPayments(payments);
				}

				// 设置促销活动.
				List<PromotionOffer> offers = this
						.getPromoOfferbyPromoId(promotion.getId());
				if (CollectionUtils.isNotEmpty(offers)) {
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
		List<PromotionOffer> offers = mapper
				.getPromoOfferbyPromoId(promotionId);
		if (CollectionUtils.isNotEmpty(offers)) {
			for (PromotionOffer offer : offers) {
				List<PromotionOfferMatchContent> contents = this
						.getMatchContentByOfferId(offer.getId());
				if (CollectionUtils.isNotEmpty(contents)) {
					offer.setPromotionOfferMatchContents(contents);
				}

				List<PromotionCondition> conditions = this
						.getConditionByOfferId(offer.getId());
				if (CollectionUtils.isNotEmpty(conditions)) {
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
		List<PromotionCondition> conditions = mapper
				.getConditionByOfferId(offerId);
		if (CollectionUtils.isNotEmpty(conditions)) {
			for (PromotionCondition condition : conditions) {
				List<PromotionConditionMatchContent> contents = this
						.getMatchContentByConditionId(condition.getId());
				if (CollectionUtils.isNotEmpty(contents)) {
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

	@Override
	public Promotion getPromotionByID(String id) {
		return mapper.getPromotionByID(id);
	}

	@Override
	public Page<Promotion> getPromotionPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Promotion> page = new Page<Promotion>();
		int totalRecord = mapper.getPromotionCountByCondition(map);
		List<Promotion> list = mapper.getPromotionPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<Promotion> getPromotionPageByCondition(Map<String, Object> map) {
		List<Promotion> list = mapper.getPromotionPageByCondition(map);
		return list;
	}

	@Override
	public void deletePromotionByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePromotionByID(oneId);
				}
			}
		}
	}

	@Override
	public void updatePromotion(Promotion promotion) {
		mapper.updatePromotion(promotion);

	}

	@Override
	public void insertPromotion(Promotion promotion) {
		if (StringUtils.isBlank(promotion.getId())) {
			promotion.setId(UUID.randomUUID().toString());
		}
		mapper.insertPromotion(promotion);
	}

}
