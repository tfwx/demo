package com.ylsh.survey.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ylsh.survey.dto.ResponseResult;
import com.ylsh.survey.util.JSONUtil;
import com.ylsh.survey.util.RequestUtil;


/**
 * @description:用户认证拦截器
 * @author ylsh
 * @date 2017年12月3日 下午12:54:43
 */
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
					throws Exception {
		// 刚方法在Controller方法return ModelAndView之后，返回客户端之前执行
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// 该方法在Controller方法执行完毕之后，return ModelAndView之前执行，可以操控返回的数据
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		// 该方法在执行Controller方法之前执行 
		/*System.err.println("url: " + request.getRequestURL());
		System.err.println("sessionId: " + request.getRequestedSessionId());*/

		if (request.getSession().getAttribute("user") == null) {
			// 跳转到主页面
			String contextPath = request.getContextPath();
			System.err.println("contextPath:" + "^" + contextPath + "$");
			if (StringUtils.isBlank(contextPath)) {
				contextPath = "/";
			}
			
			if (RequestUtil.isAjaxRequest(request)) {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(JSONUtil.toJSON(ResponseResult.error("请先登录！")));
			} else {
				String returnUrl = request.getRequestURL().append(request.getQueryString() == null ? "" : "?" + request.getQueryString()).toString();
				response.sendRedirect(contextPath + "?returnUrl=" + returnUrl);
			}
			
			// 直接跳转到响应页面，停止调用其它拦截器（若有）
			return false;
		}
		// 继续执行下一拦截器（若有）
		return true;
	}

}
