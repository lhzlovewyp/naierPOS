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
import com.joker.common.mapper.MaterialMapper;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialProperty;
import com.joker.common.service.MaterialPropertyService;
import com.joker.common.service.MaterialService;
import com.joker.common.service.RetailPriceService;
import com.joker.core.dto.Page;

/**
 * @author lvhaizhen
 * 
 */
@Service
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	MaterialMapper mapper;

	@Autowired
	MaterialPropertyService materialPropertyService;

	@Autowired
	RetailPriceService retailPriceService;

	@Override
	public Material getMaterialByCode(String clientId, String code) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("code", code);
		Material mat = mapper.getMaterialByCondition(map);
		initMaterial(mat);
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

	@Override
	public Material getMaterialByID(String id) {
		return mapper.getMaterialByID(id);
	}

	@Override
	public Page<Material> getMaterialPageByCondition(Map<String, Object> map,
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
		Page<Material> page = new Page<Material>();
		int totalRecord = mapper.getMaterialCountByCondition(map);
		List<Material> list = mapper.getMaterialPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
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