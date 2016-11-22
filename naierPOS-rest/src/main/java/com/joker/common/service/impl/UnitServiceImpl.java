/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.UnitMapper;
import com.joker.common.model.Unit;
import com.joker.common.service.UnitService;
import com.joker.core.dto.Page;
import com.joker.core.expection.FailedException;

/**
 * @author zhangfei
 * 
 */
@Service
public class UnitServiceImpl implements UnitService {

	@Autowired
	UnitMapper mapper;

	@Override
	public Unit getUnitByID(String id) {
		return mapper.getUnitByID(id);
	}

	/**
	 * 根据商户查询计量单位信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Unit> getUnitPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Unit> page = new Page<Unit>();
		int totalRecord = mapper.getUnitCountByCondition(map);
		List<Unit> list = mapper.getUnitPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	/**
	 * 根据商户查询计量单位信息.
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Unit> getUnitPageByCondition(Map<String, Object> map) {
		List<Unit> list = mapper.getUnitPageByCondition(map);
		return list;
	}

	@Override
	public void deleteUnitByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteUnitByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateUnit(Unit unit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clientId", unit.getClient().getId());
		map.put("code", unit.getCode());
		List<Unit> units = mapper.getUnitPageByCondition(map);
		if(CollectionUtils.isNotEmpty(units)){
			String id = unit.getId();
			for (Unit unit2 : units) {
				if(!unit2.getId().equals(id)){
					throw new FailedException("同一个商户下编码不能重复");
				}
			}
		}
		mapper.updateUnit(unit);

	}

	@Override
	public void insertUnit(Unit unit) {
		if (StringUtils.isBlank(unit.getId())) {
			unit.setId(UUID.randomUUID().toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clientId", unit.getClient().getId());
		map.put("code", unit.getCode());
		int count = mapper.getUnitCountByCondition(map);
		if(count > 0){
			throw new FailedException("同一个商户下编码不能重复");
		}
		mapper.insertUnit(unit);
	}
}