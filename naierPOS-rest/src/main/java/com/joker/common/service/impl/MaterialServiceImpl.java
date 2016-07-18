/**
 * 
 */
package com.joker.common.service.impl;

import java.math.BigDecimal;
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
import com.joker.common.model.UnitConversion;
import com.joker.common.service.MaterialPropertyService;
import com.joker.common.service.MaterialService;
import com.joker.common.service.RetailPriceService;
import com.joker.common.service.UnitConversionService;
import com.joker.core.util.NumberUtil;

/**
 * @author lvhaizhen
 *
 */
@Service("materialService")
public class MaterialServiceImpl implements MaterialService{

	@Autowired
    MaterialMapper mapper;
	
	@Autowired
	MaterialPropertyService materialPropertyService;
	
	@Autowired
	RetailPriceService retailPriceService;
	
	@Autowired
	UnitConversionService unitConversionService;

	@Override
	public Material getMaterialByCode(String clientId,String code) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("clientId", clientId);
		map.put("code", code);
		Material mat=mapper.getMaterialByCondition(map);
		if(mat!=null){
			initMaterial(mat);
			initUnitConversion(mat);
		}
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
			initUnitConversion(mat);
		}
		return mat;
	}
	@Override
	public Material getMaterialById(String id) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		Material mat=mapper.getMaterialByCondition(map);
		if(mat!=null){
			initMaterial(mat);
			initUnitConversion(mat);
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
	
	private void initUnitConversion(Material mat){
		if(mat.getSalesConversion() != null){
			return ;
		}
		//如果销售单位=基本单位，设置为1.
		if(mat.getBasicUnit().getId().equals(mat.getSalesUnit().getId())){
			mat.setSalesConversion(new BigDecimal(1));
			return ;
		}
		//获取销售单位和基本单位的换算关系.
		
		UnitConversion conversion = unitConversionService.getUnitConversion(mat.getClient().getId(), mat.getSalesUnit().getId(), mat.getBasicUnit().getId());
		if(conversion == null){
			mat.setSalesConversion(new BigDecimal(1));
			return ;
		}
		BigDecimal saleConversion= conversion.getQtyB().divide(conversion.getQtyA());
		mat.setSalesConversion(NumberUtil.round(saleConversion, 2, 4));
	}

	
}
