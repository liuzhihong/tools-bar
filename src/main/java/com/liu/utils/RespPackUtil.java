package com.liu.utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.remoting.TimeoutException;
import com.liu.code.BaseApiCode;
import com.liu.core.ServiceWrapper;
import com.liu.request.BaseRequest;
import com.liu.response.BaseResponse;
/**
 * @ClassName: RespPackUtil
 * @Description: 响应包装类
 * @author 汪海霖 wanghl15@midea.com.cn
 * @date 2014-10-2 上午9:53:31
 */
public class RespPackUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(RespPackUtil.class);
	
//	private static MessageProducer messageProducer;
	
	/**
	 * @Title: execInvokeService 
	 * @Description: 统一包装调用后端服务
	 * @return CoreResponse    返回类型 
	 * @throws
	 */
	public static BaseResponse execInvokeService(BaseRequest request, BaseResponse response,ServiceWrapper wrapper) {
		StringBuilder strBuff = new StringBuilder();
		long startTime = System.currentTimeMillis();
		StackTraceElement ste = new Exception().getStackTrace()[1];
		//服务方法名
		String methodName = ste.getMethodName();
		//服务类名
		String serviceName = StringUtils.substringBefore(getSimpleClassName(ste.getClassName()), "Impl");
		String facadeName = serviceName.concat(".").concat(methodName);
		String reqStr = FastJSONHelper.serialize(request);
		String reqLogStr = StringUtils.isBlank(wrapper.getTraceId()) ? reqStr : wrapper.getTraceId();
		strBuff.append("调用服务==").append(facadeName).append(";请求数据==").append(reqLogStr).append(";");
		//数据检查
		String checkResult = request.check();
		String resultCode = null;
		Exception ex = null;
		if (StringUtils.isBlank(checkResult)) {
			try{
				//调用服务
				resultCode = wrapper.invokeService();
			}catch(Exception e){//服务异常
				ex = e;
				resultCode = BaseApiCode.OPERATE_SYSTEM_FAIL;
				//异常预警
				//execInvokeFailToNotice(serviceName, methodName, ex, reqStr);
			}
		} else {//参数缺失
			resultCode = BaseApiCode.OPERATE_PARAM_INVALID;
			strBuff.append("检测到数据错误描述==").append(checkResult).append(";");
		}
		//包装response
		packBaseResponse(response, resultCode, checkResult);
		String respStr = FastJSONHelper.serialize(response);
		if(wrapper.isNoticeInvokeFail(response)){//调用失败是否通知
			//故障报警
//			StringBuilder faultCodeBuff = new StringBuilder(facadeName);
//			faultCodeBuff.append("||").append(response.getEnMsg());
//			sendNoticeToMq(faultCodeBuff.toString(), reqStr, respStr);
		}
		strBuff.append("resultCode==").append(resultCode)
			   .append(";共耗时==").append(System.currentTimeMillis()-startTime)
		       .append(";response==").append(respStr).append(";");
		if(BaseApiCode.OPERATE_SUCCESS.equals(resultCode)){
			Boolean sendResult = wrapper.sendChangeNotify();
			if(sendResult != null){
				strBuff.append("单据内容变更通知消息发送完成,notifyMqSendResult==").append(sendResult).append(";");
			}
		}
		if(StringUtils.equals(BaseApiCode.OPERATE_SUCCESS, resultCode)){//调用成功
			//服务成功但调用时间过长
			if(wrapper.isMonitorSuccess() || System.currentTimeMillis() - startTime > wrapper.timeout()){
				logger.info(strBuff.toString());
			}else{
				logger.debug(strBuff.toString());
			}
		}else{  //是否监控+是否忽略失败的操作
			if(ex != null){//服务异常
				strBuff.append(";error_msg==");
				Throwable cause = ex.getCause();
				if(isTimeoutThrowable(cause)){  //超时异常
					strBuff.append(cause.getMessage());
					logger.error(strBuff.toString());
				}else{
					strBuff.append(ex.getMessage());
					logger.error(strBuff.toString(), ex);
				}
			}else{
				logger.info(strBuff.toString());
			}
		}
		return response;
	}
	
	//包装Response
	private static void packBaseResponse(BaseResponse response,	String resultCode, String errorDetail) {
		if (!BaseApiCode.OPERATE_SUCCESS.equals(resultCode)) {
			response.setErrorCode(resultCode);
		}
		response.setEnMsg(BaseApiCode.getEnMsg(resultCode));
		response.setZhMsg(BaseApiCode.getZhMsg(resultCode));
		if (StringUtils.isNotBlank(errorDetail)) {
			response.setZhDetailMsg(errorDetail);
		}
	}
	
	//判断是否超时或者网络方面的异常
	public static boolean isTimeoutThrowable(Throwable cause){
		if(cause != null && (cause instanceof TimeoutException
				|| cause instanceof SocketTimeoutException
				|| cause instanceof ConnectException)){
			return true;
		}else{
			return false;
		}
	}
	
	//是否打印debug日志
	public static boolean isPrintDebugLog(String facadeName){
		return false;
	}
	
	private static String getSimpleClassName(String className){
		return className.substring(className.lastIndexOf(".")+1);
	}
	
	//服务失败通知策略
	/*public static void execInvokeFailToNotice(String serviceName,String methodName,
			Exception ex,String reqStr){
		//获取当前时间的月日时MMddHH
		String mdh = DateTool.getDateToMDH(DateTool.getCurrentDate());
		//异常名 如:NullPointerException
		String exName = ex.getClass().getSimpleName();
		//异常通知cache key(时间+服务名+异常名)
		String noticeKey = mdh+methodName+exName;
		//故障编码
		String faultCode = serviceName+"."+methodName;
		//计算同一服务同一类型异常次数
		Long incrNum = JedisUtil.getJedisInstance().execIncrToCache(noticeKey);
		if(incrNum == Constants.CONSTANT_ONE_INT){//首次异常设置cache作废时间
			JedisUtil.getJedisInstance().execExpireToCache(noticeKey, Constants.REDIS_ONEDAY);
		}
		StringBuilder errorBuff = new StringBuilder("调用服务:");
		errorBuff.append(faultCode).append("异常,错误信息:")
				 .append(ex.getClass().getName()).append(":");
		Throwable cause = ex.getCause();
		if(isTimeoutThrowable(cause)){//超时异常
			errorBuff.append(cause.getMessage());
		}else{//后期可以给异常分类
			errorBuff.append(ex.getMessage());
		}
		if(incrNum % 100 == Constants.CONSTANT_ONE_INT){//每累计一百次发送通知
			sendNoticeToMq(faultCode,reqStr,errorBuff.toString());
		}
	}*/
	
	//发送报障邮件数据到MQ
	/*public static boolean sendNoticeToMq(String faultCode,String reqStr,String errorStr){
		FaultReportRequest reportRequest = new FaultReportRequest();
		reportRequest.setFaultMsg(errorStr);
		reportRequest.setReqParam(reqStr);
		reportRequest.setResParam(errorStr);
		reportRequest.setFaultTime(DateTool.getCurrentDate());
		reportRequest.setFaultCode(faultCode);
		reportRequest.setReporter("系统");
		if(messageProducer == null){
			messageProducer = AppSpringContext.getBean("messageProducer", MessageProducer.class);
		}
		return messageProducer.sendMessage(MqSendType.SERVICE_FAIL_NOTICE, 
				String.valueOf(System.currentTimeMillis()).concat(RandomStringUtils.randomNumeric(5)), 
				JacksonMapper.beanToJson(reportRequest));
	}*/


}
