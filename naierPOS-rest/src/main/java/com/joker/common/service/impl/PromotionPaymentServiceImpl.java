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
import com.joker.common.mapper.ClientPaymentMapper;
import com.joker.common.mapper.PromotionMapper;
import com.joker.common.mapper.PromotionPaymentMapper;
import com.joker.common.model.ClientPayment;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionPayment;
import com.joker.common.service.PromotionPaymentService;
import com.joker.core.dto.Page;

@Service("promotionPaymentService")
public class PromotionPaymentServiceImpl implements PromotionPaymentService {

	@Autowired
	PromotionPaymentMapper mapper;

	@Autowired
	ClientPaymentMapper clientPaymentMapper;

	@Autowired
	PromotionMapper promotionMapper;

	@Override
	public PromotionPayment getPromotionPaymentByID(String id) {
		return mapper.getPromotionPaymentByID(id);
	}

	@Override
	public Page<PromotionPayment> getPromotionPaymentPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<PromotionPayment> page = new Page<PromotionPayment>();
		int totalRecord = mapper.getPromotionPaymentCountByCondition(map);
		List<PromotionPayment> list = mapper
				.getPromotionPaymentPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, ClientPayment> cacheMap = new HashMap<String, ClientPayment>();
			Map<String, Promotion> promotionCacheMap = new HashMap<String, Promotion>();
			for (PromotionPayment promotionPayment : list) {
				if (promotionPayment != null) {
					if (promotionPayment.getClientPayment() != null
							&& StringUtils.isNotBlank(promotionPayment
									.getClientPayment().getId())) {
						String clientPaymentId = promotionPayment
								.getClientPayment().getId();
						ClientPayment clientPayment = null;
						if (cacheMap.containsKey(clientPaymentId)) {
							clientPayment = cacheMap.get(clientPaymentId);
						} else {
							clientPayment = clientPaymentMapper
									.getClientPaymentByID(clientPaymentId);
							if (clientPayment != null
									&& clientPayment.getPayment() != null) {
								cacheMap.put(clientPaymentId, clientPayment);
							}
						}
						if (clientPayment != null) {
							promotionPayment.setClientPayment(clientPayment);
						}
					}

					if (promotionPayment.getPromotion() != null
							&& StringUtils.isNotBlank(promotionPayment
									.getPromotion().getId())) {
						String promotionId = promotionPayment.getPromotion()
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
							promotionPayment.setPromotion(promotion);
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
	public List<PromotionPayment> getPromotionPaymentPageByCondition(
			Map<String, Object> map) {
		List<PromotionPayment> list = mapper
				.getPromotionPaymentPageByCondition(map);
		return list;
	}

	@Override
	public void deletePromotionPaymentByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePromotionPaymentByID(oneId);
				}
			}
		}
	}

	@Override
	public void updatePromotionPayment(PromotionPayment promotionPayment) {
		mapper.updatePromotionPayment(promotionPayment);

	}

	@Override
	public void insertPromotionPayment(PromotionPayment promotionPayment) {
		if (StringUtils.isBlank(promotionPayment.getId())) {
			promotionPayment.setId(UUID.randomUUID().toString());
		}
		mapper.insertPromotionPayment(promotionPayment);
	}

}
