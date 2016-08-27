package com.joker.common.model;

import java.math.BigDecimal;

public class Member {

	// 会员编号
	private String member;
	// 会员编号
	private String memberCode;
	//門店編號
	private String shopCode;
	// 会员姓名
	private String memberName;
	// 会员手机号
	private String memberMobile;
	//
	//1-男、2-女
	private String sex;
	
	private String email;
	
	private String address;
	
	private String birthday;
	
	private String idCard;
	
	//1-已婚、2-未婚、0-保密
	private String marriage="0";
	// 会员登记.
	private String memberGrade;
	
	private BigDecimal memberPoint;
	
	private BigDecimal memberBalance;
	
	private String memberBirthDay;
	
	private MemberPointPayConfig memberPointPayConfig;
	
	
	public String getMemberBirthDay() {
		return memberBirthDay;
	}
	public void setMemberBirthDay(String memberBirthDay) {
		this.memberBirthDay = memberBirthDay;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberMobile() {
		return memberMobile;
	}
	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}
	public String getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}
	public BigDecimal getMemberPoint() {
		return memberPoint;
	}
	public void setMemberPoint(BigDecimal memberPoint) {
		this.memberPoint = memberPoint;
	}
	public BigDecimal getMemberBalance() {
		return memberBalance;
	}
	public void setMemberBalance(BigDecimal memberBalance) {
		this.memberBalance = memberBalance;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public MemberPointPayConfig getMemberPointPayConfig() {
		return memberPointPayConfig;
	}
	public void setMemberPointPayConfig(MemberPointPayConfig memberPointPayConfig) {
		this.memberPointPayConfig = memberPointPayConfig;
	}
	
	
}
