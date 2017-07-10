package com.mjoys.zjh.confgs;

import java.util.HashMap;
import java.util.Map;

import com.mjoys.zjh.utility.FileUtility;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class Language {

	private static Map<String, String> languageMap;

	static {
		// 读取配置文件
		String content = FileUtility.readFromFile((String) Configs
				.stringValue("file_language"));
		if ("".equals(content)) {
			languageMap = new HashMap<String, String>();
		} else {
			JSONObject jo = JSONObject.fromObject(content);
			languageMap = (Map<String, String>) JSONObject
					.toBean(jo, Map.class);
		}
	}

	public static String get(String key) {
		return languageMap.get(key);
	}
}
