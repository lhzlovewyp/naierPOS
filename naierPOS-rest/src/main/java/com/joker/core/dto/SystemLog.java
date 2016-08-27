package com.joker.core.dto;

/**
 * 系统日志，记录行为日志
 *
 * @Author crell
 * @Date 2016/3/18 16:13
 */
public class SystemLog {
	private String id;//编号
    private String userName;//用户名
    private String nickName;//名称
    private String actionName;//动作名
    private String message;//信息
    private String createDate;//创建时间
    private String ipAddress;//IP地址
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public void setNickName(String realName) {
		this.nickName = nickName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
    
}
