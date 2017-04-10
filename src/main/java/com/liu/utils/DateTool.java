/*
 * Copyright (C), 2013-2014, 美的电子商务有限公司
 * FileName: DateTool.java
 * Description: 时间工具类
 * Author: wanghl
 * Date: 2014-5-13 - 下午2:20:39     
 * History: 
 * <author>wanghl</author>
 * <time>2014-5-13 - 下午2:20:39</time>
 * <version>1.0.1</version>
 * <desc>增加注释文档</desc>
 */
package com.liu.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
* @ClassName: DateTool 
* @Description: 时间工具类
* @author 汪海霖    wanghl15@midea.com.cn
* @date 2014-9-2 下午5:14:14
 */
public final class DateTool {
	/** 日志 **/
	private static Log log = LogFactory.getLog(DateTool.class);
	/** 应用资源 **/
	private static final String BUNDLE_KEY = "ApplicationResources";
	/** 一天的秒数 **/
	private static int DAYSERCOND = 24 * 60 * 60;
	/**Default lenient setting for getDate.*/
	private static final boolean LENIENTDATE = false;
	
	private static final String FORMAT_STR_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	private static final String FORMAT_STR_US = "EEE,d-MMM-yyyy HH:mm:ss 'GMT'";
	
	private static final String FORMAT_STR_DATE1 = "yyyy-MM-dd";
	
	private static final String FORMAT_STR_DATE2 = "yyyyMMdd";
	
	private static final String FORMAT_STR_DATE3 = "yyyyMM";
	
	private static final String FORMAT_STR_TIME1 = " HH:mm:ss";
	
	private static final String FORMAT_STR_TIME2 = " HH:mm:ss.SSS";
	
	private static final String FORMAT_STR_TIME = "HH:mm:ss";
	
	private static final String FORMAT_STR_TIME3 = "MMddHH";
	
