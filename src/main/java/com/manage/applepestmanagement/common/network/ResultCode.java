package com.manage.applepestmanagement.common.network;

/**
 * 结果编码集合
 * @author wx
 *
 */
public enum ResultCode {

	/**
	 * success,请求成功返回码
	 */
	C10000(ResultStatus.SUCCESS, 10000),
	/**
	 * error,请求失败，通用返回码
	 */
	C20000(ResultStatus.ERROR, 20000),
	/**
	 * error,请求失败，参数类错误
	 */
	C20101(ResultStatus.ERROR, 20101),
	/**
	 * error,请求失败，服务端错误
	 */
	C20201(ResultStatus.ERROR, 20201),
	/**
	 * error,请求失败，网络不可达错误
	 */
	C20301(ResultStatus.ERROR, 20301),
	/**
	 * error,用户名或密码错误
	 */
	C30001(ResultStatus.ERROR, 30001),
	/**
	 * error,验证码错误
	 */
	C30002(ResultStatus.ERROR, 30002),
	/**
	 * error,账号被冻结（账号状态问题）
	 */
	C30101(ResultStatus.ERROR, 30101),
	/**
	 * error,账号被锁定（账号状态问题）
	 */
	C30102(ResultStatus.ERROR, 30102),
	/**
	 * error,请登录（账号状态问题）
	 */
	C30103(ResultStatus.ERROR, 30103);

	/**
	 * 状态字符串描述
	 */
	private String status;
	/**
	 * 状态编码
	 */
	private int code;

	private ResultCode(ResultStatus status, int code) {
		this.status = status.toString();
		this.code = code;
	}

	public String status() {
		return this.status;
	}

	public int code() {
		return this.code;
	}
}
