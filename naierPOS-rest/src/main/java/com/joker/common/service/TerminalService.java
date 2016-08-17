/**
 * 
 */
package com.joker.common.service;

import java.util.List;
import java.util.Map;

import com.joker.common.model.Terminal;
import com.joker.core.dto.Page;

/**
 * @author zhangfei
 * 
 */
public interface TerminalService {

	/**
	 * 根据id查询终端信息.
	 * 
	 * @param id
	 * @return
	 */
	public Terminal getTerminalByID(String id);

	/**
	 * 根据商户查询终端信息.
	 * 
	 * @param map
	 * @param pageNo
	 * @param limit
	 * @return
	 */
	public Page<Terminal> getTerminalPageByCondition(Map<String, Object> map,
			int pageNo, int limit);

	public List<Terminal> getTerminalPageByCondition(Map<String, Object> map);

	/**
	 * 删除终端信息.
	 * 
	 * @param id
	 * @return
	 */
	public void deleteTerminalByID(String id);

	/**
	 * 修改终端信息.
	 * 
	 * @param terminal
	 * @return
	 */
	public void updateTerminal(Terminal terminal);

	/**
	 * 保存终端信息.
	 * 
	 * @param terminal
	 * @return
	 */
	public void insertTerminal(Terminal terminal);
}