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
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.service.PromotionService;
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
public class PromotionController extends AbstractController {

	@Autowired
	PromotionService promotionService;

	/**
	 * 查询促销信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/promotion/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String likeName = (String) params.get("likeName");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			map.put("likeName", likeName);
			Page<Promotion> page = promotionService
					.getPromotionPageByCondition(map, pageNo, limit);
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
	@RequestMapping(value = { "/promotion/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionByList(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<Promotion> list = promotionService
					.getPromotionPageByCondition(map);
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
	@RequestMapping(value = { "/promotion/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getPromotionById(@RequestBody ParamsBody paramsBody,
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
			Promotion promotion = promotionService.getPromotionByID(id);
			rbody.setData(promotion);
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
	@RequestMapping(value = { "/promotion/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String code = (String) params.get("code");
		String name = (String) params.get("name");
		String memo = (String) params.get("memo");
		String effDate = (String) params.get("effDate");
		String expDate = (String) params.get("expDate");
		String effTime = (String) params.get("effTime");
		String expTime = (String) params.get("expTime");
		String offerRelation = (String) params.get("offerRelation");
		String repeatEffect = null;
		if (params.get("repeatEffect") != null) {
			repeatEffect = String.valueOf(params.get("repeatEffect"));
		}
		String week = (String) params.get("week");
		String days = (String) params.get("days");
		String paymentRestrict = (String) params.get("paymentRestrict");
		String memberRestrict = (String) params.get("memberRestrict");
		String excluded = (String) params.get("excluded");
		String sort = null;
		if (params.get("sort") != null) {
			sort = String.valueOf(params.get("sort"));
		}

		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销编码！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销名称！");
			return rbody;
		}
		if (StringUtils.isBlank(effDate)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入生效日期！");
			return rbody;
		}
		if (StringUtils.isBlank(offerRelation)) {
			offerRelation = "0";
		}
		if (!NumberUtils.isDigits(repeatEffect)) {
			repeatEffect = "0";
		}
		if (!NumberUtils.isDigits(sort)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("优先级请输入数字！");
			return rbody;
		}
		if (StringUtils.isBlank(paymentRestrict)) {
			paymentRestrict = "NON";
		}
		if (StringUtils.isBlank(memberRestrict)) {
			memberRestrict = "ALL";
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			Promotion promotion = new Promotion();
			promotion.setId(UUID.randomUUID().toString());
			promotion.setCode(code);
			promotion.setName(name);
			promotion.setMemo(memo);
			promotion.setEffDate(DatetimeUtil
					.toDate(effDate, DatetimeUtil.DATE));
			if (StringUtils.isNotBlank(expDate)) {
				promotion.setExpDate(DatetimeUtil.toDate(expDate,
						DatetimeUtil.DATE));
			}
			promotion.setEffTime(effTime);
			promotion.setExpTime(expTime);
			promotion.setOfferRelation(offerRelation);
			promotion.setRepeatEffect(Integer.valueOf(repeatEffect));
			promotion.setWeek(week);
			promotion.setDays(days);
			promotion.setPaymentRestrict(paymentRestrict);
			promotion.setMemberRestrict(memberRestrict);
			promotion.setExcluded(excluded);
			promotion.setSort(Integer.valueOf(sort));

			promotion.setClient(account.getClient());
			promotion.setCreated(new Date());
			promotion.setCreator(account.getId());

			promotionService.insertPromotion(promotion);
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
	@RequestMapping(value = { "/promotion/update" }, method = RequestMethod.POST)
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
		String memo = (String) params.get("memo");
		String effDate = (String) params.get("effDate");
		String expDate = (String) params.get("expDate");
		String effTime = (String) params.get("effTime");
		String expTime = (String) params.get("expTime");
		String offerRelation = (String) params.get("offerRelationId");
		String repeatEffect = null;
		if (params.get("repeatEffect") != null) {
			repeatEffect = String.valueOf(params.get("repeatEffect"));
		}
		String week = (String) params.get("week");
		String days = (String) params.get("days");
		String paymentRestrict = (String) params.get("paymentRestrictId");
		String memberRestrict = (String) params.get("memberRestrictId");
		String excluded = (String) params.get("excluded");
		String status = (String) params.get("status");
		String sort = null;
		if (params.get("sort") != null) {
			sort = String.valueOf(params.get("sort"));
		}

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销编码！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入促销名称！");
			return rbody;
		}
		if (StringUtils.isBlank(effDate)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入生效日期！");
			return rbody;
		}
		if (!NumberUtils.isDigits(sort)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("优先级请输入数字！");
			return rbody;
		}
		if (StringUtils.isBlank(offerRelation)) {
			offerRelation = "0";
		}
		if (!NumberUtils.isDigits(repeatEffect)) {
			repeatEffect = "0";
		}
		if (StringUtils.isBlank(paymentRestrict)) {
			paymentRestrict = "NON";
		}
		if (StringUtils.isBlank(memberRestrict)) {
			memberRestrict = "ALL";
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
			promotion.setId(id);
			promotion.setCode(code);
			promotion.setName(name);
			promotion.setMemo(memo);
			promotion.setEffDate(DatetimeUtil
					.toDate(effDate, DatetimeUtil.DATE));
			if (StringUtils.isNotBlank(expDate)) {
				promotion.setExpDate(DatetimeUtil.toDate(expDate,
						DatetimeUtil.DATE));
			}
			promotion.setEffTime(effTime);
			promotion.setExpTime(expTime);
			promotion.setOfferRelation(offerRelation);
			promotion.setRepeatEffect(Integer.valueOf(repeatEffect));
			promotion.setWeek(week);
			promotion.setDays(days);
			promotion.setPaymentRestrict(paymentRestrict);
			promotion.setMemberRestrict(memberRestrict);
			promotion.setExcluded(excluded);
			promotion.setClient(account.getClient());
			promotion.setStatus(status);
			promotion.setModified(new Date());
			promotion.setEditor(account.getId());
			promotion.setSort(Integer.valueOf(sort));

			promotionService.updatePromotion(promotion);
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
	@RequestMapping(value = { "/promotion/delete" }, method = RequestMethod.POST)
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
			promotionService.deletePromotionByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}