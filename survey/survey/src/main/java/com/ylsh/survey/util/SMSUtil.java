package com.ylsh.survey.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @description:短信验证码工具类
 * @author ylsh
 * @date 2017年12月29日 上午11:19:58
 */
public class SMSUtil {
	/** 身份ID */
	private static String APP_ID;
	/** 密匙 */
	private static String API_KEY;
	/** 接口 */
	private static String URL;


	/**
	 * @description: 发送验证码
	 * @author ylsh
	 * @date 2017年12月29日 下午1:12:48
	 * @param verifyCode
	 * @param phone
	 * @return
	 */
	public static boolean sendSMS(String verifyCode, String phone) {
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(URL);

		client.getParams().setContentCharset("GBK");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

	    String content = new String("您的验证码是：" + verifyCode + "，请不要把验证码泄露给其他人。");

	    // 封装数据
		NameValuePair[] data = {
		    new NameValuePair("account", APP_ID),
		    new NameValuePair("password", API_KEY),
		    new NameValuePair("mobile", phone), 
		    new NameValuePair("content", content),
		};
		method.setRequestBody(data);

		try {
			// 执行请求
			client.executeMethod(method);
			
			// 解析返回数据
			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
/*			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
*/
			if("2".equals(code)){
				System.out.println("短信提交成功");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public void setAPP_ID(String appId) { APP_ID = appId; }

	public void setAPI_KEY(String appKey) { API_KEY = appKey; }

	public void setURL(String url) { URL = url; }


}
