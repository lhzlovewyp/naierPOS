package com.joker.common.model;

import java.math.BigDecimal;

public class Member {

	// 会员编号
	private String member;
	// 会员编号
	private String memberCode;
	// 会员姓名
	private String memberName;
	// 会员手机号
	private String memberMobile;
	// 会员登记.
	private String memberGrade;
	
	private BigDecimal memberPoint;
	
	private BigDecimal memberBalance;
	
	private String memberBirthDay;
	
	
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
	
	
}
