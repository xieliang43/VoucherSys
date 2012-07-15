package com.voucher.util;

import java.io.IOException;

public class Base64Encoder {
	/**
	 * 编码
	 * 
	 * @param bstr
	 * @return String
	 */
	public static String encode(String str) {
		byte []bstr = str.getBytes();
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public static String decode(String str) {
		byte[] bts = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bts = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String(bts);
	}
}
