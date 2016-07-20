/**
 * 
 */
package com.joker.common.pay.alipay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeRefundRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.joker.common.model.Account;
import com.joker.common.pay.PayParamsVo;
import com.joker.common.pay.PayService;
import com.joker.core.constant.ResponseState;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.RandomCodeFactory;

/**
 * 
 * 阿里支付.
 * 
 * @author lvhaizhen
 *
 */
public class AlipayService implements PayService {

	private Log log = LogFactory.getLog("trade_pay");
	
	private Account account;
	
	private AlipayTradeService tradeService;
	
	public AlipayService(Account account){
		this.account=account;
		Configs.init("zfbinfo.properties");
		tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
	}
	
	@Override
	public ReturnBody pay(PayParamsVo vo) {
		String outTradeNo = RandomCodeFactory.generate(40);

        // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
        String subject = vo.getTitle();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = vo.getAmount();

        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
        String authCode = vo.getAuthCode();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        //String undiscountableAmount = request.getParameter("undiscountableAmount");

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = vo.getBody();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = account.getId();

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = account.getStore().getId();

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
//        String providerId = "2088100200300400500";
//        ExtendParams extendParams = new ExtendParams();
//        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "5m";


        // 创建请求builder，设置请求参数
        //AlipayTradePayContentBuilder builder = new AlipayTradePayContentBuilder()
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                .setOutTradeNo(outTradeNo)
                .setSubject(subject)
                .setAuthCode(authCode)
                .setTotalAmount(totalAmount)
                .setStoreId(storeId)
                .setBody(body)
                .setOperatorId(operatorId)
                .setSellerId(sellerId)
                .setTimeoutExpress(timeoutExpress);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = tradeService.tradePay(builder);
		
        ReturnBody rb=new ReturnBody();
        
        switch (result.getTradeStatus()) {
        case SUCCESS:
        	rb.setStatus(ResponseState.SUCCESS);
        	rb.setMsg("支付宝支付成功");
            break;

        case FAILED:
            rb.setStatus(ResponseState.FAILED);
            rb.setMsg("支付宝支付失败!!!");
            break;

        case UNKNOWN:
        	rb.setStatus(ResponseState.FAILED);
        	rb.setMsg("系统异常，订单状态未知!!!");
            break;

        default:
        	rb.setStatus(ResponseState.FAILED);
        	rb.setMsg("不支持的交易状态，交易返回异常!!!");
            break;
        }
        return rb;
		
	}

	@Override
	public ReturnBody refund(PayRefundVo vo) {
		// (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = vo.getOutTradeNo();

        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = vo.getAmount();

        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        //String outRequestNo = request.getParameter("outRequestNo");

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = vo.getRefundReason();

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = account.getStore().getId();

        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                .setOutTradeNo(outTradeNo)
                .setRefundAmount(refundAmount)
                .setRefundReason(refundReason)
                .setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        
        ReturnBody rb=new ReturnBody();
        
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	rb.setStatus(ResponseState.SUCCESS);
            	rb.setMsg("支付宝退款成功: )");
                break;

            case FAILED:
            	rb.setStatus(ResponseState.FAILED);
            	rb.setMsg("支付宝退款失败!!!");
                break;

            case UNKNOWN:
            	rb.setStatus(ResponseState.FAILED);
            	rb.setMsg("系统异常，订单退款状态未知!!!");
                
                break;

            default:
            	rb.setStatus(ResponseState.FAILED);
            	rb.setMsg("不支持的交易状态，交易返回异常!!!");
                break;
         }
        return rb;
	}

}
