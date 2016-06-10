/**
 * 
 */
package com.joker.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joker.common.model.SaleUser;
import com.joker.common.service.SaleUserService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.Context;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

/**
 * @author lvhaizhen
 *
 */
@Controller
public class SaleUserController extends AbstractController{

	@Autowired
	SaleUserService saleUserService;
	
	 /**
     * 校验用户名
     * @param paramsBody
     * @return
     */
    @RequestMapping(value = {"/user/validUserName"},method = RequestMethod.POST)
    @ResponseBody
    @NotNull(value = "userName")
    public ReturnBody ValidUserName(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
        ReturnBody rbody = new ReturnBody();
        Map params = paramsBody.getBody();
        String userName = (String) params.get("userName");
        SaleUser validUser = saleUserService.getUserByName(userName);
        if(validUser == null){
            rbody.setStatus(ResponseState.SUCCESS);
        }else{
            rbody.setStatus(ResponseState.FAILED);
        }
        return rbody;
    }
	
}
