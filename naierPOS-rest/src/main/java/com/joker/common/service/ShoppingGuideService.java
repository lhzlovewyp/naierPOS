package com.joker.common.service;

import java.util.Map;

import com.joker.common.model.ShoppingGuide;
import com.joker.common.model.ShoppingGuide;
import com.joker.core.dto.Page;


public interface ShoppingGuideService {
	
	/**
	 * 根据id查询导购员
	 * 
	 * @param id
	 * @return
	 */
	public ShoppingGuide getShoppingGuideById(String id);
	
	/**
	 * 根据client和编码查询导购员信息.
	 * 
	 * @param clientId
	 * @param code
	 * @return
	 */
	public ShoppingGuide getShoppingGuideByCode(String clientId,String store,String code);
	
	/**
	 * 根据id查询颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	public ShoppingGuide getShoppingGuideByID(String id);

	/**
	 * 查询颜色信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<ShoppingGuide> getShoppingGuidePageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	/**
	 * 删除颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteShoppingGuideByID(String id);

	/**
	 * 修改颜色信息.
	 * 
	 * @param ShoppingGuide
	 * @return
	 */
	public void updateShoppingGuide(ShoppingGuide shoppingGuide);

	/**
	 * 保存颜色信息.
	 * 
	 * @param ShoppingGuide
	 * @return
	 */
	public void insertShoppingGuide(ShoppingGuide shoppingGuide);
	
}
