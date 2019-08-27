
package com.kpc.eos.core.web.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.AuthorityUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.common.SysMsg;

/**
 * AuthorityCheckInterceptor
 * =================
 * Description : Authority interceptor
 * Methods :
 */
public class AuthorityCheckInterceptor extends HandlerInterceptorAdapter {

	private String system;
	private String urlPrefix;
	private Set<String> passUri = null;
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}
	
	public void setPassUri(String[] uris) {
		if (this.passUri == null) {
			this.passUri = new HashSet<String>();
		}
		for (String uri : uris) {
			this.passUri.add(uri);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if (StringUtils.isBlank(this.system)) {
			this.system = urlPrefix.substring(urlPrefix.indexOf("/")+1);
		}

		
		
		
		response.setHeader("Cache-Control", "no-store, no-cache"); // HTTP 1.1
		//response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // prevents caching at the proxy
		
		boolean enable = Boolean.parseBoolean(ApplicationSetting.getConfig("domain.prefix"));

		if (isPassUri(request)) {
			return true;
		}
		//for pass the ajax requests
		if ("XMLHttpRequest".equals( request.getHeader("X-Requested-With") )) return true;
		
		if (SessionUtil.getUser(request, this.system) == null && !isPassUri(request)) {

			String requestUri = AuthorityUtil.getRequestUri(request);
			String returnUri = null;
			response.sendRedirect(request.getContextPath() + (enable? this.urlPrefix : "") + "/Login.do?cmd=loginForm" + (returnUri != null? returnUri : ""));
			return false;
		} 
		
		//when a menuID for current action url does not exist
		String menuId = AuthorityUtil.hasAuthority(request, this.system);
		if (StringUtils.isEmpty(menuId)) {
			String beforeUrl = request.getParameter("localFullUrl");
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
			if(StringUtils.isNotBlank(beforeUrl))
				response.sendRedirect(beforeUrl);
			else
				response.sendRedirect(request.getContextPath() + (enable? this.urlPrefix : "") + "/Main.do?cmd=main");	
			
			return false;
		}else {
			SessionUtil.setData(request, Constants.SESSION_KEY_CURRENT_MENU, menuId);
		}
		
		
		
		
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * isPassUrl
	 * ===================
	 * @param request
	 * @return
	 */
	private boolean isPassUri(HttpServletRequest request) {
		
		String requestUri = AuthorityUtil.getRequestUri(request);
		String methodName = request.getParameter("cmd");
		
        if (this.passUri.contains(requestUri) || this.passUri.contains(requestUri + "?cmd=" + methodName)) {
        	return true;
        }
		
		return false;
	}
	
}
