package com.ylsh.survey.common;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @description: 自定义转换器（取出前后空格）
 * @author ylsh
 * @date 2018年7月5日 下午10:13:01
 */
public class CustomerConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		if (source != null) {
			source = source.trim();
			return "".equals(source) ? null : source;
		}
		return null;
	}

}
