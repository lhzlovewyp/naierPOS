package com.joker.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.joker.core.constant.Context;
import com.joker.core.constant.ResponseState;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.CookieUtil;
import com.joker.core.util.RestUtil;

public class LoginFilter implements Filter{
	
	/**
	 * 不需要登陆就可以访问的链接地址，多个链接以逗号隔开
	 */
	private String excludeUrl;
	
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response =(HttpServletResponse)resp;
		
		if(StringUtils.isNotBlank(excludeUrl)){
			String[] arr=excludeUrl.split(",");
			String path = request.getRequestURI();
			
			boolean needLogin=true;
			
			for(String str:arr){
				if(path.indexOf(str)>=0){
					needLogin=false;
					break;
				}
			}
			
			if(needLogin){
				//校验token的合法性.
				String token = CookieUtil.getCookie(request, response, Context.TOKEN);
				ReturnBody body = null;
				if(StringUtils.isNotBlank(token)){
					ParamsBody params=new ParamsBody();
					params.setToken(token);
					body=RestUtil.post("/login/validToken", params);
				}
				if(body == null || body.getStatus() != ResponseState.SUCCESS){
					response.sendRedirect("/front/login.html");
				}
			}
		}
		chain.doFilter(request, response);

	}

	public void init(FilterConfig config) throws ServletException {
		excludeUrl = config.getInitParameter("excludeUrl");
	}

	
	

}
