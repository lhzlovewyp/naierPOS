package com.joker.common.controller;

import com.joker.common.model.User;
import com.joker.common.service.UserSer;
import com.joker.core.annotation.NotNull;
import com.joker.core.constant.MessageCode;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.EncryptUtil;
import com.joker.core.util.ValidUtil;

import org.apache.commons.beanutils.BeanUtils;
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
public class UserController extends AbstractController {

    @Autowired
    UserSer userSer;

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
        User validUser = userSer.selectByName(userName);
        if(validUser == null){
            rbody.setStatus(ResponseState.SUCCESS);
        }else{
            rbody.setStatus(ResponseState.FAILED);
        }
        return rbody;
    }

    /**
     * 注册
     * @param paramsBody
     * @param request
     * @return
     */
    @RequestMapping(value = {"/user"},method = RequestMethod.POST)
    @ResponseBody
    public ReturnBody newUser(@RequestBody ParamsBody paramsBody,HttpServletRequest request) throws Exception {
        ReturnBody rbody = new ReturnBody();
        Map<String, Object> body = paramsBody.getBody();
        String kaptcha = (String) body.get("kaptcha");

        User user = new User();
        BeanUtils.populate(user,body);

        if(!ValidUtil.validCaptcha(request, kaptcha)){
            rbody.setMsg(MessageCode.CAPTCHA_ERROR);
            rbody.setStatus(ResponseState.FAILED);
            return rbody;
        }

        User validUser = userSer.selectByName(user.getUserName());
        if(validUser != null){
            rbody.setMsg("用户已存在");
            rbody.setStatus(ResponseState.FAILED);
        }else if(userSer.selectByNickName(user.getNickName()) != null){
            rbody.setMsg("用户别名已存在");
            rbody.setStatus(ResponseState.FAILED);
        }else {
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setPassword(EncryptUtil.doEncrypt(user.getPassword()));
            user.setCreateDate(new Date());
            userSer.addUser(user);
            rbody.setStatus(ResponseState.SUCCESS);
            rbody.setData(user);
            rbody.setMsg("注册成功");
        }

        return rbody;
    }

    /**
     * 修改用户资料
     * @param paramsBody
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/user"},method = RequestMethod.PUT)
    @ResponseBody
    public ReturnBody updateUser(@RequestBody ParamsBody paramsBody,HttpServletRequest request) throws Exception {
        ReturnBody rbody = new ReturnBody();
        Map params = paramsBody.getBody();
        User user = new User();
        BeanUtils.populate(user,params);

        userSer.update(user);
        rbody.setStatus(ResponseState.SUCCESS);
        rbody.setMsg("修改成功");
        return rbody;
    }

    /**
     * 修改用户资料
     * @param paramsBody
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/user"},method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnBody deleteUser(@RequestBody ParamsBody paramsBody,HttpServletRequest request) throws Exception {
        ReturnBody rbody = new ReturnBody();
        Map params = paramsBody.getBody();
        String userId = (String) params.get("userId");

        userSer.del(userId);
        rbody.setStatus(ResponseState.SUCCESS);
        rbody.setMsg("删除成功");
        return rbody;
    }

}
