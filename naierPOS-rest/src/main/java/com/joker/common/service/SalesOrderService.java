package com.joker.common.service;

import java.util.Date;
import java.util.Map;

import com.joker.common.dto.SaleDto;
import com.joker.common.model.Account;
import com.joker.common.model.SalesOrder;
import com.joker.core.dto.Page;

public interface SalesOrderService {
	
	
	/**
	 * 查询销售单记录.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<SalesOrder> getSalesOrderPageByClient(Map<String, Object> map,
			int pageNo, int limit);
	
	/**
	 * 获取销售总数信息.
	 * @param username
	 * @return
	 */
	public SalesOrder getSalesInfo(String clientId,String storeId,Date salesDate);
	
	/**
	 * 销售单退货.
	 * 
	 * @param clientId
	 * @param storeId
	 * @param id
	 * @return
	 */
	public String addRefund(String clientId,String storeId,String id);
	
	/**
	 * 
	 * 获取销售单信息，销售单内容包含明细，折扣和支付方式.
	 * 
	 * @param id
	 * @return
	 */
	public SalesOrder getSalesOrderById(String clientId,String storeId,String id);
	
	/**
	 * 保存销售单.
	 * 
	 * @param saleDto
	 */
	public boolean addSaleInfo(SaleDto saleDto,Account account);
	
	
	
	
}
