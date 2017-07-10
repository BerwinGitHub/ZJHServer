package com.mjoys.zjh.data;

import com.mjoys.zjh.utility.UserDefault;

public class UIData {

	private String projectRoot = UserDefault.getIntance().get("project_root",
			"/");
	private String cocos2dRoot = UserDefault.getIntance().get("cocos2d_root",
			"/");
	private String cmd = UserDefault.getIntance().get("cmd",
			"cocos jscompile -h");

	// pngquant --quality=65-80 /Users/Berwin/Desktop/temp.png -o
	// /Users/Berwin/Desktop/temp_c.png --speed 1
	private String pngquantCmd = UserDefault.getIntance().get("pngquantCmd",
			"pngquant");

	private static UIData instance = null;

	private UIData() {
	}

	public static synchronized UIData getInstance() {
		if (instance == null) {
			instance = new UIData();
		}
		return instance;
	}

	public String getProjectRoot() {
		return projectRoot;
	}

	public void setProjectRoot(String projectRoot) {
		this.projectRoot = projectRoot;
		UserDefault.getIntance().set("project_root", projectRoot);
	}

	public String getCocos2dRoot() {
		return cocos2dRoot;
	}

	public void setCocos2dRoot(String cocos2dRoot) {
		this.cocos2dRoot = cocos2dRoot;
		UserDefault.getIntance().set("cocos2d_root", cocos2dRoot);
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
		UserDefault.getIntance().set("cmd", cmd);
	}

	public String getPngquantCmd() {
		return pngquantCmd;
	}

	public void setPngquantCmd(String pngquantCmd) {
		this.pngquantCmd = pngquantCmd;
		UserDefault.getIntance().set("pngquantCmd", pngquantCmd);
	}

}
