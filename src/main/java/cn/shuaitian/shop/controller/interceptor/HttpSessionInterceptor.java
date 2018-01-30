package cn.shuaitian.shop.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.shuaitian.shop.utils.SimpleCache;


/**
 * ���������д���������ִ��֮ǰ����HttpSession�����HttpServletRequest������л��棬���ڴ���֮�󣬽�������
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
