package com.mjoys.zjh.utility;

import java.util.Iterator;
import java.util.LinkedHashMap;

import net.sf.json.JSONObject;

public class ProtobufUtility {

	@SuppressWarnings("rawtypes")
	public static byte[] toBytes(String msg) {
		JSONObject jo = JSONObject.fromObject(msg);
		LinkedHashMap map = (LinkedHashMap) JSONObject.toBean(jo,
				LinkedHashMap.class);
		byte[] bytes = new byte[map.size()];
		int i = 0;
		for (Iterator iterator = map.values().iterator(); iterator.hasNext(); i++) {
			Integer v = (Integer) iterator.next();
			bytes[i] = v.byteValue();
		}
		return bytes;
	}

	public static String stringify(byte[] bs) {
		String result = "";
		for (int i = 0; i < bs.length; i++)
			result += (bs[i] + (i != bs.length - 1 ? "," : ""));
		return result;
	}
}
