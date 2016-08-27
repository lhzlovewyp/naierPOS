package com.joker.core.dto;

import java.util.Map;

public class Pageable {
	private int start;//当前页数

	private int limit;//每页数

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
