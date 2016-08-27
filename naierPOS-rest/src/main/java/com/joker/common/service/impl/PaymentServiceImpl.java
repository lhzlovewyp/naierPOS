/**
 * 
 */
package com.joker.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.Constant.Constants;
import com.joker.common.mapper.PaymentMapper;
import com.joker.common.model.Payment;
import com.joker.common.service.PaymentService;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentMapper mapper;

	/**
	 * 根据商户查询品牌信息.
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<Payment> getPaymentPageByCondition(Map<String, Object> map) {
		List<Payment> list = mapper.getPaymentPageByCondition(map);
		return list;
	}

	@Override
	public Payment getPaymentByCode(String code) {
		return mapper.getPaymentByCode(code);
	}

	@Override
	public Page<Payment> getPaymentByCondition(Map<String, Object> map, int pageNo, int limit) {
		int start = (pageNo - 1) * limit;
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put("start", start);
		map.put("limit", limit);
		Page<Payment> page = new Page<Payment>();
		int totalRecord = mapper.getPaymentCountByCondition(map);
		List<Payment> list = mapper.getPaymentByCondition(map);
		page.setPageNo(pageNo);
		page.setPageSize(limit);
		page.setTotalRecord(totalRecord);
		page.setResults(list);
		return page;
	}

	@Override
	public void deletePaymentByCode(String code) {
		if (StringUtils.isNotBlank(code)) {
			String[] ids = code.split(Constants.COMMA);
			for (String oneId : ids) {
				if (StringUtils.isNotBlank(oneId)) {
					mapper.deletePaymentByCode(oneId);
				}
			}
		}
	}

	@Override
	public void updatePayment(Payment payment) {
		mapper.updatePayment(payment);
	}

	@Override
	public void insertPayment(Payment payment) {
		mapper.insertPayment(payment);
	}

}