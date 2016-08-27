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
	 * 查询角色信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Role> getRolePageByCondition(Map<String, Object> map,
			int pageNo, int limit);
	
	/**
	 * 查询角色信息.
	 * 
	 * @param map
	 * @return
	 */
	public List<Role> getRolePageByCondition(Map<String, Object> map);
	
	/**
	 * 根据id查询角色信息.
	 * 
	 * @param id
	 * @return
	 */
	public Role getRoleByID(String id);
	
	/**
	 * 删除角色信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteRoleByID(String id);

	/**
	 * 修改角色信息.
	 * 
	 * @param brand
	 * @return
	 */
	public void updateRole(Role role);

	/**
	 * 保存角色信息.
	 * 
	 * @param brand
	 * @return
	 */
	public void insertRole(Role role);
}