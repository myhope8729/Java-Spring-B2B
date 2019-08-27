
package com.kpc.eos.core.util;

import java.util.List;

import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.service.bizSetting.UserMenuService;
import com.kpc.eos.service.system.MenuService;

/**
 * MenuUtil
 * =================
 * Description : 
 * Methods :
 */
public class MenuUtil {

	@SuppressWarnings("unchecked")
	public static List findAccessibleMenuList(UserModel user) throws Exception {
		MenuService menuService = getMenuService();
		return menuService.findAccessibleMenuList(user);
	}
	
	public static List findUserMenuList(DefaultSModel sc) throws Exception {
		UserMenuService userMenuService = getUserMenuService();
		return userMenuService.getUserMenuList(sc);
	}
	
	public static boolean hasAuthorityOfAction(UserModel user, String actionId) throws Exception {
		MenuService menuService = getMenuService();
		return menuService.hasAuthorityOfAction(user, actionId);
	}
	
	
	/**
	 * Description	:
	 * @author 		: RKRK
	 * @param user
	 * @param actionUrl
	 * @return String : Menu Id
	 * @throws Exception
	 * 2018
	 */
	public static String hasAuthorityOfUrl(UserModel user, String actionUrl) throws Exception {
		MenuService menuService = getMenuService();
		return menuService.hasAuthorityOfUrl(user, actionUrl);
	}
		
	
	private static MenuService getMenuService() {
		return (MenuService) ApplicationSetting.getWebApplicationContext().getBean("menuService");
	}
	
	private static UserMenuService getUserMenuService() {
		return (UserMenuService) ApplicationSetting.getWebApplicationContext().getBean("userMenuService");
	}
	
}
