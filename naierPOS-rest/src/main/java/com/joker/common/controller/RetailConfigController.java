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
import com.joker.common.model.RetailConfig;
import com.joker.common.service.RetailConfigService;
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
public class RetailConfigController extends AbstractController {

	@Autowired
	RetailConfigService retailConfigService;

	/**
	 * 查询零售参数信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/retailConfig/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRetailConfigByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			Page<RetailConfig> page = retailConfigService
					.getRetailConfigPageByCondition(map, pageNo, limit);
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
	 * 查询零售参数信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/retailConfig/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRetailConfigByList(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<RetailConfig> list = retailConfigService
					.getRetailConfigPageByCondition(map);
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
	 * 查询零售参数信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/retailConfig/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRetailConfigById(@RequestBody ParamsBody paramsBody,
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
			RetailConfig retailConfig = retailConfigService
					.getRetailConfigByID(id);
			rbody.setData(retailConfig);
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
	 * 添加零售参数信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/retailConfig/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String priceDecimal = null;
		if (params.get("priceDecimal") != null) {
			priceDecimal = String.valueOf(params.get("priceDecimal"));
		}
		String itemDecimal = null;
		if (params.get("itemDecimal") != null) {
			itemDecimal = String.valueOf(params.get("itemDecimal"));
		}
		String itemRoundDown = null;
		if (params.get("itemRoundDown") != null) {
			itemRoundDown = String.valueOf(params.get("itemRoundDown"));
		}
		String transDecimal = null;
		if (params.get("transDecimal") != null) {
			transDecimal = String.valueOf(params.get("transDecimal"));
		}
		String saleRoundDown = null;
		if (params.get("saleRoundDown") != null) {
			saleRoundDown = String.valueOf(params.get("saleRoundDown"));
		}
		String clientId = (String) params.get("clientId");

		if (!StringUtils.isNumeric(priceDecimal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入单价保留小数位数！");
			return rbody;
		}
		if (!StringUtils.isNumeric(itemDecimal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入商品金额保留小数位数！");
			return rbody;
		}
		if (!StringUtils.isNumeric(itemRoundDown)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入商品金额舍去值！");
			return rbody;
		}
		if (!StringUtils.isNumeric(transDecimal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单金额保留小数位数！");
			return rbody;
		}
		if (!StringUtils.isNumeric(saleRoundDown)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单金额舍去值！");
			return rbody;
		}
		

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			

			RetailConfig retailConfig = new RetailConfig();
			retailConfig.setId(UUID.randomUUID().toString());
			retailConfig.setPriceDecimal(Integer.valueOf(priceDecimal));
			retailConfig.setItemDecimal(Integer.valueOf(itemDecimal));
			retailConfig.setItemRoundDown(Integer.valueOf(itemRoundDown));
			retailConfig.setTransDecimal(Integer.valueOf(transDecimal));
			retailConfig.setSaleRoundDown(Integer.valueOf(saleRoundDown));
			retailConfig.setClient(account.getClient());
			retailConfig.setCreated(new Date());
			retailConfig.setCreator(account.getId());

			retailConfigService.insertRetailConfig(retailConfig);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新零售参数信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/retailConfig/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String priceDecimal = null;
		if (params.get("priceDecimal") != null) {
			priceDecimal = String.valueOf(params.get("priceDecimal"));
		}
		String itemDecimal = null;
		if (params.get("itemDecimal") != null) {
			itemDecimal = String.valueOf(params.get("itemDecimal"));
		}
		String itemRoundDown = null;
		if (params.get("itemRoundDown") != null) {
			itemRoundDown = String.valueOf(params.get("itemRoundDown"));
		}
		String transDecimal = null;
		if (params.get("transDecimal") != null) {
			transDecimal = String.valueOf(params.get("transDecimal"));
		}
		String saleRoundDown = null;
		if (params.get("saleRoundDown") != null) {
			saleRoundDown = String.valueOf(params.get("saleRoundDown"));
		}

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (!StringUtils.isNumeric(priceDecimal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入单价保留小数位数！");
			return rbody;
		}
		if (!StringUtils.isNumeric(itemDecimal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入商品金额保留小数位数！");
			return rbody;
		}
		if (!StringUtils.isNumeric(itemRoundDown)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入商品金额舍去值！");
			return rbody;
		}
		if (!StringUtils.isNumeric(transDecimal)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单金额保留小数位数！");
			return rbody;
		}
		if (!StringUtils.isNumeric(saleRoundDown)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单金额舍去值！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			RetailConfig retailConfig = new RetailConfig();
			retailConfig.setId(id);
			retailConfig.setPriceDecimal(Integer.valueOf(priceDecimal));
			retailConfig.setItemDecimal(Integer.valueOf(itemDecimal));
			retailConfig.setItemRoundDown(Integer.valueOf(itemRoundDown));
			retailConfig.setTransDecimal(Integer.valueOf(transDecimal));
			retailConfig.setSaleRoundDown(Integer.valueOf(saleRoundDown));
			retailConfig.setClient(account.getClient());
			retailConfig.setModified(new Date());
			retailConfig.setEditor(account.getId());

			retailConfigService.updateRetailConfig(retailConfig);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除零售参数信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/retailConfig/delete" }, method = RequestMethod.POST)
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
			retailConfigService.deleteRetailConfigByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}