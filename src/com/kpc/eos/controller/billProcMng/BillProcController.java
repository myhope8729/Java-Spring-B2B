
package com.kpc.eos.controller.billProcMng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.JqGridUtil;
import com.kpc.eos.core.util.MathUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.bill.PayBillDetailModel;
import com.kpc.eos.model.bill.PriceDetailModel;
import com.kpc.eos.model.bill.VendorModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.billProcMng.BuyStatisticModel;
import com.kpc.eos.model.billProcMng.DistributeConfirmModel;
import com.kpc.eos.model.billProcMng.DistributeStatisticModel;
import com.kpc.eos.model.billProcMng.PrepayBillModel;
import com.kpc.eos.model.billProcMng.PrepayBillSModel;
import com.kpc.eos.model.billProcMng.SupplyStatisticModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.EmployeeModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.WorkFlowGroupModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.model.bizSetting.WorkFlowSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.bill.BillService;
import com.kpc.eos.service.bill.PriceDetailService;
import com.kpc.eos.service.billProcMng.BillProcService;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.bizSetting.WorkFlowService;

public class BillProcController extends BaseEOSController {

	private BillProcService billProcService;
	private BizDataService bizDataService;
	private UserItemService userItemService;
	private BillService	billService;
	private BizSettingService bizSettingService;
	private WorkFlowService workFlowService;
	private PriceDetailService priceDetailService;
	
	public void setBillProcService(BillProcService billProcService) {
		this.billProcService = billProcService;
	}
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	public void setUserItemService(UserItemService userItemService){
		this.userItemService = userItemService;
	}
	public void setBillService(BillService billService){
		this.billService = billService;
	}
	public void setBizSettingService(BizSettingService bizSettingService){
		this.bizSettingService = bizSettingService;
	}
	public void setWorkFlowService(WorkFlowService workFlowService){
		this.workFlowService = workFlowService;
	}
	public void setPriceDetailService(PriceDetailService priceDetailService){
		this.priceDetailService = priceDetailService;
	}
	
	public BillProcController() {
		super();
		controllerId = "BillProc";
	}
	
