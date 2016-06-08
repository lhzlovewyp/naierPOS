package com.joker.common.controller;

import com.joker.common.model.User;
import com.joker.common.service.UserSer;
import com.joker.core.annotation.NotNull;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.EncryptUtil;
import com.joker.core.util.IpUtil;
import com.joker.core.util.LogUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by crell on 2016/1/17.
 */

@RestController
public class LoginController extends AbstractController {

    @Autowired
    UserSer userSer;

    @Autowired
    LogUtil logUtil;

    @RequestMapping(value = {"/login"},method = RequestMethod.POST)
    @ResponseBody
    public ReturnBody login(@RequestBody ParamsBody paramsBody,HttpServletRequest request, HttpServletResponse response){
        ReturnBody rbody = new ReturnBody();
        Map<String, Object> body = paramsBody.getBody();

        User user = userSer.selectByName((String) body.get("userName"));
        if(user != null){
            if(!user.getPassword().equals(EncryptUtil.doEncrypt((String) body.get("password")))){
                rbody.setStatus(ResponseState.FAILED);
                rbody.setMsg("密码错误！");
            }else{
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setIp(IpUtil.getIpAddr(request));
                user.setLastLoginDate(new Date());
                user.setPassword("");
                rbody.setData(user);

                userSer.login(user);

                logUtil.action("用户登录", "用户名:" + user.getUserName());

                rbody.setStatus(ResponseState.SUCCESS);
                rbody.setMsg("Success");
            }
        }else{
            rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg("用户名不存在！");
        }
        return rbody;
    }

    @RequestMapping(value = {"/logoff"},method = RequestMethod.POST)
    @ResponseBody
    @NotNull(value = "", user = true)
    public ReturnBody logoff(@RequestBody ParamsBody paramsBody,HttpServletRequest request){
        ReturnBody rbody = new ReturnBody();

        userSer.logout(paramsBody.getToken());
        rbody.setStatus(ResponseState.SUCCESS);
        return rbody;
    }

    /**
     * 自动登录验证
     * @param request
     * @return
     */
    @RequestMapping(value = {"/autoLogin"},method = RequestMethod.POST)
    @ResponseBody
    @NotNull(value = "", user = true)
    public ReturnBody autoLogin(@RequestBody ParamsBody paramsBody,HttpServletRequest request) {
        ReturnBody rbody = new ReturnBody();

        //Map<String, String> cookieParam = ParameterUtil.cookiesToMap(request.getCookies());
        User user = null;
        String token = paramsBody.getToken();

        user = userSer.selectByToken(token);
        if (user != null) {
            user.setPassword("");
            rbody.setData(user);
            rbody.setStatus(ResponseState.SUCCESS);
        } else {
            rbody.setMsg("token失效");
            rbody.setStatus(ResponseState.FAILED);
        }

        return rbody;
    }
}
