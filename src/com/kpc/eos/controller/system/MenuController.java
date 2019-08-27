
package com.kpc.eos.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.model.system.MenuSModel;
import com.kpc.eos.service.system.MenuService;

public class MenuController extends BaseEOSController {
	private MenuService menuService;
	
	public MenuController() 
	{
		super();
		controllerId = "Menu";
	}
	
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("系统资料", "", false));
		breads.add(new BreadcrumbModel("菜单资料", getCmdUrl( "menuList" ), true));
	}
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	public ModelAndView menuList(HttpServletRequest request, HttpServletResponse response, MenuSModel sc) {
		this.initCmd();
		return new ModelAndView("system/menuList", "sc", sc);
	}
	
	public ModelAndView menuTreeAjax(HttpServletRequest request, HttpServletResponse response,MenuSModel sc) throws Exception {

		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MenuModel> list = menuService.findMenuList(sc);
		mv.addObject("rows", list);
		return mv;
	}

	
	@SuppressWarnings("unchecked")
	public ModelAndView saveMenuAjax(HttpServletRequest request, HttpServletResponse response, DefaultSModel menus) throws Exception {
		List<MenuModel> paramList = (List<MenuModel>) menus.makeModelList(MenuModel.class);
		menuService.saveMenu(paramList);
		return new ModelAndView("jsonView", "result", new ResultModel());
	}
	
	
	public ModelAndView saveMenuAjaxForMobile(HttpServletRequest request, HttpServletResponse response, MenuModel menu) throws Exception {
		List<MenuModel> paramList = new ArrayList<MenuModel>();
		paramList.add(menu);
		menuService.saveMenu(paramList);
		return new ModelAndView("jsonView", "result", new ResultModel());
	}	
	
	public ModelAndView getUpperMenuListWithJson(HttpServletRequest request, HttpServletResponse response, MenuModel menu) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<HashMap<String,String>> list = menuService.getUpperMenuList();
		mv.addObject("data", list);
		return mv;	
	}
}
