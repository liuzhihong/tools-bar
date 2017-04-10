package com.liu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil extends DateUtils {

	private static Logger log = LoggerFactory.getLogger(DateUtils.class);
	private static String format = "yyyy-MM-dd HH:mm:ss";
	private static final java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("yyyy-MM");
	
	public static String formatDate(Date date){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String formatDate(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String formatDateYYYYMMDD(Date date){
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String formatDateDD(Date date){
		return new SimpleDateFormat("dd").format(date);
	}
	
	public static String formatMonth(Date date){
		return monthFormat.format(date);
	}
	
	public static String format_yyyyMMddHHmmssSSS(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
	}
	
	public static String format_yyyyMMddHHmm(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmm").format(date);
	}
	
	public static String format_yyyyMMdd(Date date) throws ParseException {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	public static Date getDateFromyyyyMMddHHmm(String date) throws ParseException {
		return new SimpleDateFormat("yyyyMMddHHmm").parse(date);
	}
	
	/**
	 * 
	 * @param date
	 * @return 某天是当月的第几天
	 */
	public static int getDateOrderOfMonth(Date date){
		return Integer.parseInt(new java.text.SimpleDateFormat("dd").format(date));
	}
	
	public static Date parseDate(String date) throws ParseException{
		return new SimpleDateFormat(format).parse(date);
	}
	
	public static Date getDateFromyyyyMMdd(String date) throws ParseException{
		return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	}
	
	public static Date getDateFromyyyyMMddWithoutLine(String date) throws ParseException{
		return new SimpleDateFormat("yyyyMMdd").parse(date);
	}
	
	public static String format_yyyyMMddHHmmss(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	public static Date getDateFromyyyyMMddHHmmss(String date) throws ParseException {
		return new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
	}
	
	public static List<Date[]> splitTimeByHours(Date start, Date end, int hours) {
		List<Date[]> dl = new ArrayList<Date[]>();
		while (start.compareTo(end) < 0) {
			Date _end = addHours(start, hours);
			if (_end.compareTo(end) > 0) {
				_end = end;
			}
			Date[] dates = new Date[] { (Date) start.clone(),
					(Date) _end.clone() };
			dl.add(dates);

			start = _end;
		}
		return dl;
	}

	public static Date getTimeOf000000() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 0);
		return cal.getTime();
	}

	public static Date getYestoday235959() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimeOf000000());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, -1);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 0);
		return cal.getTime();
	}

	public static Date addMinutes(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, amount);
		return c.getTime();
	}
	
	public static Date addHours(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, amount);
		return c.getTime();
	}
	
	public static Date addDays(Date date, int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}
	
	public static Date addWeeks(Date date, int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_MONTH, amount);
		return c.getTime();
	}
	
	public static Date addMonths(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, amount);
		return c.getTime();
	}
	
	public static Date addYears(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, amount);
		return c.getTime();
	}
	
	public static Date getYestoday(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, amount);
		return c.getTime();
	}

	public static Date getFirstDayOfTheMonth(Date date) {
		String theFirstDayOfTheMonth = formatMonth(date) + "-01 00:00:00";
		Date theFirstDayOfTheMonthDate = null;
		try {
			theFirstDayOfTheMonthDate = parseDate(theFirstDayOfTheMonth);
		} catch (ParseException e) {
			log.error("获取当月第一天  异常:",e);
		}
		return theFirstDayOfTheMonthDate;
	}
	
	/**
	 * 
	 * @param date
	 * @return 月的天数
	 */
	public static int getDayCountOfTheMonth(Date date) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(date);
		int day=aCalendar.getActualMaximum(Calendar.DATE);
		return day;
	}
	
	/**
	 * 判断是否超过时间范围
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param range
	 *            时间范围，毫秒
	 * @return
	 */
	public static boolean isExceedRange(Date start, Date end, long range) {
		if (start == null || end == null) {
			return true;
		}
		long startMil = start.getTime();
		long endMil = end.getTime();
		long delta = endMil - startMil;
		return delta > range;
	}

	/**
	 * 时间段是否超过一天
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isExceedRang1Day(Date start, Date end) {
		return isExceedRange(start, end, RANG_ONE_DAY);
	}

   /**
     * 根据开始时间和结束时间返回时间段内的日期集合
     *
     * @param beginDate
     * @param endDate
     * @return List
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        if (beginDate.compareTo(endDate) <= 0) {
            lDate.add(beginDate);// 把开始时间加入集合
        }
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
            	if (!formatDate(endDate, "yyyy-MM-dd").equals(formatDate(cal.getTime(),"yyyy-MM-dd"))){
            		lDate.add(cal.getTime());
            	}
            } else {
                break;
            }
        }
        //如果不是同一天
        if (!formatDate(beginDate, "yyyy-MM-dd").equals(formatDate(endDate, "yyyy-MM-dd"))) {
            lDate.add(endDate);// 把结束时间加入集合
        }
        return lDate;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的小时集合
     *
     * @param beginDate
     * @param endDate
     * @return List
     */
    public static List<Date> getHoursBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        if (beginDate.compareTo(endDate) <= 0) {
            lDate.add(beginDate);// 把开始时间加入集合
        }
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.HOUR_OF_DAY, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
            	if (!formatDate(endDate, "yyyy-MM-dd HH").equals(formatDate(cal.getTime(),"yyyy-MM-dd HH"))){
            		lDate.add(cal.getTime());
            	}
            } else {
                break;
            }
        }
        //如果不是同一个小时
        if (!formatDate(beginDate, "yyyy-MM-dd HH").equals(formatDate(endDate, "yyyy-MM-dd HH"))) {
            lDate.add(endDate);// 把结束时间加入集合
        }
        return lDate;
    }
    
    //获得当天0点时间
    public static Date getTimesmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获得当天24点时间
    public static Date getTimesnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获得本周一0点时间
    public static Date getTimesWeekmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    //获得本周日24点时间
    public static Date getTimesWeeknight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    //获得本月第一天0点时间
    public static Date getTimesMonthmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    //获得本月最后一天24点时间
    public static Date getTimesMonthnight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }
    
    /**
     * @author 李易成   yicheng.li@midea.com.cn
     * @Description:两时间差距
     * @date 2015年9月24日
     * @param fromDate 起时间
     * @param toDate
     * @param type field the field from <code>Calendar</code>
     * @return
     */
    public static long getCeilingGap(Date fromDate,Date toDate,int type) {
        long fromDateMilli = fromDate.getTime();
        long toDateMilli = toDate.getTime();
        long minus = toDateMilli - fromDateMilli;
        long result = 0;
        long remainder = 0;
        if (Calendar.SECOND == type) {
        	result = minus/1000;
        	remainder = minus%1000;
        }
        if (Calendar.MINUTE == type) {
        	result = minus/(1000*60);
        	remainder = minus%(1000*60);
        }
        if (Calendar.HOUR == type) {
        	result = minus/(1000*3600);
        	remainder = minus%(1000*3600);
        }
        if (Calendar.DATE == type) {
        	result = minus/(1000*3600*24);
        	remainder = minus%(1000*3600*24);
        }
        
        if (remainder > 0) {
    		result +=1;
    	}
    	if (remainder < 0) {
    		result -=1;
    	}

        return result;
    }

	/**
	 * 一天的毫秒数
	 */
	public static final long RANG_ONE_DAY = 86400000L;
}
