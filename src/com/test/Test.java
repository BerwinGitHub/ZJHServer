package com.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {

	// J$BF35095B-4003-4AF2-BF2E-5B2EBA6BA748
	public static void main(String[] args) {
		int MAX_SEARCH = 10;
		double ADD_OFFSET = 0.5f;
		System.out.println("设:");
		System.out.println("w	+	x	=	8");
		System.out.println("+		+");
		System.out.println("y	-	z	=	6");
		System.out.println("‖		‖");
		System.out.println("13		8");
		try {
			Thread.sleep(1000 * 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("开始计算");
		long start = System.currentTimeMillis();
		// double w = 3.5f, x = 4.5f, y = 9.5, z = 3.5;
		double w = 0.0f, x = 0.0f, y = 0.0f, z = 0.0f;
		for (w = 1; w < MAX_SEARCH; w += ADD_OFFSET) {
			for (x = 1; x < MAX_SEARCH; x += ADD_OFFSET) {
				for (y = 1; y < MAX_SEARCH; y += ADD_OFFSET) {
					for (z = 1; z < MAX_SEARCH; z += ADD_OFFSET) {
						System.out.println("查找:w=" + w + "\tx:" + x + "\ty="
								+ y + "\tz=" + z);
						if ((w + x == 8) && (y - z == 6) && (w + y == 13)
								&& (x + z == 8)) {
							System.out
									.println("=============================================");
							System.out.println("时间:"
									+ (System.currentTimeMillis() - start)
									+ "毫秒");
							System.out.println("结果:w=" + w + "\tx:" + x
									+ "\ty=" + y + "\tz=" + z);
							System.out
									.println("=============================================");
							System.out.println(w + "	+	" + x + "	=	8");
							System.out.println("+		+");
							System.out.println(y + "	-	" + z + "	=	6");
							System.out.println("‖		‖");
							System.out.println("13		8");
							return;
						}
					}
				}

			}
		}
		System.out.println("没找到");

		// try {
		// String res = URLDecoder
		// .decode("J $ B F 3 5 0 9 5 B - 4 0 0 3 - 4 A F 2 - B F 2 E - 5 B 2 E B A 6 B A 7 4 8",
		// "UTF-8");
		// System.out.println(tos(res));
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
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