	public static String getDateToMDH(Date aDate){
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_TIME3);
		return df.format(aDate);
	}
	
	public static String getDateToYM(Date aDate){
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_DATE3);
		return df.format(aDate);
	}
	
	public static String getDateToYMD(Date aDate){
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_DATE2);
		return df.format(aDate);
	}

	public static String getDateToYYMMDD(Date aDate){
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_STR_DATE1);
		return df.format(aDate);
	}
	
	/**
	 * 应用的当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 比较时间大小
	 * 
	 * @param sourceDate
	 * @param targetDate
	 * @return
	 */
	public static int compareDateToInt(Date sourceDate, Date targetDate) {
		if (null == sourceDate) {
			return -1;
		} else if (null == targetDate) {
			return 1;
		} else {
			long result = sourceDate.getTime() - targetDate.getTime();
			if (result > 0) {
				return 1;
			} else if (result < 0) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * yyyyMMdd年月日
	 * 
	 * @param aDate
	 * @return
	 */
	public static String getDateToLong(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(FORMAT_STR_DATE2);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 获取几天的秒数
	 * @param day
	 * @return
	 */
	public static int transformDayToSecond(int day) {
		return day * DAYSERCOND;
	}

	/**
	 * param String YYYYMMDDHHMISS Return String YYYY-MM-DD HH:MI:SS
	 */
	public static String strToHms(String strDate) {
		if (strDate != null && strDate.length() == 14) {
			String strFormat = strDate.substring(0, 4) + "-"
					+ strDate.substring(4, 6) + "-" + strDate.substring(6, 8)
					+ " " + strDate.substring(8, 10) + ":"
					+ strDate.substring(10, 12) + ":" + strDate.substring(12);
			return strFormat;
		} else {
			return strDate == null ? "" : strDate;
		}
	}

	/**
	 * param String YYYY-MM-DD HH:MI:SS Return String YYYYMMDDHHMISS
	 */
	public static String hmsToStr(String strDate) {
		if (strDate != null && strDate.length() == 19) {
			String strFormat = strDate.substring(0, 4)
					+ strDate.substring(5, 7) + strDate.substring(8, 10)
					+ strDate.substring(11, 13) + strDate.substring(14, 16)
					+ strDate.substring(17);
			return strFormat;
		} else {
			return strDate == null ? "" : strDate;
		}
	}

	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		Locale locale = LocaleContextHolder.getLocale();
		String defaultDatePattern = FORMAT_STR_DATE1;
		try {
			defaultDatePattern = ResourceBundle.getBundle(BUNDLE_KEY, locale)
					.getString("date.format");
		} catch (MissingResourceException mse) {
			defaultDatePattern = FORMAT_STR_DATE1;
		}
		return defaultDatePattern;
	}

	/**
	 * Return default date time Pattern (yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return
	 */
	public static String getDateTimePattern() {
		return getDatePattern() + FORMAT_STR_TIME1;
	}

	public static String getDateTimeStampPattern() {
		return getDatePattern() + FORMAT_STR_TIME2;
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 转换日期为默认格式的字符串日期
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return yyyy-mm-dd
	 */
	public static String getDate(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	
	public static String getTime(Date aDate){
		SimpleDateFormat df;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(FORMAT_STR_TIME);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 转换日期为默认格式的字符串日期时间格式
	 * 
	 * @param aDate
	 * @return yyyy-mm-dd hh:mm:ss
	 */
	public static String getDateTime(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDateTimePattern());
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	public static String getDateTimeStamp(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDateTimeStampPattern());
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 转换字符串日期为日期格式
	 * 
	 * @param yyyy-MM-dd
	 * @return Date
	 */
	public static Date stringToDate(String strDate) {
		if (strDate == null || strDate.length() == 0) {
			return null;
		}
		Date aDate = null;
		try {
			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception, " + pe.getMessage());
		}
		return aDate;
	}

	/**
	 * 转换字符串日期为日期时间格式
	 * 
	 * @param yyyy-MM-dd HH:mm:ss
	 * @return Date
	 */
	public static Date stringToDateTime(String strDate) {
		if (strDate == null || strDate.length() == 0) {
			return null;
		}
		Date aDate = null;
		try {
			aDate = convertStringToDate(getDateTimePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception, " + pe.getMessage());
		}
		return aDate;
	}
	/**
	 * 获取当前系统时间 返回 12:12:12
	 * 
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {

		return stringToDate(dateString, format, LENIENTDATE);
	}
	/**
	 * 字符串转date
	 * @param dateText
	 * @param format
	 * @param lenient
	 * @return
	 */
	public static Date stringToDate(String dateText, String format,
			boolean lenient) {
		if (dateText == null) {
			return null;
		}
		DateFormat df = null;
		try {
			if (format == null) {
				df = new SimpleDateFormat();
			} else {
				df = new SimpleDateFormat(format);
			}
			df.setLenient(false);
			df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

			return df.parse(dateText);
		} catch (ParseException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public static String TimeMillToDate(String longText) {
		Date date=new Date(Long.parseLong(longText));
		SimpleDateFormat format=new SimpleDateFormat(FORMAT_STR_DATETIME);
		String str=format.format(date);
		return str;
	}
	
	public static String getDateToGMTStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(
				FORMAT_STR_US, Locale.US); 
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));  
		return sdf.format(date);  
	}
	
	public static int getHourBetween(Date sourceDate,Date targetDate){
		if(sourceDate.before(targetDate)){
			return 0;
		}else{
			Calendar cal = Calendar.getInstance();
			cal.setTime(sourceDate);
			long sourceMill = cal.getTimeInMillis();
			cal.setTime(targetDate);
			long targetMill = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((
					sourceMill-targetMill)/(60*60*1000)));
		}
	}
	
	public static int getDayBetween(Date sourceDate,Date targetDate){
		if(sourceDate.before(targetDate)){
			return 0;
		}else{
			Calendar cal = Calendar.getInstance();
			cal.setTime(sourceDate);
			long sourceMill = cal.getTimeInMillis();
			cal.setTime(targetDate);
			long targetMill = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((
					sourceMill-targetMill)/(24*60*60*1000)));
		}
	}
	
	public static long getMinuteBetween(Long startTime,Long endTime){
		return (endTime-startTime)/60000;
	}
	
	public static long getSecondBetween(Long startTime,Long endTime){
		return (endTime-startTime)/1000;
	}
	
	public static int calculateMonthIn(Date date1, Date date2) {
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(date1);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(date2);
		int c = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12
				+ cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
		return c;
	}
	
	public static void main(String[] args){
		Date date1 = stringToDate("2014-08-01");
		Date date2 = stringToDate("2014-07-01");
		System.out.println(calculateMonthIn(date1, date2));
	}
}
