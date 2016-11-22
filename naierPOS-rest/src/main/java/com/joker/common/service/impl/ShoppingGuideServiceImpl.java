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
import com.joker.common.mapper.ShoppingGuideMapper;
import com.joker.common.model.ShoppingGuide;
import com.joker.common.service.ShoppingGuideService;
import com.joker.core.dto.Page;

/**
 * @author lvhaizhen
 * 
 */
@Service
public class ShoppingGuideServiceImpl implements ShoppingGuideService {

	@Autowired
	ShoppingGuideMapper mapper;

	@Override
	public ShoppingGuide getShoppingGuideById(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		List<ShoppingGuide> list = mapper.getShoppingGuideByCondition(map);

		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@Override
	public ShoppingGuide getShoppingGuideByCode(String clientId,String store, String code) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("clientId", clientId);
		map.put("code", code);
		map.put("storeId", store);
		List<ShoppingGuide> list = mapper.getShoppingGuideByCondition(map);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}
	
	@Override
	public ShoppingGuide getShoppingGuideByID(String id) {
		return mapper.getShoppingGuideByID(id);
	}

	/**
	 * 根据商户查询颜色信息.
	 * 
	 * @param map
	 * @param clientId
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<ShoppingGuide> getShoppingGuidePageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<ShoppingGuide> page = new Page<ShoppingGuide>();
		int totalRecord = mapper.getShoppingGuideCountByCondition(map);
		List<ShoppingGuide> list = mapper.getShoppingGuidePageByCondition(map);
		page.setPageNo(pageNo);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public void deleteShoppingGuideByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteShoppingGuideByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateShoppingGuide(ShoppingGuide shoppingGuide) {
		mapper.updateShoppingGuide(shoppingGuide);

	}

	@Override
	public void insertShoppingGuide(ShoppingGuide shoppingGuide) {
		if (StringUtils.isBlank(shoppingGuide.getId())) {
			shoppingGuide.setId(UUID.randomUUID().toString());
		}
		mapper.insertShoppingGuide(shoppingGuide);
	}

}
