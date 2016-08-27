package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.ShoppingGuide;
import com.joker.core.annotation.DataSource;

@Repository
public interface ShoppingGuideMapper {

	/**
	 * 查询导购员信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public List<ShoppingGuide> getShoppingGuideByCondition(Map<String,String> map);
	
	/**
	 * 根据id查询颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public ShoppingGuide getShoppingGuideByID(String id);

	/**
	 * 查询颜色信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<ShoppingGuide> getShoppingGuidePageByCondition(Map<String, Object> map);

	/**
	 * 查询颜色信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getShoppingGuideCountByCondition(Map<String, Object> map);

	/**
	 * 删除颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteShoppingGuideByID(String id);

	/**
	 * 修改颜色信息.
	 * 
	 * @param ShoppingGuide
	 * @return
	 */
	@DataSource("master")
	public void updateShoppingGuide(ShoppingGuide shoppingGuide);

	/**
	 * 保存颜色信息.
	 * 
	 * @param ShoppingGuide
	 * @return
	 */
	@DataSource("master")
	public void insertShoppingGuide(ShoppingGuide shoppingGuide);
	
}
