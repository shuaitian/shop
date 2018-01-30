package cn.shuaitian.shop.service;

import cn.shuaitian.shop.entity.User;

public interface LoginService {
	User login(String username,String password);
}
