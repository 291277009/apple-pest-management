package com.manage.applepestmanagement.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化工具类
 * 
 * @author wx
 *
 */
public class DateFormatUtil {

	private static String pattern = "yyyy-MM-dd HH:mm:ss";

	private DateFormatUtil() {
	}

	/**
	 * 格式转换，Long转String
	 * @param time Long型时间
	 * @return
	 */
	public static String toString(Long time) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(time);
	}

	/**
	 * 格式转换，Long转String
	 * @param time Long型时间
	 * @param pattern 转换格式，默认为"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String toString(Long time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(time);
	}

	/**
	 * 格式转换，String转Long
	 * @param time String型时间，默认格式"yyyy-MM-dd HH:mm:ss"，如"2018-01-01 08:00:00"
	 * @return
	 */
	public static Long toLong(String time) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			Date date = format.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return -1L;
		}
	}

	/**
	 * 格式转换，String转Long
	 * @param time String型时间，默认格式"yyyy-MM-dd HH:mm:ss"，如"2018-01-01 08:00:00"
	 * @param pattern 转换格式，默认为"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static Long toLong(String time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			Date date = format.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return -1L;
		}
	}
}
