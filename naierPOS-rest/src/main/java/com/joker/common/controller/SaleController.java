package com.joker.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.dto.SaleDto;
import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;
import com.joker.common.model.promotion.Promotion;
import com.joker.common.service.SalesConfigService;
import com.joker.common.service.ShoppingGuideService;
import com.joker.common.service.promotion.PromotionEngine;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

@RestController
public class SaleController extends AbstractController{

	@Autowired
	ShoppingGuideService shoppingGuideService;
	
	@Autowired
	SalesConfigService salesConfigService;

	/**
	 * 初始化销售单.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/sale/initSaleOrder" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getShoppingGuide(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			SalesConfig config = salesConfigService.getCurrentSalesConfig(account);
			SaleDto dto = new SaleDto();
			dto.setSaleDate(config.getSalesDate());
			dto.setAllDISC(account.getAllDISC());
			dto.setItemDISC(account.getItemDISC());
			rbody.setData(dto);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	@RequestMapping(value = { "/sale/getPromotions" }, method = RequestMethod.POST)
	@NotNull(value="token")
	@ResponseBody
	public ReturnBody getPromotions(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response){
		ReturnBody rbody = new ReturnBody();
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			JSONObject jsonObject = new JSONObject(paramsBody.getBody());
			SaleDto dto = JSONObject.parseObject(jsonObject.toJSONString(), SaleDto.class);
			PromotionEngine engine = new PromotionEngine(account.getClient().getId(), account.getStore().getId(),dto);
			List<Promotion> promotions=engine.getAvailablePromotion();
			if(CollectionUtils.isNotEmpty(promotions)){
				dto.setEffPromotions(promotions);
			}
			rbody.setData(dto);
			rbody.setStatus(ResponseState.SUCCESS);
		}
		return rbody;
	}
	
	
}
