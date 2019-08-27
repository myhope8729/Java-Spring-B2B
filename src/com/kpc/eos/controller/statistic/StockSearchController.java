package com.kpc.eos.controller.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.JqGridUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.billProcMng.PrepayBillModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.statistic.BillSearchModel;
import com.kpc.eos.model.statistic.StatisticTotalModel;
import com.kpc.eos.model.statistic.StockItemDetailModel;
import com.kpc.eos.model.statistic.StockSearchSModel;
import com.kpc.eos.model.statistic.UserStockItemModel;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.statistic.StatisticService;

/**
 * Filename		: StockSearchController
 * Description	: Management class for the user's item Stock state.
 * 2017
 * @author		: RKRK
 */
public class StockSearchController extends BaseEOSController 
{
	private StatisticService statisticService;
	private UserItemService userItemService;
	ModelAndView mv = null;
	
	public StockSearchController() 
	{
		super();
		controllerId = "StockSearch";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "StockSearch";
		
		breads.add(new BreadcrumbModel("报表中心", ""));
		breads.add(new BreadcrumbModel("商品库存统计", getCmdUrl("stockSearchList")));
	}

	/**
	 * Description	: Set the statistic service.
	 * @author 		: RKRK
	 * @param billSearchService
	 * 2017
	 */
	public void setStatisticService(StatisticService statisticService) 
	{
		this.statisticService = statisticService;
	}
	
	/**
	 * Description	: Set the user item service.
	 * @author 		: RKRK
	 * @param userItemService
	 * 2017
	 */
	public void setUserItemService(UserItemService userItemService){
		this.userItemService = userItemService;
	}
	
	/**
	 * Description	: moving to the stock search list form.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView stockSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String key = "Statistic_stockSearchList";
		
		request.setAttribute(SC_ID_SESSION, key);
		StockSearchSModel sc = new StockSearchSModel();
		
		sc  = (StockSearchSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "statistic/stockSearchList");
		String userId = SessionUtil.getUserId(request, getSystemName());
		List<HashMap<String,String>> storeComboList = statisticService.getStoreComboListByUser(userId);
		
		String catField = statisticService.getItemCat1FieldByUser(userId, Constants.CONST_ITEM_TYPE1_CODE);
		sc.setPropertyField(catField);
		
		List<HashMap<String,String>> iTypeComboList = statisticService.getItemCategory1ListByUser(userId, null, catField);
		
			//Get item information using userId
			List<UserItemPropertyModel> userItemPropList = userItemService.getUserItemPropertyNoPriceList(userId);
			JSONArray colNameJSON = new JSONArray();
			List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
			
			colNameJSON.put("仓库");
			colModelJSON.add(JqGridUtil.getColModel("storeName", null, null, null, "left", "120", null,null));		
			
			for (UserItemPropertyModel item : userItemPropList)
			{
				colNameJSON.put(item.getPropertyDesc());
				
				JSONObject col = null;
				if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd())){
					col = JqGridUtil.getColModel(item.getPropertyName(), null, null, null, "left", "300", null,null);
				}else if (Constants.CONST_ITEM_NUM_CODE.equals(item.getPropertyTypeCd())){
					col = JqGridUtil.getColModel(item.getPropertyName(), null, null, null, "left", "120", null,null);
				}else if (Constants.CONST_ITEM_SALE_UNIT_CODE.equals(item.getPropertyTypeCd())){
					col = JqGridUtil.getColModel(item.getPropertyName(), null, null, null, "center", "80", null,null);
				}else {
					col = JqGridUtil.getColModel(item.getPropertyName(), null, null, null, "center", "100", null,null);
				}
				colModelJSON.add(col);
			}
			
			JSONObject formatoptions = new JSONObject();
			formatoptions.put("decimalPlaces", 2);
			
			colNameJSON.put("成本价");
			colModelJSON.add(JqGridUtil.getColModel("stockCost", null, null, null, "right", "100", "number", null));				
			
			colNameJSON.put("最新进价");
			colModelJSON.add(JqGridUtil.getColModel("priceIn", null, null, null, "right",  "100", "number", null));		
			
			colNameJSON.put("最新供货方");
			colModelJSON.add(JqGridUtil.getColModel("lastVendorName", null, null, null, "left"));	
			
			
			colNameJSON.put("库存数量");
			colModelJSON.add(JqGridUtil.getColModel("stockQty", null, null, null, "right", null, "number", formatoptions));
			
			colNameJSON.put("库存金额(元)");
			colModelJSON.add(JqGridUtil.getColModel("stockTot", null, null, null, "right", null, "number", formatoptions));				
			
			colNameJSON.put("操作");
			colModelJSON.add(JqGridUtil.getColModel("control", null, null, false, "center", "100", null, null));			
			
			JSONObject gridModel = new JSONObject();
			gridModel.put("colNames", colNameJSON);
			gridModel.put("colModel", colModelJSON);
			mv.addObject("gridModel", gridModel);
			
		
		mv.addObject("userId", userId);
		mv.addObject("storeComboList", storeComboList);
		mv.addObject("iTypeComboList", iTypeComboList);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the stock search list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView stockSearchGridAjax(HttpServletRequest request, HttpServletResponse response, StockSearchSModel sc) throws Exception 
	{
		request.setAttribute(SC_ID_SESSION, "Statistic_stockSearchList");
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		StatisticTotalModel total = statisticService.countStockItemListByUser(sc);
		sc.getPage().setRecords(total.getTotalCnt());
		List<UserStockItemModel> list = null;
		if (total.getTotalCnt() != 0) {
			list = statisticService.getStockItemListByUser(sc);
		}
		HashMap<String, String> footerData = new HashMap<String, String>();
		footerData.put("totalName", "合计");
		footerData.put("totalData", total.getTotalAmt());
		
		mv.addObject("footerData", footerData);
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Show the Stock Detail Search list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView stockDetailSearchList(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		breads.add(new BreadcrumbModel("商品出入库明细", getCmdUrl("stockDetailSearchList")));
		
		mv = new ModelAndView( "statistic/stockDetailSearchList");
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the Stock Detail Search list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView stockDetailSearchGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		Integer totalCount = statisticService.countStockDetailSearchList(sc);
		sc.getPage().setRecords(totalCount);
		List<StockItemDetailModel> list = null;
		if (totalCount != 0) {
			list = statisticService.getStockDetailSearchList(sc);
		}
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
}
