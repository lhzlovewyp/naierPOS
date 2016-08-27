package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.RetailConfig;
import com.joker.core.annotation.DataSource;

@Repository
public interface RetailConfigMapper {

	/**
	 * 获取当前商户的参数设置.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public RetailConfig getRetailConfig(@Param("clientId") String clientId);

	/**
	 * 根据id查询零售参数信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public RetailConfig getRetailConfigByID(String id);

	/**
	 * 查询零售参数信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<RetailConfig> getRetailConfigPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询零售参数信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getRetailConfigCountByCondition(Map<String, Object> map);

	/**
	 * 删除零售参数信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteRetailConfigByID(String id);

	/**
	 * 修改零售参数信息.
	 * 
	 * @param retailConfig
	 * @return
	 */
	@DataSource("master")
	public void updateRetailConfig(RetailConfig retailConfig);

	/**
	 * 保存零售参数信息.
	 * 
	 * @param retailConfig
	 * @return
	 */
	@DataSource("master")
	public void insertRetailConfig(RetailConfig retailConfig);
}
