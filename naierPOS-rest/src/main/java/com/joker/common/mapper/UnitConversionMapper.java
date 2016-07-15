package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.UnitConversion;
import com.joker.core.annotation.DataSource;

@Repository
public interface UnitConversionMapper {
	/**
	 * 根据id查询单位换算信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public UnitConversion getUnitConversionByID(String id);

	/**
	 * 根据商户查询单位换算信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<UnitConversion> getUnitConversionByClient(
			@Param("clientId") String clientId, @Param("start") int start,
			@Param("limit") int limit);

	/**
	 * 根据商户查询单位换算信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getUnitConversionCountByClient(@Param("clientId") String clientId);

	/**
	 * 删除单位换算信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteUnitConversionByID(String id);

	/**
	 * 修改单位换算信息.
	 * 
	 * @param unitConversion
	 * @return
	 */
	@DataSource("master")
	public void updateUnitConversion(UnitConversion unitConversion);

	/**
	 * 保存单位换算信息.
	 * 
	 * @param unitConversion
	 * @return
	 */
	@DataSource("master")
	public void insertUnitConversion(UnitConversion unitConversion);
}