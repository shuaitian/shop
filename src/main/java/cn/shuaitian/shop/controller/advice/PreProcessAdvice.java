package cn.shuaitian.shop.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.shuaitian.shop.controller.util.WebUtils;
import cn.shuaitian.shop.service.CartService;
import cn.shuaitian.shop.utils.SimpleCache;

@Component
@Aspect
public class PreProcessAdvice implements Ordered{
	
	@Autowired
	private CartService cartService; 
	
	@Before("execution(* cn.shuaitian.shop.controller.*.*(..))")
	public void setCartTotalPrice(JoinPoint jp) {
		if(!WebUtils.isReturnJSON((MethodSignature) jp.getSignature()))
			SimpleCache.request.get().setAttribute("totalPrice", cartService.getTotalPrice());
	}
	
	@Before("execution(* cn.shuaitian.shop.controller.*.*(..))")
	public void setCurURI(JoinPoint jp) {
		HttpServletRequest request = SimpleCache.request.get();
		String uri = request.getRequestURI();
		String qs = request.getQueryString();
		if(StringUtils.isEmpty(qs))
			qs = "";
		request.setAttribute("curURI", uri + "?" + qs);
		request.setAttribute("curURIWithoutQS", uri);
	}

	@Override
	public int getOrder() {
		return 2;
	}
}