	/**
	 * command initialization function.
	 * When getting a request, this function will be called before running a cmd's method.
	 * Define Breadcrumb model.
	 */
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("处理单据", "", false));
		breads.add(new BreadcrumbModel("待处理单据 ", getCmdUrl("billProcUncheckedList"), true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			billProcUncheckedList
	* Function Description		Call the view for bill process unchecked list according to employee id
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView billProcUncheckedList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		
		BillProcSModel sc = new BillProcSModel();
		
		String key = "BillProc_billProcUncheckedList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (BillProcSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		sc.setEmpId(loginUser.getEmpId());
		sc.setUserId(loginUser.getUserId());
		
		if (StringUtils.isEmpty(sc.getCreateDateFrom())){
			sc.setCreateDateFrom(billProcService.getMinMaxDate(sc, true));
		}
		if (StringUtils.isEmpty(sc.getCreateDateTo())){
			sc.setCreateDateTo(billProcService.getMinMaxDate(sc, false));
		}
		
		ModelAndView mv = new ModelAndView("billProcMng/billProcUncheckedList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			billProcUncheckedGridAjax
	* Function Description		Retreive the unchecked bill list
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView billProcUncheckedGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		request.setAttribute(SC_ID_SESSION, "BillProc_billProcUncheckedList");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		sc.setEmpId(loginUser.getEmpId());
		sc.setUserId(loginUser.getUserId());
		sc.setChecked(Constants.CONST_N);
		
		Integer totalCount = billProcService.getCountBillProcList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<BillProcModel> list = billProcService.getBillProcList(sc);
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			billProcForm
	* Function Description		Show the detail bill information
	 * @throws Exception 
	*****************************************************************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView billProcForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		
		String billProcId = request.getParameter("billProcId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		BillProcModel billProc = billProcService.getBillProc(billProcId, userId);
		
		if (billProc == null){
			return redirect("BillProc", "billProcUncheckedList");
		}
		ModelAndView mv = null;
		
		String itemUserId = "";
		
		if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcOrderForm");
			
			//Get the eos_paybill_detail_eos table in case hbmark of eos_bill_head_new is 'Y'
			if (billProc.getBillHead().getHbmark().equals("Y"))
			{
				Map map = new HashMap();
				map.put("hostUserId", billProc.getBillHead().getHostUserId());
				map.put("custUserId", billProc.getBillHead().getCustUserId());
				
				List<PrepayBillModel> prePayDtl = billProcService.getPrepayInfo(map);
				mv.addObject("prePayList", prePayDtl);
			}
			
			//Get the process environment from eos_pdat_line_new table (code_id : BD0014)
			List<BizDataModel> procEnvList = bizDataService.getBizDataByBizCode(userId, Constants.CONST_BIZDATA_ORDER_ENV, null);
			mv.addObject("procEnv", procEnvList);
			
			//Check exists bill process of other user or not
			String checkProcYn = billProcService.checkBillProc(billProc.getBillId());
			mv.addObject("checkProcYn", checkProcYn);
			
			if (Constants.ITEM_TYPE_BY_GUEST.equals(billProc.getBillHead().getItemtype()))
			{
				itemUserId = billProc.getBillHead().getCustUserId();
			}
			else
			{
				itemUserId = billProc.getBillHead().getHostUserId();
			}
			breads.add(new BreadcrumbModel("订货单详细", "", false));
		}
		else if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcReceiptForm");
			itemUserId = billProc.getBillHead().getCustUserId();
			breads.add(new BreadcrumbModel("入库单详细", "", false));
		}
		else if (Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcSaleForm");
			
			//Get the eos_paybill_detail_eos table in case hbmark of eos_bill_head_new is 'Y'
			if (billProc.getBillHead().getHbmark().equals("Y"))
			{
				Map map = new HashMap();
				map.put("hostUserId", billProc.getBillHead().getHostUserId());
				map.put("custUserId", billProc.getBillHead().getCustUserId());
				
				List<PrepayBillModel> prePayDtl = billProcService.getPrepayInfo(map);
				mv.addObject("prePayList", prePayDtl);
			}
			
			if (Constants.ITEM_TYPE_BY_GUEST.equals(billProc.getBillHead().getItemtype()))
			{
				itemUserId = billProc.getBillHead().getCustUserId();
			}
			else
			{
				itemUserId = billProc.getBillHead().getHostUserId();
				if (StringUtils.isNotEmpty(billProc.getBillHead().getWebno()))
				{
					itemUserId = billProc.getBillHead().getWebno();
				}
			}
			breads.add(new BreadcrumbModel("销售单详细", "", false));
		}
		else if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcPriceForm");
			itemUserId = billProc.getBillHead().getHostUserId();
			breads.add(new BreadcrumbModel("调价单详细", "", false));
		}
		else if (Constants.CONST_BILL_TYPE_PAYMENT.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcPaymentForm");
			breads.add(new BreadcrumbModel("货款单详细", "", false));
		}
		else
		{
			return redirect("BillProc", "billProcUncheckedList");
		}
		
		if (!Constants.CONST_BILL_TYPE_PAYMENT.equals(billProc.getBillHead().getBillType()))
		{
			mv.addObject("itemUserId", itemUserId);
			
			String jsCol = "";
			
			String itemNoCol = "";
			String itemNameCol = "";
			
			//Get item information using itemUserId
			List<UserItemPropertyModel> userItemPropList = userItemService.getUserNNItemPropertyList(itemUserId);
			JSONArray colNameJSON = new JSONArray();
			List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
			
			for (UserItemPropertyModel item : userItemPropList)
			{
				colNameJSON.put(item.getPropertyDesc());
				
				JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, "center");
				
				if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "200");
					col.put("align", "left");
					itemNameCol = item.getPropertyName();
				}else if (Constants.CONST_ITEM_NUM_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "100");
					col.put("align", "left");
					itemNoCol = item.getPropertyName();
				}else if (Constants.CONST_ITEM_SALE_UNIT_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "70");
					col.put("align", "center");
				}else if (Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "100");
					col.put("align", "right");
				}else if (Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "right");
					col.put("width", "100");
					jsCol = item.getPropertyName();
				}else{
					col.put("width", "100");
				}
				colModelJSON.add(col);
			}
			
			if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType())){
				colNameJSON.put(billProc.getBillHead().getPricedesc());
				colModelJSON.add(JqGridUtil.getColModel("priceUnion", null, null, false, "right", "100", null, null));
			} else if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType())){
				colNameJSON.put("前次入库价");
				JSONObject fomatterOption = new JSONObject();
				fomatterOption.put("decimalPlaces", 2);
				colModelJSON.add(JqGridUtil.getColModel("priceIn", null, null, false, "right", "100", "number", fomatterOption));
				colNameJSON.put("入库价");
				colModelJSON.add(JqGridUtil.getColModel("priceUnion", null, null, false, "right", "100", null, null));
			}
			
			//Get jsYn biz setting date, sys_code : 0403
			String jsYnMark = "N";
			
			if (StringUtils.isNotEmpty(jsCol))
			{
				if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()))
				{
					BizSettingModel jsYn = bizSettingService.getBizSettingBySysType(itemUserId, BizSetting.JS);
					jsYnMark = BizSetting.JS_Y.equals(jsYn.getSysValueName()) ? "Y" : "N";
				}
				else if(Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
				{
					BizSettingModel jsYn = bizSettingService.getBizSettingBySysType(itemUserId, BizSetting.JS_IN_SALE);
					jsYnMark = BizSetting.JS_IN_SALE_Y.equals(jsYn.getSysValueName()) ? "Y" : "N";
				}
			}
			
			//Get bill line list by bill id
			Map map = new HashMap();
			map.put("userId", itemUserId);
			map.put("billId", billProc.getBillId());
			if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
			{
				map.put("itemYn", billProc.getItemYn());
			}
			
			List<BillLineModel> billLineList = null;
			List<PriceDetailModel> priceDetailList = null;
			
			BillHeadModel rBillHead = null;
			List<UserItemPropertyModel> userItemPricePropertyList = null;
			
			if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType()))
			{
				priceDetailList = priceDetailService.getPriceItemList(itemUserId, billProc.getBillId(), Constants.CONST_STATE_Y);
				rBillHead = billService.getBill(billProc.getBillHead().getRbillId());
				mv.addObject("rBillHead", rBillHead);
				
				userItemPricePropertyList = userItemService.getUserItemPricePropertyList(itemUserId);
				billProc.setPriceDetailList(priceDetailList);
			}
			else
			{
				billLineList = billService.getBillItemList(map);
				billProc.setBillLineList(billLineList);
			}
			
			JSONObject gridModel = new JSONObject();
			gridModel.put("colNames", colNameJSON);
			gridModel.put("colModel", colModelJSON);
			gridModel.put("priceCols", userItemPricePropertyList);
			gridModel.put("billProc", new JSONObject(billProc));
			
			mv.addObject("gridModel", gridModel);
			mv.addObject("jsCol", jsCol);
			mv.addObject("jsYn", jsYnMark);
			mv.addObject("itemNoCol", itemNoCol);
			mv.addObject("itemNameCol", itemNameCol);
			
			if (super.isMobileClient()){
				List<BillProcModel> billProcHistory = billProcService.getBillHistoryList(billProc.getBillId(), userId);
				mv.addObject("billHistory", billProcHistory);
			}
		}
		else
		{
			List<PayBillDetailModel> detailList = billService.getPaybillDetailListByBillId(billProc.getBillId());
			mv.addObject("detailList", new JSONArray(detailList));
		}
		
		mv.addObject("billProc", billProc);
		
		billProcService.updateReadMark(billProc.getBillProcId());
		
		return mv;
	}
	
	public ModelAndView billProcHistGridAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		String billId = request.getParameter("billId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		List<BillProcModel> list = billProcService.getBillHistoryList(billId, userId);
		mv.addObject("rows", list);
		
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView saveBillProc(HttpServletRequest request, HttpServletResponse response, BillProcModel billProc) throws Exception
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView", "result", rm);
		
		String saveFlag = request.getParameter("saveFlag");
		String procNote = request.getParameter("procNote");
		String billId = billProc.getBillId();
		String billType = request.getParameter("billType");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		billProc.setProcNote(procNote);
		
		//Get bill head information
		BillHeadModel billHead = billService.getBill(billId);
		billProc.setBillHead(billHead);
		
		List<BillLineModel> billLineArray = new ArrayList<BillLineModel>();
		List<PriceDetailModel> priceDetailArray = new ArrayList<PriceDetailModel>();
		
		Double tot = null;
		
		List<String> itemIds = new ArrayList();
		HashMap<String, BillLineModel> cartItems = new HashMap<String, BillLineModel>();
		
		if (Constants.CONST_BILL_TYPE_PRICE.equals(billType)){
			String[] newPriceArray1 = request.getParameterValues("newPrice_1");
			String[] newPriceArray2 = request.getParameterValues("newPrice_2");
			String[] newPriceArray3 = request.getParameterValues("newPrice_3");
			String[] newPriceArray4 = request.getParameterValues("newPrice_4");
			String[] newPriceArray5 = request.getParameterValues("newPrice_5");
			String[] note = request.getParameterValues("note");
			String[] itemIdArray = request.getParameterValues("itemId");
			
			for (int i=0; i < itemIdArray.length; i++)
			{
				PriceDetailModel priceDetail = new PriceDetailModel();
				priceDetail.setBillId(billProc.getBillId());
				priceDetail.setItemId(itemIdArray[i]);
				priceDetail.setUserId(userId);
				
				if (newPriceArray1 != null){
					priceDetail.setD12(newPriceArray1[i]);
				}
				if (newPriceArray2 != null){
					priceDetail.setD22(newPriceArray2[i]);
				}
				if (newPriceArray3 != null){
					priceDetail.setD32(newPriceArray3[i]);
				}
				if (newPriceArray4 != null){
					priceDetail.setD42(newPriceArray4[i]);
				}
				if (newPriceArray5 != null){
					priceDetail.setD52(newPriceArray5[i]);
				}
				priceDetail.setNote(note[i]);
				priceDetail.setUpdateBy(billProc.getUpdateBy());
				
				priceDetailArray.add(priceDetail);
			}
			billProc.setPriceDetailList(priceDetailArray);
		}
		else if (Constants.CONST_BILL_TYPE_PAYMENT.equals(billType))
		{
			String totalAmt = request.getParameter("totalAmt");
			billProc.getBillHead().setTotalAmt(billProc.getBillHead().getTotal2());
			billProc.getBillHead().setTotal2(totalAmt);
		}
		else
		{
			//Get the item price2, qty2 information and set this info to BillLineModel
			String itemsStr = request.getParameter("itemsStr");
			
			tot = (double) 0;
			JSONArray jsonArray = new JSONArray(itemsStr);
			for (int i=0; i<jsonArray.length(); i++)
			{
				JSONObject item = (JSONObject)jsonArray.get(i);
				
				BillLineModel billLineModel = new BillLineModel();
				billLineModel.setItemId((String)item.get("itemId"));
				billLineModel.setPrice((String)item.get("price"));
				billLineModel.setPrice2((String)item.get("price2"));
				billLineModel.setQty((String)item.get("qty"));
				billLineModel.setQty2((String)item.get("qty2"));
				billLineModel.setTot2((String)item.get("tot2"));
				billLineModel.setJsDisplay((String)item.get("jsDisplay"));
				billLineModel.setJsQty((String)item.get("jsQty"));
				billLineModel.setNote((String)item.get("note"));
				billLineModel.setBillId(billId);
				billLineModel.setUpdateBy(billProc.getUpdateBy());
				tot += Double.valueOf(billLineModel.getTot2());
				
				itemIds.add(billLineModel.getItemId());
				cartItems.put((String)item.get("itemId"), billLineModel);
				billLineArray.add(billLineModel);
			}
			billProc.setBillLineList(billLineArray);
		}
		
		if (Constants.CONST_Y.equals(saveFlag))
		{
			billProcService.saveBillProc(billProc, null);
			SysMsg.addMsg(SysMsg.SUCCESS, rm.getResultMsg(), request);
			rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
			return mv;
		}
		
		//Check bill item stock table
		if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType())){
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("includeStockQty", "1");
			params.put("state", Constants.BILL_STATE_IN_PROCESS);
			
			if ((itemIds == null) || (itemIds.size() == 0)){
				rm.setResultMsg(MessageUtil.getMessage("billproc.submit.ok.no.item"));
				return mv;
			}
			
			List<UserItemModel> userItems = userItemService.getUserItemListByIds(billProc.getBillHead().getHostUserId(), itemIds, params);
			
			for (UserItemModel userItem : userItems){
				BillLineModel cartItem = cartItems.get(userItem.getItemId());
				if (Constants.CONST_Y.equals(userItem.getStockMark())){
					double cartQty2 = Double.valueOf(cartItem.getQty2()).doubleValue();
					double cartQty = Double.valueOf(cartItem.getQty()).doubleValue();
					double stockQty = Double.valueOf(userItem.getStockQty()).doubleValue() + cartQty;
					
					if (cartQty2 > stockQty){
						rm.setResultMsg(MessageUtil.getMessage("order.submit.ok.no.items"));
						return mv;
					}
				}
			}
		}
		
		String priceMark = Constants.CONST_N;
		if (Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
		{
			priceMark = request.getParameter("changePrice");
		}
		else if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType()))
		{
			BizSettingModel bsModel = bizSettingService.getBizSettingBySysType(userId, BizSetting.AUTO_PRICE_BY_BILL);
			priceMark = BizSetting.AUTO_PRICE_BY_BILL_Y.equals(bsModel.getSysValueName()) ? "Y" : "N";
		}
		
		//Search the next work flow for next processing
		WorkFlowModel curUserNextWFModel = null;
		List<WorkFlowModel> thirdPartyNextWFModel = new ArrayList<WorkFlowModel>();
		List<Map> nextWorkFlow = new ArrayList<Map>();
		
		if (Constants.CONST_BILL_TYPE_DING.equals(billType)){
			//Check if the bill is distributed or not
			String distributedYn = billProcService.checkDistributedOrder(billProc.getBillId());
			
			//Get vendor list of bill line's items
			List<VendorModel> vendorList = billProcService.getVendorListForBillLineByBillId(billId);
			
			if (Constants.CONST_N.equals(distributedYn)){
				// Check the vendor's workflow list
				for (VendorModel vendor : vendorList)
				{
					// If vendor's id equals with host user id of bill head, check the distribute yn value of current workflow,
					// and if distribute yn of current workflow is 'Y' and blnDistributeYn is 'Y', return fail.
					if (vendor.getUserId().equals(billProc.getBillHead().getHostUserId()))
					{
						Map map = new HashMap();
						map.put("workFlowId", billProc.getWorkFlowId());
						map.put("custUserId", billProc.getBillHead().getCustUserId());
						map.put("custWfType", Constants.WF_TYPE_ORDER_REQUEST);
						map.put("hostUserId", billProc.getBillHead().getHostUserId());
						map.put("hostWfType", Constants.WF_TYPE_ORDER_ACCEPT);
						map.put("totalAmt", tot);
						curUserNextWFModel = workFlowService.getNextWorkFlowOfBillProcess(map, false);
					}
					// In case third party supplier 
					else if (Constants.CONST_Y.equals(billProc.getDistributeYn()))
					{
						Map map = new HashMap();
						map.put("hostUserId", vendor.getUserId());
						map.put("hostWfType", Constants.WF_TYPE_ORDER_ACCEPT);
						map.put("totalAmt", tot);
						thirdPartyNextWFModel.add(workFlowService.getNextWorkFlowOfBillProcess(map, true));
					}
				}
			}
			else
			{
				Map map = new HashMap();
				map.put("workFlowId", billProc.getWorkFlowId());
				map.put("custUserId", billProc.getBillHead().getCustUserId());
				map.put("custWfType", Constants.WF_TYPE_ORDER_REQUEST);
				map.put("hostUserId", billProc.getBillHead().getHostUserId());
				map.put("hostWfType", Constants.WF_TYPE_ORDER_ACCEPT);
				map.put("totalAmt", tot);
				curUserNextWFModel = workFlowService.getNextWorkFlowOfBillProcess(map, false);
			}			
		}
		else
		{
			Map map = new HashMap();
			map.put("workFlowId", billProc.getWorkFlowId());
			if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType())){
				map.put("custUserId", billProc.getBillHead().getCustUserId());
				map.put("custWfType", Constants.WF_TYPE_RECEIPT);
			}else if (Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType())){
				map.put("hostUserId", billProc.getBillHead().getHostUserId());
				map.put("hostWfType", Constants.WF_TYPE_SALE);
			}else if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType())){
				map.put("hostUserId", billProc.getBillHead().getHostUserId());
				map.put("hostWfType", Constants.WF_TYPE_PRICE);
			}else if (Constants.CONST_BILL_TYPE_PAYMENT.equals(billProc.getBillHead().getBillType())){
				map.put("hostUserId", billProc.getBillHead().getHostUserId());
				map.put("hostWfType", Constants.WF_TYPE_PAYMENT);
			}
			map.put("totalAmt", tot);
			curUserNextWFModel = workFlowService.getNextWorkFlowOfBillProcess(map, false);
		}
		
		if (curUserNextWFModel != null){
			//Get the employee list for next workflow
			String procDatId = curUserNextWFModel.getWorkFlowId();
			
			List<EmployeeModel> procEmpList = new ArrayList<EmployeeModel>();
			
			WorkFlowSModel sc = new WorkFlowSModel();
			sc.setUserId(billProc.getBillHead().getCustUserId());
			sc.setWorkFlowId(procDatId);
			
			if (curUserNextWFModel.isGroupWorkflow()){
								
				List<WorkFlowGroupModel> procGroups = workFlowService.getWorkFlowGroupListForWfID(sc);
				
				if (procGroups != null && !procGroups.isEmpty())
				{
					for (WorkFlowGroupModel pg : procGroups)
					{
						Map p1 = new HashMap();
						p1.put("billId", billProc.getBillId());
						p1.put("condition", pg.getCond());
						Integer exist = billService.checkExistBillHeadForWFGroupCondition(p1);
						
						if (exist != null)
						{
							WorkFlowGroupModel wfGroup = new WorkFlowGroupModel();
							wfGroup.setWorkFlowId(procDatId);
							wfGroup.setSeqNo(pg.getSeqNo());
							
							List<EmployeeModel> procEmpListTemp = workFlowService.getWorkFlowGroupEmployeeList(wfGroup);
							
							if (!procEmpListTemp.isEmpty())
							{
								for (int i = 0; i < procEmpListTemp.size(); i++){
									if (procEmpList.contains(procEmpListTemp.get(i))) continue;
									procEmpList.add(procEmpListTemp.get(i));
								}
							}
						}
					}
				}
			}
			else
			{
				procEmpList = workFlowService.getWorkFlowEmpList(sc);
			}
			
			if (procEmpList.isEmpty() || procEmpList.size() == 0)
			{
				rm.setResultMsg(MessageUtil.getMessage("billproc.submit.ok.no.emp"));
				return mv;
			}
			Map map = new HashMap();
			map.put("empList", procEmpList);
			map.put("workFlow", curUserNextWFModel);
			
			nextWorkFlow.add(map);
		}
		
		for (WorkFlowModel thirdWFModel : thirdPartyNextWFModel)
		{
			//Get the employee list for next workflow
			String procDatId = thirdWFModel.getWorkFlowId();
			
			List<EmployeeModel> procEmpList = new ArrayList<EmployeeModel>();
			
			WorkFlowSModel sc = new WorkFlowSModel();
			sc.setUserId(billProc.getBillHead().getHostUserId());
			sc.setWorkFlowId(procDatId);
			
			if (thirdWFModel.isGroupWorkflow()){
				List<WorkFlowGroupModel> procGroups = workFlowService.getWorkFlowGroupListForWfID(sc);
				
				if (procGroups != null && !procGroups.isEmpty())
				{
					for (WorkFlowGroupModel pg : procGroups)
					{
						Map p1 = new HashMap();
						p1.put("billId", billProc.getBillId());
						p1.put("condition", pg.getCond());
						Integer exist = billService.checkExistBillHeadForWFGroupCondition(p1);
						
						if (exist != null)
						{
							WorkFlowGroupModel wfGroup = new WorkFlowGroupModel();
							wfGroup.setWorkFlowId(procDatId);
							wfGroup.setSeqNo(pg.getSeqNo());
							
							List<EmployeeModel> procEmpListTemp = workFlowService.getWorkFlowGroupEmployeeList(wfGroup);
							
							if (!procEmpListTemp.isEmpty())
							{
								procEmpList.addAll(procEmpListTemp);
							}
						}
					}
				}
			}
			else
			{
				procEmpList = workFlowService.getWorkFlowEmpList(sc);
			}
			
			if (procEmpList.isEmpty() || procEmpList.size() == 0)
			{
				rm.setResultMsg(MessageUtil.getMessage("billproc.submit.ok.no.emp"));
				return mv;
			}
			
			Map map = new HashMap();
			map.put("empList", procEmpList);
			map.put("workFlow", thirdWFModel);
			
			nextWorkFlow.add(map);
		}
		
		if ( StringUtils.isNotEmpty(billHead.getPaymentType()) && (Constants.CONST_BILL_TYPE_DING.equals(billType) || Constants.CONST_BILL_TYPE_SALE.equals(billType))) 
		{
			// Get the remaining prepay balance
			PrepayBillSModel ppSc = new PrepayBillSModel(billHead.getHostUserId(), billHead.getCustUserId(), billHead.getPaytypeId(), billHead.getPaymentType());
			List<PrepayBillModel> ppList = billService.getPrePayTotalAmtList(ppSc);
			if (ppList.isEmpty() || tot > MathUtil.getDouble(ppList.get(0).getTotalAmt(), true)) 
			{
				rm.setResultMsg(MessageUtil.getMessage("order.insufficient_prepayment"));
				return mv;
			}
		}
		
		billProc.setSaveFlag(saveFlag);
		billProc.setPriceMark(priceMark);
		
		billProcService.saveBillProc(billProc, nextWorkFlow);
		
		SysMsg.addMsg(SysMsg.SUCCESS, rm.getResultMsg(), request);
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return mv;
	}
	
	public ModelAndView rejectDocumentAjax(HttpServletRequest request, HttpServletResponse response, BillProcModel billProc) throws Exception
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView", "result", rm);
		
		String billId = billProc.getBillId();
		BillHeadModel billHead = billService.getBill(billId);
		billProc.setBillHead(billHead);
		
		String empId = SessionUtil.getEmpId(request, getSystemName());
		billProc.setEmpId(empId);
		billProcService.rejectDocument(billProc);
		
		SysMsg.addMsg(SysMsg.SUCCESS, rm.getResultMsg(), request);
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		return mv;
	}
	
	public ModelAndView supplyStatistic(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		initCmd();
		breads.add(new BreadcrumbModel("供货统计", "", false));
		
		request.setAttribute(SC_ID_SESSION, "BillProc_billProcUncheckedList");
		
		ModelAndView mv = new ModelAndView("billProcMng/supplyStatistics");
		
		if (StringUtils.isEmpty(sc.getCreateDateFrom()) && StringUtils.isEmpty(sc.getCreateDateTo()))
		{
			sc.setCreateDateFrom1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
			sc.setCreateDateTo1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
		}
		else
		{
			sc.setCreateDateFrom1(sc.getCreateDateFrom());
			sc.setCreateDateTo1(sc.getCreateDateTo());
		}
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		//Get item information using itemUserId
		JSONArray colNameJSON = new JSONArray();
		List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
		
		colNameJSON.put("商品编码");
		colModelJSON.add(JqGridUtil.getColModel("itemNo", null, null, false, "center", "120", null, null));
		
		colNameJSON.put("商品名称");
		colModelJSON.add(JqGridUtil.getColModel("itemName", null, null, false, "left", "250", null, null));
		
		colNameJSON.put("单位");
		colModelJSON.add(JqGridUtil.getColModel("unit", null, null, false, "center", "100", null, null));
		
		colNameJSON.put("采购数量");
		colModelJSON.add(JqGridUtil.getColModel("qty", null, null, false, "right", "100", null, null));
		
		BizSettingModel priceMark = bizSettingService.getBizSettingBySysType(userId, BizSetting.SUPPLY_STATISTIC_SALE_PRICE);
		if (BizSetting.SUPPLY_STATISTIC_PRICE_Y.equals(priceMark.getSysValueName()))
		{
			colNameJSON.put("销售价");
			colModelJSON.add(JqGridUtil.getColModel("salePrice", null, null, false, "right", "100", null, null));
		}
		
		colNameJSON.put("上次进价");
		colModelJSON.add(JqGridUtil.getColModel("lastPriceIn", null, null, false, "right", "100", null, null));
		
		colNameJSON.put("上次采购日期");
		colModelJSON.add(JqGridUtil.getColModel("arriveDate", null, null, false, "center", "120", null, null));
		
		colNameJSON.put("上次供货方");
		colModelJSON.add(JqGridUtil.getColModel("lastVendorName", null, null, false, "left", "250", null, null));
		
		JSONObject gridModel = new JSONObject();
		gridModel.put("colNames", colNameJSON);
		gridModel.put("colModel", colModelJSON);
		
		mv.addObject("gridModel", gridModel);
		mv.addObject("sc", sc);
		return mv;
	}
	
	public ModelAndView supplyStatisticGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception{

		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		List<SupplyStatisticModel> orderStatList = billProcService.getSupplyStatistics(sc);
		
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("rows", orderStatList);
		
		return mv;
	}
	
	public ModelAndView supplyStatisticPrintForm(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception{
		ModelAndView mv = new ModelAndView("billProcMng/print/supplyStatisticPrint");
		
		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		List<SupplyStatisticModel> supplyStatList = billProcService.getSupplyStatistics(sc);
		
		BizSettingModel priceMark = bizSettingService.getBizSettingBySysType(sc.getUserId(), BizSetting.SUPPLY_STATISTIC_SALE_PRICE);
		if (BizSetting.SUPPLY_STATISTIC_PRICE_Y.equals(priceMark.getSysValueName()))
		{
			mv.addObject("salePriceYn", "Y");
		}
		else
		{
			mv.addObject("salePriceYn", "N");
		}
		mv.addObject("userName", ((UserModel)SessionUtil.getUser(request, getSystemName())).getUserName());
		mv.addObject("supplyStatList", supplyStatList);
		
		return mv;
	}
	
	public ModelAndView buyStatistic(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		initCmd();
		breads.add(new BreadcrumbModel("采购单", "", false));
		
		request.setAttribute(SC_ID_SESSION, "BillProc_billProcUncheckedList");
		
		ModelAndView mv = new ModelAndView("billProcMng/buyStatistics");
		
		if (StringUtils.isEmpty(sc.getCreateDateFrom()) && StringUtils.isEmpty(sc.getCreateDateTo()))
		{
			sc.setCreateDateFrom1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
			sc.setCreateDateTo1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
		}
		else
		{
			sc.setCreateDateFrom1(sc.getCreateDateFrom());
			sc.setCreateDateTo1(sc.getCreateDateTo());
		}
		
		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		if (!isMobileClient())
		{
			List<BuyStatisticModel> list = billProcService.getBuyStatisticItemList(sc);
			
			for (int i = 0; i < list.size(); i++){
				sc.setItemId(list.get(i).getItemId());
				list.get(i).setBuyStatisticInfo(billProcService.getBuyStatistic(sc));
			}
			mv.addObject("buyList", list);
		}
		else
		{
			List<BuyStatisticModel> list = billProcService.getBuyStatisticForMoblie(sc);
			mv.addObject("buyList", list);
		}
		
		mv.addObject("sc", sc);
		return mv;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView saveBillProcInfoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		String itemId = request.getParameter("itemId");
		String inQty = request.getParameter("inQty");
		String inPrice = request.getParameter("inPrice");
		String salePrice = request.getParameter("salePrice");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		Map map = new HashMap();
		map.put("empId", SessionUtil.getEmpId(request, getSystemName()));
		map.put("userId", SessionUtil.getUserId(request, getSystemName()));
		map.put("itemId", itemId);
		map.put("inQty", inQty);
		map.put("inPrice", inPrice);
		map.put("salePrice", salePrice);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		
		billProcService.saveBuyStatisticInfo(map);
		
		ModelAndView mv =  new ModelAndView("jsonView");
		return ajaxReturn(mv, rm);
	}
	
	
	public ModelAndView buyStatisticPrintForm(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		ModelAndView mv = new ModelAndView("billProcMng/print/buyStatisticPrint");
		
		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		List<BuyStatisticModel> list = billProcService.getBuyStatisticItemList(sc);
		
		for (int i = 0; i < list.size(); i++){
			sc.setItemId(list.get(i).getItemId());
			list.get(i).setBuyStatisticInfo(billProcService.getBuyStatistic(sc));
		}
		
		mv.addObject("itemList", list);
		
		return mv;
	}
	
	public ModelAndView distributeStatistic(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		initCmd();
		breads.add(new BreadcrumbModel("拣货单", "", false));
		
		request.setAttribute(SC_ID_SESSION, "BillProc_billProcUncheckedList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(userId);
		
		if (StringUtils.isEmpty(sc.getCreateDateFrom()) && StringUtils.isEmpty(sc.getCreateDateTo()))
		{
			sc.setCreateDateFrom1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
			sc.setCreateDateTo1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
		}
		else
		{
			sc.setCreateDateFrom1(sc.getCreateDateFrom());
			sc.setCreateDateTo1(sc.getCreateDateTo());
		}
		
		//Get item information using itemUserId
		JSONArray colNameJSON = new JSONArray();
		List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
		
		colNameJSON.put("商品编码");
		colModelJSON.add(JqGridUtil.getColModel("itemNo", null, null, false, "center", "80", null, null));
		
		colNameJSON.put("名称");
		colModelJSON.add(JqGridUtil.getColModel("itemName", null, null, false, "left", "150", null, null));
		
		colNameJSON.put("单位");
		colModelJSON.add(JqGridUtil.getColModel("unit", null, null, false, "center", "60", null, null));
		
		List<BizDataModel> distributeList = billProcService.getDistributeList(sc);
		if (distributeList == null || distributeList.size() == 0)
		{
			sc.setDistributeSeqNo("-1");
		}else{
			if (StringUtils.isEmpty(sc.getDistributeSeqNo()))
			{	
				sc.setDistributeSeqNo(((BizDataModel)distributeList.get(0)).getSeqNo());
			}
		}
		List<HostCustModel> hcList = billProcService.getCustShortNameList(sc);
		
		for (int i = 0; i < hcList.size(); i++)
		{
			HostCustModel hostcust = (HostCustModel)hcList.get(i);
			colNameJSON.put(hostcust.getCustShortName());
			
			colModelJSON.add(JqGridUtil.getColModel("qty" + (i + 1), null, null, false, "right", "100", null, null));
		}
		
		colNameJSON.put("合计");
		colModelJSON.add(JqGridUtil.getColModel("totalQty", null, null, false, "right", "80", null, null));
		
		ModelAndView mv = new ModelAndView("billProcMng/distributeStatistics");
		
		List<DistributeStatisticModel> list = billProcService.getDistributeStatistic(sc);
		
		JSONObject gridModel = new JSONObject();
		gridModel.put("colNames", colNameJSON);
		gridModel.put("colModel", colModelJSON);
		gridModel.put("gridList", list);
		
		mv.addObject("gridModel", gridModel);
		mv.addObject("distributeList", distributeList);
		mv.addObject("sc", sc);
		
		return mv;
	}
		
	public ModelAndView distributeStatisticPrintForm(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		ModelAndView mv = new ModelAndView("billProcMng/print/distributeStatisticPrint");
		
		UserModel user = (UserModel)SessionUtil.getUser(request, getSystemName());
		String userId = user.getUserId();
		
		sc.setUserId(userId);
		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		
		String distributeName = null;
		if (StringUtils.isNotEmpty(sc.getDistributeSeqNo())){
			distributeName = bizDataService.getBizDataByBizCode(userId, Constants.COSNT_BIZDATA_PICKGROUP_CODE, sc.getDistributeSeqNo()).get(0).getC1();
		}
		
		mv.addObject("distributeName", distributeName);
		mv.addObject("userName", user.getUserName());
		
		List<HostCustModel> hcList = billProcService.getCustShortNameList(sc);
		List<DistributeStatisticModel> list = billProcService.getDistributeStatisticPrint(sc);
		
		mv.addObject("distList", list);
		mv.addObject("hcList", hcList);
		return mv;
	}
	
	public ModelAndView distributeConfirm(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception{
		initCmd();
		breads.add(new BreadcrumbModel("拣货确认", "", false));
		
		request.setAttribute(SC_ID_SESSION, "BillProc_billProcUncheckedList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(userId);
		
		if (StringUtils.isEmpty(sc.getCreateDateFrom()) && StringUtils.isEmpty(sc.getCreateDateTo()))
		{
			sc.setCreateDateFrom1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
			sc.setCreateDateTo1(new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
		}
		else
		{
			sc.setCreateDateFrom1(sc.getCreateDateFrom());
			sc.setCreateDateTo1(sc.getCreateDateTo());
		}
		
		//Get item information using itemUserId
		JSONArray colNameJSON = new JSONArray();
		List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
		
		JSONArray colConfirmNameJSON = new JSONArray();
		List<JSONObject> colConfirmModelJSON = new ArrayList<JSONObject>();
		
		colConfirmNameJSON.put("拣货组");
		colConfirmModelJSON.add(JqGridUtil.getColModel("distributeName", null, null, false, "left", "150", null, null));
		colConfirmNameJSON.put("客户名称");
		colConfirmModelJSON.add(JqGridUtil.getColModel("custShortName", null, null, false, "left", "200", null, null));
		
		colNameJSON.put("商品名称");
		colModelJSON.add(JqGridUtil.getColModel("itemName", null, null, false, "left", "150", null, null));
		
		colNameJSON.put("进货价格");
		colModelJSON.add(JqGridUtil.getColModel("lastPriceIn", null, null, false, "left", "150", null, null));
		
		JSONObject gridModel = new JSONObject();
		gridModel.put("colNames", colNameJSON);
		gridModel.put("colModel", colModelJSON);
		gridModel.put("confirmNames", colConfirmNameJSON);
		gridModel.put("confirmModels", colConfirmModelJSON);
		
		ModelAndView mv = new ModelAndView("billProcMng/distributeConfirm");
		
		mv.addObject("gridModel", gridModel);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	public ModelAndView distributeConfirmReceiptGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		ModelAndView mv = new ModelAndView("jsonView");

		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		sc.setReceiptFlag("Y");
		
		List<SupplyStatisticModel> list = billProcService.getLastReceiptList(sc);
		
		mv.addObject("rows", list);
		
		return mv;
	}
	
	public ModelAndView distributeConfirmGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		sc.setEmpId(SessionUtil.getEmpId(request, getSystemName()));
		sc.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		if (StringUtils.isEmpty(sc.getItemNo())){
			sc.setItemNo("-;-;");
		}
		
		List<DistributeConfirmModel> list = billProcService.getDistributeConfirmList(sc);
		
		mv.addObject("rows", list);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			billProcCheckedList
	* Function Description		Call the view for bill process checked list according to employee id
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView billProcCheckedList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("处理单据", "", false));
		breads.add(new BreadcrumbModel("已处理单据 ", getCmdUrl("billProcCheckedList"), true));
		
		BillProcSModel sc = new BillProcSModel();
		
		String key = "BillProc_billProcCheckedList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (BillProcSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("billProcMng/billProcCheckedList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			billProcCheckedGridAjax
	* Function Description		Retrieve the checked bill list
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView billProcCheckedGridAjax(HttpServletRequest request, HttpServletResponse response, BillProcSModel sc) throws Exception
	{
		request.setAttribute(SC_ID_SESSION, "BillProc_billProcCheckedList");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		sc.setEmpId(loginUser.getEmpId());
		sc.setUserId(loginUser.getUserId());
		sc.setChecked(Constants.CONST_Y);
		
		Integer totalCount = billProcService.getCountBillProcList(sc);
		sc.getPage().setRecords(totalCount);
		
		
		List<BillProcModel> list = billProcService.getBillProcList(sc);
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		mv.addObject("rows", list);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			billProcForm
	* Function Description		Show the detail bill information
	 * @throws Exception 
	*****************************************************************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView billProcCheckedForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("处理单据", "", false));
		breads.add(new BreadcrumbModel("已处理单据 ", getCmdUrl("billProcCheckedList"), true));
		
		String billProcId = request.getParameter("billProcId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		BillProcModel billProc = billProcService.getBillProc(billProcId, userId);
		
		if (billProc == null){
			return redirect("BillProc", "billProcCheckedList");
		}
		ModelAndView mv = null;
		
		String itemUserId = "";
		
		if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcOrderCheckedForm");
			
			if (Constants.ITEM_TYPE_BY_GUEST.equals(billProc.getBillHead().getItemtype()))
			{
				itemUserId = billProc.getBillHead().getCustUserId();
			}
			else
			{
				itemUserId = billProc.getBillHead().getHostUserId();
			}
			breads.add(new BreadcrumbModel("订货单明细", "", false));
		}
		else if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcReceiptCheckedForm");
			itemUserId = billProc.getBillHead().getCustUserId();
			breads.add(new BreadcrumbModel("入库单明细", "", false));
		}
		else if (Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcSaleCheckedForm");
			
			//Get the eos_paybill_detail_eos table in case hbmark of eos_bill_head_new is 'Y'
			if (billProc.getBillHead().getHbmark().equals("Y"))
			{
				Map map = new HashMap();
				map.put("hostUserId", billProc.getBillHead().getHostUserId());
				map.put("custUserId", billProc.getBillHead().getCustUserId());
				
				List<PrepayBillModel> prePayDtl = billProcService.getPrepayInfo(map);
				mv.addObject("prePayList", prePayDtl);
			}
			
			if (Constants.ITEM_TYPE_BY_GUEST.equals(billProc.getBillHead().getItemtype()))
			{
				itemUserId = billProc.getBillHead().getCustUserId();
			}
			else
			{
				itemUserId = billProc.getBillHead().getHostUserId();
				if (StringUtils.isNotEmpty(billProc.getBillHead().getWebno()))
				{
					itemUserId = billProc.getBillHead().getWebno();
				}
			}
			breads.add(new BreadcrumbModel("销售单明细", "", false));
		}
		else if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcPriceCheckedForm");
			breads.add(new BreadcrumbModel("调价单明细", "", false));
			itemUserId = billProc.getBillHead().getHostUserId();
		}
		else if (Constants.CONST_BILL_TYPE_PAYMENT.equals(billProc.getBillHead().getBillType()))
		{
			mv = new ModelAndView("billProcMng/billProcPaymentCheckedForm");
			breads.add(new BreadcrumbModel("收款单明细", "", false));
		}
		
		if (!Constants.CONST_BILL_TYPE_PAYMENT.equals(billProc.getBillHead().getBillType()))
		{
			mv.addObject("itemUserId", itemUserId);
			
			//Get item information using itemUserId
			List<UserItemPropertyModel> userItemPropList = userItemService.getUserItemPropertyNoPriceList(itemUserId);
			JSONArray colNameJSON = new JSONArray();
			List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
			
			String itemNoCol = "";
			String itemNameCol = "";
			String itemUnitCol = "";
			
			for (UserItemPropertyModel item : userItemPropList)
			{
				colNameJSON.put(item.getPropertyDesc());
				
				JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, "center");
				if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "250");
					col.put("align", "left");
					itemNameCol = item.getPropertyName();
				}else if (Constants.CONST_ITEM_NUM_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "100");
					col.put("align", "left");
					itemNoCol = item.getPropertyName();
				}else if (Constants.CONST_ITEM_SALE_UNIT_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "70");
					col.put("align", "center");
					itemUnitCol = item.getPropertyName();
				}else if (Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())){
					col.put("width", "100");
					col.put("align", "right");
				}else if (Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "right");
					col.put("width", "100");
				}else{
					col.put("width", "100");
				}
				colModelJSON.add(col);
			}
			
			if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType())){
				colNameJSON.put(billProc.getBillHead().getPricedesc());
				colModelJSON.add(JqGridUtil.getColModel("priceUnionForView", null, null, false, "right", "100", null, null));
			} else if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType())){
				colNameJSON.put("前次入库价");
				JSONObject fomatterOption = new JSONObject();
				fomatterOption.put("decimalPlaces", 2);
				colModelJSON.add(JqGridUtil.getColModel("priceIn", null, null, false, "right", "100", "number", fomatterOption));
				colNameJSON.put("入库价");
				colModelJSON.add(JqGridUtil.getColModel("priceUnion", null, null, false, "right", "100", null, null));
			}
			
			//Get bill line list by bill id
			Map map = new HashMap();
			map.put("userId", itemUserId);
			map.put("billId", billProc.getBillId());
			if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
			{
				map.put("itemYn", billProc.getItemYn());
			}
			
			List<BillLineModel> billLineList = null;
			List<PriceDetailModel> priceDetailList = null;
			
			BillHeadModel rBillHead = null;
			List<UserItemPropertyModel> userItemPricePropertyList = null;
			
			if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType()))
			{
				priceDetailList = priceDetailService.getPriceItemList(itemUserId, billProc.getBillId(), Constants.CONST_STATE_Y);
				rBillHead = billService.getBill(billProc.getBillHead().getRbillId());
				mv.addObject("rBillHead", rBillHead);
				
				userItemPricePropertyList = userItemService.getUserItemPricePropertyList(itemUserId);
				billProc.setPriceDetailList(priceDetailList);
			}
			else
			{
				billLineList = billService.getBillItemList(map);
				billProc.setBillLineList(billLineList);
			}
			
			JSONObject gridModel = new JSONObject();
			gridModel.put("colNames", colNameJSON);
			gridModel.put("colModel", colModelJSON);
			gridModel.put("priceCols", userItemPricePropertyList);
			gridModel.put("billProc", new JSONObject(billProc));
			
			mv.addObject("itemNoCol", itemNoCol);
			mv.addObject("itemNameCol", itemNameCol);
			mv.addObject("itemUnitCol", itemUnitCol);
			mv.addObject("gridModel", gridModel);
			
			if (super.isMobileClient()){
				List<BillProcModel> billProcHistory = billProcService.getBillHistoryList(billProc.getBillId(), userId);
				mv.addObject("billHistory", billProcHistory);
			}
		}
		else
		{
			List<PayBillDetailModel> detailList = billService.getPaybillDetailListByBillId(billProc.getBillId());
			mv.addObject("detailList", new JSONArray(detailList));
		}
			
		mv.addObject("billProc", billProc);
		
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView billProcPrintForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String userId = SessionUtil.getUserId(request, getSystemName());
		String billId = request.getParameter("billId");
		
		BillHeadModel billInfo = billService.getBill(billId);
		
		BizSettingModel printFormat = bizSettingService.getBizSettingBySysType(userId, BizSetting.BILL_PRINT_FORMAT);
		
		BizSettingModel qtyMark = bizSettingService.getBizSettingBySysType(userId, BizSetting.BILL_PRINT_QTY_MARK);
		String qtyMarkVal = BizSetting.BILL_PRINT_QTY_MARK_Y.equals(qtyMark.getSysValueName())?"Y":"N";
		
		BizSettingModel colCnt = bizSettingService.getBizSettingBySysType(userId, BizSetting.BILL_PRINT_COL_CNT);
		String colCount = colCnt.getSysValueName();
		if (StringUtils.isEmpty(colCount) || "".equals(colCount)) colCount = "1";
		
		BizSettingModel noteMark = bizSettingService.getBizSettingBySysType(userId, BizSetting.BILL_PRINT_NOTE_MARK);
		String noteMarkVal = BizSetting.BILL_PRINT_NOTE_Y.equals(noteMark.getSysValueName())?"Y":"N";
		
		BizSettingModel bzMark = bizSettingService.getBizSettingBySysType(userId, BizSetting.BILL_PRINT_BZ);
		String bzVal = BizSetting.BILL_PRINT_BZ_Y.equals(bzMark.getSysValueName())?"Y":"N";
		
		BizSettingModel volumnUnit = bizSettingService.getBizSettingBySysType(userId, BizSetting.ITEM_VOLUMN_UNIT);
		String volumnUnitVal = volumnUnit.getSysValueName();
		
		BizSettingModel weightUnit = bizSettingService.getBizSettingBySysType(userId, BizSetting.ITEM_WEIGHT_UNIT);
		String weightUnitVal = weightUnit.getSysValueName();
		
		//Get the item property with printing.
		List<UserItemPropertyModel> userItemPropList = userItemService.getUserItemPrintPropertyList(userId);
		
		Map map = new HashMap();
		map.put("userId", billInfo.getHostUserId());
		map.put("billId", billInfo.getBillId());
		map.put("itemYn", "N");
		List<BillLineModel> billLineList = billService.getBillItemList(map);
		
		map.clear();
		map.put("hostUserId", billInfo.getHostUserId());
		map.put("custUserId", billInfo.getCustUserId());
		map.put("codeId", "BD0011");
		BizDataModel bizDataCarInfo = bizDataService.getBizDataForHostCust(map);
		
		ModelAndView mv = null;
		
		if (BizSetting.BILL_PRINT_SMALL.equals(printFormat.getSysValueName()))
		{
			mv = new ModelAndView("billProcMng/print/billPrintSmall");
		}
		else
		{
			mv = new ModelAndView("billProcMng/print/billPrintLarge");
		}
		
		mv.addObject("billInfo", billInfo);
		mv.addObject("itemProp", userItemPropList);
		mv.addObject("billLine", billLineList);
		mv.addObject("qtyMark", qtyMarkVal);
		mv.addObject("bzMark", bzVal);
		mv.addObject("volumnUnit", volumnUnitVal);
		mv.addObject("weightUnit", weightUnitVal);
		mv.addObject("colCnt", colCount);
		mv.addObject("bizDataCar", bizDataCarInfo);
		mv.addObject("noteMark", noteMarkVal);
		return mv;
	}
	
	public ModelAndView changeBillProcPriceAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String itemsStr = request.getParameter("userData");
		
		ModelAndView mv =  new ModelAndView("jsonView", "result", new ResultModel());
				
		billProcService.changeBillProcPrice(itemsStr);
		
		return mv;
	}
}
