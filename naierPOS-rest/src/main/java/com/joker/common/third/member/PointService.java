
/**  
 * Project Name:naierPOS-rest  
 * File Name:PointService.java  
 * Package Name:com.joker.common.third.member  
 * Date:2016年8月19日上午9:45:01  
 * Copyright (c) 2016, lvhaizhen@meitunmama.com All Rights Reserved.  
 *  
 */  
  
package com.joker.common.third.member;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.third.dto.ThirdBaseDto;
import com.joker.core.util.Configer;
import com.joker.core.util.FunctionTextMd5;
import com.joker.core.util.HttpClientUtil;

/**  
 * ClassName: PointService <br/>  
 * Function: 积分相关的接口调用. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2016年8月19日 上午9:45:01 <br/>  
 *  
 * @author lvhaizhen  
 * @version   
 * @since JDK 1.7  
 */
public class PointService {

	
	private static Logger log = LoggerFactory.getLogger(PrepaidService.class);

	public static ThirdBaseDto<String>  pay(String code,String points,String orderCode){
		String url=Configer.get("thirdRestUrl");
		String action="crm/customer/change_integral_by_customer_code";
		String key="pos";
		//String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmss");
		String timestamp=new Long(System.currentTimeMillis()/1000).toString();

		TreeMap<String ,Object> map=new TreeMap<String,Object>();
		
		map.put("customer_code",code);
		map.put("integral",points);
		map.put("relation_code",orderCode);
		map.put("create_type","4");
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
			log.error(result);
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
		System.out.println(JSONObject.toJSON(pay("18957339389","-1","test")).toString());
	}
}
  
