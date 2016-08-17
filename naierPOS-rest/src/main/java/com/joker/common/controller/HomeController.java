package com.joker.common.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joker.common.dto.HomeDto;
import com.joker.common.model.Account;
import com.joker.common.model.SalesConfig;
import com.joker.common.model.SalesOrder;
import com.joker.common.service.SalesConfigService;
import com.joker.common.service.SalesOrderService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.DatetimeUtil;

@Controller
public class HomeController extends AbstractController{

	
	@Autowired
	SalesConfigService salesConfigService;
	
	@Autowired
	SalesOrderService salesOrderService;
	/**
     * 修改密码
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/home/index"},method = RequestMethod.POST)
    @NotNull(value = "token",user = true)
    @ResponseBody
    public ReturnBody index(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
        ReturnBody rbody = new ReturnBody();
        String token = paramsBody.getToken();
        if(StringUtils.isNotBlank(token)){
        	Object user = CacheFactory.getCache().get(token);
        	if(user!=null){
        		//获取当前用户所在门店的营业日期
        		Account account=(Account)user;
        		SalesConfig config = salesConfigService.getCurrentSalesConfig(account);
        		
        		HomeDto dto=new HomeDto();
        		dto.setSaleDate(config.getSalesDate());
        		
        		SalesOrder order=salesOrderService.getSalesInfo(account.getClient().getId(),account.getStore().getId(),config.getSalesDate());
        		
        		int orders=0;
        		BigDecimal salesAmount = new BigDecimal(0);
        		BigDecimal perTicketSales = new BigDecimal(0);
        		if(order!=null){
        			orders = order.getQuantity() == null ? 0:order.getQuantity().intValue();
        			salesAmount=order.getAmount() == null ? new BigDecimal(0) : order.getAmount();
        			if(orders!=0){
        				perTicketSales = salesAmount.divide(new BigDecimal(orders),2);
        			}
        		}
        		dto.setOrders(orders);
        		dto.setPerTicketSales(perTicketSales);
        		dto.setSalesAmount(salesAmount);
        		
        		rbody.setData(dto);
        		rbody.setStatus(ResponseState.SUCCESS);
        		return rbody;
        	}
        }
        rbody.setStatus(ResponseState.FAILED);
        return rbody;
    }
    /**
     * 修改密码
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/home/dayReport"},method = RequestMethod.POST)
    @NotNull(value = "token",user = true)
    @ResponseBody
    public ReturnBody dayReport(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
        ReturnBody rbody = new ReturnBody();
        String token = paramsBody.getToken();
        if(StringUtils.isNotBlank(token)){
        	Object user = CacheFactory.getCache().get(token);
        	if(user!=null){
        		//获取当前用户所在门店的营业日期
        		Account account=(Account)user;
        		SalesConfig config = salesConfigService.getCurrentSalesConfig(account);
        		
        		SalesConfig model=new SalesConfig();
        		model.setClient(account.getClient());
        		model.setStore(account.getStore());
        		model.setSalesDate(DatetimeUtil.addDays(config.getSalesDate(),1));
        		salesConfigService.insertSalesConfig(model);
         		rbody.setStatus(ResponseState.SUCCESS);
        		return rbody;
        	}
        }
        rbody.setStatus(ResponseState.FAILED);
        return rbody;
    }
}
