
package com.kpc.eos.controller.bizSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.service.bizSetting.MenuSettingService;

public class MenuSettingController extends BaseEOSController {

	private MenuSettingService menuSettingService;
	
	public void setMenuSettingService(MenuSettingService menuSettingService) {
		this.menuSettingService = menuSettingService;
	}
	
	public MenuSettingController() {
		super();
		controllerId = "MenuSetting";
	}
	
	/**
	 * command initialization function.
	 * When getting a request, this function will be called before running a cmd's method.
	 * Define Breadcrumb model.
	 */
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("业务设置", "", false));
		breads.add(new BreadcrumbModel("菜单设置 ", getCmdUrl("menuSettingList"), true));
	}
	
	/**
	 * Description	: Call the view for menu setting list according to user id
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * 2017
	 */
	public ModelAndView menuSettingList(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView mv = new ModelAndView("bizSetting/menuSettingList");
		initCmd();
		return mv;
	}
	
	/** 
	 * Description	: Retrieve the menu setting list according to user id using ajax
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView menuSettingGridAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		UserModel um = (UserModel) SessionUtil.getUser(request, getSystemName());
		
		List<MenuModel> list = menuSettingService.getMenuSettingList(um);
		mv.addObject("rows", list);
		return mv;
	}
	
	/**
	 * Description	: Save the menu names by user
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param menuSet
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveMenuSettingAjax(HttpServletRequest request, HttpServletResponse response, MenuModel menuSet) throws Exception {
		
		menuSet.setUserId(SessionUtil.getUserId(request, getSystemName()));
		menuSettingService.saveMenuSetting(menuSet);
		
		return new ModelAndView("jsonView", "result", new ResultModel());
	}
}
