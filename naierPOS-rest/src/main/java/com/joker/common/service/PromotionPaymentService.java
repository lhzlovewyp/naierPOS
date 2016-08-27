package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.promotion.PromotionPayment;
import com.joker.core.dto.Page;

public interface PromotionPaymentService {

	/**
	 * 根据id查询促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public PromotionPayment getPromotionPaymentByID(String id);

	/**
	 * 根据商户查询促销支付限制信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<PromotionPayment> getPromotionPaymentPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<PromotionPayment> getPromotionPaymentPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销支付限制信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePromotionPaymentByID(String id);

	/**
	 * 修改促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void updatePromotionPayment(PromotionPayment promotionPayment);

	/**
	 * 保存促销支付限制信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void insertPromotionPayment(PromotionPayment promotionPayment);
}