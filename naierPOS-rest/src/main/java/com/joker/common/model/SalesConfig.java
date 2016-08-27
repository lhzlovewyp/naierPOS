/**
 * 
 */
package com.joker.common.model;

import java.util.Date;

import com.joker.core.util.DatetimeUtil;

/**
 * @author lvhaizhen
 * 
 */
public class SalesConfig extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7083852360212571168L;

	// 商户
	private Client client;
	// 门店
	private Store store;
	// 销售终端
	private Terminal terminal;
	// 营业日期
	private Date salesDate;
	// 流水编号
	private Integer maxCode;
	// 并发标记
	private Integer flag;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public Integer getMaxCode() {
		return maxCode;
	}

	public void setMaxCode(Integer maxCode) {
		this.maxCode = maxCode;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
