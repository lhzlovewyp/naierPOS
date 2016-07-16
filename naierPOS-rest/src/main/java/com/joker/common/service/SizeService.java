/**
 * 
 */
package com.joker.common.service;

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
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<Size> getSizePageByClient(String clientId, int start,
			int limit);

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