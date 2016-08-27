/**
 * 
 */
package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.MaterialCategory;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
public interface MaterialCategoryService {

	/**
	 * 根据id查询品类信息.
	 * 
	 * @param id
	 * @return
	 */
	public MaterialCategory getMaterialCategoryByID(String id);

	/**
	 * 查询品类信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<MaterialCategory> getMaterialCategoryPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	/**
	 * 查询品类信息.
	 * 
	 * @param map
	 * @return
	 */
	public List<MaterialCategory> getMaterialCategoryPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除品类信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteMaterialCategoryByID(String id);

	/**
	 * 修改品类信息.
	 * 
	 * @param materialCategory
	 * @return
	 */
	public void updateMaterialCategory(MaterialCategory materialCategory);

	/**
	 * 保存品类信息.
	 * 
	 * @param materialCategory
	 * @return
	 */
	public void insertMaterialCategory(MaterialCategory materialCategory);
}
