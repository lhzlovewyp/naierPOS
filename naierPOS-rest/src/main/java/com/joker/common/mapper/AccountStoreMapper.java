package com.joker.common.mapper;

import org.springframework.stereotype.Repository;

import com.joker.common.model.AccountStore;
import com.joker.core.annotation.DataSource;

@Repository
public interface AccountStoreMapper {

	/**
	 * 删除账户和门店关联信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteAccountStoreByID(String id);
	
	/**
	 * 删除账户和门店关联信息.
	 * 
	 * @param AccountStore accountStore
	 * @return
	 */
	@DataSource("master")
	public void deleteAccountStoreByCondition(AccountStore accountStore);

	/**
	 * 修改账户和门店关联信息.
	 * 
	 * @param accountStore
	 * @return
	 */
	@DataSource("master")
	public void updateAccountStore(AccountStore accountStore);

	/**
	 * 保存账户和门店关联信息.
	 * 
	 * @param accountStore
	 * @return
	 */
	@DataSource("master")
	public void insertAccountStore(AccountStore accountStore);
}
