package com.joker.common.dto;

import java.math.BigDecimal;

import com.joker.common.model.Brand;
import com.joker.common.model.Client;
import com.joker.common.model.Color;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialCategory;
import com.joker.common.model.Size;
import com.joker.common.model.Unit;

/**
 * 
 * 销售单
 * 
 * @author lvhaizhen
 *
 */
public class SaleInfo {

	//商户
	private Client client;
	
	private String id;
	
	private String code;
	
	private String name;
	
	private String abbr;
	
	private MaterialCategory category;
	
	private Brand brand;
	
	private Unit basicUnit;
	private Unit salesUnit;
	
	private String property;
	
	private Color color;
	
	private Size size;
	
	//销售单类型：空-商品，1-单项折扣 2-整单折扣
	private String type;
	//1-单项折扣 2-整单折扣
	private String discType;
	//物料信息.
	private Material material;
	//商品数量.
	private Integer count;
	//商品单价
	private BigDecimal retailPrice;
	//商品总价.
	private BigDecimal totalPrice;
	
	private DictDto dict;
	

	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public BigDecimal getSaleInfoTotlaPrice() {
		return this.retailPrice.multiply(new BigDecimal(this.count));
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public DictDto getDict() {
		return dict;
	}

	public void setDict(DictDto dict) {
		this.dict = dict;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public MaterialCategory getCategory() {
		return category;
	}

	public void setCategory(MaterialCategory category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Unit getBasicUnit() {
		return basicUnit;
	}

	public void setBasicUnit(Unit basicUnit) {
		this.basicUnit = basicUnit;
	}

	public Unit getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(Unit salesUnit) {
		this.salesUnit = salesUnit;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
}
