package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Color;
import com.joker.core.annotation.DataSource;

@Repository
public interface ColorMapper {
	/**
	 * 根据id查询颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Color getColorByID(String id);

	/**
	 * 查询颜色信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Color> getColorPageByCondition(Map<String, Object> map);

	/**
	 * 查询颜色信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getColorCountByCondition(Map<String, Object> map);

	/**
	 * 删除颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteColorByID(String id);

	/**
	 * 修改颜色信息.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void updateColor(Color color);

	/**
	 * 保存颜色信息.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void insertColor(Color color);
}
