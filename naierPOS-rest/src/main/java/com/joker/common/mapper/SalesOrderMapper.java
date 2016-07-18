package com.joker.common.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.SalesOrder;
import com.joker.common.model.SalesOrderDetails;
import com.joker.common.model.SalesOrderDiscount;
import com.joker.common.model.SalesOrderPay;
import com.joker.core.annotation.DataSource;

@Repository
public interface SalesOrderMapper {

	/**
	 * 获取指定日期的销量数据.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public SalesOrder getSalesInfo(@Param("clientId") String clientId,@Param("storeId")String storeId,@Param("salesDate") Date salesDate);
	
	
	/**
	 * 保存销售单
	 * @param salesOrder
	 * @return
	 */
	public int saveSalesOrder(SalesOrder salesOrder);
	
	/**
	 * 保存销售单明细.
	 * @param details
	 * @return
	 */
	public int saveSalesOrderDetail(List<SalesOrderDetails> details);
	
	/**
	 * 保存支付折扣信息.
	 * @param discounts
	 * @return
	 */
	public int saveSalesOrderDiscount(List<SalesOrderDiscount> discounts);
	
	/**
	 * 保存支付方式.
	 * @param pays
	 * @return
	 */
	public int saveSalesOrderPay(List<SalesOrderPay> pays);
	
}
