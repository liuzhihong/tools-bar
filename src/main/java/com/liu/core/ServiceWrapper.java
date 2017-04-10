package com.liu.core;

import com.liu.response.BaseResponse;
/**
 * 
* @ClassName: ServiceWrapper 
* @Description: 服务包装类
* @author 汪海霖  wanghl15@midea.com.cn
* @date 2015-4-23 下午1:57:12
 */

public abstract class ServiceWrapper {
	
	//调用服务
	public abstract String invokeService();
	
	//是否监控失败请求
	public boolean isMonitorFail(){
		return true;
	}
	
	//是否监控成功请求
	public boolean isMonitorSuccess(){
		return false;
	}
	
	//超时时间
	public int timeout(){
		return 500;
	}
	
	//是否通知调用失败
	public boolean isNoticeInvokeFail(BaseResponse response){
		return false;
	}

	//获取请求traceId
	public String getTraceId(){
		return null;
	}
	
	//发送变更通知
	public Boolean sendChangeNotify(){
		return null;
	}
}
