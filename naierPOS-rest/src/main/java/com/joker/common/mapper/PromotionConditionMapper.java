package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.promotion.PromotionCondition;
import com.joker.core.annotation.DataSource;

@Repository
public interface PromotionConditionMapper {
	/**
	 * 根据id查询促销条件信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public PromotionCondition getPromotionConditionByID(String id);

	/**
	 * 查询促销条件信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<PromotionCondition> getPromotionConditionPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询促销条件信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getPromotionConditionCountByCondition(Map<String, Object> map);

	/**
	 * 删除促销条件信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deletePromotionConditionByID(String id);

	/**
	 * 修改促销条件信息.
	 * 
	 * @param promotionCondition
	 * @return
	 */
	@DataSource("master")
	public void updatePromotionCondition(PromotionCondition promotionCondition);

	/**
	 * 保存促销条件信息.
	 * 
	 * @param promotionCondition
	 * @return
	 */
	@DataSource("master")
	public void insertPromotionCondition(PromotionCondition promotionCondition);

}