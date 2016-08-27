package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Color;
import com.joker.common.model.Payment;
import com.joker.core.annotation.DataSource;

@Repository
public interface PaymentMapper {
	/**
	 * 查询商户支付方式信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Payment> getPaymentPageByCondition(Map<String, Object> map);
	
	/**
	 * 根据code查询支付方式.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Payment getPaymentByCode(String code);

	/**
	 * 查询支持的支付方式.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Payment> getPaymentByCondition(Map<String, Object> map);

	/**
	 * 查询支付方式.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getPaymentCountByCondition(Map<String, Object> map);

	/**
	 * 删除支付方式.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deletePaymentByCode(String code);

	/**
	 * 修改支付方式.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void updatePayment(Payment payment);

	/**
	 * 保存支付方式.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void insertPayment(Payment payment);
}