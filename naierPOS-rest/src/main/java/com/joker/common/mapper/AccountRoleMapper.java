package com.joker.common.mapper;

import org.springframework.stereotype.Repository;

import com.joker.common.model.AccountRole;
import com.joker.core.annotation.DataSource;

@Repository
public interface AccountRoleMapper {

	/**
	 * 删除账户和门店关联信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteAccountRoleByID(String id);
	
	/**
	 * 删除账户和门店关联信息.
	 * 
	 * @param accountRole
	 * @return
	 */
	@DataSource("master")
	public void deleteAccountRoleByCondition(AccountRole accountRole);

	/**
	 * 修改账户和门店关联信息.
	 * 
	 * @param accountStore
	 * @return
	 */
	@DataSource("master")
	public void updateAccountRole(AccountRole accountRole);

	/**
	 * 保存账户和门店关联信息.
	 * 
	 * @param accountStore
	 * @return
	 */
	@DataSource("master")
	public void insertAccountRole(AccountRole accountRole);
}
