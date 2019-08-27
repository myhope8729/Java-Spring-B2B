package com.kpc.eos.controller.statistic;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.CommonStatisticSModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.service.statistic.StatisticSaleService;
import com.kpc.eos.service.statistic.StatisticService;

/**
 * Filename		: SaleDaySearchController
 * Description	: Management class for searching the user's sale statistic info by a day
 * 2017
 * @author		: RKRK
 */
public class SaleDaySearchController extends BaseEOSController 
{
	private StatisticSaleService statisticSaleService;
	ModelAndView mv = null;
	
	public SaleDaySearchController() 
	{
		super();
		controllerId = "SaleDaySearch";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "SaleDaySearch";
		
		breads.add(new BreadcrumbModel("报表中心", ""));
		breads.add(new BreadcrumbModel("每日销售统计", getCmdUrl("saleDaySearchList")));
	}

	/**
	 * Description	: Set the setStatisticService
	 * @author 		: RKRK
	 * @param billSearchService
	 * 2017
	 */
	public void setStatisticSaleService(StatisticSaleService statisticSaleService) 
	{
		this.statisticSaleService = statisticSaleService;
	}
	
	/**
	 * Description	: Show login user's sale statistic info by a day with a JSP
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleDaySearchList(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception
	{
		String key = "Statistic_saleDaySearchList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (CommonStatisticSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "statistic/saleDaySearchList");
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Show login user's sale statistic info by a day with Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleDaySearchGridAjax(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception 
	{
		request.setAttribute(SC_ID_SESSION, "Statistic_saleDaySearchList");
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		StatisticTotalModel totalModel = statisticSaleService.countSaleDaySearchList(sc);
		Integer totalCount = totalModel.getTotalCnt();
		sc.getPage().setRecords(totalCount);
		List<BillSearchModel> list = null;
		if (totalCount != 0) {
			list = statisticSaleService.getSaleDaySearchList(sc);
		}
		
		HashMap<String, String> footerData = new HashMap<String, String>();
		footerData.put("totalName", "合计");
		footerData.put("totalData", totalModel.getTotalAmt());
		
		mv.addObject("footerData", footerData);
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Show login user's sale detail statistic info by a day with JSP page
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleDayDetailSearchList(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception
	{
		breads.add(new BreadcrumbModel("日销售明细", getCmdUrl("saleDayDetailSearchList")));
		
		mv = new ModelAndView( "statistic/saleDayDetailSearchList");
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Show login user's sale detail statistic info by a day with Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleDayDetailSearchGridAjax(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		Integer totalCount = statisticSaleService.countSaleDayDetailSearchList(sc);
		sc.getPage().setRecords(totalCount);
		List<BillSearchModel> list = null;
		if (totalCount != 0) {
			list = statisticSaleService.getSaleDayDetailSearchList(sc);
		}
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}	
	
}
