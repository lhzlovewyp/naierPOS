package com.joker.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joker.common.model.ClientPayment;
import com.joker.core.annotation.DataSource;

@Repository
public interface ClientPaymentMapper {

	/**
	 * 根据商户、用户名查询用户信息.
	 * 
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public List<ClientPayment> getClientPayments(@Param("clientId") String clientId);

	
}
