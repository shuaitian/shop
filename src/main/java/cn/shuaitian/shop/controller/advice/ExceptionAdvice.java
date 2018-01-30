package cn.shuaitian.shop.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import cn.shuaitian.shop.controller.resp.entity.BaseResp;
import cn.shuaitian.shop.controller.util.WebUtils;
import cn.shuaitian.shop.utils.SimpleCache;

@Component
@Aspect
public class ExceptionAdvice implements Ordered{
	
	@Around("execution(* cn.shuaitian.shop.controller.*.*(..))")
	public Object around(ProceedingJoinPoint jp) {
		Signature sig = jp.getSignature();
		if(!(sig instanceof MethodSignature))
			return null;
		MethodSignature methodSig = (MethodSignature) sig;
		
		Object ret = null;
		if(!WebUtils.isReturnJSON(methodSig)) {
			//返回view
			try {
				ret = jp.proceed();
			}catch(Throwable e) {
				//TODO log
				e.printStackTrace();
				HttpServletRequest request = SimpleCache.request.get();
				request.setAttribute("info", "系统内部错误");
				return "error";
			}
			
			return ret;
		}
		
		else {
			//返回json
			try {
				ret = jp.proceed();
			}catch(Throwable e) {
				e.printStackTrace();
				//TODO log
				try {
					ret = WebUtils.getReturnInstance(methodSig);
				} catch (InstantiationException | IllegalAccessException e1) {
					//log
				}
				
				BaseResp br = (BaseResp) ret;
				br.setStatus(500);
				br.setError("系统内部错误");
			}
			
			return ret;
		}
	}

	@Override
	public int getOrder() {
		return 3;
	}
}
