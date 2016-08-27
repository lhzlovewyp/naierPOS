/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.ItemClassMapper;
import com.joker.common.model.ItemClass;
import com.joker.common.service.ItemClassService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class ItemClassServiceImpl implements ItemClassService {

	@Autowired
	ItemClassMapper mapper;


	@Override
	public ItemClass getItemClassByCode(String code) {
		return mapper.getItemClassByCode(code);
	}

	@Override
	public Page<ItemClass> getItemClassByCondition(Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<ItemClass> page = new Page<ItemClass>();
		int totalRecord = mapper.getItemClassCountByCondition(map);
		List<ItemClass> list = mapper.getItemClassByCondition(map);
		page.setPageNo(pageNo);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public void deleteItemClassByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			String[] ids = code.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteItemClassByCode(oneId);
				}
			}
		}
	}

	@Override
	public void updateItemClass(ItemClass itemClass) {
		mapper.updateItemClass(itemClass);
	}

	@Override
	public void insertItemClass(ItemClass itemClass) {
		mapper.insertItemClass(itemClass);
	}

}