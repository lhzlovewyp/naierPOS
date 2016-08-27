package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Size;
import com.joker.core.annotation.DataSource;

@Repository
public interface SizeMapper {
	/**
	 * 根据id查询尺码信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Size getSizeByID(String id);

	/**
	 * 查询尺码信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Size> getSizePageByCondition(Map<String, Object> map);

	/**
	 * 查询尺码信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getSizeCountByCondition(Map<String, Object> map);

	/**
	 * 删除尺码信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteSizeByID(String id);

	/**
	 * 修改尺码信息.
	 * 
	 * @param size
	 * @return
	 */
	@DataSource("master")
	public void updateSize(Size size);

	/**
	 * 保存尺码信息.
	 * 
	 * @param size
	 * @return
	 */
	@DataSource("master")
	public void insertSize(Size size);
}
