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
import com.joker.common.mapper.ClientMapper;
import com.joker.common.mapper.SizeMapper;
import com.joker.common.model.Client;
import com.joker.common.model.Color;
import com.joker.common.model.Size;
import com.joker.common.service.SizeService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class SizeServiceImpl implements SizeService {

	@Autowired
	SizeMapper mapper;
	
	@Autowired
	ClientMapper clientMapper;

	@Override
	public Size getSizeByID(String id) {
		return mapper.getSizeByID(id);
	}

	/**
	 * 根据商户查询尺码信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Size> getSizePageByCondition(Map<String, Object> map,
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
		Page<Size> page = new Page<Size>();
		int totalRecord = mapper.getSizeCountByCondition(map);
		List<Size> list = mapper.getSizePageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)
				&& StringUtils.isNotBlank(clientId)) {
			Client client = clientMapper.getClientById(clientId);
			if (client != null && StringUtils.isNotBlank(client.getName())) {
				for (Size size : list) {
					size.setClient(client);
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
	public void deleteSizeByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteSizeByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateSize(Size size) {
		mapper.updateSize(size);

	}

	@Override
	public void insertSize(Size size) {
		if (StringUtils.isBlank(size.getId())) {
			size.setId(UUID.randomUUID().toString());
		}
		mapper.insertSize(size);
	}
}