package cn.shuaitian.shop.service;

import java.util.List;

import cn.shuaitian.shop.entity.Orders;

public interface OrderService {
	void add(Orders order);
	List<Orders> getByUserID(Integer userID);
}
