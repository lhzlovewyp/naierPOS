/**
 * 
 */
package com.joker.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.model.Account;
import com.joker.common.model.Brand;
import com.joker.common.model.Member;
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
			Member member = MemberService.getMember(memNo);
			if (member == null) {
				rbody.setStatus(ResponseState.ERROR);
			} else {
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
}
