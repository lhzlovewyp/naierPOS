/**
 * 
 */
package com.joker.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.SalesOrderMapper;
import com.joker.common.model.SalesOrder;
import com.joker.common.service.SalesOrderService;

/**
 * @author lvhaizhen
 *
 */
@Service
public class SalesOrderServiceImpl implements SalesOrderService{

	@Autowired
    SalesOrderMapper mapper;
	

	@Override
	public SalesOrder getSalesInfo(String clientId, String storeId, Date salesDate) {
		return mapper.getSalesInfo(clientId, storeId, salesDate);
	}
	

	

}
