package cn.shuaitian.shop.controller.util;

import javax.servlet.http.HttpSession;

import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.entity.UserType;

public class AuthUtils {
	public static boolean assertLogin(HttpSession session) {
		if(session.getAttribute("user") == null)
			return false;
		return true;
	}
	
	public static boolean assertSeller(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null)
			return false;
		
		return user.getType() == UserType.SELLER;
	}
	
	public static boolean assertBuyer(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null)
			return false;
		
		return user.getType() == UserType.BUYER;
	}
}
