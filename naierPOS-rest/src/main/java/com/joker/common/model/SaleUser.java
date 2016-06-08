/**
 * 
 */
package com.joker.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lvhaizhen
 *
 */
public class SaleUser implements Serializable{

	private Long id;
	private String userName;
	private String nickName;
	private String password;
	private String ip;
	private String photo;
	private String tel;
	private String qq;
	private SaleOrg org;
	
	private Date lastLoginDate;
	
	private String token;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPasspord(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public SaleOrg getOrg() {
		return org;
	}
	public void setOrg(SaleOrg org) {
		this.org = org;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
