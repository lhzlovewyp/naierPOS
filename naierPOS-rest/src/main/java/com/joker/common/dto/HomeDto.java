package com.joker.common.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 首页需要展示数据，统一进行封装.
 * 
 * @author lvhaizhen
 *
 */
public class HomeDto {

	//当前营业日期.
	private Date saleDate;
	//客单价
	private BigDecimal perTicketSales;
	//销售金额
	private BigDecimal salesAmount;
	//销售单数
	private Integer orders;
	
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public BigDecimal getPerTicketSales() {
		return perTicketSales;
	}
	public void setPerTicketSales(BigDecimal perTicketSales) {
		this.perTicketSales = perTicketSales;
	}
	public BigDecimal getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	
	
}
