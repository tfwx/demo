package com.ylsh.survey.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.ylsh.survey.pojo.TbUser;
import com.ylsh.survey.util.SessionContext;


/**
 * @description: 监听HttpSession的创建与销毁
 * @author ylsh
 * @date 2018年4月4日 下午4:45:13
 */
public class SessionListener implements HttpSessionListener {
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		TbUser user = (TbUser) se.getSession().getAttribute("user");
		if (user != null) {
			String key = "user:" + user.getId();
			SessionContext.remove(key);
		}
	}

}
