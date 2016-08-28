/**
 * 
 */
package com.joker.common.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lvhaizhen
 *
 */
public class SalesOrderPay extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9069484008102305921L;

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
	/**序号*/
	private Integer serialNo;
	/**支付时间*/
	private Date payTime;
	/**支付方式*/
	private Payment payment;
	/**金额*/
	private BigDecimal amount = new BigDecimal(0);
	/**找零*/
	private BigDecimal changed = new BigDecimal(0);
	/**溢收*/
	private BigDecimal excess = new BigDecimal(0);
	/**支付流水号*/
	private String transNo;
	
	private BigDecimal points;

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

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getChanged() {
		return changed;
	}

	public void setChange(BigDecimal changed) {
		this.changed = changed;
	}

	public BigDecimal getExcess() {
		return excess;
	}

	public void setExcess(BigDecimal excess) {
		this.excess = excess;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public void setChanged(BigDecimal changed) {
		this.changed = changed;
	}
	
	
}
