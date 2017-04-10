package com.liu.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseApiCode {
	
	public static final Map<String,String> enMsgMap = new HashMap<String,String>(300);
	
	public static final Map<String,String> zhMsgMap = new HashMap<String,String>(300);
	
	public static List<String> ignoreResultCodeList = new ArrayList<String>(10);
	
	public static List<String> retryResultCodeList = new ArrayList<String>(10);
	
	/**操作成功**/
	public static final String OPERATE_SUCCESS = "200";
	/**操作失败**/
	public static final String OPERATE_FAIL = "99";
	/**操作超时**/
	public static final String OPERATE_TIMEOUT = "98";
	/**参数无效**/
	public static final String OPERATE_PARAM_INVALID = "97";
	/**操作无效**/
	public static final String OPERATE_INVALID = "96";
	/**部分成功**/
	public static final String OPERATE_SOME_SUCCESS = "95";
	/**相关系统异常**/
	public static final String OPERATE_PARTNER_FAIL = "94";
	/**平台异常**/
	public static final String OPERATE_PLATFORM_FAIL = "93";
	/**系统异常**/
	public static final String OPERATE_SYSTEM_FAIL = "92";
	/**操作正在处理中**/
	public static final String OPERATE_PROCESSING = "91";
	
	public static String getEnMsg(String errorCode){
		return enMsgMap.get(errorCode);
	}
	
	public static String getZhMsg(String errorCode){
		return zhMsgMap.get(errorCode);
	}
	
	static{
		CardApiCode.initResponseCode();
		
		enMsgMap.put(OPERATE_SUCCESS, "base.operate-success");
		zhMsgMap.put(OPERATE_SUCCESS, "操作成功");
		enMsgMap.put(OPERATE_FAIL, "base.operate-fail");
		zhMsgMap.put(OPERATE_FAIL, "操作失败,建议刷新页面检查数据后再操作");
		enMsgMap.put(OPERATE_TIMEOUT, "base.operate-timeout");
		zhMsgMap.put(OPERATE_TIMEOUT, "操作超时,建议稍等一会再操作");
		enMsgMap.put(OPERATE_PARAM_INVALID, "base.request-parameter-invalid");
		zhMsgMap.put(OPERATE_PARAM_INVALID, "请求参数不合法");
		enMsgMap.put(OPERATE_INVALID, "base.operate-invalid");
		zhMsgMap.put(OPERATE_INVALID, "操作无效,建议刷新页面检查数据后再操作");
		enMsgMap.put(OPERATE_SOME_SUCCESS, "base.operate-some-success");
		zhMsgMap.put(OPERATE_SOME_SUCCESS, "调用部分成功");
		enMsgMap.put(OPERATE_PARTNER_FAIL, "base.operate-partner-fail");
		zhMsgMap.put(OPERATE_PARTNER_FAIL, "周边系统调用异常");
		enMsgMap.put(OPERATE_PLATFORM_FAIL, "base.operate-platform-fail");
		zhMsgMap.put(OPERATE_PLATFORM_FAIL, "平台调用异常");
		enMsgMap.put(OPERATE_SYSTEM_FAIL, "base.operate-system-error");
		zhMsgMap.put(OPERATE_SYSTEM_FAIL, "糟糕,系统卡壳了,请联系系统管理员");
		enMsgMap.put(OPERATE_PROCESSING, "base.operate-processing");
		zhMsgMap.put(OPERATE_PROCESSING, "操作正在处理中");
		ignoreResultCodeList.add(BaseApiCode.OPERATE_INVALID);
		retryResultCodeList.add(BaseApiCode.OPERATE_TIMEOUT);
		retryResultCodeList.add(BaseApiCode.OPERATE_PROCESSING);
		retryResultCodeList.add(BaseApiCode.OPERATE_PARTNER_FAIL);
		retryResultCodeList.add(BaseApiCode.OPERATE_FAIL);
		retryResultCodeList.add(BaseApiCode.OPERATE_SYSTEM_FAIL);
	}
	
}
