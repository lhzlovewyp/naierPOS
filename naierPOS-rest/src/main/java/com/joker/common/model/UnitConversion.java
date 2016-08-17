/**
 * 
 */
package com.joker.common.model;

import java.math.BigDecimal;

/**
 * 
 * 单位换算.
 * @author lvhaizhen
 *
 */
public class UnitConversion extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9182090773955608655L;

	//商户.
	private Client client;
	//甲单位.
	private Unit unitA;
	//甲单位数量.
	private BigDecimal qtyA;
	//乙单位.
	private Unit unitB;
	//乙单位数量.
	private BigDecimal qtyB;
	//说明.
	private String remark;
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Unit getUnitA() {
		return unitA;
	}
	public void setUnitA(Unit unitA) {
		this.unitA = unitA;
	}
	public BigDecimal getQtyA() {
		return qtyA;
	}
	public void setQtyA(BigDecimal qtyA) {
		this.qtyA = qtyA;
	}
	public Unit getUnitB() {
		return unitB;
	}
	public void setUnitB(Unit unitB) {
		this.unitB = unitB;
	}
	public BigDecimal getQtyB() {
		return qtyB;
	}
	public void setQtyB(BigDecimal qtyB) {
		this.qtyB = qtyB;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
}
