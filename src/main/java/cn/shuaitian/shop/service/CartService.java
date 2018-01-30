package cn.shuaitian.shop.service;

import java.util.List;

import cn.shuaitian.shop.entity.Cart;
import cn.shuaitian.shop.entity.CartItem;

public interface CartService {
	void addItem(CartItem item);

	void setItemWithCount(CartItem item, int count);

	void subItem(CartItem item);

	void addItem(CartItem cartItem, int counti);
	
	List<CartItem> getAllItems();
	
	Cart get();
	
	void clear();
	
	void deleteByID(int id);
	
	Integer getTotalPrice();
}
