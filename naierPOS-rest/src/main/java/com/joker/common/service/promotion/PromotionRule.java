package com.joker.common.service.promotion;

import com.joker.common.dto.SaleDto;

/**
 * 
 * 定义通用的促销规则接口.
 * 
 * @author lvhaizhen
 *
 */
public interface PromotionRule {
	/**
	 * 促销规则解析.
	 * 
	 * @param saleDto
	 */
	public void parse(SaleDto saleDto);
}
