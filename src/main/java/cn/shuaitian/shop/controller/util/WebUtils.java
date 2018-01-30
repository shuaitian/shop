package cn.shuaitian.shop.controller.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.shuaitian.shop.entity.Cart;
import cn.shuaitian.shop.entity.CartItem;
import cn.shuaitian.shop.entity.Good;
import cn.shuaitian.shop.entity.OrderItem;
import cn.shuaitian.shop.entity.Orders;
import cn.shuaitian.shop.entity.User;

public class WebUtils {
	/**
	 * 对价格进行处理，在数据库中价格是以分为单位存储的，该方法首先会检查priceStr参数是否可以转换成数字，并完成价格单位转换
	 * @param good
	 * @param map
	 * @return
	 */
	public static boolean processPrice(Good good) {
		String priceStr = good.getPriceStr();
		try {
			float priceFloat = Float.parseFloat(priceStr);
			good.setPrice((int)(priceFloat * 100));
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 根据方法的返回类型返回一个该类型的实例
	 * @param code
	 * @param info
	 * @param ms
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object getReturnInstance(MethodSignature ms) throws InstantiationException, IllegalAccessException {
		return ms.getReturnType().newInstance();
	}
	
	
	/**
	 * 根据Method是否有ResponseBody注解判断返回的是ModelAndView还是JSON
	 * @param ms
	 * @return
	 */
	public static boolean isReturnJSON(MethodSignature ms) {
		return ms.getMethod().getDeclaredAnnotation(ResponseBody.class) != null;
	}
	
	/**
	 * 返回首页显示的图片，默认返回第一张
	 * @param picurl
	 * @return
	 */
	public static String getIndexImage(String picurl) {
		try {
			String attr[] = picurl.split(";");
			return attr[0];
		} catch (Exception e) {
		}
		return "";
	}
	
	public static Orders transformCartToOrder(Cart cart,User user) {
		Orders order = new Orders();
		List<OrderItem> orderItems = new ArrayList<>();
		for(CartItem cartItem : cart.getItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setGoodId(cartItem.getId());
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setSummary(cartItem.getGood().getSummary());
			orderItem.setTitle(cartItem.getGood().getTitle());
			orderItem.setUserId(user.getId());
			orderItem.setIndexImg(WebUtils.getIndexImage(cartItem.getGood().getPicurl()));
			orderItems.add(orderItem);
		}
		order.setOrderItems(orderItems);
		order.setPayPrice(cart.getTotalPrice());
		order.setTotalPrice(cart.getTotalPrice());
		order.setUserId(user.getId());
		
		return order;
	}
	
	public static User currentUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return user==null?null:(User)user;
	}
}
