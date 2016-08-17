package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.StoreMapper;
import com.joker.common.model.Store;
import com.joker.common.service.StoreService;
import com.joker.core.dto.Page;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreMapper mapper;

	@Override
	public Store getStoreById(String id) {
		return mapper.getStoreById(id);
	}

	@Override
	public Store getStoreByClientAndCode(String clientId, String code) {
		return mapper.getStoreByClientAndCode(clientId, code);
	}

	@Override
	public void deleteStoreById(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteStoreById(oneId);
				}
			}
		}
	}

	@Override
	public void insertStore(Store store) {
		mapper.insertStore(store);
	}

	@Override
	public void updateStore(Store store) {
		mapper.updateStore(store);
	}

	@Override
	public Page<Store> getStorePageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		String clientId = null;
		if (map.containsKey("clientId")) {
			clientId = (String) map.get("clientId");
		}
		map.put("clientId", clientId);
		map.put("start", start);
		map.put("limit", limit);
		Page<Store> page = new Page<Store>();
		int totalRecord = mapper.getStoreCountByCondition(map);
		List<Store> list = mapper.getStorePageByCondition(map);
		page.setPageNo(pageNo);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<Store> getStorePageByCondition(Map<String, Object> map) {
		List<Store> list = mapper.getStorePageByCondition(map);
		return list;
	}
}