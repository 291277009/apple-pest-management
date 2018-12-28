package com.manage.applepestmanagement.common.network;

/**
 * 结果数据类型集合
 * @author wx
 *
 */
public enum ResultDataType {

	/**
	 * 对象类型
	 */
	OBJECT("object"),
	/**
	 * 数组类型
	 */
	ARRAY("array");

	private String des;

	private ResultDataType(String des) {
		this.des = des;
	}

	@Override
	public String toString() {
		return this.des;
	}
}
