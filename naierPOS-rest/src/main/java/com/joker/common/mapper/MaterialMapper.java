package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Material;
import com.joker.core.annotation.DataSource;

@Repository
public interface MaterialMapper {

	/**
	 * 根据id查询物料信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Material getMaterialByCondition(Map<String, String> map);

	/**
	 * 根据id查询物料信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Material getMaterialByID(String id);

	/**
	 * 查询物料信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Material> getMaterialPageByCondition(Map<String, Object> map);

	/**
	 * 查询物料信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getMaterialCountByCondition(Map<String, Object> map);

	/**
	 * 删除物料信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteMaterialByID(String id);

	/**
	 * 修改物料信息.
	 * 
	 * @param brand
	 * @return
	 */
	@DataSource("master")
	public void updateMaterial(Material material);

	/**
	 * 保存物料信息.
	 * 
	 * @param brand
	 * @return
	 */
	@DataSource("master")
	public void insertMaterial(Material material);
}