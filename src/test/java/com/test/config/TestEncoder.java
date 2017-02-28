package com.test.config;

import com.web.util.EncoderUtil;

public class TestEncoder {
	public static void main(String[] args) {
		EncoderUtil encoder = new EncoderUtil();
		String key = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.113 Safari/537.36";
		String encodedString = encoder.encode(key);
		String decodedString = encoder.decode(encodedString);
		System.out.println(encodedString);
		System.out.println(decodedString);
	}
}
