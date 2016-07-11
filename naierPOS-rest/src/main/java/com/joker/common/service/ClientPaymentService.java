package com.joker.common.service;

import java.util.List;

import com.joker.common.model.ClientPayment;

public interface ClientPaymentService {

	/**
	 * 获取商户支持的支付方式.
	 * 
	 * @param clientId
	 * @return
	 */
	public List<ClientPayment> getClientPayments(String clientId);
}
