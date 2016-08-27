package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Payment;
import com.joker.core.dto.Page;

public interface PaymentService {
	
	public List<Payment> getPaymentPageByCondition(Map<String, Object> map);
	
	/**
	 * 根据code查询支付信息.
	 * 
	 * @param id
	 * @return
	 */
	public Payment getPaymentByCode(String code);

	/**
	 * 查询支付方式.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Payment> getPaymentByCondition(Map<String, Object> map,
			int pageNo, int limit);

	/**
	 * 删除支付方式.
	 * 
	 * @param id
	 * @return
	 */
	public void deletePaymentByCode(String code);

	/**
	 * 修改支付方式.
	 * 
	 * @param payment
	 * @return
	 */
	public void updatePayment(Payment payment);

	/**
	 * 保存支付方式信息.
	 * 
	 * @param payment
	 * @return
	 */
	public void insertPayment(Payment payment);
}