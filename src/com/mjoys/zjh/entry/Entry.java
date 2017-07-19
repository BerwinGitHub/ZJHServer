package com.mjoys.zjh.entry;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import com.ericsoft.bmob.restapi.Bmob;
import com.mjoys.zjh.confgs.Configs;
import com.mjoys.zjh.utility.FileUtility;
import com.mjoys.zjh.utility.Utility;
import com.mjoys.zjh.view.MainView;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class Entry {

	public static final String FILE_CONFIGS = "./res/configs/configs.json";
	static {
		// 读取配置文件
		String content = FileUtility.readFromFile(FILE_CONFIGS);
		if ("".equals(content)) {
			Configs.configsMap = new HashMap<String, Object>();
		} else {
			JSONObject jo = JSONObject.fromObject(content);
			Configs.configsMap = (Map<String, Object>) JSONObject.toBean(jo,
					Map.class);
		}

		Dimension d = Utility.getScreenDimension();
		Configs.set("windows_width",
				(int) (d.width * Configs.floatValue("window_scale")));
		Configs.set("windows_height",
				(int) (d.height * Configs.floatValue("window_scale")));
		// 初始化数据库
		Bmob.setTimeout(15 * 1000);
		Bmob.initBmob(Configs.stringValue("db_application_id"),
				Configs.stringValue("db_rest_api_key"));
		Bmob.initMaster(Configs.stringValue("db_master_key"));

	}

	public static void main(String[] args) {
		new MainView().setVisible(true);
	}

}
