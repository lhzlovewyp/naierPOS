/**
 * 
 */
package com.joker.common.controller;

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
import com.joker.common.model.SalesConfig;
import com.joker.common.model.Store;
import com.joker.common.model.Terminal;
import com.joker.common.service.SalesConfigService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.DatetimeUtil;

/**
 * @author zhangfei
 * 
 */
@Controller
public class SalesConfigController extends AbstractController {

	@Autowired
	SalesConfigService salesConfigService;

	/**
	 * 查询终端信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/salesConfig/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getSalesConfigByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		
		String storeCode = (String) params.get("storeCode");
		String storeName = (String) params.get("storeName");
		String salesDate = (String) params.get("salesDate");
		
		
		
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			
			if(StringUtils.isNotBlank(storeCode)){
				map.put("storeCode",storeCode);
			}
			if(StringUtils.isNotBlank(storeName)){
				map.put("storeName",storeName);
			}
			if(StringUtils.isNotBlank(salesDate)){
				map.put("salesDate",salesDate);
			}
			
			Page<SalesConfig> page = salesConfigService
					.getSalesConfigPageByCondition(map, pageNo, limit);
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
	 * 查询终端信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/salesConfig/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getSalesConfigByList(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<SalesConfig> list = salesConfigService
					.getSalesConfigPageByCondition(map);
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
	 * 查询终端信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/salesConfig/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getSalesConfigById(@RequestBody ParamsBody paramsBody,
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
			SalesConfig salesConfig = salesConfigService.getSalesConfigByID(id);
			rbody.setData(salesConfig);
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
	 * 添加终端信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/salesConfig/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String storeId = (String) params.get("storeId");
		String terminalId = (String) params.get("terminalId");
		String salesDate = (String) params.get("salesDate");
		String maxCode = null;
		if (params.get("maxCode") != null) {
			maxCode = String.valueOf(params.get("maxCode"));
		}
		String flag = null;
		if (params.get("flag") != null) {
			flag = String.valueOf(params.get("flag"));
		}
		String clientId = (String) params.get("clientId");

		if (StringUtils.isBlank(salesDate)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入营业日期！");
			return rbody;
		}
		if (!StringUtils.isNumeric(maxCode)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售最大交易序号！");
			return rbody;
		}
		if (StringUtils.isBlank(storeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入门店！");
			return rbody;
		}
		

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			

			SalesConfig salesConfig = new SalesConfig();
			salesConfig.setId(UUID.randomUUID().toString());
			salesConfig.setFlag(Integer.valueOf(flag));
			salesConfig.setMaxCode(Integer.valueOf(maxCode));
			salesConfig.setSalesDate(DatetimeUtil.toDate(salesDate,
					DatetimeUtil.DATE));
			salesConfig.setClient(account.getClient());
			salesConfig.setCreated(new Date());
			salesConfig.setCreator(account.getId());

			Store store = new Store();
			store.setId(storeId);
			salesConfig.setStore(store);

			if (StringUtils.isNotBlank(terminalId)) {
				Terminal terminal = new Terminal();
				terminal.setId(terminalId);
				salesConfig.setTerminal(terminal);
			}

			salesConfigService.insertSalesConfig(salesConfig);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新终端信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/salesConfig/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String storeId = (String) params.get("storeId");
		String terminalId = (String) params.get("terminalId");
		String salesDate = (String) params.get("salesDate");
		String maxCode = null;
		if (params.get("maxCode") != null) {
			maxCode = String.valueOf(params.get("maxCode"));
		}
		String flag = null;
		if (params.get("flag") != null) {
			flag = String.valueOf(params.get("flag"));
		}

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(salesDate)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入营业日期！");
			return rbody;
		}
		if (!StringUtils.isNumeric(maxCode)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售最大交易序号！");
			return rbody;
		}
		if (StringUtils.isBlank(storeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入门店！");
			return rbody;
		}
		
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			SalesConfig salesConfig = new SalesConfig();
			salesConfig.setId(id);
			salesConfig.setFlag(Integer.valueOf(flag));
			salesConfig.setMaxCode(Integer.valueOf(maxCode));
			salesConfig.setSalesDate(DatetimeUtil.toDate(salesDate,
					DatetimeUtil.DATE));
			salesConfig.setClient(account.getClient());
			salesConfig.setModified(new Date());
			salesConfig.setEditor(account.getId());

			Store store = new Store();
			store.setId(storeId);
			salesConfig.setStore(store);

			if (StringUtils.isNotBlank(terminalId)) {
				Terminal terminal = new Terminal();
				terminal.setId(terminalId);
				salesConfig.setTerminal(terminal);
			}

			salesConfigService.updateSalesConfig(salesConfig);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除终端信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/salesConfig/delete" }, method = RequestMethod.POST)
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
			salesConfigService.deleteSalesConfigByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}