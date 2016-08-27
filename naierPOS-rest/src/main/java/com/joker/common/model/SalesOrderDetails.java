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
public class SalesOrderDetails extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5391319238634338171L;

	/**商户*/
	private Client client;
	/**销售单*/
	private SalesOrder salesOrder;
	/**交易类型*/
	private String transClass;
	/**门店*/
	private Store store;
	/**终端*/
	private Terminal terminal;
	/**营业日期.*/
	private Date salesDate;
	/**交易序号*/
	private Integer code;
	/**序号*/
	private Integer serialNo;
	/**明细类型*/
	private ItemClass itemClass;
	/**商品*/
	private Material material;
	/**销售数量*/
	private BigDecimal quantity =new BigDecimal(0);
	/**销售单位*/
	private Unit salesUnit;
	/**基本单位数量*/
	private BigDecimal basicQuantity;
	/**基本单位*/
	private Unit basicUnit;
	/**换算率*/
	private BigDecimal conversion;
	/**销售价格.*/
	private BigDecimal price;
	/**销售金额*/
	private BigDecimal amount =new BigDecimal(0);
	/**销售折扣*/
	private BigDecimal discount =new BigDecimal(0);
	/**抹零金额*/
	private BigDecimal minusChange = new BigDecimal(0);
	/**销售净额*/
	private BigDecimal payable =new BigDecimal(0);
	
	private Promotion promotion;
	/**颜色*/
	private Color color;
	/**尺寸.*/
	private Size size;

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

	public ItemClass getItemClass() {
		return itemClass;
	}

	public void setItemClass(ItemClass itemClass) {
		this.itemClass = itemClass;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Unit getSalesUnit() {
		return salesUnit;
	}

	public void setSalesUnit(Unit salesUnit) {
		this.salesUnit = salesUnit;
	}

	public BigDecimal getBasicQuantity() {
		return basicQuantity;
	}

	public void setBasicQuantity(BigDecimal basicQuantity) {
		this.basicQuantity = basicQuantity;
	}

	public Unit getBasicUnit() {
		return basicUnit;
	}

	public void setBasicUnit(Unit basicUnit) {
		this.basicUnit = basicUnit;
	}

	public BigDecimal getConversion() {
		return conversion;
	}

	public void setConversion(BigDecimal conversion) {
		this.conversion = conversion;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
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
