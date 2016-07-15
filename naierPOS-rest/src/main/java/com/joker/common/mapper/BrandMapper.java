package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.Brand;
import com.joker.core.annotation.DataSource;

@Repository
public interface BrandMapper {
	/**
	 * 根据id查询品牌信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Brand getBrandByID(String id);

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@DataSource("slave")
	public List<Brand> getBrandByClient(@Param("clientId") String clientId,
			@Param("start") int start, @Param("limit") int limit);

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param clientId
	 * @return
	 */
	@DataSource("slave")
	public int getBrandCountByClient(@Param("clientId") String clientId);

	/**
	 * 删除品牌信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteBrandByID(String id);

	/**
	 * 修改品牌信息.
	 * 
	 * @param brand
	 * @return
	 */
	@DataSource("master")
	public void updateBrand(Brand brand);

	/**
	 * 保存品牌信息.
	 * 
	 * @param brand
	 * @return
	 */
	@DataSource("master")
	public void insertBrand(Brand brand);
}