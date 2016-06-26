package com.joker.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.StoreMapper;
import com.joker.common.model.Store;
import com.joker.common.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
    StoreMapper mapper;

	@Override
	public Store getStoreById(String id) {
		return mapper.getStoreById(id);
	}
	@Override
	public Store getStoreByClientAndCode(String clientId, String code) {
		return mapper.getStoreByClientAndCode(clientId, code);
	}
	@Override
	public void deleteStoreById(String id) {
		mapper.deleteStoreById(id);
	}

	@Override
	public void insertStore(Store store) {
		mapper.insertStore(store);
	}
	@Override
	public void updateStore(Store store) {
		mapper.updateStore(store);
	}
}
