/**
 * 
 */
package com.joker.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joker.common.mapper.RoleMapper;
import com.joker.common.model.Role;
import com.joker.common.service.RoleService;

/**
 * @author lvhaizhen
 *
 */
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
    RoleMapper mapper;

	@Override
	public List<Role> getRoleByAccountId(String accountId) {
		return mapper.getRoleByAccountId(accountId);
	}
	
	

}
