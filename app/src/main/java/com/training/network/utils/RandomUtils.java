package com.training.network.utils;

import java.util.Random;

public class RandomUtils {
	
	/**
	 * 生成随机字符串
	 * @param length 字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }     
}
