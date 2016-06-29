/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.MaterialPropertyMapper;
import com.joker.common.model.MaterialProperty;
import com.joker.common.service.MaterialPropertyService;

/**
 * @author lvhaizhen
 *
 */
@Service
public class MaterialPropertyServiceImpl implements MaterialPropertyService{

	@Autowired
    MaterialPropertyMapper mapper;

	@Override
	public List<MaterialProperty> getMaterialPropertyById(String clientId,
			String materialId) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("clientId",clientId);
		map.put("materialId",materialId);
		return mapper.getMaterialPropertyByCondition(map);
	}

	@Override
	public List<MaterialProperty> getMaterialPropertyByCode(String clientId,
			String materialCode) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("clientId",clientId);
		map.put("materialCode",materialCode);
		return mapper.getMaterialPropertyByCondition(map);
	}

	@Override
	public List<MaterialProperty> getMaterialPropertyByBarCode(String clientId,
			String barCode) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("clientId",clientId);
		map.put("barCode",barCode);
		return mapper.getMaterialPropertyByCondition(map);
	}


}
