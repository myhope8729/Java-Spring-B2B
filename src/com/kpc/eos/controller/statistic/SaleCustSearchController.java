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
import com.kpc.eos.model.statistic.CommonStatisticSModel;
import com.kpc.eos.service.statistic.StatisticService;

/**
 * Filename		: SaleCustSearchController
 * Description	: Management class for the user's sale customer info searching.
 * 2017
 * @author		: RKRK
 */
public class SaleCustSearchController extends BaseEOSController 
{
	private StatisticService statisticService;
	ModelAndView mv = null;
	
	public SaleCustSearchController() 
	{
		super();
		controllerId = "SaleCustSearch";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "SaleCustSearch";
		
		breads.add(new BreadcrumbModel("报表中心", ""));
		breads.add(new BreadcrumbModel("客户销售统计", getCmdUrl("saleCustSearchList")));
	}

	/**
	 * Description	: Set the setStatisticService
	 * @author 		: RKRK
	 * @param billSearchService
	 * 2017
	 */
	public void setStatisticService(StatisticService statisticService) 
	{
		this.statisticService = statisticService;
	}
	
	/**
	 * Description	: Show the sale customer statistic info list by JSP
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleCustSearchList(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		String key = "Statistic_saleCustSearchList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (BillProcSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "statistic/saleCustSearchList");
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the customer statistic info list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleCustSearchGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception 
	{
		request.setAttribute(SC_ID_SESSION, "Statistic_saleCustSearchList");
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		Integer totalCount = statisticService.countSaleCustStatisticList(sc);
		sc.getPage().setRecords(totalCount);
		List<BillSearchModel> list = null;
		if (totalCount != 0) {
			list = statisticService.getSaleCustStatisticList(sc);
		}
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Show the sale customer statistic info list by day with JSP page
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleCustSearchByDayList(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception
	{
		breads.add(new BreadcrumbModel("客户销售明细", getCmdUrl("saleCustSearchByDayList")));
		
		mv = new ModelAndView( "statistic/saleCustSearchByDayList");
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the customer statistic info list by day by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleCustSearchByDayGridAjax(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		Integer totalCount = statisticService.countSaleCustStatisticByDayList(sc);
		sc.getPage().setRecords(totalCount);
		List<BillSearchModel> list = null;
		if (totalCount != 0) {
			list = statisticService.getSaleCustStatisticByDayList(sc);
		}
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}	
	
}
