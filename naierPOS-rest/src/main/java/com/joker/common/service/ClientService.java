package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Client;

public interface ClientService {

	/**
	 * 根据id查询商户信息.
	 * @param username
	 * @return
	 */
	public Client getClientById(String clientId);
	
	/**
	 * 根据code查询商户信息.
	 * @param username
	 * @return
	 */
	public Client getClientByCode(String code);
	
	public List<Client> getClientPageByCondition(Map<String, Object> map);
	
	/**
	 * 根据code删除商户信息.
	 * @param username
	 * @return
	 */
	public void deleteClientByCode(String code);
	
	/**
	 * 根据id删除商户信息.
	 * @param username
	 * @return
	 */
	public void deleteClientById(String id);
	
	/**
	 * 新增商户信息.
	 * @param username
	 * @return
	 */
	public void insertClient(Client client);
	
	/**
	 * 修改商户信息.
	 * @param username
	 * @return
	 */
	public void updateClient(Client client);
	
	
}
