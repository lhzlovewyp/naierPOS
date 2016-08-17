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
import com.joker.common.mapper.BrandMapper;
import com.joker.common.mapper.MaterialCategoryMapper;
import com.joker.common.mapper.MaterialMapper;
import com.joker.common.mapper.PromotionOfferMapper;
import com.joker.common.mapper.PromotionOfferMatchContentMapper;
import com.joker.common.model.Brand;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialCategory;
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

	@Autowired
	MaterialCategoryMapper materialCategoryMapper;

	@Autowired
	BrandMapper brandMapper;

	@Autowired
	MaterialMapper materialMapper;

	@Override
	public PromotionOfferMatchContent getPromotionOfferMatchContentByID(
			String id) {
		PromotionOfferMatchContent promotionOfferMatchContent = mapper
				.getPromotionOfferMatchContentByID(id);
		if (promotionOfferMatchContent != null
				&& promotionOfferMatchContent.getPromotionOffer() != null
				&& StringUtils.isNotBlank(promotionOfferMatchContent
						.getPromotionOffer().getId())) {
			String promotionOfferId = promotionOfferMatchContent
					.getPromotionOffer().getId();
			PromotionOffer promotionOffer = promotionOfferMapper
					.getPromotionOfferByID(promotionOfferId);
			if (promotionOffer != null) {
				promotionOfferMatchContent.setPromotionOffer(promotionOffer);
			}
		}
		return promotionOfferMatchContent;
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
			for (PromotionOfferMatchContent promotionOfferMatchContent : list) {
				if (promotionOfferMatchContent != null) {
					if (promotionOfferMatchContent.getPromotionOffer() != null
							&& StringUtils
									.isNotBlank(promotionOfferMatchContent
											.getPromotionOffer().getId())) {
						String promotionOfferId = promotionOfferMatchContent
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
							promotionOfferMatchContent
									.setPromotionOffer(promotionOffer);
							if (StringUtils
									.isNotBlank(promotionOfferMatchContent
											.getMatchContent())) {
								if (Constants.PROMOTION_TYPE_MATCAT
										.equals(promotionOffer.getMatchType())) {
									MaterialCategory materialCategory = materialCategoryMapper
											.getMaterialCategoryByID(promotionOfferMatchContent
													.getMatchContent());
									if (materialCategory != null) {
										promotionOfferMatchContent
												.setMatchContentDesc(materialCategory
														.getName());
									}

								} else if (Constants.PROMOTION_TYPE_BRAND
										.equals(promotionOffer.getMatchType())) {
									Brand brand = brandMapper
											.getBrandByID(promotionOfferMatchContent
													.getMatchContent());
									if (brand != null) {
										promotionOfferMatchContent
												.setMatchContentDesc(brand
														.getName());
									}

								} else if (Constants.PROMOTION_TYPE_MAT
										.equals(promotionOffer.getMatchType())) {
									Material material = materialMapper
											.getMaterialByID(promotionOfferMatchContent
													.getMatchContent());
									if (material != null) {
										promotionOfferMatchContent
												.setMatchContentDesc(material
														.getName());
									}

								}
							}
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
