/**
 * 
 */
package com.joker.common.service;

import com.joker.common.model.AccountStore;

/**
 * @author zhangfei
 * 
 */
public interface AccountStoreService {

	/**
	 * 删除账户和门店关联信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteAccountStoreByID(String id);

	/**
	 * 删除账户和门店关联信息.
	 * 
	 * @param accountStore
	 * @return
	 */
	public void deleteAccountStoreByCondition(AccountStore accountStore);

	/**
	 * 修改账户和门店关联信息.
	 * 
	 * @param accountStore
	 * @return
	 */
	public void updateAccountStore(AccountStore accountStore);

	/**
	 * 保存账户和门店关联信息.
	 * 
	 * @param accountStore
	 * @return
	 */
	public void insertAccountStore(AccountStore accountStore);
}