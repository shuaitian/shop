package cn.shuaitian.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.entity.UserExample;
import cn.shuaitian.shop.mapper.UserMapper;
import cn.shuaitian.shop.service.LoginService;


@Service("loginService")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User login(String username, String password) {
		UserExample example = new UserExample();
		example.createCriteria()
			.andUsernameEqualTo(username)
			.andPasswordEqualTo(password);
		List<User> users  = userMapper.selectByExample(example);
		if(users !=null && users.size()!=0) {
			return users.get(0);
		}
		return null;
	}

}
