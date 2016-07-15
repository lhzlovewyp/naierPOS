package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	 * 根据商户查询尺码信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<Size> getSizeByClient(
			@Param("clientId") String clientId, @Param("start") int start,
			@Param("limit") int limit);

	/**
	 * 根据商户查询尺码信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getSizeCountByClient(@Param("clientId") String clientId);

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
