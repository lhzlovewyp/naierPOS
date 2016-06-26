package com.joker.common.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.SalesOrder;
import com.joker.core.annotation.DataSource;

@Repository
public interface SalesOrderMapper {

	/**
	 * 获取指定日期的销量数据.
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public SalesOrder getSalesInfo(@Param("clientId") String clientId,@Param("storeId")String storeId,@Param("salesDate") Date salesDate);
	
	
}
