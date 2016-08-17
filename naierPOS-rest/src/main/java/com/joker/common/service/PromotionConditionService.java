package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.promotion.PromotionCondition;
import com.joker.core.dto.Page;

public interface PromotionConditionService {

	/**
	 * 根据id查询促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public PromotionCondition getPromotionConditionByID(String id);

	/**
	 * 根据商户查询促销支付限制信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<PromotionCondition> getPromotionConditionPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<PromotionCondition> getPromotionConditionPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePromotionConditionByID(String id);

	/**
	 * 修改促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void updatePromotionCondition(PromotionCondition promotionCondition);

	/**
	 * 保存促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void insertPromotionCondition(PromotionCondition promotionCondition);
}