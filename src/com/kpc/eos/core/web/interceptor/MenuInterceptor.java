
package com.kpc.eos.core.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.service.system.MenuService;

/**
 * MenuInterceptor
 * =================
 * Description : menu interceptor
 * Methods :
 */
public class MenuInterceptor extends HandlerInterceptorAdapter {

	private MenuService menuService;
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
        UserModel user = (UserModel) SessionUtil.getUser(request, Constants.REQUEST_KEY_SYSTEMNAME);
        
        List<MenuModel> menus = menuService.findAccessibleMenuList(user);
        request.setAttribute("menuList", menus);
        
		return super.preHandle(request, response, handler);
		
	}

	
}
