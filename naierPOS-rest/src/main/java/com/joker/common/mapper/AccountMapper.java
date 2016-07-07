package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.Account;
import com.joker.core.annotation.DataSource;

@Repository
public interface AccountMapper {

	/**
	 * 根据商户、用户名查询用户信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Account getAccountByClientAndName(
			@Param("clientId") String clientId, @Param("name") String userName);

	/**
	 * 根据id查询账户信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Account getAccountByID(String id);

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<Account> getAccountByClient(@Param("clientId") String clientId,
			@Param("start") int start, @Param("limit") int limit);

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getAccountCountByClient(@Param("clientId") String clientId);

	/**
	 * 删除账户信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void deleteAccountByID(String id);

	/**
	 * 修改账户信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void updateAccount(Account account);

	/**
	 * 保存账户信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void insertAccount(Account account);
}
