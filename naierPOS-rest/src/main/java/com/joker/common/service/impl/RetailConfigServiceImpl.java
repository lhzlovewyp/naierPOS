package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.RetailConfigMapper;
import com.joker.common.model.RetailConfig;
import com.joker.common.service.RetailConfigService;
import com.joker.core.dto.Page;

@Service
public class RetailConfigServiceImpl implements RetailConfigService {

	@Autowired
	RetailConfigMapper mapper;

	@Override
	public RetailConfig getRetailConfig(String clientId) {
		return mapper.getRetailConfig(clientId);
	}

	@Override
	public RetailConfig getRetailConfigByID(String id) {
		return mapper.getRetailConfigByID(id);
	}

	@Override
	public Page<RetailConfig> getRetailConfigPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<RetailConfig> page = new Page<RetailConfig>();
		int totalRecord = mapper.getRetailConfigCountByCondition(map);
		List<RetailConfig> list = mapper.getRetailConfigPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<RetailConfig> getRetailConfigPageByCondition(
			Map<String, Object> map) {
		List<RetailConfig> list = mapper.getRetailConfigPageByCondition(map);
		return list;
	}

	@Override
	public void deleteRetailConfigByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteRetailConfigByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateRetailConfig(RetailConfig retailConfig) {
		mapper.updateRetailConfig(retailConfig);

	}

	@Override
	public void insertRetailConfig(RetailConfig retailConfig) {
		if (StringUtils.isBlank(retailConfig.getId())) {
			retailConfig.setId(UUID.randomUUID().toString());
		}
		mapper.insertRetailConfig(retailConfig);
	}

}
