/**
 * 
 */
package com.joker.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.AccountStoreMapper;
import com.joker.common.model.AccountStore;
import com.joker.common.service.AccountStoreService;

/**
 * @author zhangfei
 * 
 */
@Service
public class AccountStoreServiceImpl implements AccountStoreService {

	@Autowired
	AccountStoreMapper mapper;

	@Override
	public void deleteAccountStoreByID(String id) {
		mapper.deleteAccountStoreByID(id);

	}

	@Override
	public void deleteAccountStoreByCondition(AccountStore accountStore) {
		mapper.deleteAccountStoreByCondition(accountStore);
	}
	
	@Override
	public void updateAccountStore(AccountStore accountStore) {
		mapper.updateAccountStore(accountStore);

	}

	@Override
	public void insertAccountStore(AccountStore accountStore) {
		mapper.insertAccountStore(accountStore);

	}
}