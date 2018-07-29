package com.ylsh.survey.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @description: 时间格式化工具类
 * @author ylsh
 * @date 2018年4月6日 下午3:28:01
 */
public class TimeFormat {
	
	private static SimpleDateFormat dateFormat;
	private static SimpleDateFormat durationFormat;
	private static SimpleDateFormat timestampFormat;
	
	static {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		
		// 设置格式化器的时区为格林威治时区，否则格式化的结果不对，中国的时间比格林威治时间早8小时
		durationFormat = new SimpleDateFormat("HH小时mm分ss秒", Locale.getDefault());
		durationFormat.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
		
		timestampFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
	}
	
	/**
	 * @description: 格式化日期
	 * @author ylsh
	 * @date 2018年4月6日 下午3:28:17 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}
	
	/**
	 * @description: 格式化时长
	 * @author ylsh
	 * @date 2018年4月6日 下午3:28:29 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDuration(Long timeMillis) {
		return durationFormat.format(timeMillis);
	}
	
	/**
	 * @description: 格式化时长
	 * @author ylsh
	 * @date 2018年4月6日 下午3:35:15 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String formatDuration(Date startTime, Date endTime) {
		assert startTime.getTime() < endTime.getTime();
		return formatDuration(endTime.getTime() - startTime.getTime());
	}
	
	/**
	 * @description: 生成当前时间的时间戳
	 * @author ylsh
	 * @date 2018年4月6日 下午3:28:46 
	 * @return
	 */
	public static String createTimeStamp() {
		return timestampFormat.format(new Date());
	}
	
	public static void main(String[] args) {
		Date d = new Date();
		
		System.err.println(d.getTime());
		
		d.setTime(d.getTime()/1000*1000);
		System.err.println(d.getTime());

		
		System.err.println(formatDuration(d.getTime()));
		
		
		
		
		System.err.println(formatDate(d));
	}
	
	

}
