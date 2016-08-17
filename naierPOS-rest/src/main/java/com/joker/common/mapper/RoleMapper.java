package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Role;
import com.joker.core.annotation.DataSource;

@Repository
public interface RoleMapper {

	/**
	 * 获取用户下面的权限信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public List<Role> getRoleByAccountId(String accountId);
	
	/**
	 * 查询角色信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Role> getRolePageByCondition(Map<String, Object> map);
	
	/**
	 * 查询角色信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getRoleCountByCondition(Map<String, Object> map);
	
	/**
	 * 根据id查询角色信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Role getRoleByID(String id);
	
	/**
	 * 删除角色信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteRoleByID(String id);

	/**
	 * 修改角色信息.
	 * 
	 * @param role
	 * @return
	 */
	@DataSource("master")
	public void updateRole(Role role);

	/**
	 * 保存角色信息.
	 * 
	 * @param role
	 * @return
	 */
	@DataSource("master")
	public void insertRole(Role role);
}