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
	
	
}
