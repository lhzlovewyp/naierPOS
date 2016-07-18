package com.joker.common.service.impl;

import com.joker.common.mapper.RetailConfigMapper;
import com.joker.common.model.RetailConfig;
import com.joker.common.service.RetailConfigService;

public class RetailConfigServiceImpl implements RetailConfigService{

	
	RetailConfigMapper mapper;
	
	@Override
	public RetailConfig getRetailConfig(String clientId) {
		return mapper.getRetailConfig(clientId);
	}

}
