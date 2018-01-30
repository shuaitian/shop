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
 * SpringMVC HandlerInterceptor֧���࣬ʵ���˸��ݷ������ƽ������صĻ�������
 * 
 * @author shuaitian
 */
public abstract class InterceptorSupport implements HandlerInterceptor,InitializingBean{
	
	/**
	 * ���ֵֻ��init��ע�᷽�����ƣ���������ʱ��ֻ�����interceptMethods��contains������
	 * ����û��û�в����������⣬��HashSet�ǰ�ȫ�ġ�
	 * 
	 * HashSet�Ĺٷ��ĵ���
	 * If multiple threads access a hash set concurrently, and at least one of
	 * the threads modifies the set, it <i>must</i> be synchronized externally.
	 * ����ָ�����������һ���߳̽����޸� ������Ҫʹ��ͬ��������
	 * ���Ǳ�ֵ֤��init�����Ԫ�أ����������ط�ֻ���������£��Ͳ���Ҫִ��ͬ��������
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
	 * �������ʵ�ֵĳ�ʼ������
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
	 * �������ʵ�ֵ����ط���
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
