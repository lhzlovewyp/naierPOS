/**
 * 
 */
package com.joker.common.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.UnitConversionMapper;
import com.joker.common.model.UnitConversion;
import com.joker.common.service.UnitConversionService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class UnitConversionServiceImpl implements UnitConversionService {

	@Autowired
	UnitConversionMapper mapper;

	@Override
	public UnitConversion getUnitConversionByID(String id) {
		return mapper.getUnitConversionByID(id);
	}

	/**
	 * 根据商户查询单位换算信息.
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public Page<UnitConversion> getUnitConversionPageByClient(String clientId,
			int start, int limit) {
		Page<UnitConversion> page = new Page<UnitConversion>();
		int totalRecord = mapper.getUnitConversionCountByClient(clientId);
		List<UnitConversion> list = mapper.getUnitConversionByClient(clientId,
				start, limit);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
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
		mapper.updateUnitConversion(unitConversion);

	}

	@Override
	public void insertUnitConversion(UnitConversion unitConversion) {
		if (StringUtils.isBlank(unitConversion.getId())) {
			unitConversion.setId(UUID.randomUUID().toString());
		}
		mapper.insertUnitConversion(unitConversion);
	}
}