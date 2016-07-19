/**
 * 
 */
package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Role;
import com.joker.core.dto.Page;

/**
 * @author lvhaizhen
 * 
 */
public interface RoleService {

	/**
	 * 登陆处理.
	 * 
	 * @param clientId
	 * @param userName
	 * @return
	 */
	public List<Role> getRoleByAccountId(String accountId);

	/**
	 * 查询门店信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Role> getRolePageByCondition(Map<String, Object> map,
			int pageNo, int limit);
}