
package com.kpc.eos.controller.system;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.system.ActionUrlModel;
import com.kpc.eos.model.system.ListDataSaveModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.model.system.MenuSModel;
import com.kpc.eos.service.system.MenuService;

public class ActionUrlController extends BaseEOSController {
	private MenuService menuService;
	
	public ActionUrlController() 
	{
		super();
		controllerId = "ActionUrl";
	}
	
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("系统资料", "", false));
		breads.add(new BreadcrumbModel("作用 URLs", getCmdUrl( "actionUrlList" ), true));
	}
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	public ModelAndView actionUrlList(HttpServletRequest request, HttpServletResponse response, MenuSModel sc) {
		this.initCmd();
		return new ModelAndView("system/actionUrlList", "sc", sc);
	}
	
	public ModelAndView actionMenuGridAjax(HttpServletRequest request, HttpServletResponse response,MenuSModel sc) throws Exception {

		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MenuModel> list = menuService.getActionMenuList(sc);
		mv.addObject("rows", list);
		return mv;
	}

	public ModelAndView actionUrlGridAjax(HttpServletRequest request, HttpServletResponse response,MenuSModel sc) throws Exception {

		ModelAndView mv = new ModelAndView("jsonView");
		
		List<ActionUrlModel> list = menuService.findActionUrlList(sc);
		mv.addObject("rows", list);
		return mv;
	}	

	
	@SuppressWarnings("unchecked")
	public ModelAndView saveActionUrlAjax(HttpServletRequest request, HttpServletResponse response, ListDataSaveModel menus) throws Exception {
		List<ActionUrlModel> paramList = (List<ActionUrlModel>) menus.makeModelList(ActionUrlModel.class);
		menuService.saveActinUrls(paramList);
		return new ModelAndView("jsonView", "result", new ResultModel());
	}
	
	public ModelAndView getUpperMenuListWithJson(HttpServletRequest request, HttpServletResponse response, MenuModel menu) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<HashMap<String,String>> list = menuService.getUpperMenuList();
		mv.addObject("data", list);
		return mv;	
	}
}
