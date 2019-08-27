package com.kpc.eos.controller.bill;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.JqGridUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.bill.ItemStockModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.DeliveryAddrModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeSModel;
import com.kpc.eos.model.bizSetting.StoreModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.model.bizSetting.WorkFlowSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.ListDataSaveModel;
import com.kpc.eos.service.bill.BillService;
import com.kpc.eos.service.bill.ItemStockService;
import com.kpc.eos.service.billProcMng.BillProcService;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.bizSetting.DeliveryAddrService;
import com.kpc.eos.service.bizSetting.HostCustService;
import com.kpc.eos.service.bizSetting.PayTypeService;
import com.kpc.eos.service.bizSetting.ProcDataService;
import com.kpc.eos.service.bizSetting.StoreService;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.bizSetting.WorkFlowService;
import com.kpc.eos.service.common.AddressService;
import com.kpc.eos.service.dataMng.UserService;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * Filename		: ReturnController.java
 * Description	: Management class for the user's return items.
 * 2017
 * @author		: RKRK
 */
public class ReturnController extends BaseEOSController 
{
	
	public final static String SC_KEY_USER_ITEMS = "Return_userItems";
	
	ModelAndView mv = null;
	
	private BillService billService;
	private StoreService storeService;
	private UserItemService userItemService;
	private UserService userService;
	private ItemStockService itemStockService;
	private BillProcService billProcService;
		
	private UserModel loginUser;
	private String userId;
	private String empId;
	
