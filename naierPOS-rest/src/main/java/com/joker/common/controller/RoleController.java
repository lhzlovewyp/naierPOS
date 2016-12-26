/**
 * 
 */
package com.joker.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.Role;
import com.joker.common.service.RoleService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

/**
 * @author zhangfei
 * 
 */
@Controller
public class RoleController extends AbstractController {

	@Autowired
	RoleService roleService;

	/**
	 * 查询角色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/role/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRoleByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String likeName = (String) params.get("likeName");
		String code = (String) params.get("code");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(clientId)){
				map.put("clientId", clientId);
			}
			if(StringUtils.isNotBlank(likeName)){
				map.put("likeName", likeName);
			}
			if(StringUtils.isNotBlank(code)){
				map.put("code", code);
			}
			
			Page<Role> page = roleService.getRolePageByCondition(map,
					pageNo, limit);
			rbody.setData(page);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	/**
	 * 查询角色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/role/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRoleByList(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<Role> list = roleService.getRolePageByCondition(map);
			rbody.setData(list);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	/**
	 * 查询角色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/role/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRoleById(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Role role = roleService.getRoleByID(id);
			rbody.setData(role);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 添加角色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/role/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String code = (String) params.get("code");
		String name = (String) params.get("name");
		String loginTerminal = (String) params.get("loginTerminal");
		String loginAdmin = (String) params.get("loginAdmin");
		String itemDISC = (String) params.get("itemDISC");
		String allDISC = (String) params.get("allDISC");
		String clientId = (String) params.get("clientId");

		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入品牌编码！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入名称！");
			return rbody;
		}
		if (StringUtils.isBlank(loginTerminal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许登陆收银前台！");
			return rbody;
		}
		if (StringUtils.isBlank(loginAdmin)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许登陆后台！");
			return rbody;
		}
		if (StringUtils.isBlank(itemDISC)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许单项折扣！");
			return rbody;
		}
		if (StringUtils.isBlank(allDISC)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许整单折扣！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;


			Role role = new Role();
			role.setId(UUID.randomUUID().toString());
			role.setCode(code);
			role.setName(name);
			role.setClient(account.getClient());
			role.setLoginTerminal(loginTerminal);
			role.setLoginAdmin(loginAdmin);
			role.setItemDISC(itemDISC);
			role.setAllDISC(allDISC);
			role.setCreated(new Date());
			role.setCreator(account.getId());

			roleService.insertRole(role);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新角色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/role/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String code = (String) params.get("code");
		String name = (String) params.get("name");
		String loginTerminal = (String) params.get("loginTerminal");
		String loginAdmin = (String) params.get("loginAdmin");
		String itemDISC = (String) params.get("itemDISC");
		String allDISC = (String) params.get("allDISC");
		String status = (String) params.get("status");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入品牌编码！");
			return rbody;
		}
		if (StringUtils.isBlank(loginTerminal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许登陆收银前台！");
			return rbody;
		}
		if (StringUtils.isBlank(loginAdmin)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许登陆后台！");
			return rbody;
		}
		if (StringUtils.isBlank(itemDISC)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许单项折扣！");
			return rbody;
		}
		if (StringUtils.isBlank(allDISC)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否允许整单折扣！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入名称！");
			return rbody;
		}
		if (StringUtils.isBlank(status)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入状态！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			Role role = new Role();
			role.setId(id);
			role.setCode(code);
			role.setName(name);
			role.setClient(account.getClient());
			role.setLoginTerminal(loginTerminal);
			role.setLoginAdmin(loginAdmin);
			role.setItemDISC(itemDISC);
			role.setAllDISC(allDISC);
			role.setStatus(status);
			role.setModified(new Date());
			role.setEditor(account.getId());

			roleService.updateRole(role);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除角色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/role/delete" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody delete(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请勾选需要删除的记录！");
			return rbody;
		}
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			roleService.deleteRoleByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}