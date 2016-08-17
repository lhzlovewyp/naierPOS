/**
 * 
 */
package com.joker.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.Constant.Constants;
import com.joker.common.model.Account;
import com.joker.common.model.SalesOrder;
import com.joker.common.model.SalesOrderPay;
import com.joker.common.service.SalesOrderService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

/**
 * 
 * 销退请求.
 * 
 * @author lvhaizhen
 *
 */
@RestController
public class PinBackController extends AbstractController{

	@Autowired
	SalesOrderService salesOrderService;
	
	@RequestMapping(value = { "/pinBack/getSalesOrder" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getSalesOrder(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String id=(String)params.get("id");
		String startDate=(String)params.get("startDate");
		String endDate=(String)params.get("endDate");
		String mobile=(String)params.get("tel");
		pageNo = (pageNo == null ? 1 : pageNo);
		limit = (limit == null ? 10 : limit);
		
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("clientId", account.getClient().getId());
			map.put("storeId", account.getStore().getId());
			if(StringUtils.isNotBlank(startDate)){
				map.put("startDate", startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				map.put("endDate", endDate);
			}
			if(StringUtils.isNotBlank(id)){
				map.put("id", id);
			}
			if(StringUtils.isNotBlank(mobile)){
				map.put("memberMobile", mobile);
			}
			
			Page<SalesOrder> page = salesOrderService.getSalesOrderPageByClient(map, pageNo, limit);
			rbody.setData(page);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		}else{
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	@RequestMapping(value = { "/pinBack/getSalesOrderDetails" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getSalesOrderDetails(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		
		Map params = paramsBody.getBody();
		String id=(String)params.get("id");
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			SalesOrder order = salesOrderService.getSalesOrderById(account.getClient().getId(), account.getStore().getId(), id);
			if(order == null){
				rbody.setStatus(ResponseState.FAILED);
				rbody.setMsg("找不到记录.");
			}else{
				//遍历支付信息，只有现金支付才能单项退款.
				String flag="1";
				for(SalesOrderPay pay:order.getSalesOrderPay()){
					if(!pay.getPayment().getCode().equals(Constants.PAYMENT_CASH)){
						flag="0";
						break;
					}
				}
				order.setCashFlag(flag);
				rbody.setData(order);
				rbody.setStatus(ResponseState.SUCCESS);
			}
		}else{
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	@RequestMapping(value = { "/pinBack/refund" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody refund(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		
		Map params = paramsBody.getBody();
		String salesOrderId=(String)params.get("salesOrderId");
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		
		if(StringUtils.isEmpty(salesOrderId)){
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("参数错误.");
			return rbody;
		}
		
		if (user != null) {
			Account account = (Account) user;
			String result;
			try {
				result = salesOrderService.addRefund(account,account.getClient().getId(), account.getStore().getId(), salesOrderId);
				if(StringUtils.isEmpty(result)){
					rbody.setStatus(ResponseState.SUCCESS);
				}else{
					rbody.setStatus(ResponseState.FAILED);
					rbody.setMsg("找不到记录.");
				}
			} catch (Exception e) {
				rbody.setStatus(ResponseState.FAILED);
				rbody.setMsg(" 退款失败.");
			}
			
		}else{
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
}
