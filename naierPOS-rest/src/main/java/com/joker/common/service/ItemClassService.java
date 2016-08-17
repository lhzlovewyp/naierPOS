package com.joker.common.service;

import java.util.Map;

import com.joker.common.model.ItemClass;
import com.joker.core.dto.Page;

public interface ItemClassService {
	
	
	/**
	 * 根据code查询交易类型.
	 * 
	 * @param id
	 * @return
	 */
	public ItemClass getItemClassByCode(String code);

	/**
	 * 查询交易类型.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<ItemClass> getItemClassByCondition(Map<String, Object> map,
			int pageNo, int limit);

	/**
	 * 删除交易类型.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteItemClassByCode(String code);

	/**
	 * 修改交易类型.
	 * 
	 * @param ItemClass
	 * @return
	 */
	public void updateItemClass(ItemClass itemClass);

	/**
	 * 保存交易类型.
	 * 
	 * @param ItemClass
	 * @return
	 */
	public void insertItemClass(ItemClass itemClass);
}