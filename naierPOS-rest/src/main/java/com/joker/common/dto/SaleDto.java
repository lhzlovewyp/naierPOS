package com.joker.common.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.joker.common.model.ClientPayment;
import com.joker.common.model.Member;
import com.joker.common.model.ShoppingGuide;
import com.joker.common.model.promotion.Promotion;

public class SaleDto {

	private String id;
	//当前营业日期.
	private Date saleDate;
	//会员
	private Member member;
	
	//导购
	private ShoppingGuide shoppingGuide;
	
	//整单打折权限
	private String allDISC;
	//单项打折权限
	private String itemDISC;
	//总的商品价格.
	private BigDecimal totalPrice;
	
	//总的商品数量.
	private Integer totalNum;
	
	//打折价格.
	private BigDecimal totalDiscPrice;
	//实际支付金额
	private BigDecimal pay;
	//已支付金额
	private BigDecimal payed;
	//需要支付金额
	private BigDecimal needPay;
	//销售单.
	private List<SaleInfo> saleInfos;
	//可以参加的促销活动
	private List<Promotion> effPromotions;
	//参加的促销活动.
	private List<Promotion> promotions;
	
	private List<ClientPayment> payments;
	
	private String cancelPromotion;
	
	//冗余字段,促销金额.
	private BigDecimal promotionAmount = new BigDecimal(0) ;
	
	public void addPromotionAmount(BigDecimal num){
		promotionAmount=promotionAmount.add(num);
	}
	
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
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
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<SaleInfo> getSaleInfos() {
		return saleInfos;
	}
	public void setSaleInfos(List<SaleInfo> saleInfos) {
		this.saleInfos = saleInfos;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public BigDecimal getTotalDiscPrice() {
		return totalDiscPrice;
	}
	public void setTotalDiscPrice(BigDecimal totalDiscPrice) {
		this.totalDiscPrice = totalDiscPrice;
	}
	public BigDecimal getPay() {
		return pay;
	}
	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}
	public BigDecimal getPayed() {
		return payed;
	}
	public void setPayed(BigDecimal payed) {
		this.payed = payed;
	}
	public BigDecimal getNeedPay() {
		return needPay;
	}
	public void setNeedPay(BigDecimal needPay) {
		this.needPay = needPay;
	}
	public List<Promotion> getEffPromotions() {
		return effPromotions;
	}
	public void setEffPromotions(List<Promotion> effPromotions) {
		this.effPromotions = effPromotions;
	}
	public List<Promotion> getPromotions() {
		return promotions;
	}
	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}
	public String getCancelPromotion() {
		return cancelPromotion;
	}
	public void setCancelPromotion(String cancelPromotion) {
		this.cancelPromotion = cancelPromotion;
	}
	public List<ClientPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<ClientPayment> payments) {
		this.payments = payments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getPromotionAmount() {
		return promotionAmount;
	}
	public void setPromotionAmount(BigDecimal promotionAmount) {
		this.promotionAmount = promotionAmount;
	}
	
	
}
