package com.liu.exceptions;

/**
 * 业务处理异常
 * @author xianqun.li  2015年12月30日
 *
 */
public class BusinessException extends MideaException {
	
	private static final long serialVersionUID = 1L;
	
	public static BusinessException UNKNOW_CARD_TYPE = new BusinessException(2001, "CARD_TYPE_ERROR", "未知的优惠券类型");
	
	public static BusinessException IMPORT_DATA_EXCEPTION = new BusinessException(208, "IMPORT_DATA_INVALID", "导入数据不合法");
	
	protected BusinessException(Integer status, String defineCode, String chnDesc) {
		super(status, defineCode, chnDesc);
	}
	
	public BusinessException newInstance(String msg ,Object... args){
		BusinessException ex = new BusinessException(this.status, this.defineCode, this.chnDesc);
		ex.setMessage(msg, args);
		return ex ;
	}
	
	
	

}
