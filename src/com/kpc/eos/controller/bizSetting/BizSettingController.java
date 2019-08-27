
package com.kpc.eos.controller.bizSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.BizSettingSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.service.bizSetting.BizSettingService;

public class BizSettingController extends BaseEOSController {

	private BizSettingService bizSettingService;
	
	public void setBizSettingService(BizSettingService bizSettingService) {
		this.bizSettingService = bizSettingService;
	}
	
	public BizSettingController() {
		super();
		controllerId = "BizSetting";
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
		breads.add(new BreadcrumbModel("业务配置 ", getCmdUrl("bizSettingList"), true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			bizSettingList
	* Function Description		Call the view for business setting list according to user id
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView bizSettingList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		
		BizSettingSModel sc = new BizSettingSModel();
		
		String key = "BizSetting_bisSettingList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (BizSettingSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		List<BizSettingModel> sysDetailDataList = bizSettingService.getBizSettingDetailInfo();
		
		ModelAndView mv = new ModelAndView("bizSetting/bizSettingList", "sc", sc);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("SysDetailData", sysDetailDataList);
		mv.addObject("SysDetailData", jsonObj.toString());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			bizSettingGridAjax
	* Function Description		Retrieve the business setting list according to user id
	*****************************************************************************************************************************/
	public ModelAndView bizSettingGridAjax(HttpServletRequest request, HttpServletResponse response, BizSettingSModel sc) throws Exception
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "BizSetting_bisSettingList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		List<BizSettingModel> list = bizSettingService.getBizSettingList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveBizSetting
	* Function Description		Save business setting information
	*****************************************************************************************************************************/
	public ModelAndView saveBizSetting(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String bizSetting = request.getParameter("userData");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		bizSettingService.saveBizSetting(bizSetting, userId);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		return redirect("BizSetting", "bizSettingList");
	}
}
