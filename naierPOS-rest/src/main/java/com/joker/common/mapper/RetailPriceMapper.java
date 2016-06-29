package com.joker.common.mapper;

import java.util.Map;

import com.joker.common.model.RetailPrice;
import com.joker.core.annotation.DataSource;

/**
 * 
 * 商品价格管理.
 * 
 * @author lvhaizhen
 *
 */
public interface RetailPriceMapper {

	/**
	 * 获取商品对应的价格.
	 * @param 入参map
	 * @return
	 */
	@DataSource("slave")
	public RetailPrice getMaterialPrice(Map<String,Object> map);
}
