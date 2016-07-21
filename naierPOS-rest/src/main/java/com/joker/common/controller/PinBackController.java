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

import com.joker.common.model.Account;
import com.joker.common.model.SalesOrder;
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
	public ReturnBody getShoppingGuide(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
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
}
