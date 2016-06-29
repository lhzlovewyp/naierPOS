/**
 * 
 */
package com.joker.common.service;

import java.util.Date;

import com.joker.common.model.Material;
import com.joker.common.model.RetailPrice;


/**
 * @author lvhaizhen
 *
 */
public interface RetailPriceService {
	
	
	/**
	 * 获取商品价格.
	 * 
	 * @param date
	 * @param material
	 * @return
	 */
	public RetailPrice getRetailPrice(Date date,String storeId,Material material);

	
}
