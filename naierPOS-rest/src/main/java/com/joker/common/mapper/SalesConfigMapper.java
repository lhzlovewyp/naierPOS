package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

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
	 * 根据id查询销售交易信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public SalesConfig getSalesConfigByID(String id);

	/**
	 * 查询销售交易信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<SalesConfig> getSalesConfigPageByCondition(Map<String, Object> map);

	/**
	 * 查询销售交易信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getSalesConfigCountByCondition(Map<String, Object> map);

	/**
	 * 删除销售交易信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteSalesConfigByID(String id);
	
	/**
	 * 删除销售交易信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteSalesConfigByTerminalID(String id);
	
	/**
	 * 修改销售交易信息.
	 * 
	 * @param salesConfig
	 * @return
	 */
	@DataSource("master")
	public void updateSalesConfig(SalesConfig salesConfig);
	
	/**
	 * 更新销售编号.
	 * @param salesConfig
	 * @return
	 */
	@DataSource("master")
	public int updateSalesConfigMaxCode(SalesConfig salesConfig);
	
	
	/**
	 * 保存营业日期.
	 * @param salesConfig
	 * @return
	 */
	@DataSource("master")
	public int insertSalesConfig(SalesConfig salesConfig);
}
