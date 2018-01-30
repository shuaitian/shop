package cn.shuaitian.shop.entity;

public class CartItem {
	private Integer id;
	private Integer count;
	private Integer price;
	
	private Good good;
	
	public Good getGood() {
		return good;
	}
	public void setGood(Good good) {
		this.good = good;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "CartItem [id=" + id + ", count=" + count + ", price=" + price + "]";
	}
	
	
}
