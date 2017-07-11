package com.mjoys.zjh.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mjoys.zjh.confgs.Configs;
import com.mjoys.zjh.confgs.Language;
import com.mjoys.zjh.server.Server;
import com.mjoys.zjh.view.tabs.ITabItem;
import com.mjoys.zjh.view.tabs.TabProject;

public class MainView extends JFrame implements ChangeListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane = null;

	private Server gameServer = null;

	private JButton btnStart = null;

	private JButton btnStop = null;

	public MainView() {
		this.gameServer = new Server(Configs.stringValue("server_ip"),
				Configs.intValue("server_port"));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// 当前系统风格
		} catch (Exception e) {
			e.printStackTrace();
		}
		// JMenuBar jmb = new JMenuBar();
		// JMenu settings = new JMenu("设置(S)");
		// settings.setMnemonic('S');
		// jmb.add(settings);
		// JMenu about = new JMenu("关于(A)");
		// about.setMnemonic('A');
		// jmb.add(about);
		// this.setJMenuBar(jmb);

		List<ITabItem> tabs = new ArrayList<>();
		tabs.add(new TabProject(this));

		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.add(this.tabbedPane, BorderLayout.CENTER);
		this.tabbedPane.addChangeListener(this);

		for (ITabItem item : tabs) {
			this.tabbedPane.add(item, item.getTableName());
		}

		// SOUTH
		JPanel south = new JPanel();
		this.add(south, BorderLayout.SOUTH);
		this.btnStart = new JButton(Language.get("btn_start_server"));
		south.add(this.btnStart);
		this.btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.this.gameServer.start();
				btnStart.setEnabled(!gameServer.isRunning());
				btnStop.setEnabled(gameServer.isRunning());
			}
		});
		this.btnStop = new JButton(Language.get("btn_stop_server"));
		south.add(this.btnStop);
		this.btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainView.this.gameServer.stop();
				btnStart.setEnabled(!gameServer.isRunning());
				btnStop.setEnabled(gameServer.isRunning());
			}
		});
		btnStart.setEnabled(!gameServer.isRunning());
		btnStop.setEnabled(gameServer.isRunning());

		this.setTitle(Language.get("window_title"));
		this.setSize(Configs.intValue("windows_width"),
				Configs.intValue("windows_height"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int idx = this.tabbedPane.getSelectedIndex();
		ITabItem item = (ITabItem) this.tabbedPane.getComponentAt(idx);
		item.selected();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (this.gameServer.isRunning()) {
			this.gameServer.stop();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
