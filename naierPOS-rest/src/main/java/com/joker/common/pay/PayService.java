package com.joker.common.pay;

import com.joker.common.pay.alipay.PayRefundVo;
import com.joker.core.dto.ReturnBody;

/**
 * 
 * 支付服务.
 * 
 * @author lvhaizhen
 *
 */
public interface PayService {

	/**
	 * 发起支付请求.
	 * 
	 * @return
	 */
	public ReturnBody pay(PayParamsVo vo);
	
	/**
	 * 发起退款请求.
	 * 
	 * @return
	 */
	public ReturnBody refund(PayRefundVo vo);
	
	
}
