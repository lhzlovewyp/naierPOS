/**
 * 
 */
package com.joker.common.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.BrandMapper;
import com.joker.common.mapper.MaterialCategoryMapper;
import com.joker.common.mapper.MaterialMapper;
import com.joker.common.model.Brand;
import com.joker.common.model.Color;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialCategory;
import com.joker.common.model.MaterialProperty;
import com.joker.common.model.Size;
import com.joker.common.model.Unit;
import com.joker.common.model.UnitConversion;
import com.joker.common.service.ColorService;
import com.joker.common.service.MaterialPropertyService;
import com.joker.common.service.MaterialService;
import com.joker.common.service.RetailPriceService;
import com.joker.common.service.SizeService;
import com.joker.common.service.UnitConversionService;
import com.joker.common.service.UnitService;
import com.joker.core.dto.Page;
import com.joker.core.util.NumberUtil;

/**
 * @author lvhaizhen
 * 
 */
@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	MaterialMapper mapper;

	@Autowired
	MaterialPropertyService materialPropertyService;

	@Autowired
	RetailPriceService retailPriceService;

	@Autowired
	UnitConversionService unitConversionService;

	@Autowired
	UnitService unitService;

	@Autowired
	MaterialCategoryMapper materialCategoryMapper;

	@Autowired
	BrandMapper brandMapper;
	
	@Autowired
	ColorService colorService;
	
	@Autowired
	SizeService sizeService;
	
	@Override
	public Material getDynMaterial(String clientId, String code) {
		Material material=null;
		List<MaterialProperty> properties = materialPropertyService.getMaterialPropertyByBarCode(clientId, code);
		if(CollectionUtils.isEmpty(properties)){
			material=this.getMaterialByBarCode(clientId, code);
			if(material == null){
				material = this.getMaterialByCode(clientId, code);
			}
		}else{
			//只读取第一个.
			MaterialProperty property=properties.get(0);
			material=this.getMaterialById(property.getMaterial().getId());
			Color color=colorService.getColorByID(property.getColor().getId());
			Size size=sizeService.getSizeByID(property.getSize().getId());
			material.setColor(color);
			material.setSize(size);
		}
		if(material!= null){
			initMaterial(material);
			initUnitConversion(material);
		}
		return material;
	}

	@Override
	public Material getMaterialByCode(String clientId, String code) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("code", code);
		Material mat = mapper.getMaterialByCondition(map);
		if (mat != null) {
			initMaterial(mat);
			initUnitConversion(mat);
		}
		return mat;
	}

	@Override
	public Material getMaterialByBarCode(String clientId, String barCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("barCode", barCode);
		Material mat = mapper.getMaterialByCondition(map);
		if (mat != null) {
			initMaterial(mat);
			initUnitConversion(mat);
		}
		return mat;
	}

	@Override
	public Material getMaterialById(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		Material mat = mapper.getMaterialByCondition(map);
		if (mat != null) {
			initMaterial(mat);
			initUnitConversion(mat);
		}
		return mat;
	}

	// 初始化颜色、尺寸、价格信息.
	private void initMaterial(Material mat) {
		if (mat.getProperty().equals(Constants.PROPERTY_YES)) {
			List<MaterialProperty> list = materialPropertyService
					.getMaterialPropertyByCode(mat.getClient().getId(),
							mat.getCode());
			if (CollectionUtils.isNotEmpty(list)) {
				mat.setProperties(list);
			}
		}
	}

	private void initUnitConversion(Material mat) {
//		if (mat.getSalesConversion() != null) {
//			return;
//		}

		Unit basicUnit = unitService.getUnitByID(mat.getBasicUnit().getId());
		if (basicUnit != null) {
			mat.setBasicUnit(basicUnit);
		}

		Unit salesUnit = unitService.getUnitByID(mat.getSalesUnit().getId());
		if (salesUnit != null) {
			mat.setSalesUnit(salesUnit);
		}

		// 如果销售单位=基本单位，设置为1.
		if (mat.getBasicUnit().getId().equals(mat.getSalesUnit().getId())) {
			mat.setSalesConversion(new BigDecimal(1));
			return;
		}
		// 获取销售单位和基本单位的换算关系.

		UnitConversion conversion = unitConversionService.getUnitConversion(mat
				.getClient().getId(), mat.getSalesUnit().getId(), mat
				.getBasicUnit().getId());
		if (conversion == null) {
			mat.setSalesConversion(new BigDecimal(1));
			return;
		}
		BigDecimal saleConversion = conversion.getQtyB().divide(
				conversion.getQtyA());
		mat.setSalesConversion(NumberUtil.round(saleConversion, 2, 4));
	}

	@Override
	public Material getMaterialByID(String id) {
		Material material = mapper.getMaterialByID(id);
		if (material != null && material.getCategory() != null
				&& StringUtils.isNotBlank(material.getCategory().getId())
				&& !"0".equals(material.getCategory().getId())) {
			MaterialCategory category = materialCategoryMapper
					.getMaterialCategoryByID(material.getCategory().getId());
			if (category != null) {
				material.setCategory(category);
			}
		}
		return material;
	}

	@Override
	public Page<Material> getMaterialPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Material> page = new Page<Material>();
		int totalRecord = mapper.getMaterialCountByCondition(map);
		List<Material> list = mapper.getMaterialPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, MaterialCategory> cacheMap = new HashMap<String, MaterialCategory>();
			Map<String, Brand> cacheBrandMap = new HashMap<String, Brand>();
			for (Material material : list) {
				if (material != null) {
					if (material.getCategory() != null
							&& StringUtils.isNotBlank(material.getCategory()
									.getId())) {
						String categoryId = material.getCategory().getId();
						MaterialCategory materialCategory = null;
						if (cacheMap.containsKey(categoryId)) {
							materialCategory = cacheMap.get(categoryId);
						} else {
							materialCategory = materialCategoryMapper
									.getMaterialCategoryByID(categoryId);
							if (materialCategory != null) {
								cacheMap.put(categoryId, materialCategory);
							}
						}
						if (materialCategory != null) {
							material.setCategory(materialCategory);
						}
					}

					if (material.getBrand() != null
							&& StringUtils.isNotBlank(material.getBrand()
									.getId())) {
						String brandId = material.getBrand().getId();
						Brand brand = null;
						if (cacheBrandMap.containsKey(brandId)) {
							brand = cacheBrandMap.get(brandId);
						} else {
							brand = brandMapper.getBrandByID(brandId);
							if (brand != null) {
								cacheBrandMap.put(brandId, brand);
							}
						}
						if (brand != null) {
							material.setBrand(brand);
						}
					}
				}
			}
		}
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<Material> getMaterialPageByCondition(Map<String, Object> map) {
		List<Material> list = mapper.getMaterialPageByCondition(map);
		return list;
	}

	@Override
	public void deleteMaterialByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteMaterialByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateMaterial(Material material) {
		mapper.updateMaterial(material);
	}

	@Override
	public void insertMaterial(Material material) {
		if (StringUtils.isBlank(material.getId())) {
			material.setId(UUID.randomUUID().toString());
		}
		mapper.insertMaterial(material);
	}
}