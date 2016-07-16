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
import com.joker.common.mapper.SizeMapper;
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

	@Override
	public Size getSizeByID(String id) {
		return mapper.getSizeByID(id);
	}

	/**
	 * 根据商户查询尺码信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Size> getSizePageByClient(String clientId, int start, int limit) {
		Page<Size> page = new Page<Size>();
		int totalRecord = mapper.getSizeCountByClient(clientId);
		List<Size> list = mapper.getSizeByClient(clientId, start, limit);
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