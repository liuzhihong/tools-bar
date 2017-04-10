package com.liu.code;

import java.util.Map;

public class FileUploadApiCode {

	/**
	 * 超出允许上传的最大文件大小
	 */
	public static final String EXCEED_MAX_UPLOAD_SIZE = "801";
	/**
	 * 上传文件夹为空
	 */
	public static final String UPLOAD_DIR_PATH_IS_NULL = "802";
	/**
	 * 非法状态
	 */
	public static final String ILLEGAL_STATE = "803";
	/**
	 * IO异常
	 */
	public static final String IO_EXCEPTION = "804";
	/**
	 * 文件格式不对
	 */
	public static final String INVALID_FORMAT = "805";
	/**
	 * excel上传数据条目数过多
	 */
	public static final String EXCEL_MAX_NUMBER = "806";
	
	public static void initResponseCode() {
		Map<String, String> enMsgMap = BaseApiCode.enMsgMap;
		Map<String, String> zhMsgMap = BaseApiCode.zhMsgMap;

		enMsgMap.put(EXCEED_MAX_UPLOAD_SIZE,
				"fileupload.exceed-max-upload-size");
		zhMsgMap.put(EXCEED_MAX_UPLOAD_SIZE, "上传文件太大，请保证文件小于30M");
		enMsgMap.put(UPLOAD_DIR_PATH_IS_NULL,
				"fileupload.upload-dir-path-is-null");
		zhMsgMap.put(UPLOAD_DIR_PATH_IS_NULL, "上传文件路径为空");

		enMsgMap.put(ILLEGAL_STATE, "fileupload.illegal-state");
		zhMsgMap.put(ILLEGAL_STATE, "上传文件出现异常，非法的状态");
		enMsgMap.put(IO_EXCEPTION, "fileupload.io-exception");
		zhMsgMap.put(IO_EXCEPTION, "上传文件出现IO异常");
		enMsgMap.put(INVALID_FORMAT, "fileupload.invalid-format");
		zhMsgMap.put(INVALID_FORMAT, "上传文件格式不对");
		enMsgMap.put(EXCEL_MAX_NUMBER, "import.excel-import-max-number");
		zhMsgMap.put(EXCEL_MAX_NUMBER, "导入的excel数据条数过多，请分批次导入。");
	}
}
