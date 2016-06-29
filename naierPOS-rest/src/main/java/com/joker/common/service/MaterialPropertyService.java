package com.joker.common.service;

import java.util.List;

import com.joker.common.model.MaterialProperty;

public interface MaterialPropertyService {
	
	/**
	 * 查询物料下面的所有商品属性.
	 * @param clientId
	 * @param materialId
	 * @return
	 */
	public List<MaterialProperty> getMaterialPropertyById(String clientId,String materialId);
	/**
	 * 查询物料下面的所有商品属性.
	 * @param clientId
	 * @param materialCode
	 * @return
	 */
	public List<MaterialProperty> getMaterialPropertyByCode(String clientId,String materialCode);
	
	/**
	 * 查询物料下面的所有商品属性.
	 * @param clientId
	 * @param barCode 条形码
	 * @return
	 */
	public List<MaterialProperty> getMaterialPropertyByBarCode(String clientId,String barCode);
	
	
}
