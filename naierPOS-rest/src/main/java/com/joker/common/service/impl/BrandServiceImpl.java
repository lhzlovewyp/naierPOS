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
import com.joker.common.mapper.BrandMapper;
import com.joker.common.model.Brand;
import com.joker.common.service.BrandService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	BrandMapper mapper;

	@Override
	public Brand getBrandByID(String id) {
		return mapper.getBrandByID(id);
	}

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Brand> getBrandPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Brand> page = new Page<Brand>();
		int totalRecord = mapper.getBrandCountByCondition(map);
		List<Brand> list = mapper.getBrandPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Brand> getBrandPageByCondition(Map<String, Object> map) {
		List<Brand> list = mapper.getBrandPageByCondition(map);
		return list;
	}

	@Override
	public void deleteBrandByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteBrandByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateBrand(Brand brand) {
		mapper.updateBrand(brand);

	}

	@Override
	public void insertBrand(Brand brand) {
		if (StringUtils.isBlank(brand.getId())) {
			brand.setId(UUID.randomUUID().toString());
		}
		mapper.insertBrand(brand);
	}
}