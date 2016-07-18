package com.joker.common.mapper;

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
	public RetailConfig getRetailConfig(
			@Param("clientId") String clientId);

	
}
