package cn.shuaitian.shop.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.shuaitian.shop.controller.annotation.Auth;
import cn.shuaitian.shop.controller.resp.entity.OrderResp;
import cn.shuaitian.shop.controller.util.WebUtils;
import cn.shuaitian.shop.entity.Cart;
import cn.shuaitian.shop.entity.OrderItem;
import cn.shuaitian.shop.entity.Orders;
import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.entity.UserType;
import cn.shuaitian.shop.service.CartService;
import cn.shuaitian.shop.service.OrderService;
import cn.shuaitian.shop.utils.SimpleCache;

@Controller
@RequestMapping("order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	@ResponseBody
	@Auth(identity=UserType.BUYER)
	public OrderResp create(HttpSession session) {
		OrderResp resp = new OrderResp();
		Cart cart = cartService.get();
		
		if(cart == null || cart.getItems().size() == 0) {
			resp.setStatus(1200);
			resp.setError("当前没有选择任何商品");
			return resp;
		}
		
		
		User user = WebUtils.currentUser(session);
		Orders order = WebUtils.transformCartToOrder(cart, user);
		
		orderService.add(order);
		cartService.clear();
		
		//更新用户已购买商品缓存中的ID
		@SuppressWarnings("unchecked")
		Set<Integer> buyedIds = (Set<Integer>) SimpleCache.session.get().getAttribute("buyedIds");
		if(buyedIds != null) {
			for(OrderItem item : order.getOrderItems()) {
				buyedIds.add(item.getGoodId());
			}
		}
		
		resp.setStatus(200);
		return resp;
	}
}
