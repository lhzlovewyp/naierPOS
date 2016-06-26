package com.joker.common.mapper;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;
import com.joker.core.annotation.DataSource;

@Repository
public interface SalesConfigMapper {

	/**
	 * 根据门店id查询当前营业日期.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public SalesConfig getCurrentSalesConfig(String storeId);
	
	/**
	 * 更新销售编号.
	 * @param username
	 * @return
	 */
	@DataSource("master")
	public int updateSalesConfig(SalesConfig salesConfig);
	
	
	/**
	 * 保存营业日期.
	 * @param salesConfig
	 * @return
	 */
	@DataSource("master")
	public int insertSalesConfig(SalesConfig salesConfig);
}
