package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Store;
import com.joker.core.annotation.DataSource;

@Repository
public interface StoreMapper {

	/**
	 * 根据id查询门店信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Store getStoreById(String id);
	
	/**
	 * 根据账号id查询门店信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public List<Store> getStoresByAid(String accountId);

	/**
	 * 根据商户id和门店code查询门店信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Store getStoreByClientAndCode(String clientId, String code);

	/**
	 * 查询门店信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Store> getStorePageByCondition(Map<String, Object> map);

	/**
	 * 查询门店信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getStoreCountByCondition(Map<String, Object> map);

	/**
	 * 根据id删除门店信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void deleteStoreById(String id);

	/**
	 * 新增商门店信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void insertStore(Store store);

	/**
	 * 修改门店信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void updateStore(Store store);
}
