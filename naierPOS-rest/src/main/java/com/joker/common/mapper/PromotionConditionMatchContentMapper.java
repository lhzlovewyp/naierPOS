package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.core.annotation.DataSource;

@Repository
public interface PromotionConditionMatchContentMapper {
	/**
	 * 根据id查询促销条件信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public PromotionConditionMatchContent getPromotionConditionMatchContentByID(
			String id);

	/**
	 * 查询促销条件信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<PromotionConditionMatchContent> getPromotionConditionMatchContentPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询促销条件信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getPromotionConditionMatchContentCountByCondition(
			Map<String, Object> map);

	/**
	 * 删除促销条件信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deletePromotionConditionMatchContentByID(String id);

	/**
	 * 修改促销条件信息.
	 * 
	 * @param promotionConditionMatchContent
	 * @return
	 */
	@DataSource("master")
	public void updatePromotionConditionMatchContent(
			PromotionConditionMatchContent promotionConditionMatchContent);

	/**
	 * 保存促销条件信息.
	 * 
	 * @param promotionConditionMatchContent
	 * @return
	 */
	@DataSource("master")
	public void insertPromotionConditionMatchContent(
			PromotionConditionMatchContent promotionConditionMatchContent);

}