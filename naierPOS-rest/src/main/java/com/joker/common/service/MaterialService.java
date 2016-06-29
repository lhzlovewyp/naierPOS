package com.joker.common.service;

import com.joker.common.model.Material;

public interface MaterialService {
	/**
	 * 查询物料信息.
	 * @param username
	 * @return
	 */
	public Material getMaterialByCode(String clientId,String code);
	
	/**
	 * 查询物料信息.
	 * @param username
	 * @return
	 */
	public Material getMaterialByBarCode(String clientId,String barCode);
	
	
}
