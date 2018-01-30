package cn.shuaitian.shop.service.impl;

import java.util.List;

import cn.shuaitian.shop.entity.Cart;
import cn.shuaitian.shop.entity.CartItem;
import cn.shuaitian.shop.service.CartService;

public abstract class AbstractCartService implements CartService{
	protected abstract Cart getCart();

	@Override
	public void addItem(CartItem item) {
		getCart().addItem(item);
	}

	@Override
	public void setItemWithCount(CartItem item, int count) {
		getCart().setItemWithCount(item, count);
	}

	@Override
	public void subItem(CartItem item) {
		getCart().subItem(item);
	}

	@Override
	public void addItem(CartItem cartItem, int count) { 
		getCart().addItem(cartItem, count);
	}

	@Override
	public List<CartItem> getAllItems() {
		return getCart().getItems();
	}

	@Override
	public Cart get() {
		return getCart();
	} 
	
	@Override
	public void clear() {
		getCart().getItems().clear();
	}
	
	@Override
	public void deleteByID(int id) {
		getCart().deleteByID(id);
	}

	@Override
	public Integer getTotalPrice() {
		return getCart().getTotalPrice();
	}
	
	
}
