/**
 * 
 */
package com.joker.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.ClientPaymentMapper;
import com.joker.common.model.ClientPayment;
import com.joker.common.service.ClientPaymentService;

/**
 * @author lvhaizhen
 *
 */
@Service
public class ClientPaymentServiceImpl implements ClientPaymentService {

	@Autowired
	ClientPaymentMapper mapper;
	
	@Override
	public List<ClientPayment> getClientPayments(String clientId) {
		// TODO Auto-generated method stub
		return mapper.getClientPayments(clientId);
	}

}
