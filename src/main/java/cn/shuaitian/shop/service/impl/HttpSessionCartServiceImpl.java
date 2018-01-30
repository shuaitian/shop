package cn.shuaitian.shop.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.shuaitian.shop.entity.Cart;
import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.utils.SimpleCache;

@Service("cartService")
public class HttpSessionCartServiceImpl extends AbstractCartService{

	@Override
	protected Cart getCart() {
		HttpSession session = SimpleCache.session.get();
		
		@SuppressWarnings("unchecked")
		//对于同一个sessionID，是有可能被多过用户使用的。比如，同一个浏览器，第一个用户将商品加入了购物车，然后注销。然后第二个用户登录了（或者
		//第一个用户的sessionID被劫持了），这个时候第二个用户的sessionID可能跟前一个用户相同。如果直接将购物车放入session中，可能就
		//回导致第二个用户会看到第一个用户购物车里的商品。我的解决办法是在sessino中存的是map，考虑到这里有并发访问的情况所以使用了ConcurrentHashMap。
		Map<Integer,Cart> cartMap =  (Map<Integer, Cart>) session.getAttribute("cartMap");
		if(cartMap == null) {
			cartMap = new ConcurrentHashMap<>();
			session.setAttribute("cartMap", cartMap);
		}
		
		User user = (User) session.getAttribute("user");
		
		//假如没有登录，新建一个临时用户
		if(user == null) {
			user = new User();
			user.setId(Integer.MAX_VALUE);
		}
		Cart cart = cartMap.get(user.getId());
		if(cart == null) {
			cart = new Cart();
			cartMap.put(user.getId(), cart);
		}
		
		return cart;
	}

}
