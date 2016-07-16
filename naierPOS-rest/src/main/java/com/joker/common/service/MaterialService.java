package com.joker.common.service;

import com.joker.common.model.Material;
import com.joker.core.dto.Page;

public interface MaterialService {
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
	 * 根据id查询物料信息.
	 * 
	 * @param id
	 * @return
	 */
	public Material getMaterialByID(String id);

	/**
	 * 根据商户查询物料信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<Material> getMaterialPageByClient(String clientId, int start,
			int limit);

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
