/**
 * 
 */
package com.joker.common.model.promotion;

import java.util.Date;
import java.util.List;

import com.joker.common.model.BaseModel;
import com.joker.common.model.Client;
import com.joker.common.model.Store;

/**
 * @author lvhaizhen
 *
 */
public class Promotion extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -485001981099380853L;

	//商户
	private Client client;
	//门店.
	private Store store;
	
	//促销编码
	private String code;
	
	//促销名称
	private String name;
	
	//促销说明
	private String memo;
	
	//生效日期
	private Date effDate;
	
	//失效日期
	private Date expDate;
	
	//生效时间
	private String effTime;
	
	//失效时间
	private String expTime;
	
	//多次生效
	private int repeatEffect;
	
	//限定星期
	private String week;
	
	//限定日期
	private String days;
	
	//NON.不限制、INCL.包含、EXCL.排除
	private String paymentRestrict;
	
	//ALL.会员能参加 ONLY.只能会员参加 NON.会员不能参加
	private String memberRestrict;
	//互斥促销活动
	private String excluded;
	
	private String offerRelation;
	
	//促销支付限制
	private List<PromotionPayment> promotionPayments;
	//促销优惠
	private List<PromotionOffer> promotionOffers;
	
	//促销排序，小的排在前面
	private Integer sort;
	
	public List<PromotionPayment> getPromotionPayments() {
		return promotionPayments;
	}
	public void setPromotionPayments(List<PromotionPayment> promotionPayments) {
		this.promotionPayments = promotionPayments;
	}
	public List<PromotionOffer> getPromotionOffers() {
		return promotionOffers;
	}
	public void setPromotionOffers(List<PromotionOffer> promotionOffers) {
		this.promotionOffers = promotionOffers;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getEffDate() {
		return effDate;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public String getEffTime() {
		return effTime;
	}
	public void setEffTime(String effTime) {
		this.effTime = effTime;
	}
	public String getExpTime() {
		return expTime;
	}
	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}
	public int getRepeatEffect() {
		return repeatEffect;
	}
	public void setRepeatEffect(int repeatEffect) {
		this.repeatEffect = repeatEffect;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getPaymentRestrict() {
		return paymentRestrict;
	}
	public void setPaymentRestrict(String paymentRestrict) {
		this.paymentRestrict = paymentRestrict;
	}
	public String getMemberRestrict() {
		return memberRestrict;
	}
	public void setMemberRestrict(String memberRestrict) {
		this.memberRestrict = memberRestrict;
	}
	public String getExcluded() {
		return excluded;
	}
	public void setExcluded(String excluded) {
		this.excluded = excluded;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getOfferRelation() {
		return offerRelation;
	}
	public void setOfferRelation(String offerRelation) {
		this.offerRelation = offerRelation;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
