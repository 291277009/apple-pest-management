package com.manage.applepestmanagement.common.network;

/**
 * 结果状态集合
 * 
 * @author wx
 *
 */
public enum ResultStatus {
	/**
	 * 成功标识
	 */
	SUCCESS("success"),
	/**
	 * 失败标识
	 */
	ERROR("error");

	/**
	 * 状态字符串描述
	 */
	private String des;

	private ResultStatus(String des) {
		this.des = des;
	}

	@Override
	public String toString() {
		return this.des;
	}

}
