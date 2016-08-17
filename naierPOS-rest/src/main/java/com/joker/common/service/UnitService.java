/**
 * 
 */
package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Unit;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
public interface UnitService {

	/**
	 * 根据id查询计量单位信息.
	 * 
	 * @param id
	 * @return
	 */
	public Unit getUnitByID(String id);

	/**
	 * 根据商户查询计量单位信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Unit> getUnitPageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	public List<Unit> getUnitPageByCondition(Map<String, Object> map);

	/**
	 * 删除计量单位信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteUnitByID(String id);

	/**
	 * 修改计量单位信息.
	 * 
	 * @param unit
	 * @return
	 */
	public void updateUnit(Unit unit);

	/**
	 * 保存计量单位信息.
	 * 
	 * @param unit
	 * @return
	 */
	public void insertUnit(Unit unit);
}