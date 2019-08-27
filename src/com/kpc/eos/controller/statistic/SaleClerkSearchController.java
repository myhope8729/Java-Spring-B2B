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
import com.kpc.eos.model.statistic.SaleEmpSearchModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.service.statistic.StatisticSaleService;
import com.kpc.eos.service.statistic.StatisticService;

/**
 * Filename		: SaleClerkSearchController
 * Description	: Management class for searching the user's sale employer Statistic info
 * 2017
 * @author		: RKRK
 */
public class SaleClerkSearchController extends BaseEOSController 
{
	private StatisticSaleService statisticSaleService;
	ModelAndView mv = null;
	
	public SaleClerkSearchController() 
	{
		super();
		controllerId = "SaleClerkSearch";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "SaleClerkSearch";
		
		breads.add(new BreadcrumbModel("报表中心", ""));
		breads.add(new BreadcrumbModel("业务员销售统计", getCmdUrl("saleClerkSearchList")));
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
	public ModelAndView saleClerkSearchList(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception
	{
		String key = "Statistic_saleClerkSearchList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (CommonStatisticSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "statistic/saleClerkSearchList");
		
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
	public ModelAndView saleClerkSearchGridAjax(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception 
	{
		request.setAttribute(SC_ID_SESSION, "Statistic_saleClerkSearchList");
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		StatisticTotalModel totalModel = statisticSaleService.countSaleClerkSearchList(sc);
		Integer totalRecords = totalModel.getTotalRecords();
		sc.getPage().setRecords(totalRecords);
		List<SaleEmpSearchModel> list = null;
		if (totalRecords != 0) {
			list = statisticSaleService.getSaleClerkSearchList(sc);
		}
		
		HashMap<String, String> footerData = new HashMap<String, String>();
		footerData.put("totalName", "共计");
		footerData.put("totalData", totalModel.getTotalCnt() + "&nbsp;笔单据. &nbsp;&nbsp;&nbsp;合计金额:&nbsp;" + totalModel.getTotalAmt() + "&nbsp;元");
		
		mv.addObject("footerData", footerData);
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
}
