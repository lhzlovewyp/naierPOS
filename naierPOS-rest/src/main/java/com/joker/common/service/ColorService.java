/**
 * 
 */
package com.joker.common.service;

import com.joker.common.model.Color;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
public interface ColorService {

	/**
	 * 根据id查询颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	public Color getColorByID(String id);

	/**
	 * 根据商户查询颜色信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<Color> getColorPageByClient(String clientId, int start,
			int limit);

	/**
	 * 删除颜色信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteColorByID(String id);

	/**
	 * 修改颜色信息.
	 * 
	 * @param color
	 * @return
	 */
	public void updateColor(Color color);

	/**
	 * 保存颜色信息.
	 * 
	 * @param color
	 * @return
	 */
	public void insertColor(Color color);
}