
/**  
 * Project Name:naierPOS-rest  
 * File Name:ThirdSalesOrder.java  
 * Package Name:com.joker.common.third.dto  
 * Date:2016年8月19日上午11:06:31  
 * Copyright (c) 2016, lvhaizhen@meitunmama.com All Rights Reserved.  
 *  
 */  
  
package com.joker.common.third.dto;  
 
/**  
 * ClassName: ThirdSalesOrder <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2016年8月19日 上午11:06:31 <br/>  
 *  
 * @author lvhaizhen  
 * @version   
 * @since JDK 1.7  
 */
public class ThirdSalesOrderDto {

	private String money;
	
	private String num;
	
	private String record_code;
	
	private String customer_code;
	
	private String vip_code;
	
	private String source="6";
	
	private String is_add_time;

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getRecord_code() {
		return record_code;
	}

	public void setRecord_code(String record_code) {
		this.record_code = record_code;
	}

	public String getCustomer_code() {
		return customer_code;
	}

	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}

	public String getVip_code() {
		return vip_code;
	}

	public void setVip_code(String vip_code) {
		this.vip_code = vip_code;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIs_add_time() {
		return is_add_time;
	}

	public void setIs_add_time(String is_add_time) {
		this.is_add_time = is_add_time;
	}
	
}
  
