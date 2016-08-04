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
import com.joker.common.mapper.PromotionOfferMapper;
import com.joker.common.mapper.PromotionOfferMatchContentMapper;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.common.service.PromotionOfferMatchContentService;
import com.joker.core.dto.Page;

@Service("PromotionOfferMatchContentService")
public class PromotionOfferMatchContentServiceImpl implements
		PromotionOfferMatchContentService {

	@Autowired
	PromotionOfferMatchContentMapper mapper;

	@Autowired
	PromotionOfferMapper promotionOfferMapper;

	@Override
	public PromotionOfferMatchContent getPromotionOfferMatchContentByID(
			String id) {
		return mapper.getPromotionOfferMatchContentByID(id);
	}

	@Override
	public Page<PromotionOfferMatchContent> getPromotionOfferMatchContentPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<PromotionOfferMatchContent> page = new Page<PromotionOfferMatchContent>();
		int totalRecord = mapper
				.getPromotionOfferMatchContentCountByCondition(map);
		List<PromotionOfferMatchContent> list = mapper
				.getPromotionOfferMatchContentPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, PromotionOffer> promotionCacheMap = new HashMap<String, PromotionOffer>();
			for (PromotionOfferMatchContent PromotionOfferMatchContent : list) {
				if (PromotionOfferMatchContent != null) {
					if (PromotionOfferMatchContent.getPromotionOffer() != null
							&& StringUtils
									.isNotBlank(PromotionOfferMatchContent
											.getPromotionOffer().getId())) {
						String promotionOfferId = PromotionOfferMatchContent
								.getPromotionOffer().getId();
						PromotionOffer promotionOffer = null;
						if (promotionCacheMap.containsKey(promotionOfferId)) {
							promotionOffer = promotionCacheMap
									.get(promotionOfferId);
						} else {
							promotionOffer = promotionOfferMapper
									.getPromotionOfferByID(promotionOfferId);
							if (promotionOffer != null) {
								promotionCacheMap.put(promotionOfferId,
										promotionOffer);
							}
						}
						if (promotionOffer != null) {
							PromotionOfferMatchContent
									.setPromotionOffer(promotionOffer);
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
	public List<PromotionOfferMatchContent> getPromotionOfferMatchContentPageByCondition(
			Map<String, Object> map) {
		List<PromotionOfferMatchContent> list = mapper
				.getPromotionOfferMatchContentPageByCondition(map);
		return list;
	}

	@Override
	public void deletePromotionOfferMatchContentByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePromotionOfferMatchContentByID(oneId);
				}
			}
		}
	}

	@Override
	public void updatePromotionOfferMatchContent(
			PromotionOfferMatchContent PromotionOfferMatchContent) {
		mapper.updatePromotionOfferMatchContent(PromotionOfferMatchContent);

	}

	@Override
	public void insertPromotionOfferMatchContent(
			PromotionOfferMatchContent PromotionOfferMatchContent) {
		if (StringUtils.isBlank(PromotionOfferMatchContent.getId())) {
			PromotionOfferMatchContent.setId(UUID.randomUUID().toString());
		}
		mapper.insertPromotionOfferMatchContent(PromotionOfferMatchContent);
	}

}
