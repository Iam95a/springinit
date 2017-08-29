package com.chen.common;

public class Result<T> {
	private boolean isOk;
	private int code;// 代码
	private String message;// 消息
	private T data;// 数据

	public Result() {
	}

	public Result(boolean isOk, int code, String message, T data) {
		this.isOk = isOk;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
