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
import com.joker.common.mapper.PromotionConditionMapper;
import com.joker.common.mapper.PromotionConditionMatchContentMapper;
import com.joker.common.model.Brand;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialCategory;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.common.service.PromotionConditionMatchContentService;
import com.joker.core.dto.Page;

@Service("PromotionConditionMatchContentService")
public class PromotionConditionMatchContentServiceImpl implements
		PromotionConditionMatchContentService {

	@Autowired
	PromotionConditionMatchContentMapper mapper;

	@Autowired
	PromotionConditionMapper promotionConditionMapper;

	@Autowired
	MaterialCategoryMapper materialCategoryMapper;

	@Autowired
	BrandMapper brandMapper;

	@Autowired
	MaterialMapper materialMapper;

	@Override
	public PromotionConditionMatchContent getPromotionConditionMatchContentByID(
			String id) {
		PromotionConditionMatchContent promotionConditionMatchContent = mapper
				.getPromotionConditionMatchContentByID(id);
		if (promotionConditionMatchContent != null
				&& promotionConditionMatchContent.getPromotionCondition() != null
				&& StringUtils.isNotBlank(promotionConditionMatchContent
						.getPromotionCondition().getId())) {
			String promotionOfferId = promotionConditionMatchContent
					.getPromotionCondition().getId();
			PromotionCondition promotionCondition = promotionConditionMapper
					.getPromotionConditionByID(promotionOfferId);
			if (promotionCondition != null) {
				promotionConditionMatchContent
						.setPromotionCondition(promotionCondition);
			}
		}
		return promotionConditionMatchContent;
	}

	@Override
	public Page<PromotionConditionMatchContent> getPromotionConditionMatchContentPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<PromotionConditionMatchContent> page = new Page<PromotionConditionMatchContent>();
		int totalRecord = mapper
				.getPromotionConditionMatchContentCountByCondition(map);
		List<PromotionConditionMatchContent> list = mapper
				.getPromotionConditionMatchContentPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, PromotionCondition> promotionCacheMap = new HashMap<String, PromotionCondition>();
			for (PromotionConditionMatchContent promotionConditionMatchContent : list) {
				if (promotionConditionMatchContent != null) {
					if (promotionConditionMatchContent.getPromotionCondition() != null
							&& StringUtils
									.isNotBlank(promotionConditionMatchContent
											.getPromotionCondition().getId())) {
						String promotionConditionId = promotionConditionMatchContent
								.getPromotionCondition().getId();
						PromotionCondition promotionCondition = null;
						if (promotionCacheMap.containsKey(promotionConditionId)) {
							promotionCondition = promotionCacheMap
									.get(promotionConditionId);
						} else {
							promotionCondition = promotionConditionMapper
									.getPromotionConditionByID(promotionConditionId);
							if (promotionCondition != null) {
								promotionCacheMap.put(promotionConditionId,
										promotionCondition);
							}
						}
						if (promotionCondition != null) {
							promotionConditionMatchContent
									.setPromotionCondition(promotionCondition);
							if (StringUtils
									.isNotBlank(promotionConditionMatchContent
											.getMatchContent())) {
								if (Constants.PROMOTION_TYPE_MATCAT
										.equals(promotionCondition
												.getMatchType())) {
									MaterialCategory materialCategory = materialCategoryMapper
											.getMaterialCategoryByID(promotionConditionMatchContent
													.getMatchContent());
									if (materialCategory != null) {
										promotionConditionMatchContent
												.setMatchContentDesc(materialCategory
														.getName());
									}

								} else if (Constants.PROMOTION_TYPE_BRAND
										.equals(promotionCondition
												.getMatchType())) {
									Brand brand = brandMapper
											.getBrandByID(promotionConditionMatchContent
													.getMatchContent());
									if (brand != null) {
										promotionConditionMatchContent
												.setMatchContentDesc(brand
														.getName());
									}

								} else if (Constants.PROMOTION_TYPE_MAT
										.equals(promotionCondition
												.getMatchType())) {
									Material material = materialMapper
											.getMaterialByID(promotionConditionMatchContent
													.getMatchContent());
									if (material != null) {
										promotionConditionMatchContent
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
	public List<PromotionConditionMatchContent> getPromotionConditionMatchContentPageByCondition(
			Map<String, Object> map) {
		List<PromotionConditionMatchContent> list = mapper
				.getPromotionConditionMatchContentPageByCondition(map);
		return list;
	}

	@Override
	public void deletePromotionConditionMatchContentByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePromotionConditionMatchContentByID(oneId);
				}
			}
		}
	}

	@Override
	public void updatePromotionConditionMatchContent(
			PromotionConditionMatchContent PromotionConditionMatchContent) {
		mapper.updatePromotionConditionMatchContent(PromotionConditionMatchContent);

	}

	@Override
	public void insertPromotionConditionMatchContent(
			PromotionConditionMatchContent PromotionConditionMatchContent) {
		if (StringUtils.isBlank(PromotionConditionMatchContent.getId())) {
			PromotionConditionMatchContent.setId(UUID.randomUUID().toString());
		}
		mapper.insertPromotionConditionMatchContent(PromotionConditionMatchContent);
	}

}
