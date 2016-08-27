package com.joker.common.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.common.model.promotion.PromotionPayment;
import com.joker.core.annotation.DataSource;

@Repository
public interface PromotionMapper {

	/**
	 * 
	 * 查询当前门店可用的促销活动.
	 * 
	 * @param clientId
	 * @param storeId
	 * @return
	 */
	@DataSource("slave")
	public List<Promotion> getPromotionsByStore(
			@Param("clientId") String clientId,
			@Param("storeId") String storeId, @Param("saleDate") Date saleDate);

	/**
	 * 根据促销活动id获取促销促销支付限制.
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<PromotionPayment> getPromoPaymentByPromoId(
			@Param("promotionId") String promotionId);

	/**
	 * 获取促销活动下面的促销优惠.
	 * 
	 * @param promotionId
	 * @return
	 */
	public List<PromotionOffer> getPromoOfferbyPromoId(
			@Param("promotionId") String promotionId);

	/**
	 * 获取促销优惠匹配内容.
	 * 
	 * @param offerId
	 * @return
	 */
	public List<PromotionOfferMatchContent> getMatchContentByOfferId(
			@Param("offerId") String offerId);

	/**
	 * 获取促销条件.
	 * 
	 * @param offerId
	 * @return
	 */
	public List<PromotionCondition> getConditionByOfferId(
			@Param("offerId") String offerId);

	/**
	 * 获取促销条件匹配内容.
	 * 
	 * @param offerId
	 * @return
	 */
	public List<PromotionConditionMatchContent> getMatchContentByConditionId(
			@Param("conditionId") String conditionId);

	/**
	 * 根据id查询促销信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Promotion getPromotionByID(String id);

	/**
	 * 查询促销信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Promotion> getPromotionPageByCondition(Map<String, Object> map);

	/**
	 * 查询促销信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getPromotionCountByCondition(Map<String, Object> map);

	/**
	 * 删除促销信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deletePromotionByID(String id);

	/**
	 * 修改促销信息.
	 * 
	 * @param promotion
	 * @return
	 */
	@DataSource("master")
	public void updatePromotion(Promotion promotion);

	/**
	 * 保存促销信息.
	 * 
	 * @param promotion
	 * @return
	 */
	@DataSource("master")
	public void insertPromotion(Promotion promotion);

}