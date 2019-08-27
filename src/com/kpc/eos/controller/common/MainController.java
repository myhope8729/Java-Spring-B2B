
package com.kpc.eos.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.model.common.BreadcrumbModel;

public class MainController extends BaseEOSController {
	
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("管理中心", "", false));
		breads.add(new BreadcrumbModel("首页", "/bo/Main.do?cmd=main", true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			main
	* Function Description		Call main view
	*****************************************************************************************************************************/
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("home/main");
		
		initCmd();
		
		return mv;
	}
}
