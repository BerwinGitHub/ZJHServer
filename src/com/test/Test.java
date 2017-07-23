package com.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {

	// J$BF35095B-4003-4AF2-BF2E-5B2EBA6BA748
	public static void main(String[] args) {
		try {
			String res = URLDecoder.decode(
					"J $ B F 3 5 0 9 5 B - 4 0 0 3 - 4 A F 2 - B F 2 E - 5 B 2 E B A 6 B A 7 4 8", "UTF-8");
			System.out.println(tos(res));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String tos(String s) {
		char[] cs = s.toCharArray();
		String res = "";
		for (int i = 0; i < cs.length; i++) {
			res += String.valueOf((int) cs[i]);
		}
		return res;
	}

}
