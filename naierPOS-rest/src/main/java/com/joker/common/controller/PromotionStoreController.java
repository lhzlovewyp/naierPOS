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
import com.joker.common.model.Store;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionStore;
import com.joker.common.service.PromotionStoreService;
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
public class PromotionStoreController extends AbstractController {

	@Autowired
	PromotionStoreService promotionStoreService;

	/**
	 * 查询促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionStore/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionStoreByPage(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String promotionId = (String) params.get("promotionId");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			map.put("promotionId", promotionId);
			Page<PromotionStore> page = promotionStoreService
					.getPromotionStorePageByCondition(map, pageNo, limit);
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
	 * 查询促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionStore/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionStoreByList(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<PromotionStore> list = promotionStoreService
					.getPromotionStorePageByCondition(map);
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
	 * 查询促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionStore/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionStoreById(@RequestBody ParamsBody paramsBody,
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
			PromotionStore promotionStore = promotionStoreService
					.getPromotionStoreByID(id);
			rbody.setData(promotionStore);
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
	 * 添加促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionStore/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String promotionId = (String) params.get("promotionId");
		String storeId = (String) params.get("storeId");
		String effDate = (String) params.get("effDate");
		String expDate = (String) params.get("expDate");

		if (StringUtils.isBlank(promotionId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销活动！");
			return rbody;
		}
		if (StringUtils.isBlank(storeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入门店信息！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			Promotion promotion = new Promotion();
			promotion.setId(promotionId);

			Store store = new Store();
			store.setId(storeId);

			PromotionStore promotionStore = new PromotionStore();
			promotionStore.setId(UUID.randomUUID().toString());
			promotionStore.setPromotion(promotion);
			promotionStore.setStore(store);
			if (StringUtils.isNotBlank(effDate)) {
				promotionStore.setEffDate(DatetimeUtil.toDate(effDate,
						DatetimeUtil.DATE));
			}
			if (StringUtils.isNotBlank(expDate)) {
				promotionStore.setExpDate(DatetimeUtil.toDate(expDate,
						DatetimeUtil.DATE));
			}

			promotionStore.setClient(account.getClient());
			promotionStore.setCreated(new Date());
			promotionStore.setCreator(account.getId());

			promotionStoreService.insertPromotionStore(promotionStore);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionStore/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String promotionId = (String) params.get("promotionId");
		String storeId = (String) params.get("storeId");
		String effDate = (String) params.get("effDate");
		String expDate = (String) params.get("expDate");
		String status = (String) params.get("status");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(promotionId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销活动！");
			return rbody;
		}
		if (StringUtils.isBlank(storeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入门店信息！");
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

			Promotion promotion = new Promotion();
			promotion.setId(promotionId);

			Store store = new Store();
			store.setId(storeId);

			PromotionStore promotionStore = new PromotionStore();
			promotionStore.setId(id);
			promotionStore.setPromotion(promotion);
			promotionStore.setStore(store);

			if (StringUtils.isNotBlank(effDate)) {
				promotionStore.setEffDate(DatetimeUtil.toDate(effDate,
						DatetimeUtil.DATE));
			}
			if (StringUtils.isNotBlank(expDate)) {
				promotionStore.setExpDate(DatetimeUtil.toDate(expDate,
						DatetimeUtil.DATE));
			}
			promotionStore.setClient(account.getClient());
			promotionStore.setStatus(status);
			promotionStore.setModified(new Date());
			promotionStore.setEditor(account.getId());

			promotionStoreService.updatePromotionStore(promotionStore);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionStore/delete" }, method = RequestMethod.POST)
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
			promotionStoreService.deletePromotionStoreByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}