/**
 * 
 */
package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Brand;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
public interface BrandService {

	/**
	 * 根据id查询品牌信息.
	 * 
	 * @param id
	 * @return
	 */
	public Brand getBrandByID(String id);

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Brand> getBrandPageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	public List<Brand> getBrandPageByCondition(Map<String, Object> map);

	/**
	 * 删除品牌信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteBrandByID(String id);

	/**
	 * 修改品牌信息.
	 * 
	 * @param brand
	 * @return
	 */
	public void updateBrand(Brand brand);

	/**
	 * 保存品牌信息.
	 * 
	 * @param brand
	 * @return
	 */
	public void insertBrand(Brand brand);
}