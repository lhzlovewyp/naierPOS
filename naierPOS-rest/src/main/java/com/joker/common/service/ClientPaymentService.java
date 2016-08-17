package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.ClientPayment;
import com.joker.core.dto.Page;

public interface ClientPaymentService {

	/**
	 * 获取商户支持的支付方式.
	 * 
	 * @param clientId
	 * @return
	 */
	public List<ClientPayment> getClientPayments(String clientId);

	/**
	 * 根据id查询支付方式信息.
	 * 
	 * @param id
	 * @return
	 */
	public ClientPayment getClientPaymentByID(String id);

	/**
	 * 根据商户查询支付方式信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<ClientPayment> getClientPaymentPageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<ClientPayment> getClientPaymentPageByCondition(
			Map<String, Object> map);

	/**
	 * 删除支付方式信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteClientPaymentByID(String id);

	/**
	 * 修改支付方式信息.
	 * 
	 * @param clientPayment
	 * @return
	 */
	public void updateClientPayment(ClientPayment clientPayment);

	/**
	 * 保存支付方式信息.
	 * 
	 * @param clientPayment
	 * @return
	 */
	public void insertClientPayment(ClientPayment clientPayment);
}