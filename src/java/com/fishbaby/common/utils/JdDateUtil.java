/*
package com.fishbaby.common.utils;



import org.apache.tools.ant.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class JdDateUtil extends DateUtils {
	public static final String dayPattern = "yyyy-MM-dd";
	public static final String timePattern = "yyyy-MM-dd HH:mm:ss";
	public static final String timePattern1= "yyyyMMddHHmmss";
	*/
/**
	 * 取得当天时间字符串 yyyy-MM-dd
	 * 
	 * @return
	 *//*

	public static String getToday() {
		return formatDate(new Date(), dayPattern);
	}

	*/
/**
	 * 取得当天时间DAY yyyy-MM-dd
	 * 
	 * @return
	 * @throws ParseException
	 *//*

	public static Date getNowDay() {
		try {
			return parseDate(formatDate(new Date(), dayPattern), dayPattern);
		} catch (ParseException e) {
			// 因为这个异常时直接通过date对象转化，不可能抛出这个异常，所以不处理
			e.printStackTrace();
		}
		return null;
	}
	*/
/**
	 * 取得当天时间DAY yyyyMMddHHmmss
	 *
	 * @return
	 * @throws ParseException
	 *//*

	public static Date getNowTime(String pattern) {
		try {
			return parseDate(formatDate(new Date(), pattern), pattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	*/
/**
	 * 取得昨天时间字符串 yyyy-MM-dd
	 * 
	 * @return
	 *//*

	public static String getYesterdayStr() {
		Date fTime = addDays(new Date(), -1);
		return formatDate(fTime, dayPattern);
	}

	*/
/**
	 * 取得昨天时间 yyyy-MM-dd
	 * 
	 * @return
	 *//*

	public static Date getYesterday() {
		Date fTime = null;
		try {
			fTime = parseDate(formatDate(addDays(new Date(), -1), dayPattern), dayPattern);
		} catch (ParseException e) {
			// 这个异常不可能发生，直接捕捉
			e.printStackTrace();
		}
		return fTime;
	}

	*/
/**
	 * 取得第二天时间DAY yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 *//*

	public static Date getNextDay(String date) throws ParseException {
		Date d = parseDate(date, JdDateUtil.dayPattern);
		return addDays(d, 1);
	}

	*/
/**
	 * 取得第二天时间DAY yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 *//*

	public static Date getNextDay(Date date) throws ParseException {
		return addDays(date, 1);
	}


	*/
/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 *//*

	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	*/
/**
	 * 获得指定日期的前一天 
	 * @param stringDate
	 * @return
	 * @throws ParseException 
	 *//*

	public static String getDayBefore(String stringDate) throws ParseException{ 
		Calendar cal = Calendar.getInstance(); 
		Date date = new SimpleDateFormat(JdDateUtil.dayPattern).parse(stringDate); 
		cal.setTime(date); 
		int day = cal.get(Calendar.DATE); 
		cal.set(Calendar.DATE, day - 1); 
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); 
		return dayBefore; 
	} 
	
    */
/**
	 * 格式化时间
	 *
	 * @param date
	 * @param
	 * @return
	 *//*

    public static String formatDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(dayPattern);
		return sdf.format(date);
	}
	*/
/**
	 * 将时间解析成天 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 *//*

	public static Date parseDay(Date date) throws ParseException {
		return parseDate(formatDate(date, dayPattern), dayPattern);
	}

	*/
/**
	 * 解析时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 *//*

	public static Date parseDate(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if(timePattern.equals(pattern)){
			if(!Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", date)){
				throw new ParseException("type error:"+date,0);
			}
		}
		return sdf.parse(date);
	}
}
*/
