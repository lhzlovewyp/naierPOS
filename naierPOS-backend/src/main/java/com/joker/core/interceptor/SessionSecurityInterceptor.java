package com.joker.core.interceptor;

import com.joker.common.model.User;
import com.joker.common.service.UserSer;
import com.joker.core.constant.Context;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.IpUtil;
import com.joker.core.util.SystemUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证用户权限
 * Created by crell on 2016/1/17.
 */
public class SessionSecurityInterceptor implements HandlerInterceptor {

    public String[] allowUrls;//需要拦截的url，配置文件中注入

    @Autowired
    UserSer userSer;

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求url
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        //session中取当前用户session
        User user = (User)request.getSession().getAttribute(Context.USER);
        String ipAddr = IpUtil.getIpAddr(request);

        if (null != allowUrls && allowUrls.length >= 1) {

        }
        if(user!=null){
            user.setIp(ipAddr);
            SystemUtil.setUser(user);
        }else if(requestUrl.contains("/login")){
            user = new User();
            user.setUserName(request.getParameter("userName"));
            user.setIp(ipAddr);
            SystemUtil.setUser(user);
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
