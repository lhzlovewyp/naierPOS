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
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.Material;
import com.joker.common.model.RetailPrice;
import com.joker.common.model.Store;
import com.joker.common.model.Unit;
import com.joker.common.service.RetailPriceService;
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
public class RetailPriceController extends AbstractController {

	@Autowired
	RetailPriceService retailPriceService;

	/**
	 * 查询终端信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/retailPrice/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRetailPriceByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String storeCode = (String) params.get("storeCode");
		String storeName = (String) params.get("storeName");
		String mcode = (String) params.get("mcode");
		String mname = (String) params.get("mname");
		String effDate = (String) params.get("effDate");
		String expDate = (String) params.get("expDate");
		
		
		
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
			if(StringUtils.isNotBlank(mcode)){
				map.put("mcode",mcode);
			}
			if(StringUtils.isNotBlank(mname)){
				map.put("mname",mname);
			}
			if(StringUtils.isNotBlank(effDate)){
				map.put("effDate",effDate);
			}
			if(StringUtils.isNotBlank(expDate)){
				map.put("expDate",expDate);
			}
			Page<RetailPrice> page = retailPriceService
					.getRetailPricePageByCondition(map, pageNo, limit);
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
	@RequestMapping(value = { "/retailPrice/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRetailPriceByList(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			List<RetailPrice> list = retailPriceService
					.getRetailPricePageByCondition(map);
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
	@RequestMapping(value = { "/retailPrice/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getRetailPriceById(@RequestBody ParamsBody paramsBody,
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
			RetailPrice retailPrice = retailPriceService.getRetailPriceByID(id);
			rbody.setData(retailPrice);
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
	@RequestMapping(value = { "/retailPrice/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String storeId = (String) params.get("storeId");
		String materialId = (String) params.get("materialId");
		String unitId = (String) params.get("unitId");
		String price = null;
		if (params.get("price") != null) {
			price = String.valueOf(params.get("price"));
		}
		String effectiveDate = (String) params.get("effectiveDate");
		String expiryDate = (String) params.get("expiryDate");
		String clientId = (String) params.get("clientId");

		if (StringUtils.isBlank(storeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入门店！");
			return rbody;
		}
		if (StringUtils.isBlank(materialId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入物料！");
			return rbody;
		}
		if (StringUtils.isBlank(unitId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单位！");
			return rbody;
		}
		
		if (!NumberUtils.isNumber(price)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售价格！");
			return rbody;
		}
		if (StringUtils.isBlank(effectiveDate)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入生效日期！");
			return rbody;
		}
		
		

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			

			RetailPrice retailPrice = new RetailPrice();
			retailPrice.setId(UUID.randomUUID().toString());
			retailPrice.setPrice(new BigDecimal(price));
			retailPrice.setExpiryDate(DatetimeUtil.toDate(expiryDate,
					DatetimeUtil.DATE));
			retailPrice.setEffectiveDate(DatetimeUtil.toDate(effectiveDate,
					DatetimeUtil.DATE));
			retailPrice.setClient(account.getClient());
			retailPrice.setCreated(new Date());
			retailPrice.setCreator(account.getId());

			Store store = new Store();
			store.setId(storeId);
			retailPrice.setStore(store);

			Unit unit = new Unit();
			unit.setId(unitId);
			retailPrice.setUnit(unit);

			Material material = new Material();
			material.setId(materialId);
			retailPrice.setMaterial(material);

			retailPriceService.insertRetailPrice(retailPrice);
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
	@RequestMapping(value = { "/retailPrice/update" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody update(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String id = (String) params.get("id");
		String storeId = (String) params.get("storeId");
		String materialId = (String) params.get("materialId");
		String unitId = (String) params.get("unitId");
		String price = null;
		if (params.get("price") != null) {
			price = String.valueOf(params.get("price"));
		}

		String effectiveDate = (String) params.get("effectiveDate");
		String expiryDate = (String) params.get("expiryDate");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(storeId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入门店！");
			return rbody;
		}
		if (StringUtils.isBlank(materialId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入物料！");
			return rbody;
		}
		if (StringUtils.isBlank(unitId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单位！");
			return rbody;
		}
		if (!NumberUtils.isNumber(price)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售价格！");
			return rbody;
		}
		if (StringUtils.isBlank(effectiveDate)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入生效日期！");
			return rbody;
		}

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			RetailPrice retailPrice = new RetailPrice();
			retailPrice.setId(id);
			retailPrice.setPrice(new BigDecimal(price));
			retailPrice.setExpiryDate(DatetimeUtil.toDate(expiryDate,
					DatetimeUtil.DATE));
			retailPrice.setEffectiveDate(DatetimeUtil.toDate(effectiveDate,
					DatetimeUtil.DATE));
			retailPrice.setClient(account.getClient());
			retailPrice.setModified(new Date());
			retailPrice.setEditor(account.getId());

			Store store = new Store();
			store.setId(storeId);
			retailPrice.setStore(store);

			Unit unit = new Unit();
			unit.setId(unitId);
			retailPrice.setUnit(unit);

			Material material = new Material();
			material.setId(materialId);
			retailPrice.setMaterial(material);

			retailPriceService.updateRetailPrice(retailPrice);
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
	@RequestMapping(value = { "/retailPrice/delete" }, method = RequestMethod.POST)
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
			retailPriceService.deleteRetailPriceByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}