package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Store;
import com.joker.core.dto.Page;

public interface StoreService {
	/**
	 * 根据id查询门店信息.
	 * 
	 * @param username
	 * @return
	 */
	public Store getStoreById(String id);

	/**
	 * 根据商户id和门店code查询门店信息.
	 * 
	 * @param username
	 * @return
	 */
	public Store getStoreByClientAndCode(String clientId, String code);

	/**
	 * 查询门店信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Store> getStorePageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	public List<Store> getStorePageByCondition(Map<String, Object> map);

	/**
	 * 根据id删除门店信息.
	 * 
	 * @param username
	 * @return
	 */
	public void deleteStoreById(String id);

	/**
	 * 新增商门店信息.
	 * 
	 * @param username
	 * @return
	 */
	public void insertStore(Store store);

	/**
	 * 修改门店信息.
	 * 
	 * @param username
	 * @return
	 */
	public void updateStore(Store store);
}
