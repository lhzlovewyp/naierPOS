package com.joker.common.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joker.common.model.Terminal;
import com.joker.core.annotation.DataSource;

@Repository
public interface TerminalMapper {
	/**
	 * 根据id查询终端信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("slave")
	public Terminal getTerminalByID(String id);

	/**
	 * 查询终端信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public List<Terminal> getTerminalPageByCondition(Map<String, Object> map);

	/**
	 * 查询终端信息.
	 * 
	 * @param map
	 * @return
	 */
	@DataSource("slave")
	public int getTerminalCountByCondition(Map<String, Object> map);

	/**
	 * 删除终端信息.
	 * 
	 * @param id
	 * @return
	 */
	@DataSource("master")
	public void deleteTerminalByID(String id);

	/**
	 * 修改终端信息.
	 * 
	 * @param terminal
	 * @return
	 */
	@DataSource("master")
	public void updateTerminal(Terminal terminal);

	/**
	 * 保存终端信息.
	 * 
	 * @param terminal
	 * @return
	 */
	@DataSource("master")
	public void insertTerminal(Terminal terminal);
}