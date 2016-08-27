package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.core.dto.Page;

public interface PromotionConditionMatchContentService {

	/**
	 * 根据id查询促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public PromotionConditionMatchContent getPromotionConditionMatchContentByID(
			String id);

	/**
	 * 根据商户查询促销支付限制信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<PromotionConditionMatchContent> getPromotionConditionMatchContentPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<PromotionConditionMatchContent> getPromotionConditionMatchContentPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePromotionConditionMatchContentByID(String id);

	/**
	 * 修改促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void updatePromotionConditionMatchContent(
			PromotionConditionMatchContent PromotionConditionMatchContent);

	/**
	 * 保存促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void insertPromotionConditionMatchContent(
			PromotionConditionMatchContent PromotionConditionMatchContent);
}