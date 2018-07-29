package com.ylsh.survey.util;

import java.text.NumberFormat;

/**
 * @description: 数字格式化工具类
 * @author ylsh
 * @date 2018年4月28日 上午10:13:37 
 */
public class NumberFormatUtil {
	
	private static NumberFormat numberFormat = NumberFormat.getPercentInstance();
	
	static {
		numberFormat.setMinimumFractionDigits(2);  
	}
	

	/**
	 * @description: 将数字格式化为百分比（保留两位小时）
	 * @author ylsh
	 * @date 2018年4月28日 上午10:13:37 
	 * @param decimal
	 * @return
	 */
	public static String toPercent(double decimal) { 
		return numberFormat.format(decimal);  
	}  


}
