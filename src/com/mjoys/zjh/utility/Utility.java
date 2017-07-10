package com.mjoys.zjh.utility;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Utility {

	public static Dimension getScreenDimension() {
		int screenWidth = ((int) java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().width);
		int screenHeight = ((int) java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize().height);
		return new Dimension(screenWidth, screenHeight);
	}

	@SuppressWarnings("rawtypes")
	public static byte[] toBytes(LinkedHashMap map) {
		byte[] bytes = new byte[map.size()];
		int i = 0;
		for (Iterator iterator = map.values().iterator(); iterator.hasNext(); i++) {
			Integer v = (Integer) iterator.next();
			bytes[i] = v.byteValue();
		}
		return bytes;
	}
}
