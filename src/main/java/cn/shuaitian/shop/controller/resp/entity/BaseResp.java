package cn.shuaitian.shop.controller.resp.entity;

/**
 * ����json���ݵĻ��࣬�����Զ���ķ���json���Ͷ�����̳��Դ��ࡣ
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
