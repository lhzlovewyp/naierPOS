/**
 * 
 */
package com.joker.common.controller;

import java.math.BigDecimal;
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
import com.joker.common.model.promotion.Promotion;
import com.joker.common.model.promotion.PromotionOffer;
import com.joker.common.service.PromotionOfferService;
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
public class PromotionOfferController extends AbstractController {

	@Autowired
	PromotionOfferService promotionOfferService;

	/**
	 * 查询促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotionOffer/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionOfferByPage(
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
			Page<PromotionOffer> page = promotionOfferService
					.getPromotionOfferPageByCondition(map, pageNo, limit);
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
	@RequestMapping(value = { "/promotionOffer/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionOfferByList(
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
			List<PromotionOffer> list = promotionOfferService
					.getPromotionOfferPageByCondition(map);
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
	@RequestMapping(value = { "/promotionOffer/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionOfferById(@RequestBody ParamsBody paramsBody,
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
			PromotionOffer promotionOffer = promotionOfferService
					.getPromotionOfferByID(id);
			rbody.setData(promotionOffer);
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
	@RequestMapping(value = { "/promotionOffer/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String promotionId = (String) params.get("promotionId");
		String offerTypeId = (String) params.get("offerTypeId");
		String offerContent = null;
		if (params.get("offerContent") != null) {
			offerContent = String.valueOf(params.get("offerContent"));
		}
		String matchTypeId = (String) params.get("matchTypeId");

		if (StringUtils.isBlank(promotionId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销活动！");
			return rbody;
		}
		if (StringUtils.isBlank(offerTypeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入优惠方式！");
			return rbody;
		}
		if (StringUtils.isBlank(offerContent)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入优惠值！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			Promotion promotion = new Promotion();
			promotion.setId(promotionId);

			PromotionOffer promotionOffer = new PromotionOffer();
			promotionOffer.setId(UUID.randomUUID().toString());
			promotionOffer.setPromotion(promotion);
			promotionOffer.setOfferType(offerTypeId);
			promotionOffer.setOfferContent(new BigDecimal(offerContent));
			promotionOffer.setMatchType(matchTypeId);
			promotionOffer.setClient(account.getClient());
			promotionOffer.setCreated(new Date());
			promotionOffer.setCreator(account.getId());

			promotionOfferService.insertPromotionOffer(promotionOffer);
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
	@RequestMapping(value = { "/promotionOffer/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String promotionId = (String) params.get("promotionId");
		String offerTypeId = (String) params.get("offerTypeId");
		String offerContent = null;
		if (params.get("offerContent") != null) {
			offerContent = String.valueOf(params.get("offerContent"));
		}
		String matchTypeId = (String) params.get("matchTypeId");
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
		if (StringUtils.isBlank(offerTypeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入优惠方式！");
			return rbody;
		}
		if (StringUtils.isBlank(offerContent)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入优惠值！");
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

			PromotionOffer promotionOffer = new PromotionOffer();
			promotionOffer.setId(id);
			promotionOffer.setPromotion(promotion);
			promotionOffer.setOfferType(offerTypeId);
			promotionOffer.setOfferContent(new BigDecimal(offerContent));
			promotionOffer.setMatchType(matchTypeId);
			promotionOffer.setClient(account.getClient());
			promotionOffer.setStatus(status);
			promotionOffer.setModified(new Date());
			promotionOffer.setEditor(account.getId());

			promotionOfferService.updatePromotionOffer(promotionOffer);
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
	@RequestMapping(value = { "/promotionOffer/delete" }, method = RequestMethod.POST)
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
			promotionOfferService.deletePromotionOfferByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}