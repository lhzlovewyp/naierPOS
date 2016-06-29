package com.joker.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.RetailPriceMapper;
import com.joker.common.model.Material;
import com.joker.common.model.RetailPrice;
import com.joker.common.service.RetailPriceService;

@Service
public class RetailPriceServiceImpl implements RetailPriceService{

	@Autowired
    RetailPriceMapper mapper;
	
	@Override
	public RetailPrice getRetailPrice(Date date,String storeId, Material material) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("clientId", material.getClient().getId());
		map.put("storeId", storeId);
		map.put("materialId", material.getId());
		map.put("unitId", material.getSalesUnit().getId());
		map.put("searchDate", date);
		return mapper.getMaterialPrice(map);
	}

}
