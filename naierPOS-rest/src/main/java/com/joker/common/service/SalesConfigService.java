package com.joker.common.service;

import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;

public interface SalesConfigService {
	/**
	 * 根据门店id查询当前营业日期.
	 * @param username
	 * @return
	 */
	public SalesConfig getCurrentSalesConfig(Account account);
	
	/**
	 * 更新销售编号.
	 * @param username
	 * @return
	 */
	public int updateSalesConfig(SalesConfig salesConfig);
	
	
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
}
