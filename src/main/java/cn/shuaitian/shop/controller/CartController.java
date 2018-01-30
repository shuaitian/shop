package cn.shuaitian.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.shuaitian.shop.controller.annotation.Auth;
import cn.shuaitian.shop.controller.resp.entity.AddCartResp;
import cn.shuaitian.shop.controller.util.WebUtils;
import cn.shuaitian.shop.entity.CartItem;
import cn.shuaitian.shop.entity.Good;
import cn.shuaitian.shop.entity.UserType;
import cn.shuaitian.shop.service.CartService;
import cn.shuaitian.shop.service.GoodService;

@Controller
@RequestMapping("cart")
public class CartController {
	
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private CartService httpSessionCartService;
	
	@RequestMapping("add")
	@ResponseBody
	@Auth(identity=UserType.BUYER)
	public AddCartResp addCart(@RequestParam(value="id",required=false) String id,@RequestParam(value="count",required=false) String count) {
		AddCartResp resp = new AddCartResp();
		if(!assertArgNotEmpty(id, count, resp))
			return resp;
		
		CartRequestArg arg = null;
		if((arg = getArgAndValidate(id, count, resp)) == null)
			return resp;
		
		Good good = null;
		if((good = getGoodAndAssertExist(arg.id, resp))==null)
			return resp;
		
		//设置显示的图片
		good.setIndexImg(WebUtils.getIndexImage(good.getPicurl()));
				
		CartItem cartItem = createCartItem(good);
		httpSessionCartService.addItem(cartItem,arg.count);
		
		resp.setTotalPrice(httpSessionCartService.getTotalPrice());
		resp.setStatus(200);		
		return resp;
	}
	
	@RequestMapping(value="set")
	@ResponseBody
	@Auth(identity=UserType.BUYER)
	public AddCartResp set(@RequestParam(value="id",required=false) String id ,@RequestParam(value="count",required=false) String count) {
		AddCartResp resp = new AddCartResp();
		if(!assertArgNotEmpty(id, count, resp))
			return resp;
		
		CartRequestArg arg = null;
		if((arg = getArgAndValidate(id, count, resp)) == null)
			return resp;
		
		Good good = null;
		if((good = getGoodAndAssertExist(arg.id, resp))==null)
			return resp;
		
		CartItem cartItem = createCartItem(good);
		httpSessionCartService.setItemWithCount(cartItem,arg.count);
		
		resp.setTotalPrice(httpSessionCartService.getTotalPrice());
		resp.setStatus(200);
		return resp;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.GET)
	@ResponseBody
	@Auth(identity=UserType.BUYER)
	public AddCartResp delete(@RequestParam(value="id",required=false)String id) {
		AddCartResp resp =new AddCartResp();
		if(!assertArgNotEmpty(id, "not empty", resp))
			return resp;
		
		CartRequestArg arg = null;
		if((arg = getArgAndValidate(id, "1", resp)) == null)
			return resp;
		
		httpSessionCartService.deleteByID(arg.id);
		
		resp.setTotalPrice(httpSessionCartService.getTotalPrice());
		resp.setStatus(200);
		return resp;
	}
	
	@RequestMapping(value="clear",method=RequestMethod.GET)
	@ResponseBody
	@Auth(identity=UserType.BUYER)
	public AddCartResp clear() {
		AddCartResp resp = new AddCartResp();
		httpSessionCartService.clear();
		
		resp.setTotalPrice(0);
		resp.setStatus(200);
		return resp;
	}
	
	
	//-------辅助函数
	private boolean assertArgNotEmpty(String id,String count,AddCartResp resp) {
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(count)) {
			resp.setStatus(2000);
			resp.setError("参数不能为空");
			return false;
		}
		return true;
	}
	
	private Good getGoodAndAssertExist(int id,AddCartResp resp) {
		Good good = goodService.findById(id);
		if(good == null) {
			resp.setStatus(2002);
			resp.setError("商品不存在");
			return null;
		}
		return good;
	}
	
	private CartItem createCartItem(Good good) {
		CartItem  item = new CartItem();
		item.setId(good.getId());
		item.setPrice(good.getPrice());
		item.setGood(good);
		return item;
	}
	
	private CartRequestArg getArgAndValidate(String id,String count,AddCartResp resp) {
		int idi,counti;
		try {
			idi = Integer.parseInt(id);
			counti = Integer.parseInt(count);
		}catch(NumberFormatException e) {
			resp.setStatus(2001);
			resp.setError("参数错误");
			return null;
		}
		
		if(counti <= 0) {
			resp.setStatus(2001);
			resp.setError("商品个数不能小于等于0");
			return null;
		}
		
		if(counti >= 1000) {
			resp.setStatus(2002);
			resp.setError("商品个数太多啦");
			return null;
		}
		
		CartRequestArg ret = new CartRequestArg();
		ret.id = idi;
		ret.count = counti;
		return ret;
	}
	
	private static class CartRequestArg{
		int id;
		int count;
	}
	//~~~~~~~~辅助函数
}
