package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.promotion.PromotionOffer;
import com.joker.core.annotation.DataSource;

@Repository
public interface PromotionOfferMapper {
	/**
	 * 根据id查询促销优惠信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public PromotionOffer getPromotionOfferByID(String id);

	/**
	 * 查询促销优惠信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<PromotionOffer> getPromotionOfferPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询促销优惠信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getPromotionOfferCountByCondition(Map<String, Object> map);

	/**
	 * 删除促销优惠信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deletePromotionOfferByID(String id);

	/**
	 * 修改促销优惠信息.
	 * 
	 * @param promotionOffer
	 * @return
	 */
	@DataSource("master")
	public void updatePromotionOffer(PromotionOffer promotionOffer);

	/**
	 * 保存促销优惠信息.
	 * 
	 * @param promotionOffer
	 * @return
	 */
	@DataSource("master")
	public void insertPromotionOffer(PromotionOffer promotionOffer);

}