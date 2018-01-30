package cn.shuaitian.shop.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;

/**
 * ��¼����(û��ʹ�ã�����ʹ��AuthAdvice��ɴ˹���)
 * @author shuai
 *
 */
@Deprecated
public class LoginInterceptor extends InterceptorSupport {
	/**
	 * �ж��Ƿ��¼
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
			request.setAttribute("info", "���ȵ�¼!");
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
			return false;
		}
		return true;
	}
	
}
