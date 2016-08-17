/**
 * 
 */
package com.joker.common.service;

import java.util.Map;

import com.joker.common.model.Account;
import com.joker.core.dto.Page;

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
	public Account login(String clientId, String userName);

	/**
	 * 根据商户、用户名查询用户信息.
	 * 
	 * @param clientId
	 * @param username
	 * @return
	 */
	public Account getAccountByClientAndName(String clientId, String userName);

	/**
	 * 根据商户、用户名查询用户信息.
	 * 
	 * @param clientCode
	 * @param username
	 * @return
	 */
	public Account getAccountByClientCodeAndName(String clientCode,
			String userName);

	/**
	 * 根据id查询账户信息.
	 * 
	 * @param id
	 * @return
	 */
	public Account getAccountByID(String id);

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Account> getAccountPageByCondition(Map<String, Object> map,
			int pageNo, int limit);
	
	/**
	 * 删除账户信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteAccountByID(String id);

	/**
	 * 修改账户信息.
	 * 
	 * @param account
	 * @return
	 */
	public void updateAccount(Account account);

	/**
	 * 保存账户信息.
	 * 
	 * @param account
	 * @return
	 */
	public void insertAccount(Account account);
}
