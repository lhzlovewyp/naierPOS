
  
package com.joker.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.MemberPointPayConfigMapper;
import com.joker.common.model.MemberPointPayConfig;
import com.joker.common.service.MemberPointPayConfigService;

@Service
public class MemberPointPayConfigServiceImpl implements MemberPointPayConfigService {

	@Autowired
	MemberPointPayConfigMapper mapper;
	
	@Override
	public MemberPointPayConfig getMemberPointPayConfigByClient(String clientId) {
		return mapper.getMemberPointPayConfigByClient(clientId);
	}

}
  
