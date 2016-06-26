package com.joker.common.service;

import com.joker.common.model.Client;
import com.joker.common.model.Store;

public interface StoreService {
	/**
	 * 根据id查询门店信息.
	 * @param username
	 * @return
	 */
	public Store getStoreById(String id);
	
	/**
	 * 根据商户id和门店code查询门店信息.
	 * @param username
	 * @return
	 */
	public Store getStoreByClientAndCode(String clientId,String code);
	
	/**
	 * 根据id删除门店信息.
	 * @param username
	 * @return
	 */
	public void deleteStoreById(String id);
	
	
	/**
	 * 新增商门店信息.
	 * @param username
	 * @return
	 */
	public void insertStore(Store store);
	
	/**
	 * 修改门店信息.
	 * @param username
	 * @return
	 */
	public void updateStore(Store store);
}
