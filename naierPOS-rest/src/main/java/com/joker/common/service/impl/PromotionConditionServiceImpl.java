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
import com.joker.common.mapper.PromotionConditionMapper;
import com.joker.common.mapper.PromotionOfferMapper;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.service.PromotionConditionService;
import com.joker.core.dto.Page;

@Service("PromotionConditionService")
public class PromotionConditionServiceImpl implements PromotionConditionService {

	@Autowired
	PromotionConditionMapper mapper;

	@Autowired
	PromotionOfferMapper promotionOfferMapper;

	@Override
	public PromotionCondition getPromotionConditionByID(String id) {
		return mapper.getPromotionConditionByID(id);
	}

	@Override
	public Page<PromotionCondition> getPromotionConditionPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<PromotionCondition> page = new Page<PromotionCondition>();
		int totalRecord = mapper.getPromotionConditionCountByCondition(map);
		List<PromotionCondition> list = mapper
				.getPromotionConditionPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<PromotionCondition> getPromotionConditionPageByCondition(
			Map<String, Object> map) {
		List<PromotionCondition> list = mapper
				.getPromotionConditionPageByCondition(map);
		return list;
	}

	@Override
	public void deletePromotionConditionByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePromotionConditionByID(oneId);
				}
			}
		}
	}

	@Override
	public void updatePromotionCondition(PromotionCondition PromotionCondition) {
		mapper.updatePromotionCondition(PromotionCondition);

	}

	@Override
	public void insertPromotionCondition(PromotionCondition PromotionCondition) {
		if (StringUtils.isBlank(PromotionCondition.getId())) {
			PromotionCondition.setId(UUID.randomUUID().toString());
		}
		mapper.insertPromotionCondition(PromotionCondition);
	}

}
