package cn.shuaitian.shop.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.shuaitian.shop.controller.annotation.Auth;
import cn.shuaitian.shop.controller.util.WebUtils;
import cn.shuaitian.shop.entity.Cart;
import cn.shuaitian.shop.entity.Good;
import cn.shuaitian.shop.entity.GoodStatus;
import cn.shuaitian.shop.entity.OrderItem;
import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.entity.UserType;
import cn.shuaitian.shop.service.CartService;
import cn.shuaitian.shop.service.GoodService;
import cn.shuaitian.shop.service.LoginService;
import cn.shuaitian.shop.service.OrderItemService;
import cn.shuaitian.shop.utils.SimpleCache;

/**
 * ���з�json���ظ�ʽ�����󶼻�д������
 * 
 * @author shuai
 */

@Controller
public class PageController {

	@Autowired
	private GoodService goodService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderItemService orderItemService;


	@Value("#{prop.INDEX_ITEM_COUNT}")
	private Integer itemCount;
	@Value("#{prop.BUYED_ITEM_COUNT}")
	private Integer buyedItemCount;

	private static final Integer DEFAULT_ITEM_COUNT = 24;
	private static final Integer DEFAULT_CUR_PAGE = 1;

	/**
	 * ��ҳ
	 * 
	 * @param curPage
	 *            ��ǰ�����ҳ�棬Ĭ��ֵΪ1
	 * @param itemCount
	 *            ÿҳ��ʾ��������������settings.xml�����ã�Ĭ��ֵΪ24
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(@RequestParam(value="curPage",required=false)String curPage, @RequestParam(value="itemCount",required=false)String itemCount, ModelMap model,HttpSession session) {
		
		if (this.itemCount == null)
			this.itemCount = DEFAULT_ITEM_COUNT;
		
		int totalCount = (int) SimpleCache.totalItem.get();
		Page page = processPageArg(curPage, itemCount,totalCount);
		
		List<Good> goods = goodService.findByPage(page.curPage, page.itemCount);
		
		User user = WebUtils.currentUser(session);
		if(user != null) {
			updateGoodStatus(goods,user);
			
		}
		
		
		//������Ʒ��ҳ��ʾ��ͼƬ
		for(Good good : goods) {
			good.setIndexImg(WebUtils.getIndexImage(good.getPicurl()));
		}
		
		return indexShow(goods, page, model);
		
	}
	
	/**
	 * ������Ʒ��״̬
	 * @param goods
	 * @param user
	 */
	private void updateGoodStatus(List<Good> goods, User user) {
		if(user.getType() == UserType.BUYER) {
			
			@SuppressWarnings("unche")
			Set<Integer> buyedIds = (Set<Integer>) SimpleCache.session.get().getAttribute("buyedIds");
			
			if(buyedIds == null) {
				buyedIds = goodService.getBuyed(user.getId());
			}
			
			for(Good good : goods) {
				if(buyedIds.contains(good.getId())) {
					good.setStatus(GoodStatus.BUYED);
				}
			}
		}
		else if(user.getType() == UserType.SELLER) {
			Set<Integer> selledIds = orderItemService.getSelledItems();
			for(Good good : goods) {
				if(selledIds.contains(good.getId())) {
					good.setStatus(GoodStatus.SELLED);
					Integer selledCount = orderItemService.getSelledCountByGoodId(good.getId());
					good.setSelledCount(selledCount);
				}
			}
		}
	}

	@RequestMapping(value="unbuy",method=RequestMethod.GET)
	@Auth(identity=UserType.BUYER)
	public String unBuy(@RequestParam(value="curPage",required=false)String curPage, @RequestParam(value="itemCount",required=false)String itemCount, ModelMap model,HttpSession session) {
		if (this.itemCount == null)
			this.itemCount = DEFAULT_ITEM_COUNT;
		
		int totalCount = (int) SimpleCache.totalItem.get();
		Page page = processPageArg(curPage, itemCount,totalCount);
		
		User user = WebUtils.currentUser(session);
		List<Good> goods = goodService.getUnBuy(page.curPage, page.itemCount, user.getId());
		
		//������Ʒ��ҳ��ʾ��ͼƬ
		for(Good good : goods) {
			good.setIndexImg(WebUtils.getIndexImage(good.getPicurl()));
		}
		return indexShow(goods, page, model);
	}
	
