/**
 * 
 */
package com.joker.common.model;

import java.math.BigDecimal;
import java.util.Date;

import com.joker.common.model.promotion.Promotion;

/**
 * @author lvhaizhen
 *
 */
public class SalesOrderDiscount extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 760018297898836791L;

	/**商户*/
	private Client client;
	/**销售单*/
	private SalesOrder salesOrder;
	/**交易类型*/
	private String transClass;
	/**门店*/
	private Store store;
	/**收银终端*/
	private Terminal terminal;
	/**营业日期*/
	private Date salesDate;
	/**交易序号*/
	private Integer code;
	/**折扣类销售明细*/
	private String disc;
	/**商品类销售明细*/
	private String mat;
	/**分摊折扣金额*/
	private BigDecimal amount;
	
	private Promotion promotion;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
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

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDisc() {
		return disc;
	}

	public void setDisc(String disc) {
		this.disc = disc;
	}

	public String getMat() {
		return mat;
	}

	public void setMat(String mat) {
		this.mat = mat;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	
} 
