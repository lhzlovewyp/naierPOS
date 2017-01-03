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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.model.Account;
import com.joker.common.model.Brand;
import com.joker.common.model.Client;
import com.joker.common.model.Material;
import com.joker.common.model.MaterialCategory;
import com.joker.common.model.RetailPrice;
import com.joker.common.model.SalesConfig;
import com.joker.common.model.Unit;
import com.joker.common.service.MaterialService;
import com.joker.common.service.RetailPriceService;
import com.joker.common.service.SalesConfigService;
import com.joker.common.service.ShoppingGuideService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

@RestController
public class MaterialController extends AbstractController {

	@Autowired
	ShoppingGuideService shoppingGuideService;

	@Autowired
	SalesConfigService salesConfigService;

	@Autowired
	MaterialService materialService;

	@Autowired
	RetailPriceService retailPriceService;

	/**
	 * 获取商品信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/m/getMaterialByCode" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMaterialByCode(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();

		Map<String, Object> body = paramsBody.getBody();
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null && body.get("code") != null) {
			Account account = (Account) user;
			String code = (String) body.get("code");
			Material material = materialService.getDynMaterial(account
					.getClient().getId(), code);
			if (material != null) {
				SalesConfig config = salesConfigService
						.getCurrentSalesConfig(account);
				RetailPrice price = retailPriceService.getRetailPrice(
						config.getSalesDate(), account.getStore().getId(),
						material);
				if (price != null) {
					material.setRetailPrice(price.getPrice());
				}

			}
			rbody.setData(material);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 查询品牌信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/material/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMaterialByPage(@RequestBody ParamsBody paramsBody,
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
			
			Page<Material> page = materialService.getMaterialPageByCondition(
					map, pageNo, limit);
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
	 * 查询品牌信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/material/queryByList" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMaterialByList(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer start = (Integer) params.get("start");
		Integer limit = (Integer) params.get("limit");
		start = (start == null ? 0 : start);
		limit = (limit == null ? 100 : limit);
		
		String likeOne = (String) params.get("likeOne");
		
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", clientId);
			if(params.get("start") != null && params.get("limit") != null){
				map.put("start", start);
				map.put("limit", limit);
			}
			if(StringUtils.isNotBlank(likeOne)){
				map.put("likeOne", likeOne);
			}
			
			List<Material> list = materialService
					.getMaterialPageByCondition(map);
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
	 * 查询品牌信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/material/queryById" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMaterialById(@RequestBody ParamsBody paramsBody,
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
			Material material = materialService.getMaterialByID(id);
			rbody.setData(material);
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
	 * 添加品牌信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/material/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String code = (String) params.get("code");
		String name = (String) params.get("name");
		String abbr = (String) params.get("abbr");
		String categoryId = (String) params.get("categoryId");
		String brandId = (String) params.get("brandId");
		String basicUnitId = (String) params.get("basicUnitId");
		String salesUnitId = (String) params.get("salesUnitId");
		String displayPhoto = (String) params.get("displayPhoto");
		String salesConversion = null;
		if (params.get("salesConversion") != null) {
			salesConversion = String.valueOf(params.get("salesConversion"));
		}
		String retailPrice = null;
		if (params.get("retailPrice") != null) {
			retailPrice = String.valueOf(params.get("retailPrice"));
		}
		String barCode = (String) params.get("barCode");
		String property = (String) params.get("property");
		String clientId = (String) params.get("clientId");

		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入物料编码！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入物料名称！");
			return rbody;
		}
		if (StringUtils.isBlank(abbr)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入简称！");
			return rbody;
		}
		if (StringUtils.isBlank(basicUnitId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入基本单位！");
			return rbody;
		}
		if (StringUtils.isBlank(salesUnitId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单位！");
			return rbody;
		}
		if (!NumberUtils.isNumber(salesConversion)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请正确输入与基本单位换算率！");
			return rbody;
		}
		if (!NumberUtils.isNumber(retailPrice)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请正确输入标准零售价！");
			return rbody;
		}
		

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			

			Unit basicUnit = new Unit();
			basicUnit.setId(basicUnitId);

			Unit salesUnit = new Unit();
			salesUnit.setId(salesUnitId);

			Material material = new Material();
			material.setId(UUID.randomUUID().toString());
			material.setCode(code);
			material.setName(name);
			material.setAbbr(abbr);
			material.setBasicUnit(basicUnit);
			material.setSalesUnit(salesUnit);
			material.setSalesConversion(new BigDecimal(salesConversion));
			material.setRetailPrice(new BigDecimal(retailPrice));
			material.setBarCode(barCode);
			material.setProperty(property);
			material.setClient(account.getClient());
			material.setCreated(new Date());
			material.setCreator(account.getId());
			material.setDisplaoyPhoto(displayPhoto);
			if (StringUtils.isNotBlank(categoryId)) {
				MaterialCategory materialCategory = new MaterialCategory();
				materialCategory.setId(categoryId);
				material.setCategory(materialCategory);
			}
			if (StringUtils.isNotBlank(brandId)) {
				Brand brand = new Brand();
				brand.setId(brandId);
				material.setBrand(brand);
			}

			materialService.insertMaterial(material);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 更新品牌信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/material/update" }, method = RequestMethod.POST)
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
		String abbr = (String) params.get("abbr");
		String categoryId = (String) params.get("categoryId");
		String brandId = (String) params.get("brandId");
		String basicUnitId = (String) params.get("basicUnitId");
		String salesUnitId = (String) params.get("salesUnitId");
		String displayPhoto=(String)params.get("displayPhoto");
		String salesConversion = null;
		if (params.get("salesConversion") != null) {
			salesConversion = String.valueOf(params.get("salesConversion"));
		}
		String retailPrice = null;
		if (params.get("retailPrice") != null) {
			retailPrice = String.valueOf(params.get("retailPrice"));
		}
		String barCode = (String) params.get("barcode");
		String property = (String) params.get("property");
		String status = (String) params.get("status");

		if (StringUtils.isBlank(id)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("记录唯一信息缺失，请刷新页面！");
			return rbody;
		}
		if (StringUtils.isBlank(code)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入物料编码！");
			return rbody;
		}
		if (StringUtils.isBlank(name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入物料名称！");
			return rbody;
		}
		if (StringUtils.isBlank(abbr)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入简称！");
			return rbody;
		}
		if (StringUtils.isBlank(basicUnitId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入基本单位！");
			return rbody;
		}
		if (StringUtils.isBlank(salesUnitId)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入销售单位！");
			return rbody;
		}
		if (!NumberUtils.isNumber(salesConversion)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请正确输入与基本单位换算率！");
			return rbody;
		}
		if (!NumberUtils.isNumber(retailPrice)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请正确输入标准零售价！");
			return rbody;
		}
		if (StringUtils.isBlank(property)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入是否需要维护属性！");
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

			Unit basicUnit = new Unit();
			basicUnit.setId(basicUnitId);

			Unit salesUnit = new Unit();
			salesUnit.setId(salesUnitId);

			Material material = new Material();
			material.setId(id);
			material.setCode(code);
			material.setName(name);
			material.setAbbr(abbr);
			material.setBasicUnit(basicUnit);
			material.setSalesUnit(salesUnit);
			material.setSalesConversion(new BigDecimal(salesConversion));
			material.setRetailPrice(new BigDecimal(retailPrice));
			material.setBarCode(barCode);
			material.setProperty(property);
			material.setClient(account.getClient());
			material.setStatus(status);
			material.setModified(new Date());
			material.setEditor(account.getId());
			material.setDisplaoyPhoto(displayPhoto);

			if (StringUtils.isNotBlank(categoryId)) {
				MaterialCategory materialCategory = new MaterialCategory();
				materialCategory.setId(categoryId);
				material.setCategory(materialCategory);
			}
			if (StringUtils.isNotBlank(brandId)) {
				Brand brand = new Brand();
				brand.setId(brandId);
				material.setBrand(brand);
			}

			materialService.updateMaterial(material);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 删除品牌信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/material/delete" }, method = RequestMethod.POST)
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
			materialService.deleteMaterialByID(id);
			rbody.setStatus(ResponseState.SUCCESS);
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}