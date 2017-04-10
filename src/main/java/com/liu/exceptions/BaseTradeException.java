package com.liu.exceptions;

/**
 * 
* @ClassName: BaseTradeException 
* @Description: trade core 异常类基类
* @author 汪海霖    wanghl15@midea.com.cn
* @date 2014-9-3 上午12:45:58
 */
public class BaseTradeException extends RuntimeException {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 3988229133456964977L;

	public BaseTradeException(String message) {
		super(message);
	}

	public BaseTradeException(Throwable e) {
		super(e);
	}

	public BaseTradeException(String message, Throwable cause) {
		super(message, cause);
	}

}
