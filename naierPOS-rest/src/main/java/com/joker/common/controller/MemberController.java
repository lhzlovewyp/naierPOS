/**
 * 
 */
package com.joker.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.joker.common.model.Member;
import com.joker.common.third.member.MemberService;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

/**
 * @author lvhaizhen
 *
 */
@RestController
public class MemberController  extends AbstractController {

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
			Member member=MemberService.getMember(memNo);
			if(member==null){
				rbody.setStatus(ResponseState.ERROR);
			}else{
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
}
