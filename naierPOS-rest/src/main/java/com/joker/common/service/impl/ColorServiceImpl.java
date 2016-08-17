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
import com.joker.common.mapper.ColorMapper;
import com.joker.common.model.Color;
import com.joker.common.service.ColorService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class ColorServiceImpl implements ColorService {

	@Autowired
	ColorMapper mapper;

	@Override
	public Color getColorByID(String id) {
		return mapper.getColorByID(id);
	}

	/**
	 * 根据商户查询颜色信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Color> getColorPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Color> page = new Page<Color>();
		int totalRecord = mapper.getColorCountByCondition(map);
		List<Color> list = mapper.getColorPageByCondition(map);
		page.setPageNo(pageNo);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	/**
	 * 根据商户查询颜色信息.
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Color> getColorPageByCondition(Map<String, Object> map) {
		List<Color> list = mapper.getColorPageByCondition(map);
		return list;
	}

	@Override
	public void deleteColorByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteColorByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateColor(Color color) {
		mapper.updateColor(color);

	}

	@Override
	public void insertColor(Color color) {
		if (StringUtils.isBlank(color.getId())) {
			color.setId(UUID.randomUUID().toString());
		}
		mapper.insertColor(color);
	}
}