/**
 * 
 */
package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Size;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
public interface SizeService {

	/**
	 * 根据id查询尺码信息.
	 * 
	 * @param id
	 * @return
	 */
	public Size getSizeByID(String id);

	/**
	 * 根据商户查询尺码信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Size> getSizePageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	/**
	 * 根据商户查询尺码信息.
	 * 
	 * @param map
	 * @return
	 */
	public List<Size> getSizePageByCondition(Map<String, Object> map);

	/**
	 * 删除尺码信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteSizeByID(String id);

	/**
	 * 修改尺码信息.
	 * 
	 * @param size
	 * @return
	 */
	public void updateSize(Size size);

	/**
	 * 保存尺码信息.
	 * 
	 * @param size
	 * @return
	 */
	public void insertSize(Size size);
}