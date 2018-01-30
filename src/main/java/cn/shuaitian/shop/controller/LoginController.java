package cn.shuaitian.shop.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.shuaitian.shop.controller.resp.entity.LoginResp;
import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.entity.UserType;
import cn.shuaitian.shop.service.GoodService;
import cn.shuaitian.shop.service.LoginService;
import cn.shuaitian.shop.utils.SimpleCache;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GoodService goodService;

	@RequestMapping(value = "loginjson", method = RequestMethod.POST)
	@ResponseBody
	public LoginResp loginJSON(String username, String password, HttpSession session) {
		LoginResp resp = new LoginResp();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			resp.setStatus(601);
			resp.setError("用户名或密码不能为空");
			return resp;
		}
		
		username = username.trim();
		password = password.trim();

		User user = loginService.login(username, password);
		if (user == null) {
			resp.setStatus(602);
			resp.setError("用户名或密码错误");
			return resp;
		}
		
		loadCache(user);
		session.setAttribute("user", user);
		resp.setStatus(200);
		return resp;
	}
	
	/**
	 * 加载当前用户可能需要用到的一些数据,包括：
	 * 1、用户已经购买过所有商品的ID
	 */
	private void loadCache(User user) {
		if(user.getType() == UserType.BUYER) {
			HttpSession session = SimpleCache.session.get();
			Set<Integer> buyedIds = goodService.getBuyed(user.getId());
			session.removeAttribute("buyedIds");
			session.setAttribute("buyedIds", buyedIds);
		}
	}

}
