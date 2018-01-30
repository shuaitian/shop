package cn.shuaitian.shop.controller.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * SpringMVC HandlerInterceptor支持类，实现了根据方法名称进行拦截的基础功能
 * 
 * @author shuaitian
 */
public abstract class InterceptorSupport implements HandlerInterceptor,InitializingBean{
	
	/**
	 * 如果值只在init里注册方面名称，而在拦截时都只会调用interceptMethods的contains方法，
	 * 这里没有没有并发竞争问题，用HashSet是安全的。
	 * 
	 * HashSet的官方文档：
	 * If multiple threads access a hash set concurrently, and at least one of
	 * the threads modifies the set, it <i>must</i> be synchronized externally.
	 * 这里指出如果至少有一个线程进行修改 ，才需要使用同步操作。
	 * 我们保证值在init中添加元素，而在其他地方只会读不会更新，就不需要执行同步操作。
	 */
	private final Set<String> interceptMethods = new HashSet<>();
	
	
	protected InterceptorSupport addInterceptMethodName(String name) {
		interceptMethods.add(name);
		return this;
	}
	
	protected InterceptorSupport addInterceptMethodNames(String...names) {
		interceptMethods.addAll(Arrays.asList(names));
		return this;
	}
	
	

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	/**
	 * 子类进行实现的初始化方法
	 */
	protected abstract void init();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
			
			if(interceptMethods.contains(hm.getMethod().getName())) {
				return doPreIntercept(request,response,hm);
			}
		}
		return true;
	}
	
	/**
	 * 子类进行实现的拦截方法
	 * @param handler 
	 * @param response 
	 * @param request 
	 */
	protected abstract boolean doPreIntercept(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
