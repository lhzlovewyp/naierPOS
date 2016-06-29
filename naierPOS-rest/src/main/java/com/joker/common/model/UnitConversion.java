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
	private BigDecimal qtya;
	//乙单位.
	private Unit UnitB;
	//乙单位数量.
	private BigDecimal qtyB;
	//说明.
	private String desc;
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
	public BigDecimal getQtya() {
		return qtya;
	}
	public void setQtya(BigDecimal qtya) {
		this.qtya = qtya;
	}
	public Unit getUnitB() {
		return UnitB;
	}
	public void setUnitB(Unit unitB) {
		UnitB = unitB;
	}
	public BigDecimal getQtyB() {
		return qtyB;
	}
	public void setQtyB(BigDecimal qtyB) {
		this.qtyB = qtyB;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
