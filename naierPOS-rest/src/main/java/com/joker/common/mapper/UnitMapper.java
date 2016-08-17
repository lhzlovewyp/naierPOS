package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

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
	 * 查询单位信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Unit> getUnitPageByCondition(Map<String, Object> map);

	/**
	 * 查询单位信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getUnitCountByCondition(Map<String, Object> map);

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