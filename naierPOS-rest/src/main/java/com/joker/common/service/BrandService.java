/**
 * 
 */
package com.joker.common.service;

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
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<Brand> getBrandPageByClient(String clientId, int start,
			int limit);

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