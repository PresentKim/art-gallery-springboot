package com.team4.museum.util.ajax;

public class AjaxResult {

	public int code;
	public String message;
	public String url = "";

	public AjaxResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public AjaxResult(int code, String message, String url) {
		this(code, message);
		this.url = url;
	}

	public String toJson() {
		return "{" +
				"\"message\":\"" + message + "\"," +
				"\"url\":\"" + url + "\"" +
				"}";
	}

}