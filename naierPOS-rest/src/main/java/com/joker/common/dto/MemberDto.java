/**
 * 
 */
package com.joker.common.dto;

import java.math.BigDecimal;

/**
 * 会员对象.
 * 
 * @author lvhaizhen
 *
 */
public class MemberDto {
	/**会员唯一编码*/
	private String user_id;
	/**固定为“206288888888888”*/
	private String sbs_id;
	/**门店编码*/
	private String store_id;
	/**会员编码*/
	private String card_no;
	/**姓名*/
	private String user_realname;
	/**手机号*/
	private String user_mobile;
	/**email*/
	private String user_email;
	/**储值余额*/
	private BigDecimal user_balance;
	/**积分*/
	private BigDecimal user_point;
	/**生日*/
	private String user_birthday;
	/**地址*/
	private String user_address;
	/**身份证号*/
	private String idnum;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSbs_id() {
		return sbs_id;
	}

	public void setSbs_id(String sbs_id) {
		this.sbs_id = sbs_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getUser_realname() {
		return user_realname;
	}

	public void setUser_realname(String user_realname) {
		this.user_realname = user_realname;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	

	public BigDecimal getUser_balance() {
		return user_balance;
	}

	public void setUser_balance(BigDecimal user_balance) {
		this.user_balance = user_balance;
	}

	public BigDecimal getUser_point() {
		return user_point;
	}

	public void setUser_point(BigDecimal user_point) {
		this.user_point = user_point;
	}

	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	
	
}
