package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Payment;

public interface PaymentService {
	public List<Payment> getPaymentPageByCondition(Map<String, Object> map);
}