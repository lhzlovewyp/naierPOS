package com.joker.common.service;

import java.util.Map;

import com.joker.common.model.TransClass;
import com.joker.core.dto.Page;

public interface TransClassService {
	
	
	/**
	 * 根据code查询交易类型.
	 * 
	 * @param id
	 * @return
	 */
	public TransClass getTransClassByCode(String code);

	/**
	 * 查询交易类型.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<TransClass> getTransClassByCondition(Map<String, Object> map,
			int pageNo, int limit);

	/**
	 * 删除交易类型.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteTransClassByCode(String code);

	/**
	 * 修改交易类型.
	 * 
	 * @param TransClass
	 * @return
	 */
	public void updateTransClass(TransClass transClass);

	/**
	 * 保存交易类型.
	 * 
	 * @param TransClass
	 * @return
	 */
	public void insertTransClass(TransClass transClass);
}