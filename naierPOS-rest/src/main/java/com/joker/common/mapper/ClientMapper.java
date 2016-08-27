package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Client;
import com.joker.core.annotation.DataSource;

@Repository
public interface ClientMapper {

	/**
	 * 根据id查询商户信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Client getClientById(String id);
	
	/**
	 * 根据code查询商户信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Client getClientByCode(String code);
	
	public List<Client> getClientPageByCondition(Map<String, Object> map);
	
	/**
	 * 根据code删除商户信息.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void deleteClientByCode(String code);
	
	/**
	 * 根据id删除商户信息.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void deleteClientById(String id);
	
	/**
	 * 新增商户信息.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void insertClient(Client client);
	
	/**
	 * 修改商户信息.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public void updateClient(Client client);
}
