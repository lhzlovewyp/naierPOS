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
	 * 查询门店信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Role> getRolePageByCondition(Map<String, Object> map);
	
	/**
	 * 查询门店信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getRoleCountByCondition(Map<String, Object> map);
}