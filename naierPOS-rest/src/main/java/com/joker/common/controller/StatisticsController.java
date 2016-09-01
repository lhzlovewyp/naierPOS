package com.joker.common.controller;

import java.util.HashMap;
import java.util.List;
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

import com.joker.common.model.Account;
import com.joker.common.model.SalesOrder;
import com.joker.common.model.SalesOrderDetails;
import com.joker.common.model.SalesOrderDiscount;
import com.joker.common.model.SalesOrderPay;
import com.joker.common.model.Store;
import com.joker.common.service.SalesOrderService;
import com.joker.common.service.StoreService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;


import io.netty.util.internal.StringUtil;




@RestController
public class StatisticsController extends AbstractController{
	
	@Autowired
	SalesOrderService salesOrderService;
	@Autowired
	StoreService storeService;
	@RequestMapping(value = {"/statistics/getsalesDetail"},method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
     public ReturnBody getsalesDetail(@RequestBody ParamsBody paramsBody,
    		 HttpServletRequest request,HttpServletResponse response) {
		
		ReturnBody rbody = new ReturnBody();
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String matCode=(String)params.get("matCode");
		String startDate=(String)params.get("startDate");
		String endDate=(String)params.get("endDate"); 
		pageNo = (pageNo == null ? 1 : pageNo);
		limit = (limit == null ? 10 : limit);
		
		String token = paramsBody.getToken();
		
		Object user = CacheFactory.getCache().get(token);
		
		if(user != null){
			Account account = (Account) user;
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("clientId", account.getClient().getId());
			map.put("storeId", account.getStore().getId());
			if(StringUtils.isNotBlank(startDate)){
				map.put("startDate", startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				map.put("endDate", endDate);
			}
			if(StringUtils.isNotBlank(matCode)){
				map.put("matCode", matCode);
			}
			
			Page<SalesOrder> page = salesOrderService.getSalesOrderPageByDate(map, pageNo, limit);
			rbody.setData(page);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		}else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		
		return rbody;
	}
	
	
	@RequestMapping(value = {"/statistics/getsalesSummary"},method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
     public ReturnBody getsalesSummary(@RequestBody ParamsBody paramsBody,
    		 HttpServletRequest request,HttpServletResponse response) {
		
		ReturnBody rbody = new ReturnBody();
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String matCode=(String)params.get("matCode");
		String startDate=(String)params.get("startDate");
		String endDate=(String)params.get("endDate"); 
		pageNo = (pageNo == null ? 1 : pageNo);
		limit = (limit == null ? 10 : limit);
		
		String token = paramsBody.getToken();
		
		Object user = CacheFactory.getCache().get(token);
		
		if(user != null){
			Account account = (Account) user;
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("clientId", account.getClient().getId());
			map.put("storeId", account.getStore().getId());
			if(StringUtils.isNotBlank(startDate)){
				map.put("startDate", startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				map.put("endDate", endDate);
			}
			if(StringUtils.isNotBlank(matCode)){
				map.put("matCode", matCode);
			}
			
			Page<SalesOrderDetails> page = salesOrderService.getSalesSummaryPageByCondition(map, pageNo, limit);
			rbody.setData(page);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		}else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		
		return rbody;
	}
	
	@RequestMapping(value = {"/statistics/getPaymentSummary"},method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
     public ReturnBody getPaymentSummary(@RequestBody ParamsBody paramsBody,
    		 HttpServletRequest request,HttpServletResponse response) {
		
		ReturnBody rbody = new ReturnBody();
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String id=(String)params.get("id");
		String startDate=(String)params.get("startDate");
		String endDate=(String)params.get("endDate"); 
		pageNo = (pageNo == null ? 1 : pageNo);
		limit = (limit == null ? 10 : limit);
		
		String token = paramsBody.getToken();
		
		Object user = CacheFactory.getCache().get(token);
		
		if(user != null){
			Account account = (Account) user;
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("clientId", account.getClient().getId());
			map.put("storeId", account.getStore().getId());
			if(StringUtils.isNotBlank(startDate)){
				map.put("startDate", startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				map.put("endDate", endDate);
			}
			
			List<SalesOrderPay> payments = salesOrderService.getSalesOrderPayByCondition(map);
			rbody.setData(payments);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		}else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		
		return rbody;
	}
	
	@RequestMapping(value = {"/statistics/printOrder"},method = RequestMethod.POST)
	public ReturnBody printOrder(@RequestBody ParamsBody paramsBody){
		ReturnBody rbody = new ReturnBody();
		Map params = paramsBody.getBody();
		Map<String, String> map = new HashMap<String,String>();
		
		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		String orderId = (String)params.get("orderId");
		if(user != null){
			Account account = (Account) user;
			String clientId = account.getClient().getId();
			String storeId = account.getStore().getId();
			
			map.put("storeId", storeId);
			
			SalesOrder salesOrder = salesOrderService.getSalesOrderById(clientId, storeId, orderId);
			
			Store store = storeService.getStoreById(storeId);
			salesOrder.setStore(store);
			
			rbody.setData(salesOrder);
			
		}else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		
		return rbody;
	}
}
