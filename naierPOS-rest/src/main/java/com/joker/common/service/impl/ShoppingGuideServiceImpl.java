/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.ShoppingGuideMapper;
import com.joker.common.model.ShoppingGuide;
import com.joker.common.service.ShoppingGuideService;

/**
 * @author lvhaizhen
 * 
 */
@Service
public class ShoppingGuideServiceImpl implements ShoppingGuideService {

	@Autowired
	ShoppingGuideMapper mapper;

	@Override
	public ShoppingGuide getShoppingGuideById(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		List<ShoppingGuide> list = mapper.getShoppingGuideByCondition(map);

		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@Override
	public ShoppingGuide getShoppingGuideByCode(String clientId, String code) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("code", code);
		List<ShoppingGuide> list = mapper.getShoppingGuideByCondition(map);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

}
