package com.liu.core;

import redis.clients.jedis.ShardedJedisPipeline;
/**
 * redis Pipeline 操作抽象类
* @ClassName: PipelineCallback 
* @Description: 
* @author 汪海霖 wanghl15@midea.com.cn 
* @date 2014-5-16 下午5:12:35 
*
 */
public interface PipelineCallback {
	/**
	 * 管道操作
	* @Title: invokePipeline 
	* @Description: 
	* @param @param pipeline    
	* @return void   
	* @throws
	 */
	public void invokePipeline(ShardedJedisPipeline pipeline);

}
