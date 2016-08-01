/**
 * 
 */
package com.joker.common.third.member;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.joker.core.util.Configer;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.HttpClientUtil;

/**
 * 调用第三方接口，获取
 * @author lvhaizhen
 *
 */
public class CouponService extends BaseService{
	
	/**
	 * 获取电子券的金额.
	 * @param code
	 * @return
	 */
	public static String getAmount(String code){
		String url=Configer.get("thirdRestUrl");
		String action="crm/coupon/get_detail";
		String key="baison";
		//“key”＋“password”＋“date”生成的md5；password固定为“123456”，date取当前日期，格式为“YYYY-MM-DD” 
		Date date=new Date();
		long t =date.getTime()/1000 ;
		Date d=new Date(1469782377);
		String sign=toMD5(key+"123456"+DatetimeUtil.formatDateToString(d,"yyyyMMdd")+code);
		//String sign=toMD5(key+"123456"+"2016-07-29");

		Map<String ,Object> map=new HashMap<String,Object>();
		map.put("app_act",action);
		map.put("key",key);
		map.put("sign",sign);
		map.put("coupon_code",code);
		
		map.put("timestap", t);
		System.out.println(t);
		System.out.println(sign);
		
		String result=null;
		try {
			result = HttpClientUtil.httpGet(url, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String args[]){
		System.out.println(getAmount("11951"));
	}
	
}
