/**
 * 
 */
package com.joker.common.third.member;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.service.impl.SalesOrderServiceImpl;
import com.joker.common.third.dto.ThirdBaseDto;
import com.joker.core.util.Configer;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.FunctionTextMd5;
import com.joker.core.util.HttpClientUtil;

/**
 * 储值卡支付相关接口.
 * 
 * @author lvhaizhen
 *
 */
public class PrepaidService extends BaseService{
	
	private static Logger log = LoggerFactory.getLogger(PrepaidService.class);


	public static ThirdBaseDto<String> pay(String code,String amount,String type ,String storeCode,String salesOrderId){
		String url=Configer.get("thirdRestUrl");
		String action="crm/customer/money";
		String key="pos";
		String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmss");

		TreeMap<String,Object> map=new TreeMap<String,Object>();
		map.put("customer_code",code);
		map.put("key",key);
		map.put("money",amount);
		map.put("relation_code",salesOrderId);
		map.put("shop_code",storeCode);
		map.put("timestamp", timestamp);
		map.put("type",type);
		map.put("type_code","001");
		map.put("key",key);
		
		String sign="";
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
        	sign += entry.getKey().toLowerCase()+entry.getValue(); 
        } 
		
		sign = new FunctionTextMd5().md5_3("pos" + sign + "pos365");
		map.put("app_act", action);
		map.put("sign", sign);
		

		String result=null;
		try {
			result = HttpClientUtil.httpGet(url, map);
			if(StringUtils.isNotBlank(result)){
				log.error(result);
				ThirdBaseDto<String> dto = (ThirdBaseDto<String>)JSONObject.parseObject(result, ThirdBaseDto.class);
				return dto;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	public static void main(String args[]){
		ThirdBaseDto<String> dto = pay("18957339389","1", "3", "111049", "1");
		if(dto!=null){
			System.out.println(JSONObject.toJSON(dto));
		}
		//System.out.println(pay("18957339389",new BigDecimal(0.01), "3", "1", "1"));
	}
	
	
}
