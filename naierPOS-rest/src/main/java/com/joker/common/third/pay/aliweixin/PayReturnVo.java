/**
 * 
 */
package com.joker.common.third.pay.aliweixin;

/**
 *   支付宝微信支付接口返回数据.
 * 
 * @author lvhaizhen
 *
 */
public class PayReturnVo {

	/**返回状态码
 100 成功
 101 验证失败
 103 操作失败 系统异常
 104 非法字符
 105 请求参数无法与接口匹配或参数内容不正确*/
	private String state;
	/**消息*/
	private String msg;
	/**支付通道
1：支付宝
2：微支付*/
	private String channel;
	/**支付类型
01：支付宝条码支付
02：支付宝支付撤销
03：支付宝支付退款*/
	private String code;
	/**统一支付帐号*/
	private String partner;
	/**商户订单号*/
	private String out_trade_no;
	/**交易号*/
	private String trade_no;
	/**买家用户号
买家支付宝账号对应的支付宝唯一用户号
以2088开头的纯16位数字*/
	private String buyer_user_id;
	/**买家账号
买家支付宝账号，可以为email或者手机号。对部分信息进行了隐藏*/
	private String buyer_logon_id;
	/**交易金额*/
	private String total_fee;
	/**交易时间
该笔交易的买家付款时间
格式为yyyy-MM-dd HH:mm:ss*/
	private String gmt_payment;
	/**操作员号*/
	private String operator_id;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getBuyer_user_id() {
		return buyer_user_id;
	}
	public void setBuyer_user_id(String buyer_user_id) {
		this.buyer_user_id = buyer_user_id;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}
	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}
	
	
	
}
