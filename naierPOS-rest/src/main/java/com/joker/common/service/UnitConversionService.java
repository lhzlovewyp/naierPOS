package com.joker.common.service;




import java.util.List;
import java.util.Map;

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
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<UnitConversion> getUnitConversionPageByCondition(
			Map<String, Object> map, int pageNo, int limit);
	
	/**
	 * 根据商户查询单位换算信息.
	 * 
	 * @param map
	 * @return
	 */
	public List<UnitConversion> getUnitConversionPageByCondition(
			Map<String, Object> map);

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
	/**
	 * 获取计量单位的换算关系.
	 * 
	 * @param clientId
	 * @param unitA
	 * @param unitB
	 * @return
	 */
	public UnitConversion getUnitConversion(String clientId,String unitA,String unitB);

}
