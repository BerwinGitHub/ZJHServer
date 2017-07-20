package com.ericsoft.test;

public class SLTHandleData {

	private static SLTHandleData instance = null;

	private SLTHandleData() {
	}

	public static synchronized SLTHandleData getInstance() {
		if (instance == null) {
			instance = new SLTHandleData();
		}
		return instance;
	}

	public String hanlde(String txt, String match, String result) {
		if (txt == null || "".equals(txt)) {
			return null;
		}
		if (txt.equals(match)) {
			return result;
		}
		return null;
	}

	public int handle(int num) {
		if (num >= 0 && num <= 9) {
			return this.handleLowDigits(num);
		} else if (num > 9) {
			return this.handleHighDigits(num);
		}
		return -1;
	}

	private int handleLowDigits(int num) {
		int result = 0, temp = num + 1;
		while (temp-- > 0)
			result += temp;
		return result;
	}

	private int handleHighDigits(int num) {
		int result = 0;
		String ns = num + "";
		char[] nc = ns.toCharArray();
		for (int i = 0; i < nc.length; i++) {
			result += Character.getNumericValue(nc[i]);
		}
		return result;
	}

}
