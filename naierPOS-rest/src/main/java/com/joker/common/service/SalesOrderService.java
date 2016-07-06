package com.joker.common.service;

import java.util.Date;

import com.joker.common.model.SalesOrder;

public interface SalesOrderService {
	/**
	 * 获取销售总数信息.
	 * @param username
	 * @return
	 */
	public SalesOrder getSalesInfo(String clientId,String storeId,Date salesDate);
	
	
}