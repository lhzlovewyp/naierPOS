/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.ClientPaymentMapper;
import com.joker.common.model.ClientPayment;
import com.joker.common.service.ClientPaymentService;
import com.joker.core.dto.Page;

/**
 * @author lvhaizhen
 *
 */
@Service
public class ClientPaymentServiceImpl implements ClientPaymentService {

	@Autowired
	ClientPaymentMapper mapper;
	
	@Override
	public List<ClientPayment> getClientPayments(String clientId) {
		return mapper.getClientPayments(clientId);
	}

	@Override
	public ClientPayment getClientPaymentByID(String id) {
		return mapper.getClientPaymentByID(id);
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
	public Page<ClientPayment> getClientPaymentPageByCondition(Map<String, Object> map,
			int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<ClientPayment> page = new Page<ClientPayment>();
		int totalRecord = mapper.getClientPaymentCountByCondition(map);
		List<ClientPayment> list = mapper.getClientPaymentPageByCondition(map);
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
	public List<ClientPayment> getClientPaymentPageByCondition(Map<String, Object> map) {
		List<ClientPayment> list = mapper.getClientPaymentPageByCondition(map);
		return list;
	}

	@Override
	public void deleteClientPaymentByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deleteClientPaymentByID(oneId);
				}
			}
		}
	}

	@Override
	public void updateClientPayment(ClientPayment clientPayment) {
		mapper.updateClientPayment(clientPayment);

	}

	@Override
	public void insertClientPayment(ClientPayment clientPayment) {
		if (StringUtils.isBlank(clientPayment.getId())) {
			clientPayment.setId(UUID.randomUUID().toString());
		}
		mapper.insertClientPayment(clientPayment);
	}

}