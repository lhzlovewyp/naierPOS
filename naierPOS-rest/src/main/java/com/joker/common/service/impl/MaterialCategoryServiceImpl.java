/**
 * 
 */
package com.joker.common.service.impl;

import java.util.List;
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
		return mapper.getMaterialCategoryByID(id);
	}

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public Page<MaterialCategory> getMaterialCategoryPageByClient(
			String clientId, int start, int limit) {
		Page<MaterialCategory> page = new Page<MaterialCategory>();
		int totalRecord = mapper.getMaterialCategoryCountByClient(clientId);
		List<MaterialCategory> list = mapper.getMaterialCategoryByClient(
				clientId, start, limit);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
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