
package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.UnitConversion;
import com.joker.core.annotation.DataSource;

@Repository
public interface UnitConversionMapper {
	/**
	 * 根据id查询单位换算信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public UnitConversion getUnitConversionByID(String id);

	/**
	 * 查询单位换算信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<UnitConversion> getUnitConversionPageByCondition(Map<String, Object> map);

	/**
	 * 查询单位换算信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getUnitConversionCountByCondition(Map<String, Object> map);

	/**
	 * 删除单位换算信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteUnitConversionByID(String id);

	/**
	 * 修改单位换算信息.
	 * 
	 * @param unitConversion
	 * @return
	 */
	@DataSource("master")
	public void updateUnitConversion(UnitConversion unitConversion);

	/**
	 * 保存单位换算信息.
	 * 
	 * @param unitConversion
	 * @return
	 */
	@DataSource("master")
	public void insertUnitConversion(UnitConversion unitConversion);
	
	/**
	 * 获取计量单位的换算关系.
	 * 
	 * @param clientId
	 * @param unitA
	 * @param unitB
	 * @return
	 */
	public UnitConversion getUnitConversion(@Param("clientId")String clientId,@Param("unitA")String unitA,@Param("unitB")String unitB);

}
