package com.joker.common.service.impl;

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
import com.joker.common.mapper.PromotionOfferMapper;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.service.PromotionOfferService;
import com.joker.core.dto.Page;

@Service("promotionOfferService")
public class PromotionOfferServiceImpl implements PromotionOfferService {

	@Autowired
	PromotionOfferMapper mapper;

	@Autowired
	PromotionMapper promotionMapper;

	@Override
	public PromotionOffer getPromotionOfferByID(String id) {
		return mapper.getPromotionOfferByID(id);
	}

	@Override
	public Page<PromotionOffer> getPromotionOfferPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<PromotionOffer> page = new Page<PromotionOffer>();
		int totalRecord = mapper.getPromotionOfferCountByCondition(map);
		List<PromotionOffer> list = mapper
				.getPromotionOfferPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Promotion> promotionCacheMap = new HashMap<String, Promotion>();
			for (PromotionOffer promotionOffer : list) {
				if (promotionOffer != null) {
					if (promotionOffer.getPromotion() != null
							&& StringUtils.isNotBlank(promotionOffer
									.getPromotion().getId())) {
						String promotionId = promotionOffer.getPromotion()
								.getId();
						Promotion promotion = null;
						if (promotionCacheMap.containsKey(promotionId)) {
							promotion = promotionCacheMap.get(promotionId);
						} else {
							promotion = promotionMapper
									.getPromotionByID(promotionId);
							if (promotion != null) {
								promotionCacheMap.put(promotionId, promotion);
							}
						}
						if (promotion != null) {
							promotionOffer.setPromotion(promotion);
						}
					}
				}
			}
		}
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<PromotionOffer> getPromotionOfferPageByCondition(
			Map<String, Object> map) {
		List<PromotionOffer> list = mapper
				.getPromotionOfferPageByCondition(map);
		return list;
	}

	@Override
	public void deletePromotionOfferByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePromotionOfferByID(oneId);
				}
			}
		}
	}

	@Override
	public void updatePromotionOffer(PromotionOffer PromotionOffer) {
		mapper.updatePromotionOffer(PromotionOffer);

	}

	@Override
	public void insertPromotionOffer(PromotionOffer PromotionOffer) {
		if (StringUtils.isBlank(PromotionOffer.getId())) {
			PromotionOffer.setId(UUID.randomUUID().toString());
		}
		mapper.insertPromotionOffer(PromotionOffer);
	}

}
