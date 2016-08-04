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
import com.joker.common.mapper.PromotionStoreMapper;
import com.joker.common.mapper.StoreMapper;
import com.joker.common.model.Store;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionStore;
import com.joker.common.service.PromotionStoreService;
import com.joker.core.dto.Page;

@Service("promotionStoreService")
public class PromotionStoreServiceImpl implements PromotionStoreService {

	@Autowired
	PromotionStoreMapper mapper;

	@Autowired
	StoreMapper storeMapper;

	@Autowired
	PromotionMapper promotionMapper;

	@Override
	public PromotionStore getPromotionStoreByID(String id) {
		return mapper.getPromotionStoreByID(id);
	}

	@Override
	public Page<PromotionStore> getPromotionStorePageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<PromotionStore> page = new Page<PromotionStore>();
		int totalRecord = mapper.getPromotionStoreCountByCondition(map);
		List<PromotionStore> list = mapper
				.getPromotionStorePageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Store> cacheMap = new HashMap<String, Store>();
			Map<String, Promotion> promotionCacheMap = new HashMap<String, Promotion>();
			for (PromotionStore PromotionStore : list) {
				if (PromotionStore != null) {
					if (PromotionStore.getStore() != null
							&& StringUtils.isNotBlank(PromotionStore.getStore()
									.getId())) {
						String storeId = PromotionStore.getStore().getId();
						Store store = null;
						if (cacheMap.containsKey(storeId)) {
							store = cacheMap.get(storeId);
						} else {
							store = storeMapper.getStoreById(storeId);
							if (store != null) {
								cacheMap.put(storeId, store);
							}
						}
						if (store != null) {
							PromotionStore.setStore(store);
						}
					}

					if (PromotionStore.getPromotion() != null
							&& StringUtils.isNotBlank(PromotionStore
									.getPromotion().getId())) {
						String promotionId = PromotionStore.getPromotion()
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
							PromotionStore.setPromotion(promotion);
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
	public List<PromotionStore> getPromotionStorePageByCondition(
			Map<String, Object> map) {
		List<PromotionStore> list = mapper
				.getPromotionStorePageByCondition(map);
		return list;
	}

	@Override
	public void deletePromotionStoreByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePromotionStoreByID(oneId);
				}
			}
		}
	}

	@Override
	public void updatePromotionStore(PromotionStore PromotionStore) {
		mapper.updatePromotionStore(PromotionStore);

	}

	@Override
	public void insertPromotionStore(PromotionStore PromotionStore) {
		if (StringUtils.isBlank(PromotionStore.getId())) {
			PromotionStore.setId(UUID.randomUUID().toString());
		}
		mapper.insertPromotionStore(PromotionStore);
	}

}
