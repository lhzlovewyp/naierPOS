/**
 * 
 */
package com.joker.common.service;

import java.util.List;
import java.util.Map;

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
	 * 查询颜色信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Color> getColorPageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	/**
	 * 查询颜色信息.
	 * 
	 * @param map
	 * @return
	 */
	public List<Color> getColorPageByCondition(Map<String, Object> map);

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