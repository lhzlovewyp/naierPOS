/**
 * 
 */
package com.joker.common.model;

/**
 * @author lvhaizhen
 *
 */
public class RetailConfig extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3016876060260604224L;

	/**商户号*/
	private Client client;
	/**商户号*/
	private int priceDecimal;
	/**商户号*/
	private int itemDecimal;
	/**商户号*/
	private int itemRoundDown;
	/**商户号*/
	private int transDecimal;
	/**销售单金额舍去值*/
	private int saleRoundDown;
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public int getPriceDecimal() {
		return priceDecimal;
	}
	public void setPriceDecimal(int priceDecimal) {
		this.priceDecimal = priceDecimal;
	}
	public int getItemDecimal() {
		return itemDecimal;
	}
	public void setItemDecimal(int itemDecimal) {
		this.itemDecimal = itemDecimal;
	}
	public int getItemRoundDown() {
		return itemRoundDown;
	}
	public void setItemRoundDown(int itemRoundDown) {
		this.itemRoundDown = itemRoundDown;
	}
	public int getTransDecimal() {
		return transDecimal;
	}
	public void setTransDecimal(int transDecimal) {
		this.transDecimal = transDecimal;
	}
	public int getSaleRoundDown() {
		return saleRoundDown;
	}
	public void setSaleRoundDown(int saleRoundDown) {
		this.saleRoundDown = saleRoundDown;
	}
}
