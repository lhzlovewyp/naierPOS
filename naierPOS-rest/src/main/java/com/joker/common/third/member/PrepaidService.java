/**
 * 
 */
package com.joker.common.third.member;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.joker.core.util.Configer;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.HttpClientUtil;

/**
 * @author lvhaizhen
 *
 */
public class PrepaidService extends BaseService{

	public static String pay(String code,BigDecimal amount,String type ,	String storeCode,String salesOrderId){
		String url=Configer.get("thirdRestUrl");
		String action="crm/customer/money";
		String key="baison";
		//“key”＋“password”＋“date”生成的md5；password固定为“123456”，date取当前日期，格式为“YYYY-MM-DD”
		//String sign=toMD5(key+"123456"+DatetimeUtil.nowToString(DatetimeUtil.DATE));
		String sign=toMD5(key+"123456"+"2016-07-29");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("app_act",action);
		map.put("key",key);
		map.put("sign",sign);
		map.put("Customer_code",code);
		map.put("Money",amount);
		map.put("Type",type);
		map.put("shop_code",storeCode);
		map.put("type_code","001");
		map.put("relation_code",salesOrderId);

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
		System.out.println(pay("18957339389",new BigDecimal(0.01), "3", "1", "1"));
	}
	
	
}
