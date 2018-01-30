package cn.shuaitian.shop.controller.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

import cn.shuaitian.shop.controller.util.AuthUtils;

/**
 * 用拦截器实现的权限管理（没有使用，现在使用AuthAdvice完成此功能）
 * @author shuai
 */
@Deprecated
public class AuthInterceptor extends InterceptorSupport{
	
	/**
	 *这里的两个HashSet的线程安全问题请参见InterceptrotSupport中interceptMethods的注释。 
	 */
	private final Set<String> sellerMethods = new HashSet<>();
	private final Set<String> buyerMethods = new HashSet<>();
	
	@Override
	protected void init() {
		String[] sellerMethodsArr = {
			"publish",
			"addGood"
		};
		String[] buyerMethodsArr = {
			
		};
		
		addInterceptMethodNames(sellerMethodsArr);
		addInterceptMethodNames(buyerMethodsArr);
		
		sellerMethods.addAll(Arrays.asList(sellerMethodsArr));
		buyerMethods.addAll(Arrays.asList(buyerMethodsArr));
	}

	@Override
	protected boolean doPreIntercept(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler)
			throws Exception {
		if(buyerMethods.contains(handler.getMethod().getName()) && !assertBuyer(request)) {
			foward(request,response,"只有买家可以访问");
			return false;
		}
		if(sellerMethods.contains(handler.getMethod().getName()) && !assertSeller(request)) {
			foward(request, response, "只有卖家可以访问");
			return false;
		}
		
		return true;
	}
	
	private void foward(HttpServletRequest request,HttpServletResponse response,String info) throws Exception {
		request.setAttribute("info", info);
		request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
	}
	
	private boolean assertSeller(HttpServletRequest request) {
		return AuthUtils.assertSeller(request.getSession());
	}
	
	private boolean assertBuyer(HttpServletRequest request) {
		return AuthUtils.assertBuyer(request.getSession());
	}
}
