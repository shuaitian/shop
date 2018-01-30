package cn.shuaitian.shop.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.shuaitian.shop.entity.Cart;
import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.utils.SimpleCache;

@Service("cartService")
public class HttpSessionCartServiceImpl extends AbstractCartService{

	@Override
	protected Cart getCart() {
		HttpSession session = SimpleCache.session.get();
		
		@SuppressWarnings("unchecked")
		//����ͬһ��sessionID�����п��ܱ�����û�ʹ�õġ����磬ͬһ�����������һ���û�����Ʒ�����˹��ﳵ��Ȼ��ע����Ȼ��ڶ����û���¼�ˣ�����
		//��һ���û���sessionID���ٳ��ˣ������ʱ��ڶ����û���sessionID���ܸ�ǰһ���û���ͬ�����ֱ�ӽ����ﳵ����session�У����ܾ�
		//�ص��µڶ����û��ῴ����һ���û����ﳵ�����Ʒ���ҵĽ���취����sessino�д����map�����ǵ������в������ʵ��������ʹ����ConcurrentHashMap��
		Map<Integer,Cart> cartMap =  (Map<Integer, Cart>) session.getAttribute("cartMap");
		if(cartMap == null) {
			cartMap = new ConcurrentHashMap<>();
			session.setAttribute("cartMap", cartMap);
		}
		
		User user = (User) session.getAttribute("user");
		
		//����û�е�¼���½�һ����ʱ�û�
		if(user == null) {
			user = new User();
			user.setId(Integer.MAX_VALUE);
		}
		Cart cart = cartMap.get(user.getId());
		if(cart == null) {
			cart = new Cart();
			cartMap.put(user.getId(), cart);
		}
		
		return cart;
	}

}
