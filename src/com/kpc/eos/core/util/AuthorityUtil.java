
package com.kpc.eos.core.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.common.AuthorityGroupByURL;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.system.MenuService;


/**
 * Filename		: AuthorityUtil.java
 * Description	: Class for checking Request Uri's authorities.
 * 2017
 * @author		: RKRK
 */
/**
 * Filename		: AuthorityUtil.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class AuthorityUtil {

	private static Logger logger = Logger.getLogger(AuthorityUtil.class);
	
	private static MenuService menuService = null;
	
	public static void setMenuService(MenuService menuService) {
		AuthorityUtil.menuService = menuService;
	}
	
	/**
	 * Description	:
	 * @author 		: RKRK
	 * @return
	 * 2017
	 */
	public static MenuService getMenuService() {
		return menuService;
	}
	

	/**
	 * Description	: Get the request Uri
	 * @author 		: RKRK
	 * @param request
	 * @return
	 * 2017
	 */
	public static String getRequestUri(HttpServletRequest request) {
		String requestUri = request.getRequestURI().substring(request.getContextPath().length());
		requestUri = StringUtils.substringAfterLast(requestUri, "/");
		return requestUri;
	}

	/**
	 * Description	: Return privilege group of url 
	 * @author 		: RKRK
	 * @param prefixUrl
	 * @param userNo
	 * @return
	 * 2017
	 */
	public static List<AuthorityGroupByURL> getAuthorityGroupByURL(String prefixUrl, String userNo) {
		//return menuService.hasAuthorityOfAction(prefixUrl, userNo);
		return null;
	}
	
	/**
	 * Description	: Check if the user has his own privilege if that url.
	 * @author 		: RKRK
	 * @param request
	 * @param system
	 * @return
	 * 2017
	 * @throws Exception 
	 */
	public static String hasAuthority(HttpServletRequest request, String system) throws Exception {
		
		String requestUri = getRequestUri(request);
		String methodName = request.getParameter("cmd");
		
		if(requestUri.indexOf(".do") >= 0 && methodName != null) {
			requestUri += "?cmd=" + methodName;
		}
		
		UserModel user = (UserModel) SessionUtil.getUser(request, system);

		if (requestUri.equals("Main.do?cmd=main")) {
			return Constants.LOGIN_MAIN_PAGE_NO;
		}
		
		
		
		String menuId = MenuUtil.hasAuthorityOfUrl(user, requestUri);
		
		if (StringUtils.isEmpty(menuId)) {
			if(logger.isDebugEnabled()) {
				logger.debug("Have no access privilege og that url");
			}
		//} else if (privList.size() == 1) {
		} else  {
			if(logger.isDebugEnabled()) {
				logger.debug("this user can be accessed");
			}
		} 
		
		return menuId;
	}
	
	public static boolean hasAuthority(HttpServletRequest request, String requestUri, String system) {
		System.out.println(requestUri);
		
		UserModel user = (UserModel) SessionUtil.getUser(request, system);
		List<AuthorityGroupByURL> privList = getAuthorityGroupByURL(requestUri, user.getEmpId());
		
		if (privList.size() == 0) {
			if(logger.isDebugEnabled()) {
				logger.debug("Have no access privilege og that url.");
			}
			return false;
		} else if (privList.size() == 1) {
			if(logger.isDebugEnabled()) {
				logger.debug("this user can be accessed");
			}
			return true;
		} else if (privList.size() > 1) {
			//case: url with paramester(ex,Voc.do?cmd=vocList&userKindCd=CS001002)

			for (AuthorityGroupByURL priv : privList) {
				if (requestUri.equals(priv.getConnUrl())) continue;
				
				boolean isMatch = false;
				String[] params = priv.getConnUrl().substring( requestUri.length()+1 ).split("&");
				
				for (String param : params) {
					String[] pair = param.split("=");
					if (pair.length < 2) continue;
					
					if (pair[1].equals(request.getParameter(pair[0]))) {
						isMatch = true;
					} else {
						isMatch = false;
						break;
					}
				}
				
				if (isMatch) {
					if(logger.isDebugEnabled()) {
						logger.debug("this user has a priv. url=" + priv.getConnUrl());
					}
					return true;
				}
			}
		} 
		
		if(logger.isDebugEnabled()) {
			logger.debug("This user can not be accessed");
		}
		return false;
	}
}
