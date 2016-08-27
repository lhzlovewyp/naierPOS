/**
 * 
 */
package com.joker.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.AccountMapper;
import com.joker.common.mapper.AccountRoleMapper;
import com.joker.common.mapper.AccountStoreMapper;
import com.joker.common.mapper.ClientMapper;
import com.joker.common.mapper.StoreMapper;
import com.joker.common.model.Account;
import com.joker.common.model.AccountRole;
import com.joker.common.model.AccountStore;
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

	@Autowired
	AccountStoreMapper accountStoreMapper;

	@Autowired
	AccountRoleMapper accountRoleMapper;

	@Override
	public Account login(String clientId, String userName) {
		// 获取当前账号信息.
		Account account = this.getAccountByClientAndName(clientId, userName);
		if (account != null) {
			String token = UUID.randomUUID().toString();
			account.setToken(token);

			// 获取门店信息
			List<Store> stores=storeMapper.getStoresByAid(account.getId());
			
			if (CollectionUtils.isNotEmpty(stores)) {
				account.setStores(stores);
				//如果只存在一个门店信息,直接写入，不需要选择门店.
				if(stores.size()==1){
					account.setStore(stores.get(0));
				}
			}
			

			// 获取账号对应的角色信息.
			List<Role> roles = roleService.getRoleByAccountId(account
					.getId());
			if (CollectionUtils.isNotEmpty(roles)) {
				account.setRoles(roles);
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
		Account account = mapper.getAccountByID(id);
		if (account != null) {
			// 获取账号对应的角色信息.
			List<Role> roles = roleService.getRoleByAccountId(id);
			if (CollectionUtils.isNotEmpty(roles)) {
				account.setRoles(roles);
			}
			// 获取门店信息
			List<Store> stores=storeMapper.getStoresByAid(account.getId());
			if (CollectionUtils.isNotEmpty(stores)) {
				account.setStores(stores);
			}
		}
		return account;
	}

	/**
	 * 根据商户查询用户信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Account> getAccountPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Account> page = new Page<Account>();
		int totalRecord = mapper.getAccountCountByCondition(map);
		List<Account> list = mapper.getAccountPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Store> cacheMap = new HashMap<String, Store>();
			for (Account account : list) {
				if (account != null) {
					if (account.getStore() != null
							&& StringUtils.isNotBlank(account.getStore()
									.getId())) {
						String storeId = account.getStore().getId();
						Store store = null;
						if (cacheMap.containsKey(storeId)) {
							store = cacheMap.get(storeId);
						} else {
							store = storeMapper.getStoreById(storeId);
							if (store != null) {
								cacheMap.put(storeId, store);
							}
						}
						if (store != null) {
							account.setStore(store);
						}
					}

					if (account.getId() != null) {
						// 获取账号对应的角色信息.
						List<Role> roles = roleService
								.getRoleByAccountId(account.getId());
						if (CollectionUtils.isNotEmpty(roles)) {
							account.setRoles(roles);
						}
					}
				}
			}
		}

		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public void deleteAccountByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					//删除账号.
					mapper.deleteAccountByID(oneId);
					//删除角色信息
					AccountStore delBean = new AccountStore();
					Account acc=new Account();
					acc.setId(oneId);
					delBean.setAccount(acc);
					accountStoreMapper.deleteAccountStoreByCondition(delBean);
					//删除所属门店信息.
					AccountRole delRoleBean = new AccountRole();
					delRoleBean.setAccount(acc);
					accountRoleMapper.deleteAccountRoleByCondition(delRoleBean);
				}
			}
		}
	}

	@Override
	public void updateAccount(Account account) {
		mapper.updateAccount(account);

		if (account.getClient() != null
				&& StringUtils.isNotBlank(account.getClient().getId())) {
			Client client = account.getClient();
			AccountStore delBean = new AccountStore();
			delBean.setAccount(account);
			accountStoreMapper.deleteAccountStoreByCondition(delBean);

			if (CollectionUtils.isNotEmpty(account.getStores())) {
				Date createTime = account.getCreated();
				String creator = account.getCreator();
				for (Store store : account.getStores()) {
					AccountStore accountStore = new AccountStore();
					accountStore.setId(UUID.randomUUID().toString());
					accountStore.setAccount(account);
					accountStore.setClient(client);
					accountStore.setStore(store);
					accountStore.setStatus(Constants.STATUS_SUCCESS);
					accountStore.setCreated(createTime);
					accountStore.setCreator(creator);
					accountStoreMapper.insertAccountStore(accountStore);
				}
			}
			
			
			AccountRole delRoleBean = new AccountRole();
			delRoleBean.setAccount(account);
			accountRoleMapper.deleteAccountRoleByCondition(delRoleBean);
			if (CollectionUtils.isNotEmpty(account.getRoles())) {
				Date createTime = account.getCreated();
				String creator = account.getCreator();
				for (Role role : account.getRoles()) {
					AccountRole accountRole = new AccountRole();
					accountRole.setId(UUID.randomUUID().toString());
					accountRole.setAccount(account);
					accountRole.setClient(client);
					accountRole.setRole(role);
					accountRole.setStatus(Constants.STATUS_SUCCESS);
					accountRole.setCreated(createTime);
					accountRole.setCreator(creator);
					accountRoleMapper.insertAccountRole(accountRole);
				}
			}
		}
	}

	@Override
	public void insertAccount(Account account) {
		if (StringUtils.isBlank(account.getId())) {
			account.setId(UUID.randomUUID().toString());
		}
		mapper.insertAccount(account);

		if (account.getClient() != null
				&& StringUtils.isNotBlank(account.getClient().getId())) {
			Client client = account.getClient();

			if (CollectionUtils.isNotEmpty(account.getStores())) {
				Date createTime = account.getCreated();
				String creator = account.getCreator();
				for (Store store : account.getStores()) {
					AccountStore accountStore = new AccountStore();
					accountStore.setId(UUID.randomUUID().toString());
					accountStore.setAccount(account);
					accountStore.setClient(client);
					accountStore.setStore(store);
					accountStore.setStatus(Constants.STATUS_SUCCESS);
					accountStore.setCreated(createTime);
					accountStore.setCreator(creator);
					accountStoreMapper.insertAccountStore(accountStore);
				}
			}
			
			if (CollectionUtils.isNotEmpty(account.getRoles())) {
				Date createTime = account.getCreated();
				String creator = account.getCreator();
				for (Role role : account.getRoles()) {
					AccountRole accountRole = new AccountRole();
					accountRole.setId(UUID.randomUUID().toString());
					accountRole.setAccount(account);
					accountRole.setClient(client);
					accountRole.setRole(role);
					accountRole.setStatus(Constants.STATUS_SUCCESS);
					accountRole.setCreated(createTime);
					accountRole.setCreator(creator);
					accountRoleMapper.insertAccountRole(accountRole);
				}
			}
		}
	}

	// public SaleUser login(SaleUser user) {
	// mapper.setLoginInfo(user);
	// //登陆信息放到缓存中，缓存时间30分钟
	// CacheFactory.getCache().put(user.getToken(),
	// user,Context.DEFALUT_LOGIN_TIME);
	// return user;
	// }

}
