package com.training.network.security;


public class AESTest {
	public static void main(String[] args) {
		String passwordKey = "1234567891234567";
		String data = "{\"mac\":\"18838586676582\",\"username\":\"362329199103190614\",\"password\":\"888888\",\"loginType\":\"5\",\"dt_client\":\"2015-06-03 13:20:10\"}";
		AES aes = new AES(passwordKey);
		String encodeData = aes.encrypt(data);
		System.out.println("--------encodeData--------");
		System.out.println(encodeData);
		String decodeData = aes.decrypt(encodeData);
		System.out.println("--------decodeData--------");
		System.out.println(decodeData);
	}
}
