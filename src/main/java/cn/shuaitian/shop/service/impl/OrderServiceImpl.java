package cn.shuaitian.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.shuaitian.shop.entity.OrderItem;
import cn.shuaitian.shop.entity.Orders;
import cn.shuaitian.shop.entity.OrdersExample;
import cn.shuaitian.shop.mapper.OrderItemMapper;
import cn.shuaitian.shop.mapper.OrdersMapper;
import cn.shuaitian.shop.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;

	@Override
	public void add(Orders order) {
		ordersMapper.insertAndGetID(order);
		
		int orderID = order.getId();
		for(OrderItem orderItem : order.getOrderItems()) {
			orderItem.setOrderId(orderID);
			orderItemMapper.insertSelective(orderItem);
		}
	}

	@Override
	public List<Orders> getByUserID(Integer userID) {
		OrdersExample example = new OrdersExample();
		example.createCriteria().andUserIdEqualTo(userID);
		return ordersMapper.selectByExample(example);
	}
	
}
