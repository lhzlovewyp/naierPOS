package com.joker.core.cookie;

import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieWrapper {
	private HttpServletRequest request;

	private HttpServletResponse response;

	public CookieWrapper(HttpServletRequest req, HttpServletResponse res) {
		request = req;
		response = res;
	}

	/**
	 * 获取某个cookie值
	 *@author: weip
	 *@time: 2007-8-30 上午02:22:31
	 *@param name
	 *@return
	 */
	public String getCookieValue(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null){
			return null;
		}

		Cookie cookie = null;
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
//			System.out.println("cookieName" + i + ":: " + cookie.getName());
			if (cookie.getName().equalsIgnoreCase(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 *设置cookie值
	 *@author: weip
	 *@time: 2007-8-30 上午02:23:00
	 *@param name
	 *@param value
	 *@param domain
	 *@param expire
	 */
	public void setCookie(String name, String value, String domain, int expire) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath("/");
		if (expire >= 0) {
			cookie.setMaxAge(expire);
		}
		response.addCookie(cookie);
	}

	/**
	 * 设置cookie值
	 *@author: weip
	 *@time: 2007-8-30 上午02:23:17
	 *@param name
	 *@param value
	 *@param domain
	 */
	public void setCookie(String name, String value, String domain) {
		setCookie(name, value, domain, -1);
	}

	/**
	 * 清除cookie
	 *@author: weip
	 *@time: 2007-8-30 上午02:23:28
	 *@param name
	 *@param domain
	 */
	public void clearCookie(String name, String domain) {
		setCookie(name, "", domain, 0);
	}

	/**
	 * 清除Allcookie
	 *@author: 
	 *@time: 
	 *@param name
	 *@param domain
	 */
	public void clearAllCookie() {
	Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			cookies[i].setValue(null);
		}

	}
	/**
	 * 获取某个cookie域
	 * 
	 * @param name
	 * @return
	 */
	public String getCookieDomain(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null){
			return null;
		}

		Cookie cookie = null;
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equalsIgnoreCase(name)) {
//				System.out.println(cookie.getDomain());
				return cookie.getDomain();
			}
		}
		return null;
	}
	
	/**
	 * 获取某个cookie域
	 *@param name
	 *@return
	 */
	public Cookie getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null){
			return null;
		}

		Cookie cookie = null;
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equalsIgnoreCase(name)) {
				return cookie;
			}
		}
		return null;
	}
	
	/**
	 * 判断COOKIE是否过期
	 * 
	 * @param strDate
	 *            上次访问的时间，格式：yyyy-MM-dd HH:mm:ss
	 * @param minutes
	 *            过期时间（以分为单位）
	 * @return true 过期 false 未过期
	 */
	public static boolean isCookieExpire(String strDate, int minutes) {

		Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(5, 7)) - 1;
		int day = Integer.parseInt(strDate.substring(8, 10));
		int hour = Integer.parseInt(strDate.substring(11, 13));
		int minute = Integer.parseInt(strDate.substring(14, 16));
		int second = Integer.parseInt(strDate.substring(17));
		c.set(year, month, day, hour, minute, second);
		c.add(Calendar.MINUTE, minutes);
		return c.before(Calendar.getInstance());
	}
}
