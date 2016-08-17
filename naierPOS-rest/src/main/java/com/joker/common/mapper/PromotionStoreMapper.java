package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.promotion.PromotionStore;
import com.joker.core.annotation.DataSource;

@Repository
public interface PromotionStoreMapper {
	/**
	 * 根据id查询促销门店信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public PromotionStore getPromotionStoreByID(String id);

	/**
	 * 查询促销门店信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<PromotionStore> getPromotionStorePageByCondition(
			Map<String, Object> map);

	/**
	 * 查询促销门店信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getPromotionStoreCountByCondition(Map<String, Object> map);

	/**
	 * 删除促销门店信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deletePromotionStoreByID(String id);

	/**
	 * 修改促销门店信息.
	 * 
	 * @param promotionStore
	 * @return
	 */
	@DataSource("master")
	public void updatePromotionStore(PromotionStore promotionStore);

	/**
	 * 保存促销门店信息.
	 * 
	 * @param promotionStore
	 * @return
	 */
	@DataSource("master")
	public void insertPromotionStore(PromotionStore promotionStore);

}