
package com.kpc.eos.core.web.interceptor;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kpc.eos.core.util.AuthorityUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;

/**
 * DetectDeviceCmdInterceptor
 * =================
 * Description : intercepter to determine if client is a mobile or a PC 
 * Methods :
 */
public class DetectDeviceCmdInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Enumeration<String> hnames = request.getHeaderNames();
        while (hnames.hasMoreElements()) {
            String paramName = hnames.nextElement();
            String value = request.getHeader(paramName);
            System.out.println(paramName + ": " + value);
        }
        
		String userAgentStr = request.getHeader("user-agent");
		Method method = handler.getClass().getMethod("setMobileClient", Boolean.class);	
		
		if(userAgentStr.toLowerCase().contains("mobile") || userAgentStr.toLowerCase().contains("android") || userAgentStr.toLowerCase().contains("iphone")) {
			method.invoke(handler, true);
			SessionUtil.setIsMobile(request, true, "bo");
		}else {
			method.invoke(handler, false);
			SessionUtil.setIsMobile(request, false, "bo");
		}
		
		return super.preHandle(request, response, handler);
	}
	
}
