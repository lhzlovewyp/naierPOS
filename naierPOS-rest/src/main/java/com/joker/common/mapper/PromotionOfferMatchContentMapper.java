package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.promotion.PromotionOfferMatchContent;
import com.joker.core.annotation.DataSource;

@Repository
public interface PromotionOfferMatchContentMapper {
	/**
	 * 根据id查询促销优惠匹配内容信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public PromotionOfferMatchContent getPromotionOfferMatchContentByID(
			String id);

	/**
	 * 查询促销优惠匹配内容信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<PromotionOfferMatchContent> getPromotionOfferMatchContentPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询促销优惠匹配内容信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getPromotionOfferMatchContentCountByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销优惠匹配内容信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deletePromotionOfferMatchContentByID(String id);

	/**
	 * 修改促销优惠匹配内容信息.
	 * 
	 * @param promotionOfferMatchContent
	 * @return
	 */
	@DataSource("master")
	public void updatePromotionOfferMatchContent(
			PromotionOfferMatchContent promotionOfferMatchContent);

	/**
	 * 保存促销优惠匹配内容信息.
	 * 
	 * @param promotionOfferMatchContent
	 * @return
	 */
	@DataSource("master")
	public void insertPromotionOfferMatchContent(
			PromotionOfferMatchContent promotionOfferMatchContent);

}