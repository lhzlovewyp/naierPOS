package com.joker.common.mapper;

import org.springframework.stereotype.Repository;

import com.joker.common.model.SaleUser;
import com.joker.core.annotation.DataSource;

@Repository
public interface SaleUserMapper {

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	@DataSource("slave")
	public SaleUser getUserByName(String userName);
	
	
	/**
     * 保存登录信息
     * @param user
     * @return Boolean
     */
    @DataSource("master")
    Boolean setLoginInfo(SaleUser user);
}
