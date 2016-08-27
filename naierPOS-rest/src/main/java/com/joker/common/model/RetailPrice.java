package com.joker.common.model;

import java.math.BigDecimal;
import java.util.Date;

import com.joker.core.util.DatetimeUtil;

/**
 * 商品价格管理.
 * 
 * @author lvhaizhen
 * 
 */
public class RetailPrice extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4503839587871351573L;

	// 商户
	private Client client;
	// 门店
	private Store store;
	// 物料
	private Material material;
	// 单位
	private Unit unit;
	// 销售价格.
	private BigDecimal price;
	// 生效日期
	private Date effectiveDate;
	// 失效日期.
	private Date expiryDate;

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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
