/**
 * 
 */
package com.joker.common.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.AccountMapper;
import com.joker.common.mapper.ClientMapper;
import com.joker.common.mapper.StoreMapper;
import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.Role;
import com.joker.common.model.Store;
import com.joker.common.service.AccountService;
import com.joker.common.service.RoleService;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.Context;
import com.joker.core.dto.Page;

/**
 * @author lvhaizhen
 * 
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountMapper mapper;

	@Autowired
	ClientMapper clientMapper;

	@Autowired
	StoreMapper storeMapper;

	@Autowired
	RoleService roleService;

	@Override
	public Account login(String clientId, String userName) {
		// 获取当前账号信息.
		Account account = this.getAccountByClientAndName(clientId, userName);
		if (account != null) {
			String token = UUID.randomUUID().toString();
			account.setToken(token);

			if (account.getStore() != null) {
				// 获取门店信息
				Store store = storeMapper.getStoreById(account.getStore()
						.getId());
				if (store != null) {
					account.setStore(store);
				}

				// 获取账号对应的角色信息.
				List<Role> roles = roleService.getRoleByAccountId(account
						.getId());
				if (CollectionUtils.isNotEmpty(roles)) {
					account.setRoles(roles);
				}
			}

			CacheFactory.getCache().put(account.getToken(), account,
					Context.DEFALUT_LOGIN_TIME);
		}
		return account;
	}

	@Override
	public Account getAccountByClientCodeAndName(String clientCode,
			String userName) {
		Client client = clientMapper.getClientByCode(clientCode);
		return mapper.getAccountByClientAndName(client.getId(), userName);
	}

	@Override
	public Account getAccountByClientAndName(String clientId, String userName) {
		return mapper.getAccountByClientAndName(clientId, userName);
	}

	@Override
	public Account getAccountByID(String id) {
		return mapper.getAccountByID(id);
	}

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Account> getAccountPageByClient(String clientId, int start,
			int limit) {
		Page<Account> page = new Page<Account>();
		int totalPage = mapper.getAccountCountByClient(clientId);
		List<Account> list = mapper.getAccountByClient(clientId, start, limit);
		page.setTotalPage(totalPage);
		page.setResults(list);
		return page;
	}

	@Override
	public void deleteAccountByID(String id) {
		mapper.deleteAccountByID(id);

	}

	@Override
	public void updateAccount(Account account) {
		mapper.updateAccount(account);

	}

	@Override
	public void insertAccount(Account account) {
		mapper.insertAccount(account);

	}

	// public SaleUser login(SaleUser user) {
	// mapper.setLoginInfo(user);
	// //登陆信息放到缓存中，缓存时间30分钟
	// CacheFactory.getCache().put(user.getToken(),
	// user,Context.DEFALUT_LOGIN_TIME);
	// return user;
	// }

}
