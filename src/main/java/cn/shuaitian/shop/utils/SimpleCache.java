package cn.shuaitian.shop.utils;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SimpleCache {
	
	public static final AtomicLong totalItem = new AtomicLong(0);
	
	public static final ThreadLocal<HttpSession> session = new ThreadLocal<>();
	
	public static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
}
