/**
 * 
 */
package com.joker.common.service;

import com.joker.common.model.Account;


/**
 * @author lvhaizhen
 *
 */
public interface AccountService {
	
	/**
	 * 登陆处理.
	 * 
	 * @param clientId
	 * @param userName
	 * @return
	 */
	public Account login(String clientId,String userName);

	/**
	 * 根据商户、用户名查询用户信息.
	 * @param username
	 * @return
	 */
	public Account getAccountByClientAndName(String clientId,String userName);
	
	/**
	 * 根据商户、用户名查询用户信息.
	 * @param username
	 * @return
	 */
	public Account getAccountByClientCodeAndName(String clientCode,String userName);
	
	/**
	 * 根据id查询账户信息.
	 * @param username
	 * @return
	 */
	public Account getAccountByID(String id);
	
	
	/**
	 * 删除账户信息.
	 * @param username
	 * @return
	 */
	public void deleteAccountByID(String id);
	
	/**
	 * 修改账户信息.
	 * @param username
	 * @return
	 */
	public void updateAccount(Account account);
	
	/**
	 * 保存账户信息.
	 * @param username
	 * @return
	 */
	public void insertAccount(Account account);
}
