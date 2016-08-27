package com.joker.common.controller;

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

import com.google.code.kaptcha.Constants;
import com.joker.common.model.Account;
import com.joker.common.model.Client;
import com.joker.common.model.Store;
import com.joker.common.service.AccountService;
import com.joker.common.service.ClientService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.Context;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.EncryptUtil;
import com.joker.core.util.LogUtil;

@RestController
public class LoginController extends AbstractController{

	@Autowired
	AccountService accountService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	LogUtil logUtil;
	
	/**
	 * 登陆验证.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/login"},method = RequestMethod.POST)
    @ResponseBody
    public ReturnBody login(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
		
		//进行参数校验
        ReturnBody rbody = validateParams(paramsBody, request, response);
        
        if(rbody!=null){
        	return rbody;
        }
        rbody = new ReturnBody();
        Map<String, Object> body = paramsBody.getBody();
        
        //验证码校验
        Object verifyCode=body.get("verifyCode");
        Object sessionVerifyCode=request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
    	if(!verifyCode.equals(sessionVerifyCode)){
    		rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg("验证码校验错误,请重新输入！");
            return rbody;
    	}
        String userName = (String) body.get("userName");
        String clientCode = (String) body.get("clientCode");
        String password = (String) body.get("password");
        
        //获取商户信息
        Client client= clientService.getClientByCode(clientCode);
        if(client == null){
        	rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg("商户编号不存在,请重新输入！");
            return rbody;
        }
        
        Account user = accountService.login(client.getId(), userName);
        if(user != null){
            if(!user.getPassword().equals(EncryptUtil.doEncrypt(password))){
                rbody.setStatus(ResponseState.FAILED);
                rbody.setMsg("密码错误！");
            }else{
                user.setPassword("");
                rbody.setData(user);
                rbody.setStatus(ResponseState.SUCCESS);
                rbody.setMsg("Success");
            }
        }else{
            rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg("用户名不存在！");
        }
        return rbody;
    }
	
	@RequestMapping(value = {"/login/chooseStore"},method = RequestMethod.POST)
    @ResponseBody
    @NotNull(value = "token")
    public ReturnBody chooseStore(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
        ReturnBody rbody = new ReturnBody();
        String token = paramsBody.getToken();
        Map<String,String> map=(Map)paramsBody.getBody().get("store");
        
        //Store store=
        
        if(StringUtils.isNotBlank(token)){
        	Object user = CacheFactory.getCache().get(token);
        	if(user!=null){
        		Account account=(Account)user;
        		Store store=new Store();
        		store.setId(map.get("id"));
        		store.setName(map.get("name"));
        		store.setCode(map.get("code"));
        		
        		account.setStore(store);
        		CacheFactory.getCache().put(account.getToken(), account,Context.DEFALUT_LOGIN_TIME);
        		rbody.setData(user);
        		rbody.setStatus(ResponseState.SUCCESS);
        		return rbody;
        	}
        }
        rbody.setStatus(ResponseState.INVALID_TOKEN);
        return rbody;
    }
	
	
	/**
	 * 判断token是否有效以及返回当前用户信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/login/validToken"},method = RequestMethod.POST)
    @ResponseBody
    @NotNull(value = "token")
    public ReturnBody ValidToke(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
        ReturnBody rbody = new ReturnBody();
        String token = paramsBody.getToken();
        if(StringUtils.isNotBlank(token)){
        	Object user = CacheFactory.getCache().get(token);
        	if(user!=null){
        		rbody.setData(user);
        		rbody.setStatus(ResponseState.SUCCESS);
        		return rbody;
        	}
        }
        rbody.setStatus(ResponseState.INVALID_TOKEN);
        return rbody;
    }
	/**
	 * 退出登陆操作.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"/login/logout"},method = RequestMethod.POST)
	@NotNull(value = "token")
    @ResponseBody
	public ReturnBody logout(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
		String token = paramsBody.getToken();
		if(StringUtils.isNotBlank(token)){
			//从缓存中删除用户信息.
			CacheFactory.getCache().remove(token);
		}
		
		//数据返回时永远返回true.
		ReturnBody rbody = new ReturnBody();
		rbody.setStatus(ResponseState.SUCCESS);
		return rbody;
	}
	
	private ReturnBody validateParams(ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
		 Map<String, Object> body = paramsBody.getBody();
		 Object userName = (String) body.get("userName");
		 Object clientCode = (String) body.get("clientCode");
	     Object password=(String) body.get("password");
	     Object verifyCode=body.get("verifyCode");
	     
	     if(userName==null){
	    	 ReturnBody rbody = new ReturnBody();
	    	 rbody.setStatus(ResponseState.FAILED);
	         rbody.setMsg("请输入用户名！");
	         return rbody;
	     }
	     
	     if(clientCode==null){
	    	 ReturnBody rbody = new ReturnBody();
	    	 rbody.setStatus(ResponseState.FAILED);
	         rbody.setMsg("请输入商户编号！");
	         return rbody;
	     }
	     
	     if(password==null){
	    	 ReturnBody rbody = new ReturnBody();
	    	 rbody.setStatus(ResponseState.FAILED);
	         rbody.setMsg("请输入密码！");
	         return rbody;
	     }
	     
	     if(verifyCode==null){
	    	 ReturnBody rbody = new ReturnBody();
	    	 rbody.setStatus(ResponseState.FAILED);
	         rbody.setMsg("请输入验证码！");
	         return rbody;
	     }
	     
	     return null;
	     
	}
	
}
