package com.joker.core.expection;


/** 
 * 无效用户异常
 * @ClassName: InvalidUserException 
 * @author 陈奇
 * @date 2015年12月3日 下午2:18:19
 *  
 */
public class InvalidUserException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4192065634665588330L;

	/**
	 * @param code
	 *            异常编码
	 * @param cause
	 *            原异常信息
	 */
	public InvalidUserException(String code, Throwable cause) {
		super(code, cause);
	}
	
	/**
	 * @param code
	 *            异常编码
	 */
	public InvalidUserException(String code) {
		super(code, null);
	}

}
