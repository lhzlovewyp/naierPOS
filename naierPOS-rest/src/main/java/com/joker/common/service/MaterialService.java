package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Material;
import com.joker.core.dto.Page;

public interface MaterialService {
	
	/**
	 * 根据code动态查询数据.
	 * 先从商品属性表里面查询数据,如果商品属性找不到，从商品表里面的barcode查找，如果再找不到,根据code查找.
	 * 
	 * 
	 * @param clientId
	 * @param code
	 * @return
	 */
	public Material getDynMaterial(String clientId,String code);
	
	/**
	 * 查询物料信息.
	 * 
	 * @param username
	 * @return
	 */
	public Material getMaterialByCode(String clientId, String code);

	/**
	 * 查询物料信息.
	 * 
	 * @param username
	 * @return
	 */
	public Material getMaterialByBarCode(String clientId, String barCode);

	/**
	 * 查询物料信息.
	 * 
	 * @param username
	 * @return
	 */
	public Material getMaterialById(String id);

	/**
	 * 根据id查询物料信息.
	 * 
	 * @param id
	 * @return
	 */
	public Material getMaterialByID(String id);

	/**
	 * 根据商户查询物料信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Material> getMaterialPageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	/**
	 * 根据商户查询物料信息.
	 * 
	 * @param map
	 * @return
	 */
	public List<Material> getMaterialPageByCondition(Map<String, Object> map);

	/**
	 * 删除物料信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteMaterialByID(String id);

	/**
	 * 修改物料信息.
	 * 
	 * @param materialCategory
	 * @return
	 */
	public void updateMaterial(Material material);

	/**
	 * 保存物料信息.
	 * 
	 * @param materialCategory
	 * @return
	 */
	public void insertMaterial(Material material);
}



