/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.RoleMapper;
import com.joker.common.model.Role;
import com.joker.common.service.RoleService;
import com.joker.core.dto.Page;

/**
 * @author lvhaizhen
 *
 */
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
    RoleMapper mapper;

	@Override
	public List<Role> getRoleByAccountId(String accountId) {
		return mapper.getRoleByAccountId(accountId);
	}
	
	/**
	 * 根据商户查询角色信息.
	 * 
	 * @param map
	 * @param clientId
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Role> getRolePageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Role> page = new Page<Role>();
		int totalRecord = mapper.getRoleCountByCondition(map);
		List<Role> list = mapper.getRolePageByCondition(map);
		page.setPageNo(pageNo);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	/**
	 * 根据商户查询角色信息.
	 * 
	 * @param map
	 * @param clientId
	 * @return
	 */
	@Override
	public List<Role> getRolePageByCondition(Map<String, Object> map) {
		List<Role> list = mapper.getRolePageByCondition(map);
		return list;
	}
}