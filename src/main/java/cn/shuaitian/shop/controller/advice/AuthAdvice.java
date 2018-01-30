package cn.shuaitian.shop.controller.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import cn.shuaitian.shop.controller.annotation.Auth;
import cn.shuaitian.shop.controller.resp.entity.BaseResp;
import cn.shuaitian.shop.controller.util.WebUtils;
import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.utils.SimpleCache;

/**
 * 判断用户权限的advice
 * @author shuai
 *
 */

@Component
@Aspect
public class AuthAdvice implements Ordered{
	
	/**
	 * 判断是否登录，在需要登录的方法上添加LoginRequired注解
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(cn.shuaitian.shop.controller.annotation.LoginRequired)")
	public Object login(ProceedingJoinPoint jp) throws Throwable {
		MethodSignature ms = (MethodSignature) jp.getSignature();
		HttpSession session = SimpleCache.session.get();
		if(session.getAttribute("user") != null)
		{
			return jp.proceed();
		}
		else {
			if(WebUtils.isReturnJSON(ms)) {
				return getResp(1011,"用户没有登录",(MethodSignature)jp.getSignature());
			}
			else {
				HttpServletRequest request = SimpleCache.request.get();
				request.setAttribute("info", "用户没有登录");
				return "error";
			}
		}
	}
	
	/**
	 * 判断用户身份是否合法，在需要验证身份的方法上添加Auth注解。
	 * @param jp
	 * @return
	 * @throws Throwable 
	 */
	@Around("@annotation(cn.shuaitian.shop.controller.annotation.Auth)")
	public Object sellerRequired(ProceedingJoinPoint jp) throws Throwable {
		HttpSession session = SimpleCache.session.get();
		User user = (User) session.getAttribute("user");
		MethodSignature ms = (MethodSignature) jp.getSignature();
		if(user == null)
		{
			if(WebUtils.isReturnJSON(ms)) {
				return getResp(1011,"用户没有登录",ms);
			}
			else {
				HttpServletRequest request = SimpleCache.request.get();
				request.setAttribute("info", "用户没有登录");
				return "error";
			}
		}
		Auth  anno = ms.getMethod().getDeclaredAnnotation(Auth.class);
		if(anno != null && user.getType() == anno.identity()) {
			return jp.proceed();
		}
		if(WebUtils.isReturnJSON(ms))
			return getResp(1012,"没有权限执行此操作",ms);
		else {
			HttpServletRequest request = SimpleCache.request.get();
			request.setAttribute("info", "没有权限执行此操作");
			return "error";
		}
	}
	
	private BaseResp getResp(int code,String info,MethodSignature ms) throws InstantiationException, IllegalAccessException {
		Object obj = WebUtils.getReturnInstance(ms);
		BaseResp resp = (BaseResp) obj;
		resp.setStatus(code);
		resp.setError(info);
		return resp;
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
