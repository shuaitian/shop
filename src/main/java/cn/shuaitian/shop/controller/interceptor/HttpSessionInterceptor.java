package cn.shuaitian.shop.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.shuaitian.shop.utils.SimpleCache;


/**
 * 该类在所有处理器处理执行之前，将HttpSession对象和HttpServletRequest对象进行缓存，并在处理之后，进行清理
 * @author shuai
 *
 */
public class HttpSessionInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SimpleCache.session.set(request.getSession());
		SimpleCache.request.set(request);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		SimpleCache.session.remove();
		SimpleCache.request.remove();
	}
	
	
}
