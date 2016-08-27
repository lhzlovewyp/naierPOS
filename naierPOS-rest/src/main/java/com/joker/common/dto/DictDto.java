package com.joker.common.dto;

/**
 * 折扣信息.
 * 
 * @author lvhaizhen
 *
 */
public class DictDto {

	//折扣类型 1-折扣 2-折让
	private String type;
	//折扣对应的销售单信息
	private SaleInfo saleInfo;
	//折扣值
	private String dict;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SaleInfo getSaleInfo() {
		return saleInfo;
	}

	public void setSaleInfo(SaleInfo saleInfo) {
		this.saleInfo = saleInfo;
	}

	public String getDict() {
		return dict;
	}

	public void setDict(String dict) {
		this.dict = dict;
	}
	
	
}
