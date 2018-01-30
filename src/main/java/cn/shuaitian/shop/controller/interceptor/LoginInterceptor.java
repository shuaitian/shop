package cn.shuaitian.shop.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;

/**
 * 登录管理(没有使用，现在使用AuthAdvice完成此功能)
 * @author shuai
 *
 */
@Deprecated
public class LoginInterceptor extends InterceptorSupport {
	/**
	 * 判断是否登录
	 * @param request
	 * @return
	 */
	private boolean assertLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("user") == null)
			return false;
		
		return true;
	}
	

	@Override
	protected void init() {
		String[] methodNames = {
		};
		addInterceptMethodNames(methodNames);
	}


	@Override
	protected boolean doPreIntercept(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception{
		if(!assertLogin(request)) {
			request.setAttribute("info", "请先登录!");
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
			return false;
		}
		return true;
	}
	
}
