package com.ylsh.survey.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 * @description: 
 * @author ylsh
 * @date 2018年2月24日 下午5:11:01 
 */
public class JSONUtil {
	
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	
	/**
	 * @description: 修复json字符串,不转义会破坏json数据格式导致页面读取数据出错(JSON字符串中可能存在引号、斜杠或其它转义字符，在前台直接使用JSON.parse会出错)
	 * 例：{"s":"123'/"} -> {\"s\":\"123\'\/\"}
	 * @author ylsh
	 * @date 2018年2月24日 下午5:11:07 
	 * @param s
	 * @return
	 */
	public static String fixJsonStr(String s) {       
        StringBuffer sb = new StringBuffer ();       
        for (int i=0; i<s.length(); i++) {       
            char c = s.charAt(i);       
            switch (c) {       
                case '\"':       
                    sb.append("\\\"");       
                    break;
                case '\'':       
                    sb.append("\\\'");       
                    break;   
                case '\\':       
                    sb.append("\\\\");       
                    break;       
                case '/':       
                    sb.append("\\/");       
                    break;       
                case '\b':       
                    sb.append("\\b");       
                    break;       
                case '\f':       
                    sb.append("\\f");       
                    break;       
                case '\n':       
                    sb.append("\\n");       
                    break;       
                case '\r':       
                    sb.append("\\r");       
                    break;       
                case '\t':       
                    sb.append("\\t");       
                    break;       
                default:       
                    sb.append(c);       
            }  
       }  
       return sb.toString();       
    }
	
	
	/**
	 * @description: 将对象转为json字符串
	 * @author ylsh
	 * @date 2018年4月10日 下午7:06:51 
	 * @param obj
	 * @return
	 */
	public static String toJSON(Object obj) {
		String jsonStr = null;
		try {
			jsonStr = jsonMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

}
