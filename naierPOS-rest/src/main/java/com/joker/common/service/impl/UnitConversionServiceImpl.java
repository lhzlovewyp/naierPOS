/**
 * 
 */
package com.joker.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.UnitConversionMapper;
import com.joker.common.model.UnitConversion;
import com.joker.common.service.UnitConversionService;

/**
 * @author lvhaizhen
 *
 */
@Service
public class UnitConversionServiceImpl implements UnitConversionService {

	@Autowired
	UnitConversionMapper mapper;
	
	@Override
	public UnitConversion getUnitConversion(String clientId, String unitA,
			String unitB) {
		return mapper.getUnitConversion(clientId, unitA, unitB);
	}

}
