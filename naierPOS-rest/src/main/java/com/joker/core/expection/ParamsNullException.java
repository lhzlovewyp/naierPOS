package com.joker.core.expection;


/** 
 * 参数为空异常
 * @ClassName: ParamsNullException 
 * @author 陈奇
 * @date 2015年12月3日 下午2:18:19
 *  
 */
public class ParamsNullException extends CommonException {

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
	public ParamsNullException(String code, Throwable cause) {
		super(code, cause);
	}
	
	/**
	 * @param code
	 *            异常编码
	 */
	public ParamsNullException(String code) {
		super(code, null);
	}

}
