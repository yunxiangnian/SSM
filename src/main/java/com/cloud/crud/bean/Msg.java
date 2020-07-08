package com.cloud.crud.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回的类
 *
 */
public class Msg {
	
	//状态码 100 - 成功 200 失败
	private int code;
	//提示信息
	private String name;
	
	//用户要返回给浏览器的数据
	private Map<String , Object> extend = new HashMap<String, Object>();

	//处理成功
	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setName("处理成功！");
		return result;
		
	}
	
	//处理失败
	public static Msg fail() {
		Msg result = new Msg();
		result.setCode(200);
		result.setName("处理失败！");
		return result;
		
	}
	
	//Msg可以链式操作
	public Msg add(String key,Object value) {
		this.getExtend().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
	
	
}
