package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.promotion.PromotionStore;
import com.joker.core.dto.Page;

public interface PromotionStoreService {

	/**
	 * 根据id查询促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public PromotionStore getPromotionStoreByID(String id);

	/**
	 * 根据商户查询促销支付限制信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<PromotionStore> getPromotionStorePageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<PromotionStore> getPromotionStorePageByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePromotionStoreByID(String id);

	/**
	 * 修改促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void updatePromotionStore(PromotionStore promotionStore);

	/**
	 * 保存促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void insertPromotionStore(PromotionStore promotionStore);
}