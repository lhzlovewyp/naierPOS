package com.joker.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.model.Account;
import com.joker.common.model.Material;
import com.joker.common.model.RetailPrice;
import com.joker.common.model.SalesConfig;
import com.joker.common.service.MaterialService;
import com.joker.common.service.RetailPriceService;
import com.joker.common.service.SalesConfigService;
import com.joker.common.service.ShoppingGuideService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

@RestController
public class MaterialController extends AbstractController{

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
	public ReturnBody getShoppingGuide(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		
		Map<String,Object> body=paramsBody.getBody();
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null && body.get("code")!=null) {
			Account account = (Account) user;
			String code=(String) body.get("code");
			Material material=materialService.getMaterialByCode(account.getClient().getId(), code);
			if(material!=null){
				SalesConfig config = salesConfigService.getCurrentSalesConfig(account);
				RetailPrice price=retailPriceService.getRetailPrice(config.getSalesDate(), account.getStore().getId(), material);
				if(price!=null){
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
	
	
}
