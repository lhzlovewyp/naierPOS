package com.joker.core.expection;


/** 
 * 业务错误异常
 * @ClassName: FailedException 
 * @author 陈奇
 * @date 2015年12月3日 下午2:18:19
 *  
 */
public class FailedException extends CommonException {

	/** 
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 3682997135306617703L;

	/**
	 * @param code
	 *            异常编码
	 * @param cause
	 *            原异常信息
	 */
	public FailedException(String code, Throwable cause) {
		super(code, cause);
	}
	
	/**
	 * @param code
	 *            异常编码
	 */
	public FailedException(String code) {
		super(code, null);
	}

}
