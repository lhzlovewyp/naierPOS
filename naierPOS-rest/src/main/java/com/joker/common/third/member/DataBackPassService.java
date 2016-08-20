
  
package com.joker.common.third.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.third.dto.ThirdBaseDto;
import com.joker.common.third.dto.ThirdSalesOrderDetail;
import com.joker.common.third.dto.ThirdSalesOrderDto;
import com.joker.core.util.Configer;
import com.joker.core.util.FunctionTextMd5;
import com.joker.core.util.HttpClientUtil;

/**  
 * ClassName: DataBackPassService <br/>  
 * Function: 数据回传接口. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2016年8月19日 上午10:27:12 <br/>  
 *  
 * @author lvhaizhen  
 * @version   
 * @since JDK 1.7  
 */
public class DataBackPassService {

	//回传数据.
	public static boolean process(ThirdSalesOrderDto orderDto,List<ThirdSalesOrderDetail> details){
		String url=Configer.get("thirdRestUrl");
		String action="crm/consume/add";
		String key="pos";
		//String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmss");
		String timestamp=new Long(System.currentTimeMillis()/1000).toString();

		TreeMap<String ,Object> map=new TreeMap<String,Object>();
		
		map.put("consume",JSONObject.toJSON(orderDto));
		map.put("consume_detail",JSONObject.toJSON(details));
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
			result = HttpClientUtil.httpGetNew(url, map);
			ThirdBaseDto<String>  dto=(ThirdBaseDto<String>)JSONObject.parseObject(result, ThirdBaseDto.class);
			if("1".equals(dto.getStatus())){
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void main(String args[]){
		ThirdSalesOrderDto dto=new ThirdSalesOrderDto();
		dto.setCustomer_code("18957339389");
		dto.setMoney("1");
		dto.setNum("2");
		dto.setRecord_code("abc");
		dto.setVip_code("18957339389");
		dto.setIs_add_time("2016-08-19 13:20:20");
		
		List<ThirdSalesOrderDetail> details = new ArrayList<ThirdSalesOrderDetail>();
		ThirdSalesOrderDetail detail=new ThirdSalesOrderDetail();
		detail.setMoney("0.5");
		detail.setNum("1");
		detail.setRecord_code("abc");
		detail.setDetail_record_code("abcdddddddf");
		detail.setGoods_code("a");
		
		ThirdSalesOrderDetail detail1=new ThirdSalesOrderDetail();
		detail1.setMoney("0.5");
		detail1.setNum("1");
		detail1.setRecord_code("abc");
		detail1.setDetail_record_code("abcdddddddf1");
		detail1.setGoods_code("b");
		details.add(detail);
		details.add(detail1);
		System.out.println(process(dto,details));
	}
}
  
