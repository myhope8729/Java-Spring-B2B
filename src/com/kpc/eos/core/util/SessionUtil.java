package com.kpc.eos.core.util;

import javax.servlet.http.HttpServletRequest;

import com.kpc.eos.core.Constants;
import com.kpc.eos.model.dataMng.UserModel;

public class SessionUtil {

	public static Object getData(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}
	
	public static void setData(HttpServletRequest request, String key, Object data) {
		request.getSession().setAttribute(key, data);
	}
	
	public static void removeData(HttpServletRequest request, String key){
		request.getSession().removeAttribute(key);
	}
	
	public static Object getUser(HttpServletRequest request, String system) {
		return getData(request, Constants.SESSION_KEY_LOGIN_USER + system);
	}
	
	public static String getUserId(HttpServletRequest request, String system) {
		UserModel um = (UserModel) SessionUtil.getUser(request, system);
		if (um != null) 
		{
			return um.getUserId();
		}
		return null;
	}
	
	public static String getEmpId(HttpServletRequest request, String system) {
		UserModel um = (UserModel) SessionUtil.getUser(request, system);
		if (um != null) 
		{
			return um.getEmpId();
		}
		return null;
	}
	
	public static void setUser(HttpServletRequest request, UserModel user, String system) {
		setData(request, Constants.SESSION_KEY_LOGIN_USER + system, user);
	}

	public static void removeUser(HttpServletRequest request, String system) {
		removeData(request, Constants.SESSION_KEY_LOGIN_USER + system);
	}
	
	public static void clearUserData(HttpServletRequest request, String system) {
		removeUser(request, system);
		
		removeData(request, Constants.SESSION_KEY_SC);
		removeData(request, Constants.SESSION_KEY_SYS_MSG);
		removeData(request, Constants.REQUEST_KEY_RETURN_DATA);
		removeData(request, Constants.REQUEST_KEY_RETURN_URL);
		
	}
	
	public static boolean isMobile(HttpServletRequest request, String system) {
		UserModel um = (UserModel) SessionUtil.getUser(request, system);
		if (um != null) 
		{
			return um.isMobile();
		}
		return false;
	}
	
	public static void setIsMobile(HttpServletRequest request, boolean isMobile, String system) {
		UserModel um = (UserModel) SessionUtil.getUser(request, system);
		if (um != null) 
		{
			 um.setMobile(isMobile);
			 setUser(request, um, system);
		}
	}
	
}
