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
import com.joker.common.model.promotion.PromotionCondition;
import com.joker.common.model.promotion.PromotionConditionMatchContent;
import com.joker.common.service.PromotionConditionMatchContentService;
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
public class PromotionConditionMatchContentController extends AbstractController {

	@Autowired
	PromotionConditionMatchContentService promotionConditionMatchContentService;

	/**
	 * 查询促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionConditionMatchContent/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionConditionMatchContentByPage(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String promotionConditionId = (String) params.get("promotionConditionId");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			map.put("promotionConditionId", promotionConditionId);
			Page<PromotionConditionMatchContent> page = promotionConditionMatchContentService
					.getPromotionConditionMatchContentPageByCondition(map, pageNo,
							limit);
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
	@RequestMapping(value = { "/promotionConditionMatchContent/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionConditionMatchContentByList(
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
			List<PromotionConditionMatchContent> list = promotionConditionMatchContentService
					.getPromotionConditionMatchContentPageByCondition(map);
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
	@RequestMapping(value = { "/promotionConditionMatchContent/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionConditionMatchContentById(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
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
			PromotionConditionMatchContent promotionConditionMatchContent = promotionConditionMatchContentService
					.getPromotionConditionMatchContentByID(id);
			rbody.setData(promotionConditionMatchContent);
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
	@RequestMapping(value = { "/promotionConditionMatchContent/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String promotionConditionId = (String) params.get("promotionConditionId");
		String matchContent = (String) params.get("matchContent");

		if (StringUtils.isBlank(promotionConditionId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销条件！");
			return rbody;
		}
		if (StringUtils.isBlank(matchContent)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入条件匹配内容！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			PromotionCondition promotionCondition = new PromotionCondition();
			promotionCondition.setId(promotionConditionId);

			PromotionConditionMatchContent promotionConditionMatchContent = new PromotionConditionMatchContent();
			promotionConditionMatchContent.setId(UUID.randomUUID().toString());
			promotionConditionMatchContent.setPromotionCondition(promotionCondition);
			promotionConditionMatchContent.setMatchContent(matchContent);
			promotionConditionMatchContent.setClient(account.getClient());
			promotionConditionMatchContent.setCreated(new Date());
			promotionConditionMatchContent.setCreator(account.getId());

			promotionConditionMatchContentService
					.insertPromotionConditionMatchContent(promotionConditionMatchContent);
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
	@RequestMapping(value = { "/promotionConditionMatchContent/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String matchContent = (String) params.get("matchContent");
		String status = (String) params.get("status");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(matchContent)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入条件匹配内容！");
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

			PromotionConditionMatchContent promotionConditionMatchContent = new PromotionConditionMatchContent();
			promotionConditionMatchContent.setId(id);
			promotionConditionMatchContent.setMatchContent(matchContent);
			promotionConditionMatchContent.setClient(account.getClient());
			promotionConditionMatchContent.setStatus(status);
			promotionConditionMatchContent.setModified(new Date());
			promotionConditionMatchContent.setEditor(account.getId());

			promotionConditionMatchContentService
					.updatePromotionConditionMatchContent(promotionConditionMatchContent);
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
	@RequestMapping(value = { "/promotionConditionMatchContent/delete" }, method = RequestMethod.POST)
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
			promotionConditionMatchContentService
					.deletePromotionConditionMatchContentByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}