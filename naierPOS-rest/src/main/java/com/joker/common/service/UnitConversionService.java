/**
 * 
 */
package com.joker.common.service;

import com.joker.common.model.UnitConversion;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
public interface UnitConversionService {

	/**
	 * 根据id查询单位换算信息.
	 * 
	 * @param id
	 * @return
	 */
	public UnitConversion getUnitConversionByID(String id);

	/**
	 * 根据商户查询单位换算信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<UnitConversion> getUnitConversionPageByClient(String clientId,
			int start, int limit);

	/**
	 * 删除单位换算信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteUnitConversionByID(String id);

	/**
	 * 修改单位换算信息.
	 * 
	 * @param unitConversion
	 * @return
	 */
	public void updateUnitConversion(UnitConversion unitConversion);

	/**
	 * 保存单位换算信息.
	 * 
	 * @param unitConversion
	 * @return
	 */
	public void insertUnitConversion(UnitConversion unitConversion);
}