
  
package com.joker.common.model;

import java.math.BigDecimal;

public class MemberPointPayConfig  extends BaseModel{

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
	 * @since JDK 1.7 
	 */
	private static final long serialVersionUID = 7202971604338430729L;

	private Client client;
	
	private BigDecimal pointPay;
	
	private BigDecimal pointPayAMT;
	
	//可用积分
	private BigDecimal points;
	//积分可以兑换金额.
	private BigDecimal pointsMoney;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public BigDecimal getPointPay() {
		return pointPay;
	}

	public void setPointPay(BigDecimal pointPay) {
		this.pointPay = pointPay;
	}

	public BigDecimal getPointPayAMT() {
		return pointPayAMT;
	}

	public void setPointPayAMT(BigDecimal pointPayAMT) {
		this.pointPayAMT = pointPayAMT;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public BigDecimal getPointsMoney() {
		return pointsMoney;
	}

	public void setPointsMoney(BigDecimal pointsMoney) {
		this.pointsMoney = pointsMoney;
	}
	
	
	
}
  
