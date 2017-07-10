package com.mjoys.zjh.utility;

import java.util.HashMap;
import java.util.Map;

import com.mjoys.zjh.confgs.Configs;

import net.sf.json.JSONObject;

public class UserDefault {
	private static UserDefault instance = null;
	private Map<String, Object> keyValues = null;

	@SuppressWarnings("unchecked")
	private UserDefault() {
		// 读取配置文件
		String content = FileUtility.readFromFile(Configs
				.stringValue("file_userdefault"));
		if ("".equals(content)) {
			this.keyValues = new HashMap<String, Object>();
		} else {
			JSONObject jo = JSONObject.fromObject(content);
			this.keyValues = (Map<String, Object>) JSONObject.toBean(jo,
					Map.class);
		}
		// 初始化文件
	}

	public synchronized static UserDefault getIntance() {
		if (instance == null) {
			instance = new UserDefault();
		}
		return instance;
	}

	public void set(String key, int value) {
		this.keyValues.put(key, value);
		this.commit();
	}

	public int get(String key, int defaultValue) {
		Object o = this.getObjectByKey(key);
		if (this.getObjectByKey(key) != null) {
			return (int) o;
		}
		return defaultValue;
	}

	public void set(String key, boolean value) {
		this.keyValues.put(key, value);
		this.commit();
	}

	public boolean get(String key, boolean defaultValue) {
		Object o = this.getObjectByKey(key);
		if (this.getObjectByKey(key) != null) {
			return (boolean) o;
		}
		return defaultValue;
	}

	public void set(String key, String value) {
		this.keyValues.put(key, value);
		this.commit();
	}

	public String get(String key, String defaultValue) {
		Object o = this.getObjectByKey(key);
		if (this.getObjectByKey(key) != null) {
			return (String) o;
		}
		return defaultValue;
	}

	private void commit() {
		JSONObject jo = JSONObject.fromObject(this.keyValues);
		FileUtility.writeToFile(Configs.stringValue("file_userdefault"),
				jo.toString(), false);
	}

	private Object getObjectByKey(String key) {
		if (!this.keyValues.containsKey(key)) {
			return null;
		}
		return this.keyValues.get(key);
	}

}
