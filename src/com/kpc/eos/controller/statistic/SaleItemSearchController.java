package com.kpc.eos.controller.statistic;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.CommonStatisticSModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.model.statistic.StockItemDetailModel;
import com.kpc.eos.service.statistic.StatisticSaleService;
import com.kpc.eos.service.statistic.StatisticService;

/**
 * Filename		: SaleItemSearchController
 * Description	: Management class for searching the user's sale Item statistic
 * 2017
 * @author		: RKRK
 */
public class SaleItemSearchController extends BaseEOSController 
{
	private StatisticSaleService statisticSaleService;
	private StatisticService statisticService;
	
	ModelAndView mv = null;
	
	public SaleItemSearchController() 
	{
		super();
		controllerId = "SaleItemSearch";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "SaleItemSearch";
		
		breads.add(new BreadcrumbModel("报表中心", ""));
		breads.add(new BreadcrumbModel("商品销售统计", getCmdUrl("SaleItemSearchList")));
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
	
	public void setStatisticService(StatisticService statisticService) 
	{
		this.statisticService = statisticService;
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
	public ModelAndView saleItemSearchList(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception
	{
		String key = "Statistic_saleItemSearchList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (CommonStatisticSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "statistic/saleItemSearchList");
		
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
	public ModelAndView saleItemSearchGridAjax(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception 
	{
		request.setAttribute(SC_ID_SESSION, "Statistic_saleItemSearchList");
		mv = new ModelAndView("jsonView");
		
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		String catField = statisticService.getItemCat1FieldByUser(userId, Constants.CONST_ITEM_NAME_CODE);
		sc.setSearchString2(catField);
		
		StatisticTotalModel totalModel = statisticSaleService.countSaleItemSearchList(sc);
		Integer totalCount = totalModel.getTotalCnt();
		sc.getPage().setRecords(totalCount);
		List<StockItemDetailModel> list = null;
		if (totalCount != 0) {
			list = statisticSaleService.getSaleItemSearchList(sc);
		}
		
		HashMap<String, String> footerData = new HashMap<String, String>();
		footerData.put("totalName", "合计金额");
		footerData.put("totalData", totalModel.getTotalAmt()+"&nbsp;元 ");
		
		mv.addObject("footerData", footerData);
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Show login user's sale item detail statistic info with JSP page
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleItemDetailSearchList(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception
	{
		breads.add(new BreadcrumbModel("商品销售明细", getCmdUrl("saleItemDetailSearchList")));
		
		mv = new ModelAndView( "statistic/saleItemDetailSearchList");
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Show login user's sale item detail statistic info with Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleItemDetailSearchGridAjax(HttpServletRequest request, HttpServletResponse response, CommonStatisticSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		Integer totalCount = statisticSaleService.countSaleItemDetailSearchList(sc);
		sc.getPage().setRecords(totalCount);
		List<StockItemDetailModel> list = null;
		if (totalCount != 0) {
			list = statisticSaleService.getSaleItemDetailSearchList(sc);
		}
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}	
	
}
