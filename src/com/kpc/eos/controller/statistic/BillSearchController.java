package com.kpc.eos.controller.statistic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.service.statistic.StatisticService;

/**
 * Filename		: BillSearchController.java
 * Description	: Management class for the user's billSearchs.
 * 2017
 * @author		: RKRK
 */
public class BillSearchController extends BaseEOSController 
{
	private StatisticService statisticService;
	ModelAndView mv = null;
	
	public BillSearchController() 
	{
		super();
		controllerId = "BillSearch";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "BillSearch";
		
		breads.add(new BreadcrumbModel("报表中心", ""));
		breads.add(new BreadcrumbModel("业务单据查询", getCmdUrl("billSearchList")));
	}

	/**
	 * Description	: Set the billSearch service.
	 * @author 		: RKRK
	 * @param billSearchService
	 * 2017
	 */
	public void setStatisticService(StatisticService statisticService) 
	{
		this.statisticService = statisticService;
	}
	
	/**
	 * Description	: Show the billSearchs list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView billSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String key = "Statistic_billSearchList";
		
		request.setAttribute(SC_ID_SESSION, key);
		BillProcSModel sc = new BillProcSModel();
		
		sc  = (BillProcSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "statistic/billSearchList");
		String userId = SessionUtil.getUserId(request, getSystemName());
		String printType = statisticService.getPrintTypeByUser(userId);
		mv.addObject("userId", userId);
		mv.addObject("printType", printType);
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
	public ModelAndView billSearchGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception 
	{
		request.setAttribute(SC_ID_SESSION, "Statistic_billSearchList");
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		Integer totalCount = statisticService.countBillSearchList(sc);
		sc.getPage().setRecords(totalCount);
		List<BillSearchModel> list = null;
		if (totalCount != 0) {
			list = statisticService.getBillSearchList(sc);
		}
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
}
