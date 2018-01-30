package cn.shuaitian.shop.controller.resp.entity;

/**
 * 返回json数据的基类，所有自定义的返回json类型都必须继承自此类。
 * @author shuai
 *
 */
public class BaseResp {
	private Integer status;
	private String error;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
