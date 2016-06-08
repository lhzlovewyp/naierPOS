package com.joker.common.controller;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.joker.common.model.SaleUser;
import com.joker.common.service.SaleUserService;
import com.joker.core.constant.Context;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.EncryptUtil;
import com.joker.core.util.IpUtil;
import com.joker.core.util.LogUtil;

@RestController
public class LoginController extends AbstractController{

	@Autowired
	SaleUserService saleUserService;
	
	@Autowired
	LogUtil logUtil;
	
	@RequestMapping(value = {"/login"},method = RequestMethod.POST)
    @ResponseBody
    public ReturnBody login(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
        ReturnBody rbody = new ReturnBody();
        Map<String, Object> body = paramsBody.getBody();
        
        //验证码校验
        Object verifyCode=body.get("verifyCode");
        Object sessionVerifyCode=request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(verifyCode == null || sessionVerifyCode ==null){
        	rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg("请输入验证码！");
            return rbody;
        }else{
        	if(!verifyCode.equals(sessionVerifyCode)){
        		rbody.setStatus(ResponseState.FAILED);
                rbody.setMsg("验证码校验错误,请重新输入！");
                return rbody;
        	}
        }
        
        SaleUser user = saleUserService.getUserByName((String) body.get("userName"));
        if(user != null){
            //if(!user.getPassword().equals(EncryptUtil.doEncrypt((String) body.get("password")))){
        	//Todo:待完善之后需要加密.
            if(!user.getPassword().equals((String) body.get("password"))){
                rbody.setStatus(ResponseState.FAILED);
                rbody.setMsg("密码错误！");
            }else{
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setIp(IpUtil.getIpAddr(request));
                user.setLastLoginDate(new Date());
                user.setPassword("");
                rbody.setData(user);

                saleUserService.login(user);
                /*request.setAttribute(Context.USER, user);*/
                //logUtil.action("用户登录", "用户名:" + user.getUserName());
                
                rbody.setStatus(ResponseState.SUCCESS);
                rbody.setMsg("Success");
            }
        }else{
            rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg("用户名不存在！");
        }
        return rbody;
    }
}
