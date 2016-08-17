package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.promotion.PromotionOffer;
import com.joker.core.dto.Page;

public interface PromotionOfferService {

	/**
	 * 根据id查询促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public PromotionOffer getPromotionOfferByID(String id);

	/**
	 * 根据商户查询促销支付限制信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<PromotionOffer> getPromotionOfferPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<PromotionOffer> getPromotionOfferPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePromotionOfferByID(String id);

	/**
	 * 修改促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void updatePromotionOffer(PromotionOffer promotionOffer);

	/**
	 * 保存促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void insertPromotionOffer(PromotionOffer promotionOffer);
}