	private String indexShow(List<Good> goods,Page page, ModelMap model) {
		model.put("totalPage", page.totalPage);
		model.put("goods", goods);
		model.put("curPage", page.curPage);
		model.put("next", page.next);
		model.put("previous", page.previous);
		return "index";
	}
	
	/**
	 * �����ҳ����
	 * @param curPage
	 * @param itemCount
	 * @param totalCount 
	 * @return
	 */
	private Page processPageArg(String curPage,String itemCount, int totalCount) {
		Page page = new Page();
		try {
			page.itemCount = (!StringUtils.isEmpty(itemCount)) ? Integer.parseInt(itemCount) : this.itemCount;
			page.curPage = (!StringUtils.isEmpty(curPage)) ? Integer.parseInt(curPage) : DEFAULT_CUR_PAGE;
		}catch(NumberFormatException e) {
			page.itemCount=DEFAULT_ITEM_COUNT;
			page.curPage=DEFAULT_CUR_PAGE;
		}
		
		if(page.itemCount <=0 || page.curPage<=0) {
			page.itemCount=DEFAULT_ITEM_COUNT;
			page.curPage=DEFAULT_CUR_PAGE;
		}
		
		page.totalCount = totalCount;
		page.totalPage = (totalCount + page.itemCount - 1) / page.itemCount;
		page.next = (page.curPage + 1) > page.totalPage ? page.totalPage : (page.curPage + 1);
		page.previous = (page.curPage - 1) < 1 ? 1 : (page.curPage - 1);
		
		return page;
	}
	
	private static class Page{
		int curPage;
		int itemCount;
		int totalPage;
		@SuppressWarnings("unused")
		int totalCount;
		int next;
		int previous;
	}

	/**
	 * ��¼ҳ��
	 * 
	 * @param info
	 * @param model
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "account", method = RequestMethod.GET)
	public String account(String info, ModelMap model, String username) {
		if (!StringUtils.isEmpty(info))
			model.put("info", info);
		if (!StringUtils.isEmpty(username))
			model.put("username", username);
		return "account";
	}

	/**
	 * ��¼�ύҳ��
	 * 
	 * @param username
	 * @param password
	 * @param ra
	 * @param model
	 * @param info
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(String username, String password, RedirectAttributes ra, ModelMap model, String info,
			HttpSession session) {
		if (session.getAttribute("user") != null) {
			// �û��Ѿ���¼
			return "account";
		}

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			ra.addAttribute("info", "�û���������Ϊ��");
			ra.addAttribute("username", username);
			return "redirect:account.do";
		}

		User user = loginService.login(username, password);
		if (user == null) {
			ra.addAttribute("info", "�û������������");
			ra.addAttribute("username", username);
			return "redirect:account.do";
		}

		session.setAttribute("user", user);

		return "redirect:index.do";
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			session.removeAttribute("user");
		}
		return "redirect:index.do";
	}

	/**
	 * ��Ʒ������ʾҳ��
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(@RequestParam(value = "id", required = false) String id, ModelMap model,HttpSession session) {
		if (StringUtils.isEmpty(id)) {
			model.put("info", "id����Ϊ��");
			return "error";
		}

		// ��ʼ����
		int idi = Integer.parseInt(id);
		Good good = goodService.findById(idi);
		if (good == null) {
			model.put("info", "��Ʒ������!");
			return "error";
		}
		
		//�ж��Ƿ��Ѿ�����
		User user = WebUtils.currentUser(session);
		if(user != null && user.getType()==UserType.BUYER) {
			@SuppressWarnings("unchecked")
			Set<Integer> buyedIds = (Set<Integer>) session.getAttribute("buyedIds");
			if(buyedIds == null) {
				buyedIds = goodService.getBuyed(user.getId());
			}
			//����״̬�͹���ʱ�ļ۸�
			if(buyedIds.contains(good.getId())) {
				good.setStatus(GoodStatus.BUYED);
				List<OrderItem> items = orderItemService.findByGoodIdAndUserId(user.getId(), good.getId()) ;
				if(items.size() > 0) {
					good.setPrice(items.get(0).getPrice());
				}
			}
		}
		model.put("good", good);

		return "single";
	}

	/**
	 * �����Ʒҳ��
	 * 
	 * @return
	 */
	@RequestMapping(value = "publish", method = RequestMethod.GET)
	@Auth(identity=UserType.SELLER)
	public String publish() {
		return "publish";
	}

