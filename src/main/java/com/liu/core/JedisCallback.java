/**   
* Copyright © 1968-2014 美的集团股份有限公司
* @FileName: JedisCallback.java 
* @Package com.midea.trade.core.impl.core.jedis 
* @Description: 
* @author 汪海霖     wanghl15@midea.com.cn
* @date 2014-9-2 下午2:18:46 
* @version V0.0.1   
*/
package com.liu.core;

import redis.clients.jedis.ShardedJedis;


/** 
 * @ClassName: JedisCallback 
 * @Description: 
 * @author 汪海霖    wanghl15@midea.com.cn
 * @date 2014-9-2 下午2:18:46  
 */
public interface JedisCallback<T> {
	
	public T invoke(ShardedJedis jedis);

}
