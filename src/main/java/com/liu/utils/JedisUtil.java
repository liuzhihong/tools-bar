package com.liu.utils;

import java.util.Set;

import com.liu.Constants;
import com.liu.core.AppSpringContext;
import com.liu.core.JedisCallback;
import com.liu.core.PipelineCallback;
import com.liu.exceptions.RedisClientException;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 
* @ClassName: JedisUtil 
* @Description: jedis工具类
* @author 汪海霖     wanghl15@midea.com.cn
* @date 2014-9-2 下午1:49:20
 */
public class JedisUtil {
	
	private static int RETRY_NUM = 5;
	
	private static JedisUtil jedisUtil = new JedisUtil();
	
	private ShardedJedisPool sharedJedisPool = null;
	
	private JedisUtil(){
		sharedJedisPool = AppSpringContext.getBean("shardedJedisPool",ShardedJedisPool.class);
	}
	
	public static JedisUtil getJedisInstance(){
		return jedisUtil;
	}
	/**
	 * 获取ShardedJedis池资源
	 * @return
	 */
	public ShardedJedis getJedis(){
		return sharedJedisPool.getResource();
	}
	/**
	 * 返回ShardedJedis池资源
	 * @param jedis
	 */
	public void returnJedisResource(ShardedJedis shardedJedis) {
		if(shardedJedis == null){
			return;
		}
		sharedJedisPool.returnResource(shardedJedis);
	}
	
	public void returnJedisBrokenResource(ShardedJedis shardedJedis){
		if(shardedJedis == null){
			return;
		}
		sharedJedisPool.returnBrokenResource(shardedJedis);
	}
	
	/**
	 * 
	* @Title: openPipeline 
	* @Description: jedis管道批量操作  支持异常情况下的5次补偿操作
	* @param @param callback    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void execJedisPipelineOperate(PipelineCallback callback){
		for(int index = 1; index <= RETRY_NUM ; index++){
			try{
				invokePipeline(callback);
			}catch(RedisClientException ex){
				if(index < RETRY_NUM){
					continue;
				}
				throw ex;
			}
			break;
		}
	}
	
	/**
	 * 
	* @Title: execJedisOperate 
	* @Description: jedis操作  支持异常情况下的5次补偿操作
	* @param @param callback
	* @param @return
	* @return T    返回类型 
	* @throws
	 */
	public <T> T execJedisOperate(JedisCallback<T> callback){
		T result = null;
		for(int index = 1; index <= RETRY_NUM ; index++){
			try{
				result = invokeJedis(callback);
			}catch(RedisClientException ex){
				if(index < RETRY_NUM){
					continue;
				}
				throw ex;
			}
			break;
		}
		return result;
	}
	
	/**
	 * 
	* @Title: invokePipeline 
	* @Description: jedis管道操作核心方法
	* @param @param callback
	* @return void    返回类型 
	* @throws
	 */
	private void invokePipeline(PipelineCallback callback){
		boolean isReturn = true;
		ShardedJedis shardedJedis = null;
		try{
			shardedJedis = getJedis();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			callback.invokePipeline(pipeline);
			pipeline.sync();
		}catch(Exception ex){
			if(ex instanceof JedisConnectionException){
				isReturn = false;
				returnJedisBrokenResource(shardedJedis);
			}
			throw new RedisClientException(ex);
		}finally{
			if(isReturn){
				returnJedisResource(shardedJedis);
			}
		}
	}
	
	/**
	 * 
	* @Title: invokeJedis 
	* @Description: jedis操作核心方法
	* @param @param callback
	* @param @return
	* @return T    返回类型 
	* @throws
	 */
	private <T> T invokeJedis(JedisCallback<T> callback){
		boolean isReturn = true;
		ShardedJedis shardedJedis = null;
		try{
			shardedJedis = getJedis();
			return callback.invoke(shardedJedis);
		}catch(Exception ex){
			if(ex instanceof JedisConnectionException){
				isReturn = false;
				returnJedisBrokenResource(shardedJedis);
			}
			throw new RedisClientException(ex);
		}finally{
			if(isReturn){
				returnJedisResource(shardedJedis);
			}
		}
	}
	
