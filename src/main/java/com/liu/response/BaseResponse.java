package com.liu.response;

import java.io.Serializable;

public class BaseResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 844524418947789561L;

	/**
	 * 错误码，请求成功时该属性返回null,可以用该属性来判断请求是否成功
	 */
	private String errorCode;
	
	/**
	 * 错误信息中文描述
	 */
	private String zhMsg;
	
	/**
	 * 错误信息英文描述
	 */
	private String enMsg;
	
	/**
	 * 中文详细信息
	 */
	private String zhDetailMsg;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getZhMsg() {
		return zhMsg;
	}
	public void setZhMsg(String zhMsg) {
		this.zhMsg = zhMsg;
	}
	public String getEnMsg() {
		return enMsg;
	}
	public void setEnMsg(String enMsg) {
		this.enMsg = enMsg;
	}
	public String getZhDetailMsg() {
		return zhDetailMsg;
	}
	public void setZhDetailMsg(String zhDetailMsg) {
		this.zhDetailMsg = zhDetailMsg;
	}
	
}
