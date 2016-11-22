package com.joker.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.ShoppingGuide;
import com.joker.common.model.Store;
import com.joker.common.service.ShoppingGuideService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

@RestController
public class ShoppingGuideSaleController extends AbstractController {

	@Autowired
	ShoppingGuideService shoppingGuideService;

	@RequestMapping(value = { "/shoppingGuide/getShoppingGuide" }, method = RequestMethod.POST)
	@NotNull(value = "token,code")
	@ResponseBody
	public ReturnBody getShoppingGuide(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		
		String token = paramsBody.getToken();
		String code = (String) paramsBody.getBody().get("code");
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			// 获取当前用户所在门店的营业日期
			Account account = (Account) user;
			ShoppingGuide shoppingGuide = shoppingGuideService.getShoppingGuideByCode(account.getClient().getId(),account.getStore().getId(), code);
			
			if(shoppingGuide!=null){
				rbody.setData(shoppingGuide);
				rbody.setStatus(ResponseState.SUCCESS);
			}
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	/**
	 * 查询颜色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/shoppingGuide/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getShoppingGuideByPage(@RequestBody ParamsBody paramsBody,
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
			map.put("clientId", clientId);
			if(StringUtils.isNotBlank(likeName)){
				map.put("likeName", likeName);
			}
			if(StringUtils.isNotBlank(code)){
				map.put("code", code);
			}
			
			Page<ShoppingGuide> page = shoppingGuideService.getShoppingGuidePageByCondition(map,
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
	 * 查询颜色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/shoppingGuide/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getShoppingGuideById(@RequestBody ParamsBody paramsBody,
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
			ShoppingGuide ShoppingGuide = shoppingGuideService.getShoppingGuideByID(id);
			rbody.setData(ShoppingGuide);
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
	 * 添加颜色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/shoppingGuide/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String storeId = (String) params.get("storeId");
		String code = (String) params.get("code");
		String name = (String) params.get("name");

		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入编号！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入名称！");
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

			

			ShoppingGuide addShoppingGuide = new ShoppingGuide();
			addShoppingGuide.setId(UUID.randomUUID().toString());
			addShoppingGuide.setCode(code);
			addShoppingGuide.setName(name);
			addShoppingGuide.setClient(account.getClient());
			addShoppingGuide.setCreated(new Date());
			addShoppingGuide.setCreator(account.getId());
			
			Store store = new Store();
			store.setId(storeId);
			addShoppingGuide.setStore(store);

			shoppingGuideService.insertShoppingGuide(addShoppingGuide);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新颜色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/shoppingGuide/update" }, method = RequestMethod.POST)
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
		String status = (String) params.get("status");
		String storeId = (String) params.get("storeId");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入编号！");
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
		if (StringUtils.isBlank(storeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入门店！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			

			ShoppingGuide updateShoppingGuide = new ShoppingGuide();
			updateShoppingGuide.setId(id);
			updateShoppingGuide.setCode(code);
			updateShoppingGuide.setName(name);
			updateShoppingGuide.setClient(account.getClient());
			updateShoppingGuide.setStatus(status);
			updateShoppingGuide.setModified(new Date());
			updateShoppingGuide.setEditor(account.getId());
			
			Store store = new Store();
			store.setId(storeId);
			updateShoppingGuide.setStore(store);

			shoppingGuideService.updateShoppingGuide(updateShoppingGuide);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除颜色信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/shoppingGuide/delete" }, method = RequestMethod.POST)
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
			shoppingGuideService.deleteShoppingGuideByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

}
