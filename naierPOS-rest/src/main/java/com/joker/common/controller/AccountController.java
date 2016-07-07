/**
 * 
 */
package com.joker.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joker.common.dto.SaleDto;
import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;
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
	 * 初始化销售单.
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
		Integer start = (Integer) params.get("start");
		Integer limit = (Integer) params.get("limit");
		start = (start == null ? 0 : start);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientCode = account.getClient().getId();
			Page<Account> page = accountService.getAccountPageByClient(
					clientCode, start, limit);
			rbody.setData(page);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}
