package com.joker.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.common.model.promotion.PromotionPayment;
import com.joker.core.dto.Page;

public interface PromotionService {

	/**
	 * 获取当前门店生效的促销活动.
	 * 
	 * @param clientId
	 * @param storeId
	 * @param saleDate
	 * @return
	 */
	public List<Promotion> getPromotionsByStore(String clientId,
			String storeId, Date saleDate);

	/**
	 * 根据促销活动id获取促销促销支付限制.
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<PromotionPayment> getPromoPaymentByPromoId(String promotionId);

	/**
	 * 获取促销活动下面的促销优惠.
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<PromotionOffer> getPromoOfferbyPromoId(String promotionId);

	/**
	 * 获取促销优惠匹配内容.
	 * 
	 * @param offerId
	 * @return
	 */
	public List<PromotionOfferMatchContent> getMatchContentByOfferId(
			String offerId);

	/**
	 * 获取促销条件.
	 * 
	 * @param offerId
	 * @return
	 */
	public List<PromotionCondition> getConditionByOfferId(String offerId);

	/**
	 * 获取促销条件匹配内容.
	 * 
	 * @param offerId
	 * @return
	 */
	public List<PromotionConditionMatchContent> getMatchContentByConditionId(
			String conditionId);

	/**
	 * 根据id查询促销信息.
	 * 
	 * @param id
	 * @return
	 */
	public Promotion getPromotionByID(String id);

	/**
	 * 根据商户查询促销信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Promotion> getPromotionPageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	public List<Promotion> getPromotionPageByCondition(Map<String, Object> map);

	/**
	 * 删除促销信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePromotionByID(String id);

	/**
	 * 修改促销信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void updatePromotion(Promotion promotion);

	/**
	 * 保存促销信息.
	 * 
	 * @param promotion
	 * @return
	 */
	public void insertPromotion(Promotion promotion);
}