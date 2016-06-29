package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.MaterialProperty;
import com.joker.core.annotation.DataSource;

@Repository
public interface MaterialPropertyMapper {

	/**
	 * 根据id查询门店信息.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public List<MaterialProperty> getMaterialPropertyByCondition(Map<String,String> map);
	
	
}
