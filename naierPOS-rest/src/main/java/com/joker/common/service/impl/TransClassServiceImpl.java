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
import com.joker.common.mapper.TransClassMapper;
import com.joker.common.model.TransClass;
import com.joker.common.service.TransClassService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class TransClassServiceImpl implements TransClassService {

	@Autowired
	TransClassMapper mapper;


	@Override
	public TransClass getTransClassByCode(String code) {
		return mapper.getTransClassByCode(code);
	}

	@Override
	public Page<TransClass> getTransClassByCondition(Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<TransClass> page = new Page<TransClass>();
		int totalRecord = mapper.getTransClassCountByCondition(map);
		List<TransClass> list = mapper.getTransClassByCondition(map);
		page.setPageNo(pageNo);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public void deleteTransClassByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			String[] ids = code.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteTransClassByCode(oneId);
				}
			}
		}
	}

	@Override
	public void updateTransClass(TransClass transClass) {
		mapper.updateTransClass(transClass);
	}

	@Override
	public void insertTransClass(TransClass transClass) {
		mapper.insertTransClass(transClass);
	}

}