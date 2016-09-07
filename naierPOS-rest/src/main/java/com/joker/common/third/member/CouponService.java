/**
 * 
 */
package com.joker.common.third.member;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.third.dto.ThirdBaseDto;
import com.joker.common.third.dto.ThirdCouponDto;
import com.joker.core.util.Configer;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.FunctionTextMd5;
import com.joker.core.util.HttpClientUtil;

/**
 * 电子券相关的第三方接口
 * @author lvhaizhen
 *
 */
public class CouponService extends BaseService{
	
	/**
	 * 获取电子券的金额.
	 * @param code
	 * @return
	 */
	public static ThirdBaseDto<ThirdCouponDto>  getAmount(String code){
		String url=Configer.get("thirdRestUrl");
		String action="crm/coupon/get_detail";
		String key="pos";
		//String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmss");
		String timestamp=new Long(System.currentTimeMillis()/1000).toString();

		TreeMap<String ,Object> map=new TreeMap<String,Object>();
		
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
			ThirdBaseDto<ThirdCouponDto>  dto=(ThirdBaseDto<ThirdCouponDto>)JSONObject.parseObject(result, ThirdBaseDto.class);
			return dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**  
	 * pay:消费电子券. <br/>  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param code
	 * @return  
	 * @since JDK 1.7  
	 */
	public static ThirdBaseDto<String>  pay(String code){
		String url=Configer.get("thirdRestUrl");
		String action="crm/coupon/used";
		String key="pos";
		//“key”＋“password”＋“date”生成的md5；password固定为“123456”，date取当前日期，格式为“YYYY-MM-DD” 
		String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmss");


		TreeMap<String ,Object> map=new TreeMap<String,Object>();
		
		map.put("code",code);
		map.put("is_consume",0);
		map.put("key",key);
		map.put("timestamp", timestamp);
		map.put("consume", "");
		map.put("consume_detail", "");
		map.put("type", "1");
		
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
			System.out.println(result);
			ThirdBaseDto<String>  dto=(ThirdBaseDto<String>)JSONObject.parseObject(result, ThirdBaseDto.class);
			return dto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String args[]){
		//System.out.println((getAmount("12010")));
		System.out.println(JSONObject.toJSON(getAmount("12011")).toString());

		//System.out.println(JSONObject.toJSON(pay("12011")).toString());
	}
	
}
