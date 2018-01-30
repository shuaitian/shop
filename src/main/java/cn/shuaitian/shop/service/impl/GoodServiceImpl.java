package cn.shuaitian.shop.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.shuaitian.shop.entity.Good;
import cn.shuaitian.shop.mapper.GoodMapper;
import cn.shuaitian.shop.service.GoodService;

@Service("goodService")
public class GoodServiceImpl implements GoodService{
	@Autowired
	private GoodMapper goodMapper;
	
	@Override
	public void add(Good good) {
		goodMapper.insertSelective(good);
	}

	@Override
	public List<Good> findByPage(int curPage, int itemCount) {
		return goodMapper.selectByPage(curPage, itemCount);
	}

	@Override
	public long getCount() {
		return goodMapper.countByExample(null);
	}

	@Override
	public Good findById(Integer id) {
		return goodMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Good good) {
		goodMapper.updateByPrimaryKeySelective(good);
	}

	@Override
	public void delete(Integer id) {
		goodMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer getPrice(Integer id) {
		return goodMapper.selectPrice(id);
	}

	@Override
	public List<Good> getUnBuy(int curPage, int itemCount, int userID) {
		return goodMapper.getGoodsWithoutUserID(curPage,itemCount,userID);
	}

	@Override
	public Set<Integer> getBuyed(int userID) {
		return goodMapper.getGoodsWitUserID(userID);
	}

}
