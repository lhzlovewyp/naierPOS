
  
package com.joker.common.third.dto;

import java.util.List;

public class ThirdMemberPage<T> {

	private Integer count;
	
	private List<ThirdMemberDto> data;
	
	private Integer num;
	
	private Integer countPage;
	
	private Integer pageNum;
	
	private String total_field;
	
	private Integer size;
	
	private Integer td_num;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<ThirdMemberDto> getData() {
		return data;
	}

	public void setData(List<ThirdMemberDto> data) {
		this.data = data;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getCountPage() {
		return countPage;
	}

	public void setCountPage(Integer countPage) {
		this.countPage = countPage;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getTotal_field() {
		return total_field;
	}

	public void setTotal_field(String total_field) {
		this.total_field = total_field;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTd_num() {
		return td_num;
	}

	public void setTd_num(Integer td_num) {
		this.td_num = td_num;
	}
	
	
}
  
