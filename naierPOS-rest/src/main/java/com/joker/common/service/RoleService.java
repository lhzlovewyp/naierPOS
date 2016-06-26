/**
 * 
 */
package com.joker.common.service;

import java.util.List;

import com.joker.common.model.Role;


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

	
}
