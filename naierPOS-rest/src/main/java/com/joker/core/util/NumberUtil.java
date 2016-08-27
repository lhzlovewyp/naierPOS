package com.joker.core.util;

import java.math.BigDecimal;

public class NumberUtil {

	/**
	 * 对数据保留x个小数位数，并进行x舍x+1入
	 * 
	 * @param data
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal  round(BigDecimal data,int scale ,int roundingMode){
		data = data.setScale(scale,roundingMode);   
		return data;
	}
	
	public static final void main(String args[]){
		System.out.println(round(new BigDecimal(1.2345),3,5));
	}
}
