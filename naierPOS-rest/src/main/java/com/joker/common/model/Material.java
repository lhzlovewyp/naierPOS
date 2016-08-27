/**
 * 
 */
package com.joker.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.joker.common.dto.AttrDto;

/**
 * 物料
 * 
 * @author lvhaizhen
 *
 */
public class Material  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1440190141170417762L;

	//商户
	private Client client;
	//物料编码
	private String code;
	//物料名称
	private String name;
	//简称
	private String abbr;
	//品类
	private MaterialCategory category;
	//品牌
	private Brand brand;
	//基本单位
	private Unit basicUnit;
	//销售单位
	private Unit salesUnit;
	//与基本单位换算率,如果是通用换算率，此处可以为空.
	private BigDecimal salesConversion;
	//标准零售价
	private BigDecimal retailPrice;
	//条形码
	private String barCode;
	//需要维护属性.
	private String property;
	
	private String displayPhoto;
	
	private List<MaterialProperty> properties;
	
	private List<AttrDto> attrDtos;
	
	private Color color;
	
	private Size size;
	
	public String getDisplayPhoto() {
		return displayPhoto;
	}

	public void setDisplaoyPhoto(String displayPhoto) {
		this.displayPhoto = displayPhoto;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public BigDecimal getSalesConversion() {
		return salesConversion;
	}

	public void setSalesConversion(BigDecimal salesConversion) {
		this.salesConversion = salesConversion;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
		
		
	}

	public List<MaterialProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<MaterialProperty> properties) {
		this.properties = properties;
		//在往property中设置属性的时候，自动转换.
		if(CollectionUtils.isNotEmpty(properties)){
			Map<String,AttrDto> map=new HashMap<String,AttrDto>();
			attrDtos=new ArrayList<AttrDto>();
			for(MaterialProperty property:properties){
				if(map.containsKey(property.getColor().getId())){
					map.get(property.getColor().getId()).addSize(property.getSize());
				}else{
					AttrDto attrDto=new AttrDto();
					attrDto.setColor(property.getColor());
					attrDto.addSize(property.getSize());
					map.put(property.getColor().getId(), attrDto);
					attrDtos.add(attrDto);
				}
			}
		}
	}

	public List<AttrDto> getAttrDtos() {
		return attrDtos;
	}

	public void setAttrDtos(List<AttrDto> attrDtos) {
		this.attrDtos = attrDtos;
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



