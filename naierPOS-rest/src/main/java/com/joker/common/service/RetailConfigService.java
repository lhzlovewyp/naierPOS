package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.RetailConfig;
import com.joker.core.dto.Page;

public interface RetailConfigService {

	/**
	 * 获取当前销售配置信息.
	 * 
	 * @param clientId
	 * @return
	 */
	public RetailConfig getRetailConfig(String clientId);

	/**
	 * 根据id查询销售配置信息.
	 * 
	 * @param id
	 * @return
	 */
	public RetailConfig getRetailConfigByID(String id);

	/**
	 * 根据商户查询销售配置信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<RetailConfig> getRetailConfigPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<RetailConfig> getRetailConfigPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除销售配置信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteRetailConfigByID(String id);

	/**
	 * 修改销售配置信息.
	 * 
	 * @param terminal
	 * @return
	 */
	public void updateRetailConfig(RetailConfig terminal);

	/**
	 * 保存销售配置信息.
	 * 
	 * @param terminal
	 * @return
	 */
	public void insertRetailConfig(RetailConfig terminal);
}