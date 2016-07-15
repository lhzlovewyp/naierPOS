package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.Unit;
import com.joker.core.annotation.DataSource;

@Repository
public interface UnitMapper {
	/**
	 * 根据id查询单位信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Unit getUnitByID(String id);

	/**
	 * 根据商户查询单位信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<Unit> getUnitByClient(@Param("clientId") String clientId,
			@Param("start") int start, @Param("limit") int limit);

	/**
	 * 根据商户查询单位信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getUnitCountByClient(@Param("clientId") String clientId);

	/**
	 * 删除单位信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteUnitByID(String id);

	/**
	 * 修改单位信息.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void updateUnit(Unit unit);

	/**
	 * 保存单位信息.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void insertUnit(Unit unit);
}