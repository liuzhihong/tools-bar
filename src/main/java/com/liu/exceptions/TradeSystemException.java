package com.liu.exceptions;

public class TradeSystemException extends BaseTradeException {

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -6132660873946805356L;
	
	/**
     * 构造异常对象
     * 
     * @param msg
     */
    public TradeSystemException(String msg) {
        super(msg);
    }

    /**
     * TradeSystemException
     * 
     * @param exception
     */
    public TradeSystemException(Throwable exception) {
        super(exception);
    }

    /**
     * TradeSystemException
     * 
     * @param mag
     * @param exception
     */
    public TradeSystemException(String mag, Exception exception) {
        super(mag, exception);
    }

}
