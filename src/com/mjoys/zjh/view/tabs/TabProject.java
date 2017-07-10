package com.mjoys.zjh.view.tabs;

import javax.swing.JFrame;

import com.mjoys.zjh.confgs.Language;
import com.mjoys.zjh.data.UIData;
import com.mjoys.zjh.view.components.FileBrowser.FileBrowserListener;

public class TabProject extends ITabItem implements FileBrowserListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabProject(JFrame parentFrame) {
		super(parentFrame);

	}

	@Override
	public String getTableName() {
		return Language.get("tab_title_project");
	}

	@Override
	public void choosed(String path, String file) {
		UIData.getInstance().setProjectRoot(path);
	}

	@Override
	public void cancelled() {

	}

}
