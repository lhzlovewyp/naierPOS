/**
 * 
 */
package com.joker.common.third.pay.aliweixin;

/**
 * 支付参数设置.
 * 
 * @author lvhaizhen
 * 
 */
public class PayParamsVo {

	/**统一支付账号.*/
	private String partner;
	/**支付通道：1.支付宝、2.微支付*/
	private String channel;
	/**支付通道为1.支付宝时，交易操作类型：
		01：支付宝条码支付
		02：支付宝支付撤销
		03：支付宝支付退款
	支付通道为2.微支付时，交易操作类型：
		04：微支付条码支付
		05：微支付支付撤销
		06：微支付支付退款*/
	private String code;
	/**商户订单号，取SalesOrder.Client_SalesOrder.Store_SalesOrder.Terminal_SalesOrder.SalesDate_SalesOrder.Code*/
	private String out_trade_no;
	/**交易号，交易操作类型为”02：支付宝支付撤销”或者”03：支付宝支付退款”可以有值
		该交易在支付宝系统中的交易流水号
		最短16位，最长64位
		如果同时传了out_trade_no和trade_no，则以trade_no为准*/
	private String trade_no;
	/**条码，支付宝付款输入的条码，该字段不可为空*/
	private String barcode;
	/**交易标题，交易操作类型为"01：支付宝条码支付"时不可为空
		该参数最长为128个汉字
		取SalesOrder.Client_SalesOrder.Store_SalesOrder.Terminal_SalesOrder.SalesDate_SalesOrder.Code.*/
	private String subject;
	/**交易金额，交易操作类型为"01：支付宝条码支付"或者"03：支付宝支付退款"时不可为空
		该笔订单的资金总额，取值范围[0.01,100000000]，精确到小数点后2位*/
	private String total_fee;
	/**操作员号，取当前登录的账号.*/
	private String operator_id;
	
	private String sign;
	
	
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	

	
	

}
