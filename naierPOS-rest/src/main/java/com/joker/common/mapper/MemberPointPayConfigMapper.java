package com.joker.common.mapper;

import org.springframework.stereotype.Repository;

import com.joker.common.model.MemberPointPayConfig;
import com.joker.core.annotation.DataSource;

@Repository
public interface MemberPointPayConfigMapper {

	/**
	 * 查询积分兑换信息.
	 * 
	 * @param clientId
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public MemberPointPayConfig getMemberPointPayConfigByClient(String clientId);

	
}
