package com.ylsh.survey.pojo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @description: 问卷来源统计数据封装实体类
 * @author ylsh
 * @date 2018年4月23日 下午7:10:15
 */
public class AreaSourceData extends ArrayList<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public AreaSourceData() {}
	
	/**
	 * @param longitude	经度
	 * @param latitude	纬度
	 * @param value		人数
	 */
	public AreaSourceData(Object longitude, Object latitude, Object value) {
		super(Arrays.asList(longitude, latitude, value));
	}

}
