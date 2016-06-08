/**
 * 
 */
package com.joker.common.service.impl;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.SaleUserMapper;
import com.joker.common.model.SaleUser;
import com.joker.common.service.SaleUserService;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.Context;

/**
 * @author lvhaizhen
 *
 */
@Service
public class SaleUserServiceImpl implements SaleUserService{

	@Autowired
    SaleUserMapper mapper;
	
	
	public SaleUser getUserByName(String userName) {
		return mapper.getUserByName(userName);
	}
	
	public SaleUser login(SaleUser user) {
		mapper.setLoginInfo(user);
		//登陆信息放到缓存中，缓存时间30分钟
		CacheFactory.getCache().put(user.getToken(), user,Context.DEFALUT_LOGIN_TIME);
        return user;
	}

}
