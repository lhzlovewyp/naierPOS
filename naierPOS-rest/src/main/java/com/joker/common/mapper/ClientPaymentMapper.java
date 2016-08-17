package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.ClientPayment;
import com.joker.core.annotation.DataSource;

@Repository
public interface ClientPaymentMapper {

	/**
	 * 根据商户、用户名查询用户信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public List<ClientPayment> getClientPayments(
			@Param("clientId") String clientId);

	/**
	 * 根据id查询商户支付方式信息
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public ClientPayment getClientPaymentByID(String id);

	/**
	 * 查询商户支付方式信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<ClientPayment> getClientPaymentPageByCondition(
			Map<String, Object> map);

	/**
	 * 查询商户支付方式信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getClientPaymentCountByCondition(Map<String, Object> map);

	/**
	 * 删除商户支付方式信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteClientPaymentByID(String id);

	/**
	 * 修改商户支付方式信息.
	 * 
	 * @param clientPayment
	 * @return
	 */
	@DataSource("master")
	public void updateClientPayment(ClientPayment clientPayment);

	/**
	 * 保存商户支付方式信息.
	 * 
	 * @param clientPayment
	 * @return
	 */
	@DataSource("master")
	public void insertClientPayment(ClientPayment clientPayment);
}
