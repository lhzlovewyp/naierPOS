package com.joker.core.dto;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 分页对象
 *
 * @Author crell
 * @Date 2016/3/7 10:57
 */
public class Page<T> {
	private int pageNo = 1;//当前页

	private int pageSize = 10;//每页记录数

	private int totalRecord;//总记录数

	private int totalPage;//总页数

	private List<T> results;//返回结果集

	private Map<String, Object> params = new HashMap<String, Object>();

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
		this.setTotalPage(totalPage);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [pageNo=").append(pageNo).append(", pageSize=")
				.append(pageSize).append(", results=").append(results).append(
				", totalPage=").append(totalPage).append(
				", totalRecord=").append(totalRecord).append("]");
		return builder.toString();
	}
}
