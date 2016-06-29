package com.joker.common.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Material;
import com.joker.core.annotation.DataSource;

@Repository
public interface MaterialMapper {

	/**
	 * 根据id查询门店信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public Material getMaterialByCondition(Map<String,String> map);
	
	
}
