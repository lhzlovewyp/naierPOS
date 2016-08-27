/**
 * 
 */
package com.joker.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * 销售单
 * @author lvhaizhen
 *
 */
public class SalesOrder extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8623739487823664110L;
	//商户
	private Client client;
	//交易类型  SO-销售 RTN-销售退货
	private String transClass;
	//门店
	private Store store;
	//营业日期
	private Date salesDate;
	//交易序号
	private int code;
	//原始单号 交易类型为RTN时才需要，关联SalesOrder.ID
	private SalesOrder originOrder; 
	
	private Member member;
	
	//导购员
	private ShoppingGuide shoppingGuide;
	//销售总数
	private BigDecimal quantity;
	//销售金额
	private BigDecimal amount;
	//销售折扣
	private BigDecimal discount;
	//抹零金额
	private BigDecimal minusChange;
	//应付金额
	private BigDecimal payable;
	//实付金额
	private BigDecimal paid;
	//溢收金额
	private BigDecimal excess;
	//取消促销
	private String cancelPromotion;
	//是否已传百胜.
	private Integer toBaison;
	//完成时间
	private Date finished;
	
	private List<SalesOrderDetails> salesOrderDetails;
	
	private List<SalesOrderDiscount> salesOrderDiscount;
	
	private List<SalesOrderPay> salesOrderPay;
	
	private String cashFlag;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getTransClass() {
		return transClass;
	}

	public void setTransClass(String transClass) {
		this.transClass = transClass;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public SalesOrder getOriginOrder() {
		return originOrder;
	}

	public void setOriginOrder(SalesOrder originOrder) {
		this.originOrder = originOrder;
	}

	

	public ShoppingGuide getShoppingGuide() {
		return shoppingGuide;
	}

	public void setShoppingGuide(ShoppingGuide shoppingGuide) {
		this.shoppingGuide = shoppingGuide;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getMinusChange() {
		return minusChange;
	}

	public void setMinusChange(BigDecimal minusChange) {
		this.minusChange = minusChange;
	}

	public BigDecimal getPayable() {
		return payable;
	}

	public void setPayable(BigDecimal payable) {
		this.payable = payable;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public BigDecimal getExcess() {
		return excess;
	}

	public void setExcess(BigDecimal excess) {
		this.excess = excess;
	}

	public String getCancelPromotion() {
		return cancelPromotion;
	}

	public void setCancelPromotion(String cancelPromotion) {
		this.cancelPromotion = cancelPromotion;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	public List<SalesOrderDetails> getSalesOrderDetails() {
		return salesOrderDetails;
	}

	public void setSalesOrderDetails(List<SalesOrderDetails> salesOrderDetails) {
		this.salesOrderDetails = salesOrderDetails;
	}

	public List<SalesOrderDiscount> getSalesOrderDiscount() {
		return salesOrderDiscount;
	}

	public void setSalesOrderDiscount(List<SalesOrderDiscount> salesOrderDiscount) {
		this.salesOrderDiscount = salesOrderDiscount;
	}

	public List<SalesOrderPay> getSalesOrderPay() {
		return salesOrderPay;
	}

	public void setSalesOrderPay(List<SalesOrderPay> salesOrderPay) {
		this.salesOrderPay = salesOrderPay;
	}

	public String getCashFlag() {
		return cashFlag;
	}

	public void setCashFlag(String cashFlag) {
		this.cashFlag = cashFlag;
	}

	

	

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getToBaison() {
		return toBaison;
	}

	public void setToBaison(Integer toBaison) {
		this.toBaison = toBaison;
	}
	
	
	
}
