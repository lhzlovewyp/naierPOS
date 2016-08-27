
/**  
 * Project Name:naierPOS-rest  
 * File Name:ThirdBaseDto.java  
 * Package Name:com.joker.common.third.dto  
 * Date:2016年8月19日上午8:07:21  
 * Copyright (c) 2016, lvhaizhen@meitunmama.com All Rights Reserved.  
 *  
 */  
  
package com.joker.common.third.dto;  
 
/**  
 * ClassName: ThirdBaseDto <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2016年8月19日 上午8:07:21 <br/>  
 *  
 * @author lvhaizhen  
 * @version   
 * @since JDK 1.7  
 */
public class ThirdBaseDto<T> {
	
	private T data;
	
	private String status;
	
	private String message;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
  
