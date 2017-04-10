/**
 * 
 */
package com.liu.exceptions;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常
 * 
 * @author：wenhao.zeng
 * @version:
 */

public class MideaException extends RuntimeException{

	private static final long serialVersionUID = -5097768787801034398L;
	
	/**
	 * 异常ID，用于表示某一异常实例，每一个异常实例都有一个唯一的异常ID
	 */
	protected String id;

	/**
	 * 异常信息，包含必要的上下文业务信息，用于打印日志
	 */
	protected String message;
	
	protected Integer status;
	
	protected String defineCode;
	
	protected String chnDesc;
	
	protected String realClassName;
	
	
	public MideaException(Throwable e){
		super();
	}

	protected MideaException(String defineCode) {
		super();
		this.defineCode = defineCode;
		initId();
	}
	
	protected MideaException(Integer status, String defineCode, String chnDesc) {
		super();
		this.status = status;
		this.defineCode = defineCode;
		this.chnDesc = chnDesc;
		this.setMessage(this.chnDesc, null);
		initId();
	}

	private void initId() {
		this.id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message, Object... args) {
		this.message = MessageFormat.format(message, args);
	}


	public String getDefineCode() {
		return defineCode;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public String getChnDesc(){
		return chnDesc;
	}
 
	public static <T extends MideaException> T newException(T exception, String message, Object...args){
		if(exception == null){
			throw new RuntimeException("no exception instance specified");
		}
		try {
			Constructor constructor = exception.getClass().getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			T newException = (T)constructor.newInstance(exception.getDefineCode());
			newException.setMessage(message, args);
			return newException;
		} catch (Throwable e) {
			throw new RuntimeException("create exception instance fail : "+e.getMessage(), e);
		}
	}
	
	/**
	 * 比较异常的class和defineCode是否相同
	 * @param e
	 * @return
	 */
	public boolean codeEquals(MideaException e){
		if(e == null){
			return false;
		}
		if(!e.getClass().equals(this.getClass())){
			return false;
		}
		if(!e.getDefineCode().equals(getDefineCode())){
			return false;
		}
		return true;
	}
	
	public MideaException upcasting() {
		if(this.getClass().equals(MideaException.class)){
			return this;
		}
		MideaException superexception = new MideaException(this.status, this.defineCode,this.chnDesc);
		superexception.message = this.message;
		superexception.realClassName = this.getClass().getName();
		superexception.id = this.id;
		superexception.setStackTrace(this.getStackTrace());
		return superexception;
	}
	
	public MideaException downcasting(){
		if(this.realClassName == null || MideaException.class.getName().equals(this.realClassName)){
			return this;
		}
		Class clz = null;
		try{
			clz = Class.forName(this.realClassName);
		}catch(Exception e){
		}
		if(clz == null){
			return this;
		}
		try {
			Constructor constructor = clz.getDeclaredConstructor(String.class);
			constructor.setAccessible(true);
			MideaException newException = (MideaException)constructor.newInstance(this.defineCode);
			newException.message = this.message;
			newException.id = this.id;
			newException.setStackTrace(this.getStackTrace());
			return newException;
		} catch (Throwable e) {
			return this;
		}
	}

	public String getRealClassName() {
		if(realClassName==null){
			return this.getClass().getName();
		}
		return realClassName;
	}
	
	public StackTraceElement[] getCoreStackTrace(){
		List<StackTraceElement> list = new ArrayList<StackTraceElement>();
		for(StackTraceElement traceEle : getStackTrace()){
			if(traceEle.getClassName().startsWith("com.midea.trade")){
				list.add(traceEle);
			}
		}
		StackTraceElement[] stackTrace = new StackTraceElement[list.size()];
		return list.toArray(stackTrace);
	}
	
	public String getCoreStackTraceStr(){
		StringBuffer sb = new StringBuffer();
		for(StackTraceElement traceEle : getCoreStackTrace()){
			sb.append("\n"+traceEle.toString());
		}
		return sb.toString();
	}
}
