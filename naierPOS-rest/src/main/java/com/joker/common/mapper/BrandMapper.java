package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

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
	 * 查询品牌信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Brand> getBrandPageByCondition(Map<String, Object> map);

	/**
	 * 查询品牌信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getBrandCountByCondition(Map<String, Object> map);

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