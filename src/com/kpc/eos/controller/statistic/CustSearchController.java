package com.kpc.eos.controller.statistic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.bizSetting.CustTypeModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.CustSearchModel;
import com.kpc.eos.service.bizSetting.CustTypeService;
import com.kpc.eos.service.statistic.StatisticService;

/**
 * Filename		: CustSearchController
 * Description	: Management class for the user's customer Info search.
 * 2017
 * @author		: RKRK
 */
public class CustSearchController extends BaseEOSController 
{
	private StatisticService statisticService;
	private CustTypeService custTypeService;
	
	ModelAndView mv = null;
	
	public CustSearchController() 
	{
		super();
		controllerId = "CustSearch";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "BillSearch";
		
		breads.add(new BreadcrumbModel("报表中心", ""));
		breads.add(new BreadcrumbModel("客户列表", ""));
	}

	/**
	 * Description	: Set the statistic service.
	 * @author 		: RKRK
	 * @param statisticService
	 * 2017
	 */
	public void setStatisticService(StatisticService statisticService) 
	{
		this.statisticService = statisticService;
	}
	
	/**
	 * Description	: Set the CustTypeService. 
	 * @author 		: RKRK
	 * @param custTypeService
	 * 2017
	 */
	public void setCustTypeService(CustTypeService custTypeService) {
		this.custTypeService = custTypeService;
	}	
	
	/**
	 * Description	: Show the user's customer info list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView custSearchList(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception
	{
		String key = "Statistic_custSearchList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (HostCustSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "statistic/custSearchList");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		//Get the customer type list by user
		List<CustTypeModel> custTypeList = custTypeService.getCustTypeListByUser(userId);
		mv.addObject("custTypeList", custTypeList);
		mv.addObject("userId", userId);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the billSearchs list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView custSearchGridAjax(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception 
	{
		request.setAttribute(SC_ID_SESSION, "Statistic_custSearchList");
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		Integer totalCount = statisticService.countCustInfoSearchList(sc);
		sc.getPage().setRecords(totalCount);
		List<CustSearchModel> list = null;
		if (totalCount != 0) {
			list = statisticService.getCustInfoSearchList(sc);
		}
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
}
