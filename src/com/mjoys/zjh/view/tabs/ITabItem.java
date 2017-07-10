package com.mjoys.zjh.view.tabs;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class ITabItem extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JFrame parentFrame = null;

	public ITabItem(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public abstract String getTableName();

	public void selected() {
	}

}
