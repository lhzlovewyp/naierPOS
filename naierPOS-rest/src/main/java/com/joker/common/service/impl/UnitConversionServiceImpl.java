/**
 * 
 */
package com.joker.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.UnitConversionMapper;
import com.joker.common.mapper.UnitMapper;
import com.joker.common.model.Unit;
import com.joker.common.model.UnitConversion;
import com.joker.common.service.UnitConversionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.joker.common.Constant.Constants;
import com.joker.core.dto.Page;
import com.joker.core.expection.ParamsNullException;

/**
 * @author zhangfei
 * 
 */
@Service
public class UnitConversionServiceImpl implements UnitConversionService {

	@Autowired
	UnitConversionMapper mapper;

	@Autowired
	UnitMapper unitMapper;

	@Override
	public UnitConversion getUnitConversion(String clientId, String unitA,
			String unitB) {
		return mapper.getUnitConversion(clientId, unitA, unitB);
	}

	@Override
	public UnitConversion getUnitConversionByID(String id) {
		return mapper.getUnitConversionByID(id);
	}

	/**
	 * 根据商户查询单位换算信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<UnitConversion> getUnitConversionPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<UnitConversion> page = new Page<UnitConversion>();
		int totalRecord = mapper.getUnitConversionCountByCondition(map);
		List<UnitConversion> list = mapper
				.getUnitConversionPageByCondition(map);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Unit> unitMap = new HashMap<String, Unit>();
			for (UnitConversion unitConversion : list) {
				unitConversion.setUnitA(getUnitInfoById(
						unitConversion.getUnitA(), unitMap));
				unitConversion.setUnitB(getUnitInfoById(
						unitConversion.getUnitB(), unitMap));
			}
		}
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}
	
	@Override
	public List<UnitConversion> getUnitConversionPageByCondition(
			Map<String, Object> map) {
		List<UnitConversion> list = mapper
				.getUnitConversionPageByCondition(map);
		return list;
	}

	@Override
	public void deleteUnitConversionByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteUnitConversionByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateUnitConversion(UnitConversion unitConversion) {
		String unitAId = unitConversion.getUnitA().getId();
		String unitBId = unitConversion.getUnitB().getId();

		validDuplicate(unitBId, unitAId);
		mapper.updateUnitConversion(unitConversion);
	}

	@Override
	public void insertUnitConversion(UnitConversion unitConversion) {
		String unitAId = unitConversion.getUnitA().getId();
		String unitBId = unitConversion.getUnitB().getId();

		validDuplicate(unitAId, unitBId);
		validDuplicate(unitBId, unitAId);

		if (StringUtils.isBlank(unitConversion.getId())) {
			unitConversion.setId(UUID.randomUUID().toString());
		}
		mapper.insertUnitConversion(unitConversion);
	}

	private Unit getUnitInfoById(Unit unit, Map<String, Unit> unitMap) {
		Unit returnUnit = unit;
		if (unit != null && StringUtils.isNotBlank(unit.getId())) {
			String unitId = unit.getId();
			Unit cacheUnit = unitMap.get(unitId);
			if (cacheUnit != null) {
				returnUnit = cacheUnit;
			} else {
				Unit dbUnit = unitMapper.getUnitByID(unitId);
				if (dbUnit != null) {
					returnUnit = dbUnit;
				}
				unitMap.put(unitId, dbUnit);
			}
		}
		return returnUnit;
	}

	private void validDuplicate(String unitAId, String unitBId) {
		if (StringUtils.isNotBlank(unitAId) && StringUtils.isNotBlank(unitBId)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("unitAId", unitAId);
			map.put("unitBId", unitBId);
			int count = mapper.getUnitConversionCountByCondition(map);
			if (count > 0) {
				throw new DuplicateKeyException("唯一编码重复");
			}
		} else {
			throw new ParamsNullException("0");
		}
	}
}