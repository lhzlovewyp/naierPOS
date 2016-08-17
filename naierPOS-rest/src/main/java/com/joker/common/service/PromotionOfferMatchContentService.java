package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.core.dto.Page;

public interface PromotionOfferMatchContentService {

	/**
	 * 根据id查询促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public PromotionOfferMatchContent getPromotionOfferMatchContentByID(
			String id);

	/**
	 * 根据商户查询促销支付限制信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<PromotionOfferMatchContent> getPromotionOfferMatchContentPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<PromotionOfferMatchContent> getPromotionOfferMatchContentPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePromotionOfferMatchContentByID(String id);

	/**
	 * 修改促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void updatePromotionOfferMatchContent(
			PromotionOfferMatchContent promotionOfferMatchContent);

	/**
	 * 保存促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void insertPromotionOfferMatchContent(
			PromotionOfferMatchContent promotionOfferMatchContent);
}