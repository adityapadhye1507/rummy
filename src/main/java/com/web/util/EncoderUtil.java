package com.web.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Repository;

@Repository("encoderUtil")
public class EncoderUtil {

	public String encode(String key) {
		byte[] encodedArray = Base64.encodeBase64(key.getBytes());
		return rot13(new String(encodedArray));
		//return new String(Base64.encodeBase64(key.getBytes()));
	}

	public String decode(String key) {
		String decodedString = rot13(key);
		return new String(Base64.decodeBase64(decodedString.getBytes()));
		//return new String(Base64.decodeBase64(key.getBytes()));
	}

    public static String rot13(String textToEncode) {
        StringBuffer encodedText = new StringBuffer("");
        int textLength = textToEncode.length();
 
        for (int i = 0; i < textLength; i++) {
            char c = textToEncode.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            encodedText.append(c);
        }
        return encodedText.toString();
    }
}
