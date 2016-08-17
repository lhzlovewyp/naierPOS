package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.MaterialCategory;
import com.joker.core.annotation.DataSource;

@Repository
public interface MaterialCategoryMapper {
	/**
	 * 根据id查询品类信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public MaterialCategory getMaterialCategoryByID(String id);

	/**
	 * 查询品类信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<MaterialCategory> getMaterialCategoryPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询品类信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getMaterialCategoryCountByCondition(Map<String, Object> map);

	/**
	 * 删除品类信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteMaterialCategoryByID(String id);

	/**
	 * 修改品类信息.
	 * 
	 * @param materialCategory
	 * @return
	 */
	@DataSource("master")
	public void updateMaterialCategory(MaterialCategory materialCategory);

	/**
	 * 保存品类信息.
	 * 
	 * @param materialCategory
	 * @return
	 */
	@DataSource("master")
	public void insertMaterialCategory(MaterialCategory materialCategory);
}
