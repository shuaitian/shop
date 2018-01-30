package cn.shuaitian.shop.service;

import java.util.List;
import java.util.Set;

import cn.shuaitian.shop.entity.OrderItem;

public interface OrderItemService {
	
	void add(OrderItem item);

	List<OrderItem> findByPage(Integer userID,Integer curPage, Integer itemCount);
	
	Integer getCountByUserID(Integer userID);
	
	Set<Integer> getSelledItems();
	
	List<OrderItem> findByGoodIdAndUserId(int userId,int goodId);
	
	Integer getSelledCountByGoodId(int goodId);
}
