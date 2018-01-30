package cn.shuaitian.shop.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.shuaitian.shop.entity.OrderItem;
import cn.shuaitian.shop.entity.OrderItemExample;
import cn.shuaitian.shop.mapper.OrderItemMapper;
import cn.shuaitian.shop.service.OrderItemService;

@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemMapper itemMapper;
	
	@Override
	public void add(OrderItem item) {
		itemMapper.insertSelective(item);
	}

	@Override
	public List<OrderItem> findByPage(Integer userID,Integer curPage, Integer itemCount) {
		return itemMapper.getByPage(userID,curPage, itemCount);
	}

	@Override
	public Integer getCountByUserID(Integer userID) {
		return itemMapper.getCountByUserID(userID);
	}

	@Override
	public Set<Integer> getSelledItems() {
		return itemMapper.getDistinctGoodIds();
	}

	@Override
	public List<OrderItem> findByGoodIdAndUserId(int userId,int goodId) {
		OrderItemExample example = new OrderItemExample();
		example.createCriteria()
			.andUserIdEqualTo(userId)
			.andGoodIdEqualTo(goodId);
		
		return itemMapper.selectByExample(example);
	}

	@Override
	public Integer getSelledCountByGoodId(int goodId) {
		return itemMapper.getCountByGoodId(goodId);
	}

}
