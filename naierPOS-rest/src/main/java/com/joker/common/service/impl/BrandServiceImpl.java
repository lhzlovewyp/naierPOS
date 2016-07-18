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
import com.joker.common.mapper.BrandMapper;
import com.joker.common.mapper.ClientMapper;
import com.joker.common.model.Brand;
import com.joker.common.model.Client;
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
	
	@Autowired
	ClientMapper clientMapper;

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
		String clientId = null;
		if (map.containsKey("clientId")) {
			clientId = (String) map.get("clientId");
		}
		map.put("clientId", clientId);
		map.put("start", start);
		map.put("limit", limit);
		Page<Brand> page = new Page<Brand>();
		int totalRecord = mapper.getBrandCountByCondition(map);
		List<Brand> list = mapper.getBrandPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)
				&& StringUtils.isNotBlank(clientId)) {
			Client client = clientMapper.getClientById(clientId);
			if (client != null && StringUtils.isNotBlank(client.getName())) {
				for (Brand brand : list) {
					brand.setClient(client);
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