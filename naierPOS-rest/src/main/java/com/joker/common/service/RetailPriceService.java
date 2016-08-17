/**
 * 
 */
package com.joker.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joker.common.model.Material;
import com.joker.common.model.RetailPrice;
import com.joker.core.dto.Page;

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
	public RetailPrice getRetailPrice(Date date, String storeId,
			Material material);

	/**
	 * 根据id查询商品价格信息.
	 * 
	 * @param id
	 * @return
	 */
	public RetailPrice getRetailPriceByID(String id);

	/**
	 * 根据商户查询商品价格信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<RetailPrice> getRetailPricePageByCondition(
			Map<String, Object> map, int pageNo, int limit);

	public List<RetailPrice> getRetailPricePageByCondition(
			Map<String, Object> map);

	/**
	 * 删除商品价格信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteRetailPriceByID(String id);

	/**
	 * 修改商品价格信息.
	 * 
	 * @param retailPrice
	 * @return
	 */
	public void updateRetailPrice(RetailPrice retailPrice);

	/**
	 * 保存商品价格信息.
	 * 
	 * @param retailPrice
	 * @return
	 */
	public void insertRetailPrice(RetailPrice retailPrice);
}