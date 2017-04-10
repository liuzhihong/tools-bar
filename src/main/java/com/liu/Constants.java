package com.liu;

public class Constants {
	
	/**常量byte类型1**/
	public static final byte CONSTANT_ONE_BYTE = 1;
	/**常量byte类型0 **/
	public static final byte CONSTANT_ZERO_BYTE = 0;
	/**常量int类型1**/
	public static final int CONSTANT_ONE_INT = 1;
	/**常量int类型0 **/
	public static final int CONSTANT_ZERO_INT = 0;
	/** 常量字符串类型1 **/
	public static final String CONSTANT_ONE_STR = "1";
	
	public static final int CONSTANT_TEN_INT = 10;
	/**缓存过期时间一个月**/
	public final static int REDIS_ONEMONTH = 2592000;//60*60*24*30 
	/**缓存过期时间一天**/
	public final static int REDIS_ONEDAY = 86400;//60*60*24 
	/**缓存过期时间半天**/
	public final static int REDIS_HALFDAY = 43200;//60*60*12 
	/**缓存过期时间十天**/
	public final static int REDIS_TENDAY = 864000;//60*60*24*10
	/**缓存过期时间十分钟**/
	public final static int REDIS_TENMINUTE = 600;//60*10
	/**过期时间为一周**/
	public static final int REDIS_ONEWEEK = 604800;
	/**缓存过期时间三小时**/
	public final static int REDIS_THREEHOUR = 10800;//3*60*60
	/**缓存过期时间一小时**/
	public final static int REDIS_ONEHOUR = 3600;//60*60
	/**缓存过期时间半小时**/
	public final static int REDIS_HALFHOUR = 1800;//60*30
	/**缓存过期时间半分钟**/
	public final static int REDIS_HALFMINUTE = 30;
	/**缓存过期时间90秒**/
	public final static int REDIS_NINETY = 90;
	/**空字符串**/
	public final static String CONSTANT_EMPTY_STR = "";
	/**时间一天**/
	public final static int CONSTANT_ONEDAY_SECOND = 86400;//60*60*24 
	/**半小时毫秒数**/
	public final static int HALFHOUR_MILLISECOND = 1800000;
	/**一个月的毫秒数**/
	public final static long ONEMONTH_MILLISECOND = 2592000000L;//60*60*24*30*1000
	/** 常量30秒 **/
	public static final int CONSTANT_THIRTY_SECOND = 30000;
	/**响应名称**/
	public static final String RESPONSE_NAME = "result_code";
	/**响应描述**/
	public static final String RESPONSE_DESCR = "result_descr";
	/**响应记录编码**/
	public static final String RESPONSE_RECORDID = "result_recordId";
	/**响应记录内容**/
	public static final String RESPONSE_CONTENT = "result_content";
	/** 常量字符串类型1 **/
	public static final String CONSTANT_ZERO_STR = "0";
	
	public static final String CONSUME_HANDLER = "HANDLER";
	/**品类、sku分隔符**/
	public static final String CAT_SKU_SPLIT = ",";
	/**请求头appid**/
	public static final String AUTH_TOKEN = "CMMS_APPID";
}
