package com.joker.common.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.joker.common.model.Brand;
import com.joker.common.model.Client;
import com.joker.common.model.Color;
import com.joker.common.model.ItemClass;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialCategory;
import com.joker.common.model.SalesOrderDiscount;
import com.joker.common.model.Size;
import com.joker.common.model.Unit;
import com.joker.common.model.promotion.Promotion;

/**
 * 
 * 销售单
 * 
 * @author lvhaizhen
 *
 */
public class SaleInfo {

	//前台显示的序号.
	private String sort;
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
	
	//销售单类型：空-商品，1-单项折扣 2-整单折扣 ,3-促销折扣 4-促销相关商品.
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
	//折扣信息.
	private DictDto dict;
	
	//单项折扣金额.
	private BigDecimal discount = new BigDecimal(0) ;
	//整单折扣金额.
	private BigDecimal allDiscount = new BigDecimal(0) ;
	//促销折扣金额.
	private BigDecimal promotionDiscount = new BigDecimal(0) ;
	
	private List<SalesOrderDiscount> promotionDiscountDetails;
	
	//活动折扣.
	private Promotion promotion;
	
	//明细类型.MAT:商品 ITEMDISC:折扣折让 TRANSDISC:整单折扣 PROMDISC:促销折扣
	private ItemClass itemClass;
	
	//如果是促销活动类型，存储参加当前促销活动的物料信息.
	private List<SaleInfo> promotionDetails;
	
	private String relatedId;

	
	public void addPromotionDiscount(BigDecimal amount,String disc){
		if(promotionDiscountDetails == null){
			promotionDiscountDetails = new ArrayList<SalesOrderDiscount>();
		}
		SalesOrderDiscount sod=new SalesOrderDiscount();
		sod.setAmount(amount);
		sod.setDisc(disc);
		promotionDiscountDetails.add(sod);
	}
	
	public void addItemClassCode(String itemCode){
		if(itemClass ==null){
			itemClass=new ItemClass();
			itemClass.setCode(itemCode);
		}else{
			itemClass.setCode(itemCode);
		}
	}
	
	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public Material getMaterial() {
		return this.material;
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

	public BigDecimal getSaleInfoTotalPrice() {
		if(this.retailPrice!=null && this.count!=null){
			return this.retailPrice.multiply(new BigDecimal(this.count)).add(this.discount).add(this.promotionDiscount).add(this.allDiscount);
		}
		return new BigDecimal(0);
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

	public ItemClass getItemClass() {
		return itemClass;
	}

	public void setItemClass(ItemClass itemClass) {
		this.itemClass = itemClass;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<SaleInfo> getPromotionDetails() {
		return promotionDetails;
	}

	public void setPromotionDetails(List<SaleInfo> promotionDetails) {
		this.promotionDetails = promotionDetails;
	}

	public BigDecimal getAllDiscount() {
		return allDiscount;
	}

	public void setAllDiscount(BigDecimal allDiscount) {
		this.allDiscount = allDiscount;
	}

	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public List<SalesOrderDiscount> getPromotionDiscountDetails() {
		return promotionDiscountDetails;
	}

	public void setPromotionDiscountDetails(
			List<SalesOrderDiscount> promotionDiscountDetails) {
		this.promotionDiscountDetails = promotionDiscountDetails;
	}

	public String getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}
	
}
