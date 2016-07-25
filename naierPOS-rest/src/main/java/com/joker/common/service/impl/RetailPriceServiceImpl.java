package com.joker.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.RetailPriceMapper;
import com.joker.common.model.Material;
import com.joker.common.model.RetailPrice;
import com.joker.common.service.RetailPriceService;
import com.joker.core.dto.Page;

@Service
public class RetailPriceServiceImpl implements RetailPriceService {

	@Autowired
	RetailPriceMapper mapper;

	@Override
	public RetailPrice getRetailPrice(Date date, String storeId,
			Material material) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clientId", material.getClient().getId());
		map.put("storeId", storeId);
		map.put("materialId", material.getId());
		map.put("unitId", material.getSalesUnit().getId());
		map.put("searchDate", date);
		return mapper.getMaterialPrice(map);
	}

	@Override
	public RetailPrice getRetailPriceByID(String id) {
		return mapper.getRetailPriceByID(id);
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
	public Page<RetailPrice> getRetailPricePageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<RetailPrice> page = new Page<RetailPrice>();
		int totalRecord = mapper.getRetailPriceCountByCondition(map);
		List<RetailPrice> list = mapper.getRetailPricePageByCondition(map);
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
	public List<RetailPrice> getRetailPricePageByCondition(
			Map<String, Object> map) {
		List<RetailPrice> list = mapper.getRetailPricePageByCondition(map);
		return list;
	}

	@Override
	public void deleteRetailPriceByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteRetailPriceByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateRetailPrice(RetailPrice retailPrice) {
		mapper.updateRetailPrice(retailPrice);

	}

	@Override
	public void insertRetailPrice(RetailPrice retailPrice) {
		if (StringUtils.isBlank(retailPrice.getId())) {
			retailPrice.setId(UUID.randomUUID().toString());
		}
		mapper.insertRetailPrice(retailPrice);
	}
}