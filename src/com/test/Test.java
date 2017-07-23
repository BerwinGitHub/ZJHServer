package com.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {

	public static void main(String[] args) {
		try {
			URLDecoder.decode("", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
