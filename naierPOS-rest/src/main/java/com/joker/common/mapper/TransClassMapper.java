package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.TransClass;
import com.joker.core.annotation.DataSource;

@Repository
public interface TransClassMapper {
	
	
	/**
	 * 根据code查询交易类型.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public TransClass getTransClassByCode(String code);

	/**
	 * 查询支持的交易类型.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<TransClass> getTransClassByCondition(Map<String, Object> map);

	/**
	 * 查询交易类型.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getTransClassCountByCondition(Map<String, Object> map);

	/**
	 * 删除支付方式.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteTransClassByCode(String code);

	/**
	 * 修改支付方式.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void updateTransClass(TransClass transClass);

	/**
	 * 保存支付方式.
	 * 
	 * @param color
	 * @return
	 */
	@DataSource("master")
	public void insertTransClass(TransClass transClass);
}