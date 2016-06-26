/**
 * 
 */
package com.joker.common.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.joker.common.mapper.SalesConfigMapper;
import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;
import com.joker.common.service.SalesConfigService;
import com.joker.core.util.RandomCodeFactory;

/**
 * @author lvhaizhen
 *
 */
@Service
public class SalesConfigServiceImpl implements SalesConfigService{

	@Autowired
    SalesConfigMapper mapper;
	
	

	@Override
	public SalesConfig getCurrentSalesConfig(Account account) {
		//获取当前营业日期
		SalesConfig config =  mapper.getCurrentSalesConfig(account.getStore().getId());
		//如果不存在营业日期,初始化一笔记录.
		if(config == null){
			config = new SalesConfig();
			//生成guid.
			config.setId(RandomCodeFactory.defaultGenerateMixed());
			config.setClient(account.getClient());
			config.setStore(account.getStore());
			config.setSalesDate(new Date());
			mapper.insertSalesConfig(config);
		}
		return config;
	}

	@Override
	public int updateSalesConfig(SalesConfig salesConfig) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSalesConfig(SalesConfig salesConfig) {
		if(StringUtils.isEmpty(salesConfig.getId())){
			salesConfig.setId(RandomCodeFactory.defaultGenerateMixed());
		}
		return mapper.insertSalesConfig(salesConfig);
	}
	

	

}
