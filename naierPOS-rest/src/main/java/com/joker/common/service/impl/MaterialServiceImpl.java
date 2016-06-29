/**
 * 
 */
package com.joker.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.MaterialMapper;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialProperty;
import com.joker.common.service.MaterialPropertyService;
import com.joker.common.service.MaterialService;
import com.joker.common.service.RetailPriceService;

/**
 * @author lvhaizhen
 *
 */
@Service
public class MaterialServiceImpl implements MaterialService{

	@Autowired
    MaterialMapper mapper;
	
	@Autowired
	MaterialPropertyService materialPropertyService;
	
	@Autowired
	RetailPriceService retailPriceService;

	@Override
	public Material getMaterialByCode(String clientId,String code) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("clientId", clientId);
		map.put("code", code);
		Material mat=mapper.getMaterialByCondition(map);
		initMaterial(mat);
		return mat;
	}

	@Override
	public Material getMaterialByBarCode(String clientId, String barCode) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("clientId", clientId);
		map.put("barCode", barCode);
		Material mat=mapper.getMaterialByCondition(map);
		if(mat!=null){
			initMaterial(mat);
		}
		return mat;
	}
	
	//初始化颜色、尺寸、价格信息.
	private void initMaterial(Material mat){
		if(mat.getProperty().equals(Constants.PROPERTY_YES)){
			List<MaterialProperty> list = materialPropertyService.getMaterialPropertyByCode(mat.getClient().getId(),mat.getCode());
			if(CollectionUtils.isNotEmpty(list)){
				mat.setProperties(list);
			}
		}
	}
}
