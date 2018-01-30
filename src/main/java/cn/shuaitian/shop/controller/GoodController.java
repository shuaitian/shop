package cn.shuaitian.shop.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.shuaitian.shop.controller.annotation.Auth;
import cn.shuaitian.shop.controller.resp.entity.BaseResp;
import cn.shuaitian.shop.controller.resp.entity.GoodUpdateResp;
import cn.shuaitian.shop.controller.util.WebUtils;
import cn.shuaitian.shop.entity.Good;
import cn.shuaitian.shop.entity.UserType;
import cn.shuaitian.shop.service.GoodService;
import cn.shuaitian.shop.service.OrderItemService;
import cn.shuaitian.shop.utils.SimpleCache;

@Controller
public class GoodController {

	@Autowired
	private GoodService goodService;
	@Autowired
	private OrderItemService orderItemService;

	@RequestMapping(value = "updategood", method = RequestMethod.POST)
	@ResponseBody
	@Auth(identity = UserType.SELLER)
	public GoodUpdateResp updateGood(@Valid Good good, BindingResult bindingResult, HttpSession session) {
		GoodUpdateResp resp = new GoodUpdateResp();
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError error : bindingResult.getAllErrors()) {
				sb.append(error.getDefaultMessage() + "; ");
			}
			resp.setStatus(402);
			resp.setError(sb.toString());
			return resp;
		}

		if (!WebUtils.processPrice(good)) {
			resp.setStatus(403);
			resp.setError("价格输入不符合要求");
			return resp;
		}
		goodService.update(good);
		resp.setStatus(200);

		return resp;
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseBody
	@Auth(identity = UserType.SELLER)
	public BaseResp delete(@RequestParam(value = "id", required = false) String id, HttpSession session) {
		BaseResp resp = new BaseResp();

		if (StringUtils.isEmpty(id)) {
			resp.setStatus(701);
			resp.setError("id不能为空");
			return resp;
		}

		int idi;
		try {
			idi = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			resp.setStatus(702);
			resp.setError("id不合法");
			return resp;
		}
		
		Set<Integer> selledIds = orderItemService.getSelledItems();
		if(selledIds.contains(idi)) {
			resp.setError("不能删除此商品");
			resp.setStatus(703);
			return resp;
		}
		
		goodService.delete(idi);
		resp.setStatus(200);

		// 更新商品总数
		SimpleCache.totalItem.decrementAndGet();
		return resp;
	}

}
