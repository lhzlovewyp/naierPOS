/**
 * 
 */
package com.joker.common.third.member;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.third.dto.ThirdBaseDto;
import com.joker.core.util.Configer;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.FunctionTextMd5;
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
	public static ThirdBaseDto<String>  getAmount(String code){
		String url=Configer.get("thirdRestUrl");
		String action="crm/coupon/get_detail";
		String key="pos";
		//“key”＋“password”＋“date”生成的md5；password固定为“123456”，date取当前日期，格式为“YYYY-MM-DD” 
		String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmss");


		Map<String ,Object> map=new LinkedHashMap<String,Object>();
		
		map.put("coupon_code",code);
		map.put("key",key);
		map.put("timestamp", timestamp);
		
		String sign="";
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
        	sign += entry.getKey().toLowerCase()+entry.getValue(); 
        } 
		
		sign = new FunctionTextMd5().md5_3("pos" + sign + "pos365");
		
		map.put("app_act",action);
		map.put("sign", sign);
		
		String result=null;
		try {
			result = HttpClientUtil.httpGet(url, map);
			ThirdBaseDto<String>  dto=(ThirdBaseDto<String>)JSONObject.parseObject(result, ThirdBaseDto.class);
			return dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ThirdBaseDto<String>  pay(String code){
		String url=Configer.get("thirdRestUrl");
		String action="crm/coupon/user";
		String key="pos";
		//“key”＋“password”＋“date”生成的md5；password固定为“123456”，date取当前日期，格式为“YYYY-MM-DD” 
		String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmss");


		Map<String ,Object> map=new LinkedHashMap<String,Object>();
		
		map.put("code",code);
		map.put("is_consume",0);
		map.put("key",key);
		map.put("timestamp", timestamp);
		
		String sign="";
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
        	sign += entry.getKey().toLowerCase()+entry.getValue(); 
        } 
		
		sign = new FunctionTextMd5().md5_3("pos" + sign + "pos365");
		
		map.put("app_act",action);
		map.put("sign", sign);
		
		String result=null;
		try {
			result = HttpClientUtil.httpGet(url, map);
			ThirdBaseDto<String>  dto=(ThirdBaseDto<String>)JSONObject.parseObject(result, ThirdBaseDto.class);
			return dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String args[]){
		//System.out.println(JSONObject.toJSON(getAmount("11952")).toString());
		System.out.println(JSONObject.toJSON(pay("11952")).toString());
	}
	
}
