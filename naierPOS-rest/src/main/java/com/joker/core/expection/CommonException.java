package com.joker.core.expection;


/** 
 * 异常基类
 * 
 * @ClassName: CommonException 
 * @author 陈奇
 * @date 2015年12月3日 下午2:17:08
 *  
 */
public class CommonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 508651458709970043L;

	/**
	 * @param code
	 *            异常编码
	 * @param cause
	 *            原异常信息
	 */
	public CommonException(String code, Throwable cause) {
		super(code, cause);
	}

}
