package com.kpc.eos.core.web.function;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.util.HTMLInputFilter;

/**
 * 
 * StpFunctions
 * =================
 * Description : 
 * Methods :
 */
public class EOSFunctions {

	public static String nlToBr(String input) {
		if (input == null) return null;
		return input.replaceAll("\n", "<br/>");
	}
	
	public static String encodeUrl(String input) {
		if (input == null) return null;
		try {
			return URLEncoder.encode(input, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String removeComment(String input) {
		if (input == null) return null;
		return HTMLInputFilter.removeComments(input);
	}

	public static String leftPad(String str, int size, String padStr) {
		return StringUtils.leftPad(str, size, padStr);
	}
	
}
