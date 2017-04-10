package com.liu.utils;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: RedisLockUtil 
* @Description: redis锁实现类
* @author 汪海霖    wanghl15@midea.com.cn
* @date 2014-9-2 下午11:00:46
 */
public class RedisLockUtil {
	
	/**加锁标志 **/
    public static final String LOCKED = "TRUE"; 
    /**锁的超时时间（秒），过期删除 **/
    public static final int EXPIRE = 180; 
    /**1毫秒对应的纳秒时间**/
	public static final long ONE_MILLI_NANOS = 1000000L; 
	/**默认超时时间（毫秒） **/
    public static final long DEFAULT_TIME_OUT = 2000; 
    /**随机数**/
    public static final Random random = new Random(); 
    
    private static Logger logger = LoggerFactory.getLogger(RedisLockUtil.class);
    
    /**
     * 加锁 保证原子性
     * @param operateLogic 处理逻辑 
     * @param lockCacheKey 锁的cache key
     * @param timeOut 毫秒数    	 获取锁的超时时间 
     * @return 
     * @throws Exception 
     */
	public static <T> T executeSynchOperate(MainOperator<T> operator,
				String lockCacheKey,long milliTimeout) throws Exception{
		Boolean locked = false;
		long startNaros = System.nanoTime();
		long nanoTimeOut = milliTimeout*1000000L;
		T resultObj = null;
		try{
			while(System.nanoTime()-startNaros < nanoTimeOut){
				if(JedisUtil.getJedisInstance().execSetnxToCache(lockCacheKey, LOCKED) == 1){
					JedisUtil.getJedisInstance().execExpireToCache(lockCacheKey, EXPIRE);
					locked = true;
					break;
				}
				Thread.sleep(30,random.nextInt(500));
			}
			resultObj = operator.executeInvokeLogic(locked);
		}catch(Exception ex){
			throw ex;
		}finally{
			if (locked) {
				releaseRedisLock(lockCacheKey);
			}
		}
		return resultObj;
	}
	
	/**
	 * 操作本身实现的逻辑
	 * @param <T>
	 */
	public abstract interface MainOperator<T>{
		public abstract T executeInvokeLogic (boolean result)throws Exception;
	}
	
	/**
	 * 释放锁
	 * @param cacheKey
	 */
	public static void releaseRedisLock(final String cacheKey){
		JedisUtil.getJedisInstance().execDelToCache(cacheKey);
	}
}
