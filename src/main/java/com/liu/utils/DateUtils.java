package com.liu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class DateUtils {

    public static Date getDateFromStr(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(str);
        return date;
    }

    public static Date getDateFromyyyyMM(String date)  {
        try {
            return new SimpleDateFormat("yyyy-MM").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getOldDate(Date... dates) {

        TreeSet<Date> dateTreeSet = new TreeSet<Date>();
        for (Date date : dates) {
            if (date != null) dateTreeSet.add(date);
        }
        return dateTreeSet.first();
    }

    public static Date getLastDate(Date... dates) {

        TreeSet<Date> dateTreeSet = new TreeSet<Date>();
        for (Date date : dates) {
            if (date != null) dateTreeSet.add(date);
        }
        return dateTreeSet.last();
    }

    public static Date getDayFromStr(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(str);
        return date;
    }

    public static String getDateStr(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String getDateStr(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(date);
    }

    public static String getDayStr(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getDayFromStr("2015-12-10"));
        ;
    }


}
