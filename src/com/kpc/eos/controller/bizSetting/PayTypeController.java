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
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.service.bizSetting.PayTypeService;

public class PayTypeController extends BaseEOSController {

	private PayTypeService payTypeService;
	
	public void setPayTypeService(PayTypeService payTypeService) {
		this.payTypeService = payTypeService;
	}
	
	public PayTypeController() {
		super();
		controllerId = "PayType";
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
		breads.add(new BreadcrumbModel("付款方式", getCmdUrl("payTypeList"), true));
	}
	
	public ModelAndView payTypeList(HttpServletRequest request, HttpServletResponse response) {
		initCmd();
		
		DefaultSModel sc = new DefaultSModel();
		
		String key = "Paytype_paytypeList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (DefaultSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/payTypeList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			payTypeGridAjax
	* Function Description		Retrieve the pay type list according to pay type id
	*****************************************************************************************************************************/
	public ModelAndView payTypeGridAjax(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Paytype_paytypeList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		Integer totalCount = payTypeService.getTotalCountPayTypeList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<PayTypeModel> list = payTypeService.getPayTypeList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
				
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			payTypeForm
	* Function Description		Get the pay type information according to paytype id
	*****************************************************************************************************************************/
	public ModelAndView payTypeForm(HttpServletRequest request, HttpServletResponse response, PayTypeModel payTypeModel) throws Exception {
		
		initCmd();
		
		String payTypeId = request.getParameter("payTypeId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		PayTypeModel payType = null;
		
		if (StringUtils.isNotEmpty(payTypeId))
		{
			payType = payTypeService.getPayType(payTypeId);
			
			if (payType == null || StringUtils.isEmpty(userId) || !userId.equals(payType.getUserId()))
			{
				return redirect("PayType", "payTypeList");
			}
			
			breads.add(new BreadcrumbModel("修改付款方式", "", false));
		}
		else
		{
			breads.add(new BreadcrumbModel("新增付款方式", "", false));
		}
		
		if (payTypeModel != null && isPost(request)){
			payType = payTypeModel;
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/payTypeForm");
		mv.addObject("payType", payType);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			savePayType
	* Function Description		Save the pay type information.
	*****************************************************************************************************************************/
	public ModelAndView savePayType(HttpServletRequest request, HttpServletResponse response, PayTypeModel payType) throws Exception {
		String userId = SessionUtil.getUserId(request, getSystemName());
		payType.setUserId(userId);
		
		ModelAndView mv = new ModelAndView();
		
		formErrors = payType.validate();
		
		Integer exists = payTypeService.existPayTypeName(payType);
		if (exists != null)
		{
			formErrors.rejectValue("payTypeName", "system.common.duplicated", new Object[]{"付款方式"}, null);
			mv = payTypeForm(request, response, payType);
			return mv;
		}
		
		payTypeService.savePayType(payType);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("PayType", "payTypeList");
	}
}
