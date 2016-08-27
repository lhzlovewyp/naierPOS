package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;
import com.joker.core.dto.Page;

public interface SalesConfigService {
	/**
	 * 根据门店id查询当前营业日期.
	 * @param username
	 * @return
	 */
	public SalesConfig getCurrentSalesConfig(Account account);
	
	/**
	 * 更新销售编号.
	 * @param salesConfig
	 * @return
	 */
	public void updateSalesConfig(SalesConfig salesConfig);
	
	
	/**
	 * 保存营业日期.
	 * @param salesConfig
	 * @return
	 */
	public int insertSalesConfig(SalesConfig salesConfig);
	
	/**
	 * 获取当前最大销售序列号.
	 * @param account
	 * @return
	 */
	public int getSaleMaxCode(Account account);
	
	/**
	 * 根据id查询销售交易信息.
	 * 
	 * @param id
	 * @return
	 */
	public SalesConfig getSalesConfigByID(String id);

	/**
	 * 根据商户查询销售交易信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<SalesConfig> getSalesConfigPageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	public List<SalesConfig> getSalesConfigPageByCondition(Map<String, Object> map);

	/**
	 * 删除销售交易信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteSalesConfigByID(String id);
}
