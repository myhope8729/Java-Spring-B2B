package com.kpc.eos.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filename		: CookieUtil.java
 * Description	:
 * 2018
 * @author		: RKRK
 */
public class CookieUtil {

	public static Object getData(HttpServletRequest request, String key) {
		
		String szValue = "";
		
		Cookie [] cookies = request.getCookies();		
		if ( cookies != null ) {
			for( int i = 0; i < cookies.length; i++ ) {				
				if (cookies[i].getName().equals(key)) {
			        if (!cookies[i].getValue().equals("")) {
			        	szValue = cookies[i].getValue();
			        }
			    }				
			}
		}
		
		return szValue;
	}
	
	public static void setData(HttpServletResponse response, String key, String data) 
	{
		Cookie obj = new Cookie(key, data);
		
		response.addCookie(obj);
	}
}
