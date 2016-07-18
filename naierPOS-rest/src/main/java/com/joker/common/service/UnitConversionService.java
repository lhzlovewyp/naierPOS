package com.joker.common.service;


import com.joker.common.model.UnitConversion;

public interface UnitConversionService {

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

