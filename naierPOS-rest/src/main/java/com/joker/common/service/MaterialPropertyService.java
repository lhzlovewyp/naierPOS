package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.MaterialProperty;
import com.joker.core.dto.Page;

public interface MaterialPropertyService {

	/**
	 * 查询物料下面的所有商品属性.
	 * 
	 * @param clientId
	 * @param materialId
	 * @return
	 */
	public List<MaterialProperty> getMaterialPropertyById(String clientId,
			String materialId);

	/**
	 * 查询物料下面的所有商品属性.
	 * 
	 * @param clientId
	 * @param materialCode
	 * @return
	 */
	public List<MaterialProperty> getMaterialPropertyByCode(String clientId,
			String materialCode);

	/**
	 * 查询物料下面的所有商品属性.
	 * 
	 * @param clientId
	 * @param barCode
	 *            条形码
	 * @return
	 */
	public List<MaterialProperty> getMaterialPropertyByBarCode(String clientId,
			String barCode);

	/**
	 * 根据id查询物料属性信息.
	 * 
	 * @param id
	 * @return
	 */
	public MaterialProperty getMaterialPropertyByID(String id);

	/**
	 * 根据商户查询物料属性信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<MaterialProperty> getMaterialPropertyPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<MaterialProperty> getMaterialPropertyPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除物料属性信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteMaterialPropertyByID(String id);

	/**
	 * 修改物料属性信息.
	 * 
	 * @param materialProperty
	 * @return
	 */
	public void updateMaterialProperty(MaterialProperty materialProperty);

	/**
	 * 保存物料属性信息.
	 * 
	 * @param materialProperty
	 * @return
	 */
	public void insertMaterialProperty(MaterialProperty materialProperty);
}
