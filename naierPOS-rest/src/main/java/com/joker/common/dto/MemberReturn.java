/**
 * 
 */
package com.joker.common.dto;

/**
 * @author lvhaizhen
 *
 */
public class MemberReturn {

	private String code;
	
	private MemberDto data;
	
	private String result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MemberDto getData() {
		return data;
	}

	public void setData(MemberDto data) {
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
