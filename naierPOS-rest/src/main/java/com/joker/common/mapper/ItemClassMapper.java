package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.ItemClass;
import com.joker.core.annotation.DataSource;

@Repository
public interface ItemClassMapper {
	
	
	/**
	 * 根据code查询交易类型.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public ItemClass getItemClassByCode(String code);

	/**
	 * 查询支持的交易类型.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<ItemClass> getItemClassByCondition(Map<String, Object> map);

	/**
	 * 查询交易类型.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getItemClassCountByCondition(Map<String, Object> map);

	/**
	 * 删除支付方式.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteItemClassByCode(String code);

	/**
	 * 修改支付方式.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void updateItemClass(ItemClass itemClass);

	/**
	 * 保存支付方式.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void insertItemClass(ItemClass itemClass);
}