	/**
	 * 
	* @Title: executeIncrValueToCache 
	* @Description: 得到从缓冲中自增num的值
	* @param @param pkKey
	* @param @param pkNum
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execIncrByToCache(final String cacheKey,final int num){
		Long pkVal = execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.incrBy(cacheKey, (long)num);
			}
		});
		return pkVal;
	}
	
	/**
	 * 
	* @Title: execIncrToCache 
	* @Description: 得到从缓冲中自增1的值
	* @param @param cacheKey
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execIncrToCache(final String cacheKey){
		Long pkVal = execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.incr(cacheKey);
			}
		});
		return pkVal;
	}
	
	public Long execDecrToCache(final String cacheKey){
		Long pkVal = execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.decr(cacheKey);
			}
		});
		return pkVal;
	}
	
	/**
	 * 
	* @Title: execDecrByToCache 
	* @Description: 
	* @param @param cacheKey
	* @param @param num
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execDecrByToCache(final String cacheKey,final int num){
		Long pkVal = execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.decrBy(cacheKey, (long)num);
			}
		});
		return pkVal;
	}
	
	/**
	 * 
	* @Title: execDelToCache 
	* @Description: 删除缓存
	* @param @param cacheKey
	* @param @return
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean execDelToCache(final String cacheKey){
		return execJedisOperate(new JedisCallback<Boolean>() {
			@Override
			public Boolean invoke(ShardedJedis jedis) {
				return jedis.del(cacheKey)==
						Constants.CONSTANT_ZERO_INT?false:true;
			}
		});
	}
	
	/**
	 * 
	* @Title: execSetToCache 
	* @Description: 存入缓存值 LRU
	* @param @param cacheKey
	* @param @param value
	* @return void  返回类型 
	* @throws
	 */
	public void execSetToCache(final String cacheKey,final String value){
		execJedisOperate(new JedisCallback<Void>() {
			@Override
			public Void invoke(ShardedJedis jedis) {
				jedis.set(cacheKey, value);
				return null;
			}
		});
	}
	
	/**
	 * 
	* @Title: execSetexToCache 
	* @Description: 执行
	* @param @param key
	* @param @param seconds
	* @param @param value
	* @return void    返回类型 
	* @throws
	 */
	public void execSetexToCache(final String cacheKey,final int seconds,final String value){
		execJedisOperate(new JedisCallback<Void>() {
			@Override
			public Void invoke(ShardedJedis jedis) {
				jedis.setex(cacheKey, seconds,value);
				return null;
			}
		});
	}
	
	/**
	 * 
	* @Title: execGetFromCache 
	* @Description: 
	* @param @param cacheKey
	* @param @return
	* @return String    返回类型 
	* @throws
	 */
	public String execGetFromCache(final String cacheKey){
		return execJedisOperate(new JedisCallback<String>(){
			@Override
			public String invoke(ShardedJedis jedis) {
				return jedis.get(cacheKey);
			}
		});
	}
	
	/**
	 * 
	* @Title: execExistsFromCache 
	* @Description: 是否已经缓存
	* @param @param cacheKey
	* @param @return
	* @return Boolean    返回类型 
	* @throws
	 */
	public Boolean execExistsFromCache(final String cacheKey){
		return execJedisOperate(new JedisCallback<Boolean>() {
			@Override
			public Boolean invoke(ShardedJedis jedis) {
				return jedis.exists(cacheKey);
			}
		});
	}
	
	/**
	 * 
	* @Title: execExpireToCache 
	* @Description: 设置过期时间
	* @param @param cacheKey
	* @param @return
	* @return Boolean    返回类型 
	* @throws
	 */
	public Long execExpireToCache(final String cacheKey,final int seconds){
		return execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.expire(cacheKey, seconds);
			}
		});
	}
	
	/**
	 * 
	* @Title: execSetnxToCache 
	* @Description: 
	* @param @param cacheKey
	* @param @param value
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execSetnxToCache(final String cacheKey,final String value){
		return execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.setnx(cacheKey, value);
			}
		});
	}
	
	/**
	 * 获取keys
	 * @param cacheKey
	 * @return
	 */
	public Set<String> execKeysFromCache(final String cacheKey){
		return execJedisOperate(new JedisCallback<Set<String>>() {
			@Override
			public Set<String> invoke(ShardedJedis jedis) {
				return jedis.getShard(Constants.CONSTANT_ONE_STR).keys(cacheKey);
			}
		});
	}
}
