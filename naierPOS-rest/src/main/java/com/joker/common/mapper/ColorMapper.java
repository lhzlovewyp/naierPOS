package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	 * 根据商户查询颜色信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<Color> getColorByClient(@Param("clientId") String clientId,
			@Param("start") int start, @Param("limit") int limit);

	/**
	 * 根据商户查询颜色信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getColorCountByClient(@Param("clientId") String clientId);

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
