/**
 * 
 */
package com.joker.common.service;

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
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<Unit> getUnitPageByClient(String clientId, int start,
			int limit);

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