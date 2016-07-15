package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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
	 * 根据商户查询物料信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<Material> getMaterialByClient(
			@Param("clientId") String clientId, @Param("start") int start,
			@Param("limit") int limit);

	/**
	 * 根据商户查询物料信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getMaterialCountByClient(@Param("clientId") String clientId);

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