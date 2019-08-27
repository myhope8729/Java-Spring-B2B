
package com.kpc.eos.controller.bizSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bizSetting.UserMenuModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.ListDataSaveModel;
import com.kpc.eos.service.bizSetting.UserMenuService;

public class UserMenuController extends BaseEOSController {
	private UserMenuService userMenuService;
	
	private UserModel loginUser;
	private String userId;
	
	public UserMenuController() 
	{
		super();
		controllerId = "ActionUrl";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("系统资料", "", false));
		breads.add(new BreadcrumbModel("作用 URLs", getCmdUrl( "actionUrlList" ), true));
		
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
	}
	
	public void setUserMenuService(UserMenuService userMenuService) {
		this.userMenuService = userMenuService;
	}
	
	public ModelAndView userMenuList(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) {
		this.initCmd();
		return new ModelAndView("bizSetting/userMenuList", "sc", sc);
	}
	
	public ModelAndView userMenuGridAjax(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {

		ModelAndView mv = new ModelAndView("jsonView");
		
		sc.setUserId(userId);
		
		List<UserMenuModel> list = userMenuService.getUserMenuList(sc);
		mv.addObject("rows", list);
		return mv;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView saveUserMenuAjax(HttpServletRequest request, HttpServletResponse response, ListDataSaveModel menus) throws Exception {
		List<UserMenuModel> menuList = (List<UserMenuModel>) menus.makeModelList(UserMenuModel.class);
		userMenuService.saveUserMenu(menuList, userId);
		return new ModelAndView("jsonView", "result", new ResultModel());
	}
	
}
