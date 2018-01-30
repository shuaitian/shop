package cn.shuaitian.shop.controller.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.shuaitian.shop.service.GoodService;
import cn.shuaitian.shop.utils.SimpleCache;

@Component
public class Global {
	
	@Autowired
	private GoodService goodService;
	
	@PostConstruct
	public void onApplicationStart() {
		SimpleCache.totalItem.set(goodService.getCount());
	}
}
