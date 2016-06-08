/**
 * 
 */
package com.joker.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joker.core.cookie.CookieWrapper;



/**
 * cookie操作工具类
 * 
 * @author lvhaizhen
 *
 */
public class CookieUtil {
	
	public static String getCookie(HttpServletRequest request,HttpServletResponse response,String key){
		try{
			CookieWrapper cookieWrapper = new CookieWrapper(request, response);
			return cookieWrapper.getCookie(key).getValue();
		}catch(Exception e){
			
		}
		return null;
	}
}
