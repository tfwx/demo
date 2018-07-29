package com.ylsh.survey.util;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * @description: MD5加密工具类
 * @author ylsh
 * @date 2018年2月24日 下午5:11:01 
 */
public class MD5Util {
	
	/** MD5盐值	*/
	private static String MD5_SALT;
	
	public void setMD5_SALT(String md5salt) { MD5_SALT = md5salt; }
	
	/**
	 * @description: MD5加密
	 * @author ylsh
	 * @date 2018年2月24日 下午5:11:09 
	 * @param text
	 * @return
	 */
	public static String toMD5(String text) {
		return DigestUtils.md5Hex(text + MD5_SALT);
	}

	/**
	 * @description: 验证MD5密码
	 * @author ylsh
	 * @date 2018年5月20日 下午10:00:33 
	 * @param text
	 * @param md5
	 * @return
	 */
	public static boolean verify(String text, String md5) {
		return toMD5(text).equals(md5);
	}
	
	public static void main(String[] args) {
		MD5Util.toMD5("12345");
	}
}