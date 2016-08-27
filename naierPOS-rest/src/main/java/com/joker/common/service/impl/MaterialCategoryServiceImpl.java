/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.MaterialCategoryMapper;
import com.joker.common.model.MaterialCategory;
import com.joker.common.service.MaterialCategoryService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

	@Autowired
	MaterialCategoryMapper mapper;

	@Override
	public MaterialCategory getMaterialCategoryByID(String id) {
		MaterialCategory materialCategory = mapper.getMaterialCategoryByID(id);
		if (materialCategory != null && materialCategory.getParent() != null
				&& StringUtils.isNotBlank(materialCategory.getParent().getId())
				&& !"0".equals(materialCategory.getParent().getId())) {
			MaterialCategory parent = mapper
					.getMaterialCategoryByID(materialCategory.getParent()
							.getId());
			if (parent != null) {
				materialCategory.setParent(parent);
			}
		}
		return materialCategory;
	}

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<MaterialCategory> getMaterialCategoryPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<MaterialCategory> page = new Page<MaterialCategory>();
		int totalRecord = mapper.getMaterialCategoryCountByCondition(map);
		List<MaterialCategory> list = mapper
				.getMaterialCategoryPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<MaterialCategory> getMaterialCategoryPageByCondition(
			Map<String, Object> map) {
		List<MaterialCategory> list = mapper
				.getMaterialCategoryPageByCondition(map);
		return list;
	}

	@Override
	public void deleteMaterialCategoryByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteMaterialCategoryByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateMaterialCategory(MaterialCategory materialCategory) {
		mapper.updateMaterialCategory(materialCategory);

	}

	@Override
	public void insertMaterialCategory(MaterialCategory materialCategory) {
		if (StringUtils.isBlank(materialCategory.getId())) {
			materialCategory.setId(UUID.randomUUID().toString());
		}
		mapper.insertMaterialCategory(materialCategory);
	}
}