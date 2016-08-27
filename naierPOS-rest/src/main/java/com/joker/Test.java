package com.joker;

import java.math.BigDecimal;

public class Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		BigDecimal a=new BigDecimal(70);
		BigDecimal b=new BigDecimal(266);
		System.out.println(a.divide(b,2,4));
	}

}
