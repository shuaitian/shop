package cn.shuaitian.shop.service;

import java.util.List;
import java.util.Set;

import cn.shuaitian.shop.entity.Good;

public interface GoodService {
	void add(Good good);
	
	List<Good> findByPage(int curPage,int itemCount);
	
	long getCount();
	
	Good findById(Integer id);

	void update(Good good);
	
	void delete(Integer id);
	
	Integer getPrice(Integer id);
	
	List<Good> getUnBuy(int curPage,int itemCount,int userID);
	
	Set<Integer> getBuyed(int userID);
}
