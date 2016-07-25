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
import com.joker.common.mapper.TerminalMapper;
import com.joker.common.model.Terminal;
import com.joker.common.service.TerminalService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class TerminalServiceImpl implements TerminalService {

	@Autowired
	TerminalMapper mapper;

	@Override
	public Terminal getTerminalByID(String id) {
		return mapper.getTerminalByID(id);
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
	public Page<Terminal> getTerminalPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Terminal> page = new Page<Terminal>();
		int totalRecord = mapper.getTerminalCountByCondition(map);
		List<Terminal> list = mapper.getTerminalPageByCondition(map);
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
	public List<Terminal> getTerminalPageByCondition(Map<String, Object> map) {
		List<Terminal> list = mapper.getTerminalPageByCondition(map);
		return list;
	}

	@Override
	public void deleteTerminalByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteTerminalByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateTerminal(Terminal terminal) {
		mapper.updateTerminal(terminal);

	}

	@Override
	public void insertTerminal(Terminal terminal) {
		if (StringUtils.isBlank(terminal.getId())) {
			terminal.setId(UUID.randomUUID().toString());
		}
		mapper.insertTerminal(terminal);
	}
}