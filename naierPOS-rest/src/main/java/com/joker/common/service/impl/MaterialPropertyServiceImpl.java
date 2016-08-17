/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.ColorMapper;
import com.joker.common.mapper.MaterialMapper;
import com.joker.common.mapper.MaterialPropertyMapper;
import com.joker.common.mapper.SizeMapper;
import com.joker.common.model.Color;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialProperty;
import com.joker.common.model.Size;
import com.joker.common.service.MaterialPropertyService;
import com.joker.core.dto.Page;

/**
 * @author lvhaizhen
 * 
 */
@Service
public class MaterialPropertyServiceImpl implements MaterialPropertyService {

	@Autowired
	MaterialPropertyMapper mapper;

	@Autowired
	MaterialMapper materialMapper;

	@Autowired
	ColorMapper colorMapper;

	@Autowired
	SizeMapper sizeMapper;

	@Override
	public List<MaterialProperty> getMaterialPropertyById(String clientId,
			String materialId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("materialId", materialId);
		return mapper.getMaterialPropertyByCondition(map);
	}

	@Override
	public List<MaterialProperty> getMaterialPropertyByCode(String clientId,
			String materialCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("materialCode", materialCode);
		return mapper.getMaterialPropertyByCondition(map);
	}

	@Override
	public List<MaterialProperty> getMaterialPropertyByBarCode(String clientId,
			String barCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("barCode", barCode);
		return mapper.getMaterialPropertyByCondition(map);
	}

	@Override
	public MaterialProperty getMaterialPropertyByID(String id) {
		return mapper.getMaterialPropertyByID(id);
	}

	@Override
	public Page<MaterialProperty> getMaterialPropertyPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<MaterialProperty> page = new Page<MaterialProperty>();
		int totalRecord = mapper.getMaterialPropertyCountByCondition(map);
		List<MaterialProperty> list = mapper
				.getMaterialPropertyPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Material> cacheMap = new HashMap<String, Material>();
			Map<String, Color> colorCacheMap = new HashMap<String, Color>();
			Map<String, Size> sizeCacheMap = new HashMap<String, Size>();

			for (MaterialProperty materialProperty : list) {
				if (materialProperty != null) {
					if (materialProperty.getMaterial() != null
							&& StringUtils.isNotBlank(materialProperty
									.getMaterial().getId())) {
						String materialId = materialProperty.getMaterial()
								.getId();
						Material material = null;
						if (cacheMap.containsKey(materialId)) {
							material = cacheMap.get(materialId);
						} else {
							material = materialMapper
									.getMaterialByID(materialId);
							if (material != null) {
								cacheMap.put(materialId, material);
							}
						}
						if (material != null) {
							materialProperty.setMaterial(material);
						}
					}

					if (materialProperty.getColor() != null
							&& StringUtils.isNotBlank(materialProperty
									.getColor().getId())) {
						String colorId = materialProperty.getColor().getId();
						Color color = null;
						if (colorCacheMap.containsKey(colorId)) {
							color = colorCacheMap.get(colorId);
						} else {
							color = colorMapper.getColorByID(colorId);
							if (color != null) {
								colorCacheMap.put(colorId, color);
							}
						}
						if (color != null) {
							materialProperty.setColor(color);
						}
					}

					if (materialProperty.getSize() != null
							&& StringUtils.isNotBlank(materialProperty
									.getSize().getId())) {
						String sizeId = materialProperty.getSize().getId();
						Size size = null;
						if (sizeCacheMap.containsKey(sizeId)) {
							size = sizeCacheMap.get(sizeId);
						} else {
							size = sizeMapper.getSizeByID(sizeId);
							if (size != null) {
								sizeCacheMap.put(sizeId, size);
							}
						}
						if (size != null) {
							materialProperty.setSize(size);
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
	public List<MaterialProperty> getMaterialPropertyPageByCondition(
			Map<String, Object> map) {
		List<MaterialProperty> list = mapper
				.getMaterialPropertyPageByCondition(map);
		return list;
	}

	@Override
	public void deleteMaterialPropertyByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteMaterialPropertyByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateMaterialProperty(MaterialProperty materialProperty) {
		mapper.updateMaterialProperty(materialProperty);

	}

	@Override
	public void insertMaterialProperty(MaterialProperty materialProperty) {
		if (StringUtils.isBlank(materialProperty.getId())) {
			materialProperty.setId(UUID.randomUUID().toString());
		}
		mapper.insertMaterialProperty(materialProperty);
	}

}
