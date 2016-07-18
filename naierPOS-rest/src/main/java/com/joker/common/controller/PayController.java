package com.joker.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.dto.SaleDto;
import com.joker.common.model.Account;
import com.joker.common.model.ClientPayment;
import com.joker.common.service.ClientPaymentService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

/**
 * @author lvhaizhen
 *
 */
@RestController
public class PayController extends AbstractController {

	@Autowired
	ClientPaymentService clientPaymentService;
	
	
	@RequestMapping(value = { "/pay/initPay" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody initPay(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			List<ClientPayment> clientPayments=clientPaymentService.getClientPayments(account.getClient().getId());
			rbody.setData(clientPayments);
			rbody.setStatus(ResponseState.SUCCESS);
		}
		return rbody;
	}
	
	
	
}
