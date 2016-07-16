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
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Brand> getBrandPageByClient(String clientId, int start, int limit) {
		Page<Brand> page = new Page<Brand>();
		int totalRecord = mapper.getBrandCountByClient(clientId);
		List<Brand> list = mapper.getBrandByClient(clientId, start, limit);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
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