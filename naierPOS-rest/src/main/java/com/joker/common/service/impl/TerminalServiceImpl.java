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
import com.joker.common.mapper.SalesConfigMapper;
import com.joker.common.mapper.SalesOrderMapper;
import com.joker.common.mapper.StoreMapper;
import com.joker.common.mapper.TerminalMapper;
import com.joker.common.model.SalesConfig;
import com.joker.common.model.Store;
import com.joker.common.model.Terminal;
import com.joker.common.service.TerminalService;
import com.joker.core.dto.Page;
import com.joker.core.expection.FailedException;

/**
 * @author zhangfei
 * 
 */
@Service
public class TerminalServiceImpl implements TerminalService {

	@Autowired
	TerminalMapper mapper;

	@Autowired
	SalesConfigMapper salesConfigMapper;

	@Autowired
	StoreMapper storeMapper;

	@Override
	public Terminal getTerminalByID(String id) {
		return mapper.getTerminalByID(id);
	}

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	@Override
	public Page<Terminal> getTerminalPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Terminal> page = new Page<Terminal>();
		int totalRecord = mapper.getTerminalCountByCondition(map);
		List<Terminal> list = mapper.getTerminalPageByCondition(map);
		page.setPageNo(start + 1);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Terminal> getTerminalPageByCondition(Map<String, Object> map) {
		List<Terminal> list = mapper.getTerminalPageByCondition(map);
		return list;
	}

	@Override
	public void deleteTerminalByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteTerminalByID(oneId);
					salesConfigMapper.deleteSalesConfigByTerminalID(oneId);
				}
			}
		}
	}

	@Override
	public void updateTerminal(Terminal terminal) {
		Terminal newTerminal = mapper.getTerminalByID(terminal.getId());
		mapper.updateTerminal(terminal);
		if (terminal.getStore() != null
				&& StringUtils.isNotBlank(terminal.getStore().getId())
				&& !terminal.getStore().getId()
						.equals(newTerminal.getStore().getId())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("terminalId", terminal.getId());
			List<SalesConfig> list = salesConfigMapper
					.getSalesConfigPageByCondition(map);

			if (CollectionUtils.isNotEmpty(list) && list.size() == 1) {
				SalesConfig salesConfig = new SalesConfig();
				salesConfig.setId(list.get(0).getId());
				salesConfig.setStore(terminal.getStore());
				salesConfigMapper.updateSalesConfig(salesConfig);
			} else if (CollectionUtils.isNotEmpty(list)) {
				throw new FailedException("销售交易设定信息有多条,请检查！");
			} else {
				throw new FailedException("没有对应销售交易设定信息,请检查！");
			}
		}
	}

	@Override
	public void insertTerminal(Terminal terminal) {
		if (StringUtils.isBlank(terminal.getId())) {
			terminal.setId(UUID.randomUUID().toString());
		}

		Store store = terminal.getStore();
		if (store == null || StringUtils.isBlank(store.getId())) {
			throw new FailedException("门店信息为空");
		} else {
			store = storeMapper.getStoreById(store.getId());
			if (store == null) {
				throw new FailedException("门店信息为空");
			} else if (store.getOpened() == null) {
				throw new FailedException("门店的开业日期为空");
			}
		}

		mapper.insertTerminal(terminal);

		SalesConfig salesConfig = new SalesConfig();
		salesConfig.setId(UUID.randomUUID().toString());
		salesConfig.setClient(terminal.getClient());
		salesConfig.setCreated(terminal.getCreated());
		salesConfig.setCreator(terminal.getCreator());
		salesConfig.setMaxCode(0);
		salesConfig.setSalesDate(store.getOpened());
		salesConfig.setStore(terminal.getStore());
		salesConfig.setTerminal(terminal);
		salesConfigMapper.insertSalesConfig(salesConfig);
	}
}