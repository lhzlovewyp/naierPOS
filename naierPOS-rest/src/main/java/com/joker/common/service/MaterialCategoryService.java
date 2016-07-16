/**
 * 
 */
package com.joker.common.service;

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
	 * 根据商户查询品类信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<MaterialCategory> getMaterialCategoryPageByClient(
			String clientId, int start, int limit);

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
