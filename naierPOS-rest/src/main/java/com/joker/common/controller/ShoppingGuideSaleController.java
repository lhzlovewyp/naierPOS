package com.joker.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.model.Account;
import com.joker.common.model.ShoppingGuide;
import com.joker.common.service.ShoppingGuideService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
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
			ShoppingGuide shoppingGuide = shoppingGuideService.getShoppingGuideByCode(account.getClient().getId(), code);
			
			if(shoppingGuide!=null){
				rbody.setData(shoppingGuide);
				rbody.setStatus(ResponseState.SUCCESS);
			}
		}
		// 数据返回时永远返回true.
		return rbody;
	}

}
