package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	 * 根据商户查询品类信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<MaterialCategory> getMaterialCategoryByClient(
			@Param("clientId") String clientId, @Param("start") int start,
			@Param("limit") int limit);

	/**
	 * 根据商户查询品类信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getMaterialCategoryCountByClient(
			@Param("clientId") String clientId);

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
