
package com.kpc.eos.core.util;

import java.util.Random;

/**
 * RandomUtil
 * =================
 * Description : random utility
 * Methods :
 */
public class RandomUtil {

	/**
	 * getRandom
	 * ===================
	 * @param size
	 * @return
	 */
	public static String getRandom(int size) {
		int max = new Double(Math.pow(10, size)).intValue();
		int min = new Double(Math.pow(10, (size-1))).intValue();
		
		Random rand = new Random(System.currentTimeMillis());  
		return Integer.toString(Math.abs(rand.nextInt(max - min) + min));
	}
	
	/**
	 * getRandomUpper
	 * ===================
	 * @param size
	 * @return
	 */
	public static String getRandomUpper(int size) {
		String random = "";
		for (int i = 0; i < size; i++) {
			char ch = (char) ((Math.random() * 26) + 65);
			random += ch;
		}
		
		return random;
	}
	
	/**
	 * getRandomLower
	 * ===================
	 * @param size
	 * @return
	 */
	public static String getRandomLower(int size) {
		String random = "";
		for (int i = 0; i < size; i++) {
			char ch = (char) ((Math.random() * 26) + 97);
			random += ch;
		}
		
		return random;
	}
}
