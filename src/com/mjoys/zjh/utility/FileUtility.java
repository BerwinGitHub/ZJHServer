package com.mjoys.zjh.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class FileUtility {

	public interface CopyFilter {
		/**
		 * true 表示可以copy
		 * 
		 * @return
		 */
		public boolean copyFilter(String srcFile, String destFile);
	};

	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param descFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName,
			boolean overlay) {
		File srcFile = new File(srcFileName);

		// 判断源文件是否存在
		if (!srcFile.exists()) {
			System.out.println("源文件：" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			System.out.println("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFileName).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName
	 *            待复制目录的目录名
	 * @param destDirName
	 *            目标目录名
	 * @param overlay
	 *            如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName,
			boolean overlay) {
		return copyDirectory(srcDirName, destDirName, overlay, null);
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName
	 *            待复制目录的目录名
	 * @param destDirName
	 *            目标目录名
	 * @param overlay
	 *            如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName,
			boolean overlay, CopyFilter filter) {
		// 判断源目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {// 目标不存在
			return false;
		} else if (!srcDir.isDirectory()) { // 复制的不是目录
			return false;
		}

		// 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// 如果目标文件夹存在
		if (destDir.exists()) {
			// 如果允许覆盖则删除已存在的目标目录
			if (overlay) {
				boolean delete = new File(destDirName).delete();
				System.out.println("DELETE - " + destDirName + "  " + delete);
			} else { // 这已经有文件了
				return false;
			}
		}
		// else {
		// // 创建目的目录
		// System.out.println("目的目录不存在，准备创建。。。");
		// if (!destDir.mkdirs()) {
		// System.out.println("复制目录失败：创建目的目录失败！");
		// return false;
		// }
		// }

		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 复制文件
			if (files[i].isFile()) {
				String srcFile = files[i].getAbsolutePath();
				String destFile = destDirName + files[i].getName();
				if (filter != null && filter.copyFilter(srcFile, destFile)) {
					flag = copyFile(srcFile, destFile, overlay);
				} else if (filter == null) {
					flag = copyFile(srcFile, destFile, overlay);
				}
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = copyDirectory(files[i].getAbsolutePath(), destDirName
						+ files[i].getName(), overlay, filter);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("复制目录" + srcDirName + "至" + destDirName + "失败！");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(new File(dir, children[i]));
				if (!success)
					return false;
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 写入到文件
	 * 
	 * @param filename
	 * @param content
	 * @param append
	 */
	public static void writeToFile(String filename, String content,
			boolean append) {
		File file = FileUtility.openFile(filename, true);
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(file, append));
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从文件中读取
	 * 
	 * @param filename
	 * @return
	 */
	public static String readFromFile(String filename) {
		File file = FileUtility.openFile(filename, true);
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String temp = null;
			temp = in.readLine();
			while (temp != null) {
				// 读取的每一行内容后面加上一个空格用于拆分成语句
				sb.append(temp);
				temp = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 打开文件
	 * 
	 * @param filename
	 * @param create
	 * @return
	 */
	public static File openFile(String filename, boolean create) {
		File file = new File(filename);
		if (!file.exists() && create) {
			try {
				if (!file.getParentFile().exists()) {
					System.out.println(file.getParentFile());
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 小文件的MD5
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String md5Hex(String file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		return DigestUtils.md5Hex(fis);
	}

	/**
	 * 计算大文件的MD5
	 * 
	 * @param fileName
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String md5HexLargeFile(String fileName)
			throws NoSuchAlgorithmException, IOException {
		File f = new File(fileName);
		InputStream ins = new FileInputStream(f);
		byte[] buffer = new byte[8192];
		MessageDigest md5 = MessageDigest.getInstance("MD5");

		int len;
		while ((len = ins.read(buffer)) != -1) {
			md5.update(buffer, 0, len);
		}
		ins.close();
		// return DigestUtils.md5Hex(md5.digest()); // 这个方法不准确
		return toHexString(md5.digest());
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}
}
