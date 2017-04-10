package com.liu.exceptions;

/** 
* @ClassName: BaseException 
* @Description: 系统基本异常类
* @author libin.chen@midea.com.cn 
* @date 2015年8月15日 下午4:36:03 
*  
*/
public class BaseException extends MideaException {

	private static final long serialVersionUID = -5997285054839713700L;

	public static BaseException PARAM_INVALID = new BaseException(97, "PARAM_INVALID", "参数无效");
	
	public static BaseException OPERATE_TIMEOUT = new BaseException(96, "OPERATE_TIMEOUT", "操作超时");

	public static BaseException UNKNOWN_SYSTEM_FAIL = new BaseException(95, "UNKNOWN_SYSTEM_FAIL", "亲,系统出现异常,请联系管理员!");
	
	protected BaseException(Integer status, String defineCode, String chnDesc) {
		super(status, defineCode, chnDesc);
	}
	public BaseException(String errorCode) {
		this(errorCode, "");
	}
	
	public BaseException(String errorCode, String detailMsg) {
		super(errorCode);
		this.defineCode = errorCode;
		this.chnDesc = detailMsg;
	}
	public BaseException newInstance(String message, Object... args) {
		BaseException ex = new BaseException(this.status, this.defineCode, this.chnDesc);
		message= this.chnDesc;
		ex.setMessage(message,args);
		return ex;
	}
}
