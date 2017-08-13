package com.mjoys.zjh.confgs;

import java.util.Map;

public class Configs {

	public static Map<String, Object> configsMap;

	// public static <T> T get(String key) {
	// return (T) configsMap.get(key);
	// }

	public static String stringValue(String key) {
		return (String) configsMap.get(key);
	}

	public static int intValue(String key) {
		return (int) configsMap.get(key);
	}

	public static long longValue(String key) {
		return (long) configsMap.get(key);
	}

	public static float floatValue(String key) {
		return (float) doubleValue(key);
	}

	public static double doubleValue(String key) {
		return (double) configsMap.get(key);
	}

	public static boolean booleanValue(String key) {
		return (boolean) configsMap.get(key);
	}

	public static void set(String key, Object value) {
		configsMap.put(key, value);
	}

}
