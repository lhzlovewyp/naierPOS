/**
 * 
 */
package com.joker.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.joker.common.model.UnitConversion;

/**
 * @author lvhaizhen
 *
 */
public interface UnitConversionMapper {

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
