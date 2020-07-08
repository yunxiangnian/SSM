package com.cloud.crud.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * ͨ�÷��ص���
 *
 */
public class Msg {
	
	//״̬�� 100 - �ɹ� 200 ʧ��
	private int code;
	//��ʾ��Ϣ
	private String name;
	
	//�û�Ҫ���ظ������������
	private Map<String , Object> extend = new HashMap<String, Object>();

	//����ɹ�
	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setName("����ɹ���");
		return result;
		
	}
	
	//����ʧ��
	public static Msg fail() {
		Msg result = new Msg();
		result.setCode(200);
		result.setName("����ʧ�ܣ�");
		return result;
		
	}
	
	//Msg������ʽ����
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
