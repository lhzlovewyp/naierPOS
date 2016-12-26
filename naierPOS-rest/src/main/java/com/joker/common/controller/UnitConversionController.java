/**
 * 
 */
package com.joker.common.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joker.common.model.Account;
import com.joker.common.model.Unit;
import com.joker.common.model.UnitConversion;
import com.joker.common.service.UnitConversionService;
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
public class UnitConversionController extends AbstractController {

	@Autowired
	UnitConversionService unitConversionService;

	/**
	 * 查询单位换算信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/unitConversion/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getUnitConversionByPage(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String uniA = (String) params.get("uniA");
		String uniB = (String) params.get("uniB");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			if (StringUtils.isNotBlank(uniA)) {
				map.put("uniA", uniA);
			}
			if (StringUtils.isNotBlank(uniB)) {
				map.put("uniB", uniB);
			}
			Page<UnitConversion> page = unitConversionService
					.getUnitConversionPageByCondition(map, pageNo, limit);
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
	 * 查询单位换算信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/unitConversion/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getUnitConversionByList(
			@RequestBody ParamsBody paramsBody, HttpServletRequest request,
			HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String uniA = (String) params.get("uniA");
		String uniB = (String) params.get("uniB");
		String unitAId = (String) params.get("unitAId");
		String unitBId = (String) params.get("unitBId");
		String unitCanChange = (String) params.get("unitCanChange");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			if (StringUtils.isNotBlank(uniA)) {
				map.put("uniA", uniA);
			}
			if (StringUtils.isNotBlank(uniB)) {
				map.put("uniB", uniB);
			}
			if (StringUtils.isNotBlank(unitAId)) {
				map.put("unitAId", unitAId);
			}
			if (StringUtils.isNotBlank(unitBId)) {
				map.put("unitBId", unitBId);
			}
			List<UnitConversion> list = unitConversionService
					.getUnitConversionPageByCondition(map);
			if (CollectionUtils.isEmpty(list)
					&& StringUtils.isNotBlank(unitCanChange)) {
				if (StringUtils.isNotBlank(unitAId)) {
					map.put("unitBId", unitAId);
				}
				if (StringUtils.isNotBlank(unitBId)) {
					map.put("unitAId", unitBId);
				}
				list = unitConversionService
						.getUnitConversionPageByCondition(map);
			}
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
	 * 查询单位换算信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/unitConversion/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getUnitConversionById(@RequestBody ParamsBody paramsBody,
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
			UnitConversion unitConversion = unitConversionService
					.getUnitConversionByID(id);
			rbody.setData(unitConversion);
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
	 * 添加单位换算信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/unitConversion/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String unitAId = (String) params.get("unitAId");
		String qtyA = null;
		if (params.get("qtyA") != null) {
			qtyA = String.valueOf(params.get("qtyA"));
		}
		String unitBId = (String) params.get("unitBId");
		String qtyB = null;
		if (params.get("qtyB") != null) {
			qtyB = String.valueOf(params.get("qtyB"));
		}
		String remark = (String) params.get("remark");

		if (StringUtils.isBlank(unitAId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入甲单位！");
			return rbody;
		}
		if (!StringUtils.isNumeric(qtyA)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入甲单位数量！");
			return rbody;
		}
		if (StringUtils.isBlank(unitBId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入乙单位！");
			return rbody;
		}
		if (!StringUtils.isNumeric(qtyB)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入乙单位数量！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			Unit unitA = new Unit();
			unitA.setId(unitAId);

			Unit unitB = new Unit();
			unitB.setId(unitBId);

			UnitConversion addUnitConversion = new UnitConversion();
			addUnitConversion.setId(UUID.randomUUID().toString());
			addUnitConversion.setUnitA(unitA);
			addUnitConversion.setQtyA(new BigDecimal(qtyA));
			addUnitConversion.setUnitB(unitB);
			addUnitConversion.setQtyB(new BigDecimal(qtyB));
			addUnitConversion.setRemark(remark);
			addUnitConversion.setClient(account.getClient());
			addUnitConversion.setCreated(new Date());
			addUnitConversion.setCreator(account.getId());

			unitConversionService.insertUnitConversion(addUnitConversion);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新单位换算信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/unitConversion/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String unitAId = (String) params.get("unitAId");
		String qtyA = null;
		if (params.get("qtyA") != null) {
			qtyA = String.valueOf(params.get("qtyA"));
		}
		String unitBId = (String) params.get("unitBId");
		String qtyB = null;
		if (params.get("qtyB") != null) {
			qtyB = String.valueOf(params.get("qtyB"));
		}
		String remark = (String) params.get("remark");
		String status = (String) params.get("status");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(unitAId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入甲单位！");
			return rbody;
		}
		if (!StringUtils.isNumeric(qtyA)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入甲单位数量！");
			return rbody;
		}
		if (StringUtils.isBlank(unitBId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入乙单位！");
			return rbody;
		}
		if (!StringUtils.isNumeric(qtyB)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入乙单位数量！");
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

			Unit unitA = new Unit();
			unitA.setId(unitAId);

			Unit unitB = new Unit();
			unitB.setId(unitBId);

			UnitConversion updateUnitConversion = new UnitConversion();
			updateUnitConversion.setId(id);
			updateUnitConversion.setUnitA(unitA);
			updateUnitConversion.setQtyA(new BigDecimal(qtyA));
			updateUnitConversion.setUnitB(unitA);
			updateUnitConversion.setQtyB(new BigDecimal(qtyB));
			updateUnitConversion.setRemark(remark);
			updateUnitConversion.setStatus(status);
			updateUnitConversion.setModified(new Date());
			updateUnitConversion.setEditor(account.getId());

			unitConversionService.updateUnitConversion(updateUnitConversion);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除单位换算信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/unitConversion/delete" }, method = RequestMethod.POST)
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
			unitConversionService.deleteUnitConversionByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}