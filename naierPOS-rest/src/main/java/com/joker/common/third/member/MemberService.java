/**
 * 
 */
package com.joker.common.third.member;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.joker.common.dto.MemberDto;
import com.joker.common.dto.MemberReturn;
import com.joker.common.model.Member;
import com.joker.core.util.Configer;
import com.joker.core.util.DatetimeUtil;
import com.joker.core.util.HttpClientUtil;


/**
 * @author lvhaizhen
 *
 */
public class MemberService {

	private Logger log = LoggerFactory.getLogger(MemberService.class);

	
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
	
	
}
