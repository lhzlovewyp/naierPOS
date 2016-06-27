package com.joker.common.dto;

import java.util.Date;

import com.joker.common.model.ShoppingGuide;

public class SaleDto {

	//当前营业日期.
	private Date saleDate;
	//会员
	private String member;
	//导购
	private ShoppingGuide shoppingGuide;
	
	//整单打折权限
	private String allDISC;
	//单项打折权限
	private String itemDISC;
	
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public ShoppingGuide getShoppingGuide() {
		return shoppingGuide;
	}
	public void setShoppingGuide(ShoppingGuide shoppingGuide) {
		this.shoppingGuide = shoppingGuide;
	}
	public String getAllDISC() {
		return allDISC;
	}
	public void setAllDISC(String allDISC) {
		this.allDISC = allDISC;
	}
	public String getItemDISC() {
		return itemDISC;
	}
	public void setItemDISC(String itemDISC) {
		this.itemDISC = itemDISC;
	}
	
	
	
	
	
}
