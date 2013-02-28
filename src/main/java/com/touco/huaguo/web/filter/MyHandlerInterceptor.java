package com.touco.huaguo.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.touco.huaguo.common.Constants;

public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(MyHandlerInterceptor.class);

	/**
	 * 在controller执行前调用 //发向控制器之前执行的动作 (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String url = request.getRequestURI();
		if (url.contains("/static/") || url.contains("/login") || url.toLowerCase().contains("/adminlogin") || url.contains("/index")) {
			return true;
		}
		if (url.endsWith("captcha-image")) {
			return true;
		}

		if (request.getSession() != null && request.getSession().getAttribute(Constants.ADMIN_SESSION_INFO) != null) {
			return true;
		}
		if (request.getSession() != null && request.getSession().getAttribute(Constants.CUSTOMER_ACCOUNT_SESSION_INFO) != null) {
			return true;
		}

		request.getRequestDispatcher("/login").forward(request, response);
		return false;
	}

	/**
	 * 
	 * //释放资源 (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			// String url = request.getRequestURI();
			// if (url.contains("/static/")) {
			// return ;
			// }
			return;
		} catch (Exception e) {
			logger.error("记录操作日志时:" + e.getMessage());
		}

	}

}