	public ReturnController() 
	{
		super();
		controllerId = "Return";
	}
	
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "Return";
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
		empId = loginUser.getEmpId();
		
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] methodList = new String[]{"returnList", "returnForm", "billList"};
		
		if ( ArrayUtils.contains(methodList, methodName) )
		{
			breads.add(new BreadcrumbModel("填写单据", ""));
			breads.add(new BreadcrumbModel("退货单 ", getCmdUrl("returnList")));
		}
	}
	
	/**
	 * Description	: Set the order service.
	 * @author 		: RKRK
	 * @param orderService
	 * 2017
	 */
	public void setBillService(BillService billService) 
	{
		this.billService = billService;
	}
	
	public void setStoreService(StoreService storeService) 
	{
		this.storeService = storeService;
	}
	
	public void setUserItemService(UserItemService userItemService) 
	{
		this.userItemService = userItemService;
	}
	
	public void setUserService(UserService userService) 
	{
		this.userService = userService;
	}
	
	public void setItemStockService(ItemStockService itemStockService)
	{
		this.itemStockService = itemStockService;
	}
	
	public void setBillProcService(BillProcService billProcService)
	{
		this.billProcService = billProcService;
	}

	/**
	 * Description	: Show the return list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView returnList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = "Return_returnList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/returnList", "sc", sc );
		
		pageTitle = "退货单";
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the return list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView returnGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Return_returnList");
		
		sc.setBillType(Constants.CONST_BILL_TYPE_TUI);
		sc.setInputorId(empId);
		
		Integer totalCount = billService.countBillList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<BillHeadModel> list = billService.getBillList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Show the bill list for return.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView billList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = "Return_billList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/billList", "sc", sc );
		
		pageTitle = "选择原单据";
		
		//this.initCmd();
		breads.add(new BreadcrumbModel("选择原单据", "#"));
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		mv.addObject("selectActionUrl", getCmdUrl("Return", "returnForm"));
		
		return mv;
	}
	
	/**
	 * Description	: show the bill list for return by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView billGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Return_billList");
		
		sc.setUserId(userId);
		
		Integer totalCount = billService.countBillForReturnList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<BillHeadModel> list = billService.getBillForReturnList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Show the receipt form. This is the first step to create an order.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView returnForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/returnForm" );
		
		String billId = request.getParameter("billId");
		
		if (billId == null || billId.equals("")){
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Return", "returnList");
		}
		
		BillHeadModel bill = billService.getBill(billId);
		
		Map<String, String> map = new HashMap();
		map.put("userId", userId);
		map.put("billId", billId);
		List<BillLineModel> billItemList = billService.getBillItemList(map);
		
		mv.addObject("billItemList", JSONArray.fromObject(billItemList));
		
		mv.addObject("originBill", bill);
		
		//List<PayTypeModel> payTypeList = payTypeService.getAvailablePayTypeList(userId);
		
		List<StoreModel> storeList = storeService.getAllStoreList(userId);
		
		// getting goods items.
		// ------ jqGrid configuration
		String itemOwnerId = userId;
		if (bill.getBillType().equals("DT0002")){
			itemOwnerId = bill.getCustUserId();
		}else{
			itemOwnerId = bill.getHostUserId();
		}
		List<UserItemPropertyModel> ipList = userItemService.getUserNNItemPropertyList(itemOwnerId);
		
		List<JSONObject> cartGridColModel = new ArrayList<JSONObject>();
		JSONArray cartGridColName = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			String align = "left";
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, align);
			
			if (! Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) && !Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd()))
			{
				if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "left");
					col.put("width", "250");
				}else if (Constants.CONST_ITEM_NUM_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "left");
					col.put("width", "120");
				}else if (Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "right");
					col.put("width", "100");
				}
				cartGridColModel.add(col);
				cartGridColName.add(item.getPropertyDesc());
			}
		}
		
		JSONObject tmp = new JSONObject();
		
		tmp.put("colModel", cartGridColModel);
		tmp.put("colNames", cartGridColName);
		
		mv.addObject("cartGridData", tmp);
		// ------ End of jqGrid configuration
		
		pageTitle = "新增退货单";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		mv.addObject("storeList", storeList);
		
		
		return mv;
	}
	
	/**
	 * Description	: Save the return data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveReturn(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String originBillId = request.getParameter("originBillId");
		
		BillHeadModel originBill = billService.getBill(originBillId);
		double totalPrice = 0;
		if (!StringUtils.isEmpty(request.getParameter("total_price"))){
			totalPrice = Double.parseDouble(request.getParameter("total_price")); 
		}
		System.out.println(totalPrice);
		// 取得订货方资料
		String hostUserId = request.getParameter("hostUserId");
		UserModel hostUser = userService.getUserById(hostUserId);
		String custUserId = request.getParameter("custUserId");
		UserModel custUser = userService.getUserById(custUserId);
		
		String storeId = request.getParameter("storeId");
		StoreModel store = storeService.getStore(storeId);
		
		BillProcModel bpFirstModel = new BillProcModel();
		bpFirstModel.setEmpId(empId);
		bpFirstModel.setState(Constants.PROC_STATUS_COMPLETED);
		bpFirstModel.setProcTypeCd(Constants.PROC_TYPE_SUBMIT_BILL);
		bpFirstModel.setProcSeqNo("-1");
		bpFirstModel.setProcDatId("0");
		bpFirstModel.setBillProcName(Constants.PROC_NAME_RETURNED_SUBMIT);
		bpFirstModel.setUserName(loginUser.getUserName());
		bpFirstModel.setEmpName(loginUser.getEmpName());
		bpFirstModel.setEmpNameok(loginUser.getEmpName());
		bpFirstModel.setCreateBy(empId);
		bpFirstModel.setUpdateBy(empId);
		bpFirstModel.setUserId(userId);
		bpFirstModel.setUserNo(loginUser.getUserNo());
		
		// BILL HEAD MODEL
		BillHeadModel bhModel = new BillHeadModel();
		bhModel.setBillType(Constants.CONST_BILL_TYPE_TUI);
		bhModel.setRbillId(originBillId);
		bhModel.setRbillType(request.getParameter("originBillType"));
		bhModel.setBillProc(Constants.PROC_TYPE_SUBMIT_BILL);
		bhModel.setProcMan(loginUser.getEmpName());
		bhModel.setItemtype(Constants.ITEM_TYPE_WANG_LU);
		bhModel.setCustUserId(originBill.getCustUserId());
		bhModel.setCustUserNo(originBill.getCustUserNo());
		bhModel.setCustUserName(originBill.getCustUserName());
		bhModel.setCustContactName(originBill.getCustContactName());
		bhModel.setCustTelNo(originBill.getCustTelNo());
		bhModel.setCustMobileNo(originBill.getCustMobileNo());
		bhModel.setHostUserId(originBill.getHostUserId());
		bhModel.setHostUserNo(originBill.getHostUserNo());
		bhModel.setHostUserName(originBill.getHostUserName());
		bhModel.setHostContactName(originBill.getHostContactName());
		bhModel.setHostTelNo(originBill.getHostTelNo());
		bhModel.setHostQqNo(originBill.getHostQqNo());
		bhModel.setHostMobileNo(originBill.getHostMobileNo());
		
		bhModel.setItype( originBill.getItype() );
		bhModel.setPricecol( originBill.getPricecol() );
		bhModel.setPricedesc( originBill.getPricedesc() );
		bhModel.setNostockmark( originBill.getNostockmark() );
		
		// paytypes
		bhModel.setHbmark( originBill.getHbmark() );
		bhModel.setPaytypeId( originBill.getPaytypeId() );
		bhModel.setPaytypeName( originBill.getPaytypeName() );
		bhModel.setPaymentType( originBill.getPaymentType() );
		bhModel.setCusttypeId( originBill.getCusttypeId() );
		bhModel.setCusttypeName( originBill.getCusttypeName() );
		
		// address
		bhModel.setAddrId( originBill.getAddrId() );
		bhModel.setAddrLocationId( originBill.getAddrLocationId() );
		bhModel.setAddrLocationName( originBill.getAddrLocationName() );
		bhModel.setAddrContactName( originBill.getAddrContactName() );
		bhModel.setAddrAddress( originBill.getAddrAddress() );
		bhModel.setAddrTelNo( originBill.getAddrTelNo() );
		bhModel.setAddrMobile( originBill.getAddrMobile() );
		
		bhModel.setState(Constants.BILL_STATE_COMPLETED);
		bhModel.setArriveDate(request.getParameter("returnDate"));
		bhModel.setNote(request.getParameter("returnReason"));
		bhModel.setManagerId(empId);
		bhModel.setManagerName(loginUser.getEmpName());
		bhModel.setInputorId(empId);
		bhModel.setInputorName(loginUser.getEmpName());
		bhModel.setItemAmt(Double.toString(totalPrice));
		bhModel.setFreight("0");
		bhModel.setTotalAmt(Double.toString(totalPrice));
		bhModel.setItemamount2(Double.toString(totalPrice));
		bhModel.setFreightamount2("0");
		bhModel.setTotal2(Double.toString(totalPrice));
		bhModel.setStoreId(storeId);
		bhModel.setStoreName(store.getStoreName());
		bhModel.setCreateBy(empId);
		bhModel.setUpdateBy(empId);
		
		String[] itemIdArray = request.getParameterValues("itemId");
		String[] itemPriceArray = request.getParameterValues("price");
		String[] itemQtyArray = request.getParameterValues("tuiQty");
		String[] noteArray = request.getParameterValues("tuiNote");
		
		List<BillLineModel> blModelList = new ArrayList<BillLineModel>();
		List<ItemStockModel> newItemStockList = new ArrayList<ItemStockModel>();
		List<ItemStockModel> updateItemStockList = new ArrayList<ItemStockModel>();
		List<UserItemModel> updateItemList = new ArrayList<UserItemModel>();
		
		for (int i=0; i < itemIdArray.length; i++){
			if (itemQtyArray[i].equals("")){
				itemQtyArray[i] = "0";
			}
			if (Double.parseDouble(itemQtyArray[i]) > 0){
				double subTotal = (double)Math.round(Double.parseDouble(itemQtyArray[i]) * Double.parseDouble(itemPriceArray[i]) * 100) / 100;
				BillLineModel blModel = new BillLineModel();
				blModel.setRbillId(originBillId);
				blModel.setItemId(itemIdArray[i]);
				blModel.setPrice(itemPriceArray[i]);
				blModel.setPrice2(itemPriceArray[i]);
				blModel.setQty(itemQtyArray[i]);
				blModel.setQty2(itemQtyArray[i]);
				blModel.setTot(Double.toString(subTotal));
				blModel.setTot2(Double.toString(subTotal));
				blModel.setCost("0");
				blModel.setNote(noteArray[i]);
				blModel.setCreateBy(empId);
				blModel.setUpdateBy(empId);
				blModelList.add(blModel);
				
				ItemStockModel itemTotal = itemStockService.getItemTotalCost(userId, itemIdArray[i]);
				String cost = "";
				double itemPrice = Double.parseDouble(itemPriceArray[i]);
				double itemQty = Double.parseDouble(itemQtyArray[i]);
				if (itemTotal != null){
					if ("".equals(itemTotal.getTotalCost())){
						cost = itemPriceArray[i];
					}else{
						double totalCost = Double.parseDouble(itemTotal.getTotalCost());
						double totalQty = Double.parseDouble(itemTotal.getTotalQty());
						cost = Double.toString((itemPrice * itemQty + totalCost) / (totalQty + itemQty));
					}
				}
				
				ItemStockModel itemStock = itemStockService.getItemStock(storeId, itemIdArray[i]);
				if (itemStock != null){
					if (request.getParameter("originBillType").equals(Constants.CONST_BILL_TYPE_RUKU)){
						itemStock.setQty(Double.toString(Double.parseDouble(itemStock.getQty()) - itemQty));
					}else{
						itemStock.setQty(Double.toString(Double.parseDouble(itemStock.getQty()) + itemQty));
					}
					itemStock.setCost(cost);
					itemStock.setUpdateBy(empId);
					updateItemStockList.add(itemStock);
					
					UserItemModel itemModel = new UserItemModel();
					itemModel.setCost(cost);
					itemModel.setUserId(userId);
					itemModel.setItemId(itemIdArray[i]);
					itemModel.setUpdateBy(empId);
					updateItemList.add(itemModel);
				}else{
					itemStock = new ItemStockModel();
					itemStock.setUserId(userId);
					itemStock.setStoreId(storeId);
					itemStock.setItemId(itemIdArray[i]);
					itemStock.setCost(itemPriceArray[i]);
					if (request.getParameter("originBillType").equals(Constants.CONST_BILL_TYPE_RUKU)){
						itemStock.setQty("-" + itemQtyArray[i]);
					}else{
						itemStock.setQty(itemQtyArray[i]);
					}
					itemStock.setCreateBy(empId);
					itemStock.setUpdateBy(empId);
					newItemStockList.add(itemStock);
				}
			}
		}
		
		billService.processReturn(bhModel, bpFirstModel, blModelList, newItemStockList, updateItemStockList, updateItemList);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("Return", "returnList");
	}
	
	/**
	 * Description	: View Return Bill Data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView viewReturn(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		mv = new ModelAndView( "bill/returnView" );
		
		String billId = request.getParameter("billId");
		
		BillHeadModel bhModel = billService.getBill(billId);
		
		if (!empId.equals(bhModel.getInputorId()) && !empId.equals(bhModel.getManagerId()) && !empId.equals(bhModel.getCreateBy()))
		{
			return redirect("Return", "returnList");
		}
		
		List<UserItemPropertyModel> ipList = userItemService.getUserNNItemPropertyList(userId);
		
		List<JSONObject> cartGridColModel = new ArrayList<JSONObject>();
		JSONArray cartGridColName = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			String align = "left";
			
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, align);
			
			if (! Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) && !Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd()))
			{
				if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "left");
					col.put("width", "300");
				}else if (Constants.CONST_ITEM_NUM_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "left");
					col.put("width", "120");
				}else if (Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd())){
					col.put("align", "right");
					col.put("width", "100");
				}
				cartGridColModel.add(col);
				cartGridColName.add(item.getPropertyDesc());
			}
		}
		
		JSONObject tmp = new JSONObject();
		tmp.put("colModel", cartGridColModel);
		tmp.put("colNames", cartGridColName);
		
		mv.addObject("cartGridData", tmp);
		
		mv.addObject("bill", bhModel);
		
		pageTitle = "退货单明细";
		
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		return mv;
	}
	
	public ModelAndView billItemsGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		String billId = request.getParameter("billId");
		
		// get the user item list.
		
		Map<String, String> map = new HashMap();
		map.put("userId", userId);
		map.put("billId", billId);
		List<BillLineModel> billItemList = billService.getBillItemList(map);
		
		BillLineModel billItemsTotal = billService.getBillItemsTotal(map);
		HashMap<String, String> footerData = new HashMap<String, String>();
		footerData.put("totalName", "合计");
		footerData.put("totalAmtData", billItemsTotal.getItemsTotalAmt());
		footerData.put("totalQtyData", billItemsTotal.getItemsTotalQty());
		
		mv.addObject("footerData", footerData);
		
		mv.addObject("rows", billItemList);
		return mv;
	}
	
	public ModelAndView billProcGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		String billId = request.getParameter("billId");
		
		// get the user item list.
		List<BillProcModel> billProcList = billProcService.getBillHistoryList(billId, userId);
		
		mv.addObject("rows", billProcList);
		return mv;
	}
	
	public ModelAndView printReturn(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception
	{
		mv = new ModelAndView( "bill/print/returnPrint" );
		
		String billId = request.getParameter("billId");
		
		BillHeadModel bhModel = billService.getBill(billId);
		
		if (!empId.equals(bhModel.getInputorId()) && !empId.equals(bhModel.getManagerId()) && !empId.equals(bhModel.getCreateBy()))
		{
			return redirect("Return", "returnList");
		}
		
		List<UserItemPropertyModel> ipList = userItemService.getUserItemPrintPropertyList(userId);
		
		String today = new SimpleDateFormat("M/dd/yyyy").format(new Date());
		
		// get the user item list.
		
		Map<String, String> map = new HashMap();
		map.put("userId", userId);
		map.put("billId", billId);
		List<BillLineModel> billItemList = billService.getBillItemList(map);
		
		BillLineModel billItemsTotal = billService.getBillItemsTotal(map);
		mv.addObject("totalAmtData", billItemsTotal.getItemsTotalAmt());
		mv.addObject("totalQtyData", billItemsTotal.getItemsTotalQty());
		
		mv.addObject("itemProp", ipList);
		mv.addObject("billLine", billItemList);
		
		mv.addObject("bill", bhModel);
		mv.addObject("pageTitle", loginUser.getUserName() + "退货单");
		mv.addObject("printDate", today);
		
		return mv;
	}
	
	/**
	 * Description	: View Return Bill Data For Mobile.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView viewReturnForMobile(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		mv = new ModelAndView( "bill/returnView" );
		
		String billId = request.getParameter("billId");
		
		BillHeadModel bhModel = billService.getBill(billId);
		
		if (!empId.equals(bhModel.getInputorId()) && !empId.equals(bhModel.getManagerId()) && !empId.equals(bhModel.getCreateBy()))
		{
			return redirect("Return", "returnList");
		}
		
		List<UserItemPropertyModel> ipList = userItemService.getUserNNItemPropertyList(userId);
		
		List<JSONObject> cartGridColModel = new ArrayList<JSONObject>();
		JSONArray cartGridColName = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null);
			
			if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd())){
				cartGridColModel.add(col);
				cartGridColName.add(item.getPropertyDesc());
			}
		}
		
		JSONObject tmp = new JSONObject();
		tmp.put("colModel", cartGridColModel);
		tmp.put("colNames", cartGridColName);
		
		mv.addObject("cartGridData", tmp);
		
		mv.addObject("bill", bhModel);
		
		pageTitle = "退货单明细";
		
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		return mv;
	}
	
}
