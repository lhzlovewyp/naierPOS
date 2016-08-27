/**
 * 
 */
package com.joker.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.SalesConfigMapper;
import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;
import com.joker.common.service.SalesConfigService;
import com.joker.core.dto.Page;
import com.joker.core.util.RandomCodeFactory;

/**
 * @author lvhaizhen
 * 
 */
@Service
public class SalesConfigServiceImpl implements SalesConfigService {

	private Logger log = LoggerFactory.getLogger(SalesConfigServiceImpl.class);

	@Autowired
	SalesConfigMapper mapper;

	@Override
	public SalesConfig getCurrentSalesConfig(Account account) {
		// 获取当前营业日期
		SalesConfig config = mapper.getCurrentSalesConfig(account.getStore()
				.getId());
		// 如果不存在营业日期,初始化一笔记录.
		if (config == null) {
			config = new SalesConfig();
			// 生成guid.
			config.setId(RandomCodeFactory.defaultGenerateMixed());
			config.setClient(account.getClient());
			config.setStore(account.getStore());
			config.setSalesDate(new Date());
			mapper.insertSalesConfig(config);
		}
		return config;
	}

	@Override
	public void updateSalesConfig(SalesConfig salesConfig) {
		mapper.updateSalesConfig(salesConfig);
	}

	@Override
	public int insertSalesConfig(SalesConfig salesConfig) {
		if (StringUtils.isEmpty(salesConfig.getId())) {
			salesConfig.setId(RandomCodeFactory.defaultGenerateMixed());
		}
		return mapper.insertSalesConfig(salesConfig);
	}

	@Override
	public int getSaleMaxCode(Account account) {
		// flag标记循环5次.
		for (int i = 0; i <= 5; i++) {
			SalesConfig config = mapper.getCurrentSalesConfig(account
					.getStore().getId());
			if (config == null) {
				log.error("get config null!");
				return -1;
			}
			config.setMaxCode(config.getMaxCode() + 1);
			if (mapper.updateSalesConfigMaxCode(config) == 1) {
				return config.getMaxCode();
			}
		}
		// 多次重试还不能获取，则抛出异常
		log.error("get maxcode error");
		return -1;
	}

	@Override
	public SalesConfig getSalesConfigByID(String id) {
		return mapper.getSalesConfigByID(id);
	}

	@Override
	public Page<SalesConfig> getSalesConfigPageByCondition(
			Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<SalesConfig> page = new Page<SalesConfig>();
		int totalRecord = mapper.getSalesConfigCountByCondition(map);
		List<SalesConfig> list = mapper.getSalesConfigPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public List<SalesConfig> getSalesConfigPageByCondition(
			Map<String, Object> map) {
		List<SalesConfig> list = mapper.getSalesConfigPageByCondition(map);
		return list;
	}

	@Override
	public void deleteSalesConfigByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteSalesConfigByID(oneId);
				}
			}
		}
	}

}