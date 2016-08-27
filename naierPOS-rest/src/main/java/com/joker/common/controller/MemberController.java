/**
 * 
 */
package com.joker.common.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.model.Account;
import com.joker.common.model.Member;
import com.joker.common.model.MemberPointPayConfig;
import com.joker.common.service.MemberPointPayConfigService;
import com.joker.common.third.dto.ThirdBaseDto;
import com.joker.common.third.dto.ThirdMemberDto;
import com.joker.common.third.dto.ThirdMemberPage;
import com.joker.common.third.member.MemberService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

/**
 * @author lvhaizhen
 * 
 */
@RestController
public class MemberController extends AbstractController {
	
	@Autowired
	MemberPointPayConfigService memberPointPayConfigService;

	/**
	 * 查询账号信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/member/getSingleMember" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getAccountByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String memNo = (String) params.get("memNo");

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account=(Account)user;
			Member member = MemberService.getMember(memNo);
			//设置会员积分消费情况.
			if (member == null) {
				rbody.setStatus(ResponseState.ERROR);
			} else {
				MemberPointPayConfig memberPointPayConfig = memberPointPayConfigService.getMemberPointPayConfigByClient(account.getClient().getId());
				BigDecimal memberPoint = member.getMemberPoint();
				//计算积分和最小兑换积分的比率.
				BigDecimal[] datas = memberPoint.divideAndRemainder(memberPointPayConfig.getPointPay());
				if(datas[0].intValue() == 0){
					memberPointPayConfig.setPoints(BigDecimal.valueOf(0L));
				}else{
					memberPointPayConfig.setPoints(memberPoint.subtract(datas[1]));
				}
				//设置积分可以兑换的金额.
				memberPointPayConfig.setPointsMoney(memberPointPayConfig.getPointPayAMT().multiply(datas[0]));
				member.setMemberPointPayConfig(memberPointPayConfig);
				rbody.setData(member);
				rbody.setStatus(ResponseState.SUCCESS);
			}
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}

	/**
	 * 查询会员信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/member/queryByPage" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody getMemberByPage(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		Integer pageNo = (Integer) params.get("pageNo");
		Integer limit = (Integer) params.get("limit");
		String customer_code = (String) params.get("customer_code");
		String customer_name = (String) params.get("customer_name");
		String customer_tel = (String) params.get("customer_tel");
		pageNo = (pageNo == null ? 0 : pageNo);
		limit = (limit == null ? 10 : limit);

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;
			String storeId = "";//account.getStore().getId();
			
			ThirdMemberPage<ThirdMemberDto> thirdMemberPage = MemberService.getMembers(
					customer_code, customer_name, customer_tel, storeId, pageNo, limit);
			Page<ThirdMemberDto> page = new Page<ThirdMemberDto>();
			page.setPageNo(thirdMemberPage.getPageNum());
			page.setPageSize(thirdMemberPage.getNum());
			page.setResults(thirdMemberPage.getData());
			page.setTotalPage(thirdMemberPage.getCountPage());
			page.setTotalRecord(thirdMemberPage.getCount());
			rbody.setData(page);
			rbody.setStatus(ResponseState.SUCCESS);
			return rbody;
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
	/**
	 * 添加会员信息信息.
	 * 
	 * @param paramsBody
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/member/add" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody add(@RequestBody ParamsBody paramsBody,
			HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		// 参数校验
		Map params = paramsBody.getBody();
		String customer_name = (String) params.get("customer_name");
		String tel = (String) params.get("tel");
		String customer_sex = (String) params.get("customer_sex");
		String email = (String) params.get("email");
		String address = (String) params.get("address");
		String birthday = (String) params.get("birthday");
		String id_card = (String) params.get("id_card");
		String marriage = (String) params.get("marriage");
		
		if (StringUtils.isBlank(customer_name)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入姓名！");
			return rbody;
		}
		if (StringUtils.isBlank(tel)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入手机号码！");
			return rbody;
		}
		if (StringUtils.isBlank(id_card)) {
			rbody.setStatus(ResponseState.FAILED);
			rbody.setMsg("请输入身份证号！");
			return rbody;
		}
		

		String token = paramsBody.getToken();
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			Account account = (Account) user;

			ThirdMemberDto memberDto = new ThirdMemberDto();
			memberDto.setVip_code(tel);
			memberDto.setCustomer_name(customer_name);
			memberDto.setTel(tel);
			memberDto.setCustomer_sex(customer_sex);
			memberDto.setEmail(email);
			memberDto.setAddress(address);
			memberDto.setBirthday(birthday);
			memberDto.setId_card(id_card);
			memberDto.setMarriage(marriage);
			memberDto.setSource("6");
			ThirdBaseDto<ThirdMemberDto> thirdBaseDto = MemberService
					.createMember(memberDto);
			if(thirdBaseDto != null && "1".equals(thirdBaseDto.getStatus())){
				rbody.setStatus(ResponseState.SUCCESS);
			}else{
				rbody.setStatus(ResponseState.ERROR);
				if(thirdBaseDto != null && StringUtils.isNotBlank(thirdBaseDto.getMessage())){
					rbody.setMsg(thirdBaseDto.getMessage());
				}else{
					rbody.setMsg("接口错误！");	
				}
			}
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
}
