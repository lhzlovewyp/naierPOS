/**
 * 
 */
package com.joker.common.service;

import com.joker.common.model.SaleUser;
import com.joker.core.annotation.DataSource;

/**
 * @author lvhaizhen
 *
 */
public interface SaleUserService {

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	public SaleUser getUserByName(String userName);
	
	
	/**
	 * 登陆.
	 * @param user
	 * @return
	 */
	public SaleUser login(SaleUser user);
}
