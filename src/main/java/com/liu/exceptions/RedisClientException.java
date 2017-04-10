package com.liu.exceptions;

/**
 * 
* @ClassName: RedisClientException 
* @Description: redis 操作异常类
* @author 汪海霖    wanghl15@midea.com.cn
* @date 2014-9-2 下午2:33:20
 */
public class RedisClientException extends BaseTradeException{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1027452261936311766L;
	
	 /**
     * 构造异常对象
     * 
     * @param msg
     */
    public RedisClientException(String msg) {
        super(msg);
    }

    /**
     * RedisClientException
     * 
     * @param exception
     */
    public RedisClientException(Throwable exception) {
        super(exception);
    }

    /**
     * RedisClientException
     * 
     * @param mag
     * @param exception
     */
    public RedisClientException(String mag, Exception exception) {
        super(mag, exception);
    }

}
