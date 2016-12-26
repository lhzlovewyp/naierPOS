/**
 * 
 */
package com.joker.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.Role;
import com.joker.common.model.Store;
import com.joker.common.service.AccountService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.EncryptUtil;

/**
 * @author lvhaizhen
 * 
 */
@Controller
public class AccountController extends AbstractController {

	@Autowired
	AccountService accountService;

	/**
	 * 校验用户名
	 * 
	 * @param paramsBody
	 * @return
	 */
	@RequestMapping(value = { "/account/validUserName" }, method = RequestMethod.POST)
	@ResponseBody
	@NotNull(value = "userName")
	public ReturnBody ValidUserName(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		Map params = paramsBody.getBody();
		String userName = (String) params.get("userName");
		String clientCode = (String) params.get("clientCode");
		Account validUser = accountService.getAccountByClientAndName(
				clientCode, userName);
		if (validUser == null) {
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.FAILED);
		}
		return rbody;
	}

	/**
	 * 修改密码
	 * 
	 * @param paramsBody
	 * @return
	 */
	@RequestMapping(value = { "/account/updatePWD" }, method = RequestMethod.POST)
	@NotNull(value = "oldPassword,newPassword,reNewPassword", user = true)
	@ResponseBody
	public ReturnBody updatePWD(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String password = (String) params.get("oldPassword");
		String newPassword = (String) params.get("newPassword");
		String reNewPassword = (String) params.get("reNewPassword");

		if (!newPassword.equals(reNewPassword)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("2次输入的新密码不一致!");
			return rbody;
		}

		// 获取当前用户信息
		String token = paramsBody.getToken();
		if (StringUtils.isNotBlank(token)) {
			Account user = (Account) CacheFactory.getCache().get(token);
			// 查询用户密码
			user = accountService.getAccountByID(user.getId());
			if (user != null) {
				// 如果输入的旧密码和数据库中不一致
				if (!EncryptUtil.doEncrypt(user.getPassword()).equals(password)) {
					rbody.setStatus(ResponseState.FAILED);
					rbody.setMsg("初始密码输入错误!");
					return rbody;
				}
				user.setPassword(newPassword);
				user.setChangePWD("0");
				accountService.updateAccount(user);
				rbody.setStatus(ResponseState.SUCCESS);
				return rbody;
			}
		}
		// 修改密码失败.
		rbody.setStatus(ResponseState.FAILED);
		rbody.setMsg("初始密码输入错误!");
		return rbody;
	}

	/**
	 * 查询账号信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/account/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getAccountByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String likeName = (String) params.get("likeName");
		String name = (String) params.get("name");
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
			if(StringUtils.isNotBlank(name)){
				map.put("name", name);
			}
			
			Page<Account> page = accountService.getAccountPageByCondition(
					map, pageNo, limit);
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
	 * 查询账号信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/account/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getAccountById(@RequestBody ParamsBody paramsBody,
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
			Account account = accountService.getAccountByID(id);
			rbody.setData(account);
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
	 * 添加账户信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/account/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String name = (String) params.get("name");
		String nick = (String) params.get("nick");
		String password = (String) params.get("password");
		String changePWD = (String) params.get("changePWD");
		String storeIds = (String) params.get("storeIds");
		String roleId = (String) params.get("roleId");
		String displayPhoto=(String)params.get("displayPhoto");

		password=EncryptUtil.doEncrypt("111111");
		
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入账户！");
			return rbody;
		}
		if (StringUtils.isBlank(nick)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入昵称！");
			return rbody;
		}
		if (StringUtils.isBlank(password)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入密码！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;


			Account addAccount = new Account();
			addAccount.setId(UUID.randomUUID().toString());
			addAccount.setChangePWD(changePWD);
			addAccount.setName(name);
			addAccount.setNick(nick);
			addAccount.setClient(account.getClient());
			addAccount.setPassword(password);
			addAccount.setCreated(new Date());
			addAccount.setCreator(account.getId());
			addAccount.setDisplayPhoto(displayPhoto);

			if (StringUtils.isNotBlank(storeIds)) {
				String[] storeIdArr = storeIds.split(",");
				List<Store> stores=new ArrayList<Store>();
				for(String storeId:storeIdArr){
					Store store=new Store();
					store.setId(storeId);
					stores.add(store);
				}
				addAccount.setStores(stores);
			}
			if (StringUtils.isNotBlank(roleId)) {
				String[] roleIdArr = roleId.split(",");
				List<Role> roles = new ArrayList<Role>();
				for (int i = 0; i < roleIdArr.length; i++) {
					String oneRoleId = roleIdArr[i];
					Role role = new Role();
					role.setId(oneRoleId);
					roles.add(role);
				}
				addAccount.setRoles(roles);
			}

			accountService.insertAccount(addAccount);
			rbody.setStatus(ResponseState.SUCCESS);
		}else{
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	/**
	 * 更新账户信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/account/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String name = (String) params.get("name");
		String nick = (String) params.get("nick");
		String password = (String) params.get("password");
		String storeIds = (String) params.get("storeIds");
		String roleId = (String) params.get("roleId");
		String status = (String) params.get("status");
		String displayPhoto=(String)params.get("displayPhoto");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入账户！");
			return rbody;
		}
		if (StringUtils.isBlank(nick)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入昵称！");
			return rbody;
		}
		if (StringUtils.isBlank(password)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入密码！");
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
			Account loginAccount = (Account) user;

			Account account = new Account();
			account.setId(id);
			account.setName(name);
			account.setNick(nick);
			account.setClient(account.getClient());
			account.setPassword(password);
			account.setCreated(new Date());
			account.setCreator(loginAccount.getId());
			account.setStatus(status);
			account.setDisplayPhoto(displayPhoto);

			if (StringUtils.isNotBlank(storeIds)) {
				String[] storeIdArr = storeIds.split(",");
				List<Store> stores=new ArrayList<Store>();
				for(String storeId:storeIdArr){
					Store store=new Store();
					store.setId(storeId);
					stores.add(store);
				}
				account.setStores(stores);
			}
			if (StringUtils.isNotBlank(roleId)) {
				String[] roleIdArr = roleId.split(",");
				List<Role> roles = new ArrayList<Role>();
				for (int i = 0; i < roleIdArr.length; i++) {
					String oneRoleId = roleIdArr[i];
					Role role = new Role();
					role.setId(oneRoleId);
					roles.add(role);
				}
				account.setRoles(roles);
			}

			accountService.updateAccount(account);
			rbody.setStatus(ResponseState.SUCCESS);
		}else{
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	/**
	 * 删除账户信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/account/delete" }, method = RequestMethod.POST)
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
			rbody.setMsg("请勾选需要删除的账号！");
			return rbody;
		}
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			accountService.deleteAccountByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		}else{
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}