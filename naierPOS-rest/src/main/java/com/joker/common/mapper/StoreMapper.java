package com.joker.common.mapper;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Client;
import com.joker.common.model.Store;
import com.joker.core.annotation.DataSource;

@Repository
public interface StoreMapper {

	/**
	 * 根据id查询门店信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Store getStoreById(String id);
	
	/**
	 * 根据商户id和门店code查询门店信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Store getStoreByClientAndCode(String clientId,String code);
	
	/**
	 * 根据id删除门店信息.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void deleteStoreById(String id);
	
	
	/**
	 * 新增商门店信息.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void insertStore(Store store);
	
	/**
	 * 修改门店信息.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void updateStore(Store store);
}
