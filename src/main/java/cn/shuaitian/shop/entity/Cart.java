package cn.shuaitian.shop.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private final List<CartItem> items = new ArrayList<>();
	
	public void addItem(CartItem cartItem) {
		for(CartItem item : items) {
			if(cartItem.getId() == item.getId()) {
				item.setCount(item.getCount() + 1);
				return ;
			}
		}
		cartItem.setCount(1);
		items.add(cartItem);
	}
	
	public void addItem(CartItem cartItem,int count) {
		for(CartItem item : items) {
			if(cartItem.getId() == item.getId()) {
				item.setCount(item.getCount() + count);
				return ;
			}
		}
		
		cartItem.setCount(count);
		items.add(cartItem);
	}
	
	public void subItem(CartItem cartItem) {
		for(CartItem item : items) {
			if(cartItem.getId() == item.getId()) {
				if(cartItem.getCount() <= 0)
					return ;
				item.setCount(item.getCount()-1);
			}
		}
	}
	
	public void setItemWithCount(CartItem cartItem,int count) {
		for(CartItem item : items) {
			if(cartItem.getId() == item.getId()) {
				item.setCount(count);
				return ;
			}
		}
		
		cartItem.setCount(count);
		items.add(cartItem);
	}
	
	
	
	
	public List<CartItem> getItems(){
		return this.items;
	}

	public void deleteByID(int id) {
		for(CartItem item : items) {
			if(item.getId() == id) {
				items.remove(item);
				return ;
			}
		}
	}

	public Integer getTotalPrice() {
		Integer total = 0;
		
		for(CartItem item : items) {
			total += (item.getPrice()*item.getCount());
		}
		
		return total;
	}
}