	/**
	 * �����Ʒ�ύҳ��
	 * 
	 * @param good
	 * @param bindingResult
	 * @param map
	 * @param pictype
	 * @return
	 */
	@RequestMapping(value = "addgood", method = RequestMethod.POST)
	@Auth(identity=UserType.SELLER)
	public String addGood(@Valid Good good, BindingResult bindingResult, ModelMap map, Integer pictype) {
		if (pictype != null) {
			map.put("pictype", pictype);
		}
		
//		if(!StringUtils.isEmpty(good.getContent())) {
//			good.setContent(good.getContent().replaceAll("\"", "&quot;"));
//		}
		
		if (bindingResult.getErrorCount() > 0) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getDefaultMessage());
				if (map.containsAttribute(error.getField() + "Error"))
					continue;
				sb.append(error.getDefaultMessage() + "; ");
				map.put(error.getField() + "Error", error.getDefaultMessage());
			}
			map.put("allErrors", sb.toString());
			map.put("good", good);
			return "publish";
		}
		// good.setPrice(good.getPrice() * 100);
		if (!WebUtils.processPrice(good)) {
			map.put("priceStrError", "�۸����벻����Ҫ��");
			return "publish";
		}
		goodService.add(good);

		// ������Ʒ����
		SimpleCache.totalItem.incrementAndGet();
		map.addAttribute("info", "��ӳɹ���");
		map.addAttribute("id",good.getId());

		return "addgoodresult";
	}
	
	
	/**
	 * ���ﳵ����
	 * @return
	 */
	@RequestMapping(value="cart",method=RequestMethod.GET)
	@Auth(identity=UserType.BUYER)
	public String cart(ModelMap model,@RequestParam(value="curURI",required=false)String curURI) {
		Cart cart = cartService.get();
		if(cart.getItems().size() != 0)
			model.put("cart", cart);
		
		if(StringUtils.isEmpty(curURI))
			curURI = "index.do";
		
		model.put("lastURI", curURI);
		return "cart";
	}
	
	/**
	 * �ѹ�����Ʒ
	 */
	@RequestMapping(value="buyed",method=RequestMethod.GET)
	@Auth(identity=UserType.BUYER)
	public String buyed(@RequestParam(value="curPage",required=false)String curPage, @RequestParam(value="itemCount",required=false)String itemCount, ModelMap model,HttpSession session) {
		if(this.buyedItemCount == null) {
			this.buyedItemCount = DEFAULT_ITEM_COUNT;
		}
		int cp=0,ic=0;
		try {
			cp = Integer.parseInt(curPage);
		}catch(Exception e) {
			cp = DEFAULT_CUR_PAGE;
		}
		
		try {
			ic = Integer.parseInt(itemCount);
		}catch(Exception e) {
			ic = this.buyedItemCount;
		}
		User user = WebUtils.currentUser(session);
		int totalCount = orderItemService.getCountByUserID(user.getId());
		int totalPage = (totalCount + ic - 1) / ic;
		
		//����ҳ~~~~~~~~~~
		ic = totalCount;
		cp = 1;
		//~~~~~~~~~~~~~~
		List<OrderItem> orderItems = orderItemService.findByPage(user.getId(),cp, ic);
		
		model.put("orderItems", orderItems);
		

		
		model.put("totalPage", totalPage);
		model.put("curPage", cp);
		model.put("next", (cp + 1) > totalPage ? totalPage : (cp + 1));
		model.put("previous", (cp - 1) < 1 ? 1 : (cp - 1));
		
		Integer totalPrice = calcTotalPrice(orderItems);
		model.put("buyedTotalPrice", totalPrice);
		return "buyed";
	}

	/**
	 * �����ѹ�����Ʒ���ܼ�
	 * @param orderItems
	 * @return
	 */
	private Integer calcTotalPrice(List<OrderItem> orderItems) {
		int ret = 0;
		for(OrderItem item : orderItems) {
			ret += (item.getPrice() * item.getCount());
		}
		return ret;
	}
}
