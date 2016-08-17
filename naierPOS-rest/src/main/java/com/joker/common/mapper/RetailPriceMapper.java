package com.joker.common.mapper;

import java.util.List;
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
	
	/**
	 * 根据id查询商品价格信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public RetailPrice getRetailPriceByID(String id);

	/**
	 * 查询商品价格信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<RetailPrice> getRetailPricePageByCondition(Map<String, Object> map);

	/**
	 * 查询商品价格信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getRetailPriceCountByCondition(Map<String, Object> map);

	/**
	 * 删除商品价格信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteRetailPriceByID(String id);

	/**
	 * 修改商品价格信息.
	 * 
	 * @param retailPrice
	 * @return
	 */
	@DataSource("master")
	public void updateRetailPrice(RetailPrice retailPrice);

	/**
	 * 保存商品价格信息.
	 * 
	 * @param retailPrice
	 * @return
	 */
	@DataSource("master")
	public void insertRetailPrice(RetailPrice retailPrice);
}