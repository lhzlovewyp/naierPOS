package com.joker.common.service;


import com.joker.common.model.RetailConfig;

public interface RetailConfigService {

	/**
	 * 获取当前销售配置信息.
	 * @param clientId
	 * @return
	 */
	public RetailConfig getRetailConfig(String clientId);

}
