package com.mjoys.zjh.view.components;

import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileBrowser extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8499381003416513871L;
	public static final int TEXTFIELD_COLUMNS = 45;

	private JFrame parentFrame = null;
	private String title = null;
	private int mode = FileDialog.LOAD;

	private JTextField textField = null;
	private JButton btnBrowser = null;

	private String defaultPath = null;

	private FileBrowserListener listener = null;

	public static final int FILES_ONLY = 0;
	public static final int DIRECTORIES_ONLY = 1;
	public static final int FILES_AND_DIRECTORIES = 2;
	public static final int SAVE = 3;

	public FileBrowser(JFrame parentFrame, String title, int mode,
			String defaultPath) {
		this.parentFrame = parentFrame;
		this.title = title;
		this.mode = mode;
		this.defaultPath = defaultPath;

		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);

		this.textField = new JTextField(TEXTFIELD_COLUMNS);
		this.textField.setEditable(false);
		this.textField.setText(this.defaultPath);
		this.add(this.textField);

		this.btnBrowser = new JButton("浏览");
		this.btnBrowser.addActionListener(this);
		this.add(this.btnBrowser);

		GridBagConstraints s = new GridBagConstraints();// 定义一个GridBagConstraints，
		// 是用来控制添加进的组件的显示位置
		s.fill = GridBagConstraints.BOTH;
		s.gridwidth = 4;
		s.weightx = 1;
		s.weighty = 0;
		layout.setConstraints(this.textField, s);
		s.gridwidth = 0;
		s.weightx = 0;
		s.weighty = 0;
		layout.setConstraints(this.btnBrowser, s);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.mode == FILES_ONLY || this.mode == SAVE) {
			FileDialog dialog = new FileDialog(this.parentFrame, this.title,
					this.mode == SAVE ? 1 : FILES_ONLY);
			dialog.setDirectory(this.textField.getText().toString().trim());
			dialog.setVisible(true);
			String path = dialog.getDirectory();
			String file = dialog.getFile();
			if (path != null || file != null) {
				if (listener != null) {
					listener.choosed(path == null ? "" : path,
							file == null ? "" : path);
				}
			} else {
				if (listener != null) {
					listener.cancelled();
				}
			}
		} else {
			JFileChooser jfc = new JFileChooser(new File(this.textField
					.getText().toString().trim()));
			jfc.setDialogTitle(this.title);
			jfc.setDialogType(JFileChooser.OPEN_DIALOG);
			jfc.setFileSelectionMode(this.mode);
			int res = jfc.showOpenDialog(this.parentFrame);
			if (res == JFileChooser.APPROVE_OPTION) {
				File dir = jfc.getSelectedFile();
				this.textField.setText(dir.getPath());
				if (listener != null) {
					listener.choosed(dir.getPath(), dir.getName());
				}
			} else {
				if (listener != null) {
					listener.cancelled();
				}
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JButton getBtnBrowser() {
		return btnBrowser;
	}

	public void setBtnBrowser(JButton btnBrowser) {
		this.btnBrowser = btnBrowser;
	}

	public FileBrowserListener getListener() {
		return listener;
	}

	public void setListener(FileBrowserListener listener) {
		this.listener = listener;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getTextfieldColumns() {
		return TEXTFIELD_COLUMNS;
	}

	public interface FileBrowserListener {

		public void choosed(String path, String file);

		public void cancelled();
	}

}
