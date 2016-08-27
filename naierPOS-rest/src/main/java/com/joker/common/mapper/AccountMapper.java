package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.Account;
import com.joker.core.annotation.DataSource;

@Repository
public interface AccountMapper {

	/**
	 * 根据商户、用户名查询用户信息.
	 * 
	 * @param clientId
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Account getAccountByClientAndName(
			@Param("clientId") String clientId, @Param("name") String userName);

	/**
	 * 根据id查询账户信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Account getAccountByID(String id);

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Account> getAccountPageByCondition(Map<String, Object> map);

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getAccountCountByCondition(Map<String, Object> map);

	/**
	 * 删除账户信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteAccountByID(String id);

	/**
	 * 修改账户信息.
	 * 
	 * @param account
	 * @return
	 */
	@DataSource("master")
	public void updateAccount(Account account);

	/**
	 * 保存账户信息.
	 * 
	 * @param account
	 * @return
	 */
	@DataSource("master")
	public void insertAccount(Account account);
}
