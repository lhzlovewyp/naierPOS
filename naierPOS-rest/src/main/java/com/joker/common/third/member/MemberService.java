/**
 * 
 */
package com.joker.common.third.member;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.dto.MemberDto;
import com.joker.common.dto.MemberReturn;
import com.joker.common.model.Member;
import com.joker.common.third.dto.ThirdBaseDto;
import com.joker.common.third.dto.ThirdMemberDto;
import com.joker.common.third.dto.ThirdMemberPage;
import com.joker.core.util.Configer;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.FunctionTextMd5;
import com.joker.core.util.HttpClientUtil;


/**
 * 会员相关的接口.
 * @author lvhaizhen
 *
 */
public class MemberService {

	private Logger log = LoggerFactory.getLogger(MemberService.class);

	
	/**  
	 * getMember:获取单一会员信息. <br/>  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param memNo
	 * @return  
	 * @since JDK 1.7  
	 */
	public static Member getMember(String memNo){
		String url=Configer.get("member_single");
		String api="18f9ffb3c8cf7ec67e9ee2862af94e28";
		String sbs_id="206288888888888";
		String key="18f9ffb3c8cf7ec67e9ee2862af94e28";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sbs_id",sbs_id);
		map.put("card_no", memNo);
		map.put("password", "");
		
		String t = DatetimeUtil.formatDateToString(new Date(), "yyyyMMddHHmmss");
		
		map.put("t", t);
		try {
		String sign=sbs_id+map.get("card_no")+""+t+key;
		MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] md5 = md.digest(sign.getBytes());
		map.put("sign",md5.toString());
			String obj=HttpClientUtil.httpGet(url,map);
			if(obj!=null){
				MemberReturn memberReturn = JSONObject.parseObject(obj, MemberReturn.class);
				if(memberReturn==null || !memberReturn.getCode().equals("A00006")){
					return null;
				}

				MemberDto memberDto = memberReturn.getData();
				if(StringUtils.isEmpty(memberDto.getUser_id())){
					return null;
				}
				Member member=new Member();
				member.setMemberCode(memberDto.getCard_no());
				member.setMember(memberDto.getUser_id());
				member.setMemberPoint(memberDto.getUser_point());
				member.setMemberMobile(memberDto.getUser_mobile());
				member.setMemberName(memberDto.getUser_realname());
				member.setMemberBalance(memberDto.getUser_balance());
				member.setMemberBirthDay(memberDto.getUser_birthday());
				return member;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**  
	 * getMembers:批量获取会员信息. <br/>  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param code
	 * @param name
	 * @param tel
	 * @param shopCode
	 * @param pageNum
	 * @param num
	 * @return  
	 * @since JDK 1.7  
	 */
	public static ThirdMemberPage<ThirdMemberDto> getMembers(String code,String name,String tel,String shopCode,Integer pageNum,Integer num){
		String url=Configer.get("thirdRestUrl");
		String action="crm/customer/get_list";
		String key="pos";
		String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmSS");
		
		TreeMap<String ,Object> map=new TreeMap<String,Object>();
		map.put("fields","*");
		if(!StringUtils.isEmpty(code)) map.put("customer_code", code);
		if(!StringUtils.isEmpty(name)) map.put("customer_name", name);
		if(!StringUtils.isEmpty(tel)) map.put("customer_tel", tel);
		if(!StringUtils.isEmpty(shopCode)) map.put("shop_code", shopCode);
		map.put("pageNum", pageNum);
		map.put("num", num);
		map.put("key", key);
		map.put("timestamp", timestamp);
		
		String sign="";
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
        	sign += entry.getKey()+entry.getValue(); 
        } 
		
		sign = new FunctionTextMd5().md5_3("pos" + sign + "pos365");
		map.put("app_act", action);
		map.put("sign", sign);
		
		try {
			String result = HttpClientUtil.httpGetNew(url, map);
			ThirdBaseDto<JSONObject> dto = (ThirdBaseDto<JSONObject>)JSONObject.parseObject(result, ThirdBaseDto.class);
			if("1".equals(dto.getStatus())){
				JSONObject data=dto.getData();
				ThirdMemberPage<ThirdMemberDto> page = JSONObject.parseObject(data.toString(),ThirdMemberPage.class);
				return page;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**  
	 * createMember:创建会员信息. <br/>  
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>  
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>  
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>  
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>  
	 *  
	 * @author Administrator  
	 * @param memberDto
	 * @return  
	 * @since JDK 1.7  
	 */
	public static ThirdBaseDto<ThirdMemberDto> createMember(ThirdMemberDto memberDto){
		String url=Configer.get("thirdRestUrl");
		String action="crm/customer/add_vip";
		String key="pos";
		String timestamp=DatetimeUtil.formatDateToString(new Date(),"yyyyMMddHHmmSS");
		
		
		String data=JSONObject.toJSON(memberDto).toString();
		Map<String ,Object> map=new LinkedHashMap<String,Object>();
		map.put("data",data);
		map.put("key", key);
		map.put("timestamp", timestamp);
		
		String sign="";
		
		for (Map.Entry<String, Object> entry : map.entrySet()) {  
        	sign += entry.getKey()+entry.getValue(); 
        } 
		
		sign = new FunctionTextMd5().md5_3("pos" + sign + "pos365");
		map.put("app_act", action);
		map.put("sign", sign);
		
		try {
			String result = HttpClientUtil.httpGetNew(url, map);
			ThirdBaseDto<ThirdMemberDto> dto = (ThirdBaseDto<ThirdMemberDto>)JSONObject.parseObject(result, ThirdBaseDto.class);
			return dto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static void main(String args[]){
		Member member=getMember("18957339389");
		System.out.println(JSONObject.toJSON(member).toString());
		
		//創建會員測試
//		ThirdMemberDto member=new ThirdMemberDto();
//		member.setVip_code("13036140375");
//		member.setTel("13036140375");
//		member.setShop_code("test");
		
		
		
		
		//System.out.println(JSONObject.toJSON(createMember(member)).toString());
		
		
		//查询会员信息.
		/*ThirdMemberPage<ThirdMemberDto> dto = getMembers(null,null,null,"test",1,10);
		System.out.println(JSONObject.toJSON(dto).toString());*/
	}
	
	
}
