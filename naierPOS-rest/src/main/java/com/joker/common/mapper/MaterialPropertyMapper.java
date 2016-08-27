package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.MaterialProperty;
import com.joker.core.annotation.DataSource;

@Repository
public interface MaterialPropertyMapper {

	/**
	 * 根据id查询物料属性信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public List<MaterialProperty> getMaterialPropertyByCondition(
			Map<String, String> map);

	/**
	 * 根据id查询物料属性信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public MaterialProperty getMaterialPropertyByID(String id);

	/**
	 * 查询物料属性信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<MaterialProperty> getMaterialPropertyPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询物料属性信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getMaterialPropertyCountByCondition(Map<String, Object> map);

	/**
	 * 删除物料属性信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteMaterialPropertyByID(String id);

	/**
	 * 修改物料属性信息.
	 * 
	 * @param materialProperty
	 * @return
	 */
	@DataSource("master")
	public void updateMaterialProperty(MaterialProperty materialProperty);

	/**
	 * 保存物料属性信息.
	 * 
	 * @param materialProperty
	 * @return
	 */
	@DataSource("master")
	public void insertMaterialProperty(MaterialProperty materialProperty);
}
