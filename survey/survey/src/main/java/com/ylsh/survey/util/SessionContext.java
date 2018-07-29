package com.ylsh.survey.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

/**
 * @description: SessionContext，记录已登陆用户的session
 * @author ylsh
 * @date 2018年4月4日 下午4:33:12
 */
@Component
public class SessionContext {
	
	private static final Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	
	
	public static boolean contains(String key) {
		return sessionMap.containsKey(key);
	}
	
	public static HttpSession get(String key) {
		return sessionMap.containsKey(key) ? sessionMap.get(key) : null;
	}
	
	public static HttpSession put(String key, HttpSession value) {
		return sessionMap.put(key, value);
	}
	
	public static HttpSession remove(String key) {
		return sessionMap.containsKey(key) ? sessionMap.remove(key) : null;
	}
	
	public static int size() {
		return sessionMap.size();
	}
	
	public static void clear() {
		sessionMap.clear();
	}
	

}
