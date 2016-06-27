package com.joker.common.service;

import com.joker.common.model.ShoppingGuide;


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
	public ShoppingGuide getShoppingGuideByCode(String clientId,String code);
	
	
}
