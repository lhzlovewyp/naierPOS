/**
 * 
 */
package com.joker.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.PaymentMapper;
import com.joker.common.model.Payment;
import com.joker.common.service.PaymentService;

/**
 * @author zhangfei
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentMapper mapper;

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Payment> getPaymentPageByCondition(Map<String, Object> map) {
		List<Payment> list = mapper.getPaymentPageByCondition(map);
		return list;
	}

}