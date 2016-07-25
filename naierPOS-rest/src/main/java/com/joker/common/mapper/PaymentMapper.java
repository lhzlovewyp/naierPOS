package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

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
}