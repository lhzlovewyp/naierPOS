package com.joker.common.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.SalesOrder;
import com.joker.common.model.SalesOrderDetails;
import com.joker.common.model.SalesOrderDiscount;
import com.joker.common.model.SalesOrderPay;
import com.joker.common.model.Store;
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
	 * 查询订单详细信息.
	 * 
	 * @param salesOrderId
	 * @return
	 */
	public List<SalesOrderDetails> getSalesOrderDetailById(@Param("salesOrderId")String salesOrderId);
	
	
	/**
	 * 获取订单支付信息.
	 * @param salesOrderId
	 * @return
	 */
	public List<SalesOrderPay> getSalesOrderPayById(@Param("salesOrderId")String salesOrderId);
	
	/**
	 * 获取订单折扣信息.
	 * @param salesOrderId
	 * @return
	 */
	public List<SalesOrderDiscount> getSalesOrderDiscountById(@Param("salesOrderId")String salesOrderId);
	
	/**
	 * 根据条件查询销售单信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<SalesOrder> getSalesOrderPageByCondition(Map<String, Object> map);
	
	/**
	 * 根据条件查询销售单记录数.
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getSalesOrderCountByCondition(Map<String, Object> map);
	
	/**
	 * 保存销售单
	 * @param salesOrder
	 * @return
	 */
	public int saveSalesOrder(SalesOrder salesOrder);
	
	/**
	 * 修改销售单.
	 * @param salesOrder
	 * @return
	 */
	public int updateSalesOrder(SalesOrder salesOrder);
	
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
	
	
	
	/**
	 * 根据条件查询支付信息.
	 * @param salesOrderId
	 * @return
	 */
	@DataSource("slave")
	public List<SalesOrderPay> getSalesOrderPayByCondition(Map<String, Object> map);
	/**
	 * 销售明细查询.
	 * @param startDate,endDate,code
	 * @return
	 */
	public List<SalesOrder> getSalesOrderPageByDate(Map<String, Object> map);
	
	/**
	 * 根据商品查询销售订单.
	 * @param startDate,endDate,code
	 * @return
	 */
	public List<SalesOrder> getSalesOrderPageByMaterial(Map<String, Object> map);

	/**
	 * 根据商品查询销售订单 总页数.
	 * @param startDate,endDate,code
	 * @return
	 */
	public int getSalesOrderCountByMaterial(Map<String, Object> map);

	/**
	 * 根据商品查询销售订单 总页数.
	 * @param startDate,endDate,code
	 * @return
	 */
	public int getSalesOrderDetailCountByMaterial(Map<String, Object> map);
	/**
	 * 根据条件查询销售明细信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<SalesOrderDetails> getSalesOrderDetailPageByCondition(Map<String, Object> map);

	@DataSource("slave")
	public Store getStoreByIds(Map<String, String> map);
	
	@DataSource("slave")
	public List<SalesOrder> getLimitSalesOrderByFinished(Map<String,Date> map);
	
}
