
package com.kpc.eos.controller.bizSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bizSetting.CustTypeModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.service.bizSetting.CustTypeService;

public class CustTypeController extends BaseEOSController {

	private CustTypeService custTypeService;
	
	public void setCustTypeService(CustTypeService custTypeService) {
		this.custTypeService = custTypeService;
	}
	
	public CustTypeController() {
		super();
		controllerId = "CustType";
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
		breads.add(new BreadcrumbModel("客户类别 ", getCmdUrl("custTypeList"), true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			custTypeList
	* Function Description		Call the view for customer type list according to user id
	*****************************************************************************************************************************/
	public ModelAndView custTypeList(HttpServletRequest request, HttpServletResponse response)
	{
		initCmd();
		
		DefaultSModel sc = new DefaultSModel();
		
		String key = "Custtype_custtypeList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (DefaultSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/custTypeList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			custTypeGridAjax
	* Function Description		Retrieve the customer type list according to customer type id
	*****************************************************************************************************************************/
	public ModelAndView custTypeGridAjax(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Custtype_custtypeList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		Integer totalCount = custTypeService.getTotalCountCustTypeList(sc);
		sc.getPage().setRecords(totalCount);
				
		List<CustTypeModel> list = custTypeService.getCustTypeList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			custTypeForm
	* Function Description		Get the customer type information according to customer type id
	*****************************************************************************************************************************/
	public ModelAndView custTypeForm(HttpServletRequest request, HttpServletResponse response, CustTypeModel custTypeModel) throws Exception {
		initCmd();
		String custTypeId = request.getParameter("custTypeId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		CustTypeModel custType = null;
		
		if (StringUtils.isNotEmpty(custTypeId))
		{
			custType = custTypeService.getCustType(custTypeId);
			if (StringUtils.isEmpty(userId) || custType == null || !userId.equals(custType.getUserId()))
			{
				return redirect("CustType", "custTypeList");
			}
			breads.add(new BreadcrumbModel("修改客户类别", "", false));
			
		}
		else
		{
			breads.add(new BreadcrumbModel("新增客户类别", "", false));
		}
		
		if (custTypeModel != null && isPost(request))
		{
			custType = custTypeModel;
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/custTypeForm");
		
		mv.addObject("custType", custType);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveCustTypeAjax
	* Function Description		Save the customer type information.
	*****************************************************************************************************************************/
	public ModelAndView saveCustType(HttpServletRequest request, HttpServletResponse response, CustTypeModel custType) throws Exception {
		String userId = SessionUtil.getUserId(request, getSystemName());
		custType.setUserId(userId);
		
		formErrors = custType.validate();
		
		ModelAndView mv = new ModelAndView();
		
		Integer exists = custTypeService.existCustTypeName(custType);
		if (exists != null)
		{
			formErrors.rejectValue("custTypeName", "system.common.duplicated", new Object[]{"客户类别"}, null);
			mv = custTypeForm(request, response, custType);
			return mv;
		}
		
		custTypeService.saveCustType(custType);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("CustType", "custTypeList");
	}
}
