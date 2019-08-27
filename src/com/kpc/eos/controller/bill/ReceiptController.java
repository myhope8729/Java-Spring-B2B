package com.kpc.eos.controller.bill;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Filename		: ReceiptController.java
 * Description	: Management class for the user's receipt.
 * 2017
 * @author		: RKRK
 */
public class ReceiptController extends BaseEOSController 
{
	
	public final static String SC_KEY_USER_ITEMS = "Receipt_userItems";
	
	ModelAndView mv = null;
	
	private BillService billService;
	private HostCustService hostCustService;
	private PayTypeService payTypeService;
	private StoreService storeService;
	private UserService userService;
	private UserItemService userItemService;
	private AddressService addrService;
	private WorkFlowService workFlowService;
	private ItemStockService itemStockService;
	private BillProcService billProcService;
	
	private UserModel loginUser;
	private String userId;
	private String empId;
	
	public ReceiptController() 
	{
		super();
		controllerId = "Receipt";
	}
	
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "Receipt";
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
		empId = loginUser.getEmpId();
		// get the pre breadcrumbs.
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] methodList = new String[]{"receiptList", "receiptForm", "vendorsList"};
		
		if ( ArrayUtils.contains(methodList, methodName) )
		{
			breads.add(new BreadcrumbModel("填写单据", ""));
			breads.add(new BreadcrumbModel("入库单 ", getCmdUrl("receiptList")));
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
	
	public void setHostCustService(HostCustService hostCustService) 
	{
		this.hostCustService = hostCustService;
	}
	
	public void setPayTypeService(PayTypeService payTypeService) 
	{
		this.payTypeService = payTypeService;
	}
	
	public void setStoreService(StoreService storeService)
	{
		this.storeService = storeService;
	}
	
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}
	
	public void setAddrService(AddressService addrService)
	{
		this.addrService = addrService;
	}
	
	public void setUserItemService(UserItemService userItemService)
	{
		this.userItemService = userItemService;
	}
	
	public void setWorkFlowService(WorkFlowService workFlowService)
	{
		this.workFlowService = workFlowService;
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
	 * Description	: Show the receipt list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView receiptList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = "Receipt_receiptList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/receiptList", "sc", sc );
		
		pageTitle = "入库单";
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the receipt list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView receiptGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Receipt_receiptList");
		
		sc.setBillType(Constants.CONST_BILL_TYPE_RUKU);
		sc.setInputorId(empId);
		sc.setCustUserId(userId);
		
		Integer totalCount = billService.countBillList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<BillHeadModel> list = billService.getBillList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Show the vendors list to select a vendor
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView vendorsList(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/vendorsList");
		
		HostCustSModel sc = new HostCustSModel();
		
		// getting the search model from session
		String key = "Receipt_vendorsList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (HostCustSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		pageTitle = "供货方目录";
		
		breads.add(new BreadcrumbModel(pageTitle, "#"));
		
		mv.addObject("returnUrl", getCmdUrl("Receipt", "receiptList"));
		mv.addObject("gridAjaxUrl", getCmdUrl("Receipt", "vendorsListGridAjax"));
		mv.addObject("selectActionUrl", getCmdUrl("Receipt", "receiptForm"));
		
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/**
	 * Description	: Show the vendors list to select a vendor
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView vendorsListGridAjax(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Receipt_vendorsList");
		
		sc.setCustId(userId);
		
		Integer totalCount = hostCustService.countHostListForOrder(sc);
		sc.getPage().setRecords(totalCount);
		
		List<HostCustModel> list = hostCustService.getHostListForOrder(sc);
		
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
	@SuppressWarnings("rawtypes")
	public ModelAndView receiptForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/receiptForm" );
		
		String billId = request.getParameter("billId");
		
		HostCustModel hcModel = null;
		
		UserModel hostUser = null;
		
		String hostUserId = null;
		
		if (billId != null){
			BillHeadModel bill = billService.getBill(billId);
			hostUserId = bill.getHostUserId();
			hcModel = hostCustService.getCustSetting(hostUserId, userId);
			UserModel currentHostUser = userService.getUserById(hostUserId);
			hostUser = new UserModel();
			hostUser.setUserId(hostUserId);
			hostUser.setUserName(bill.getHostUserName());
			hostUser.setTelNo(bill.getHostTelNo());
			hostUser.setContactName(bill.getHostContactName());
			hostUser.setAddress(bill.getHostAddress());
			hostUser.setNote(currentHostUser.getNote());
			
			Map<String, String> map = new HashMap();
			map.put("userId", userId);
			map.put("billId", billId);
			List<BillLineModel> billItemList = billService.getBillItemList(map);
			
			mv.addObject("billItemList", JSONArray.fromObject(billItemList));
			
			mv.addObject("receipt", bill);
		}else{
			hostUserId = request.getParameter("hostUserId");
			hcModel = hostCustService.getCustSetting(hostUserId, userId);
			hostUser = userService.getUserById(hostUserId) ;
			String fullAddress = addrService.getFullAddressByLocationId(hostUser.getLocationId());
			hostUser.setAddress(fullAddress + hostUser.getAddress());
			mv.addObject("billItemList", new JSONArray());
		}
		
		if (hcModel == null)
		{
			if (billId != null){
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect("Receipt", "receiptList");
			}
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Receipt", "vendorsList");
		}
		
		List<PayTypeModel> payTypeList = payTypeService.getAvailablePayTypeList(userId);
		
		List<StoreModel> storeList = storeService.getAllStoreList(userId);
		
		UserItemPropertyModel itemType1Property = null;	//item category
		UserItemPropertyModel itemType2Property = null;	//item sub-category
		
		// getting goods items.
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getUserNNItemPropertyList(userId);
		
		List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
		JSONArray colNameJSON = new JSONArray();
		
		List<JSONObject> cartGridColModel = new ArrayList<JSONObject>();
		JSONArray cartGridColName = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			String align = "left";
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, align);
			
			colModelJSON.add(col);
			colNameJSON.add(item.getPropertyDesc());
			
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
			
			if ( Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemType1Property = item;
			}
			
			if ( Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemType2Property = item;
			}
		}
		
		colNameJSON.add("前次入库价");
		colModelJSON.add(JqGridUtil.getColModel("priceIn", null, null, false, "right", "100", null, null));
		
		cartGridColName.add("前次入库价");
		cartGridColModel.add(JqGridUtil.getColModel("priceIn", null, null, false, "right", "100", null, null));
		
		JSONObject tmp = new JSONObject();
		tmp.put("colModel", colModelJSON);
		tmp.put("colNames", colNameJSON);
		
		mv.addObject("gridData", tmp);
		
		tmp = new JSONObject();
		tmp.put("colModel", cartGridColModel);
		tmp.put("colNames", cartGridColName);
		
		mv.addObject("cartGridData", tmp);
		// ------ End of jqGrid configuration
		
		// ------ Get items category List
		List<HashMap> itemType1List = new ArrayList<HashMap>();
		List<HashMap> itemType2List = new ArrayList<HashMap>();
		if (itemType1Property != null)
		{
			itemType1List = (List<HashMap>) userItemService.getUserItemCategoryWithCountList(userId, itemType1Property.getPropertyName());
		}
		
		mv.addObject("itemType1List", itemType1List);
		
		mv.addObject("itemType1Property", itemType1Property);
		mv.addObject("itemType2Property", itemType2Property);
		
		mv.addObject("hostData", hcModel);
		mv.addObject("hostUser", hostUser);
		mv.addObject("payTypeList", payTypeList);
		mv.addObject("storeList", storeList);
		
		pageTitle = "新增入库单";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_USER_ITEMS);
		UserItemSModel sc = new UserItemSModel();
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(SC_KEY_USER_ITEMS, sc, request);
		mv.addObject( "sc", sc );
		
		return mv;
	}
	
	@SuppressWarnings("rawtypes")
	public ModelAndView userItemsGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		mv = new ModelAndView("jsonView");
		
		// get the user item list.
		sc.setUserId(userId);
		sc.setChelp(request.getParameter("searchKey"));
		
		Integer totalCount = userItemService.countUserItemListInOrder(sc);
		
		String category = sc.getCategory();
		
		if (StringUtils.isNotEmpty(category)){
			List<HashMap> itemType2List = new ArrayList<HashMap>();
			itemType2List = (List<HashMap>) userItemService.getUserItemCategoryWithCountList(userId, sc.getCatFieldName2(), sc.getCatFieldName(), category);
			mv.addObject("cat2List", itemType2List);
		}
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> list = userItemService.getUserItemListInOrder(sc);
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_USER_ITEMS);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Save the receipt data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveReceipt(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String billId = "";
		if (!request.getParameter("billId").equals("")){
			billId = request.getParameter("billId");
		}
		
		float totalPrice = Float.parseFloat(request.getParameter("total_price"));
		
		boolean isDraft = request.getParameter("draftFlg").equals("Y")?true:false;
		
		// 取得订货方资料
		String hostUserId = request.getParameter("hostUserId");
		UserModel hostUser = userService.getUserById(hostUserId) ;
		String storeId = request.getParameter("store");
		StoreModel store = storeService.getStore(storeId);
		
		String payTypeId = request.getParameter("paytype");
		PayTypeModel payType = payTypeService.getPayType(payTypeId);
		
		String billProc = "";
		String billState = isDraft?Constants.BILL_STATE_DRAFT:Constants.BILL_STATE_COMPLETED;
		String procMan = "";
		
		WorkFlowSModel sc = new WorkFlowSModel();
		
		sc.setWorkFlowType(Constants.WF_TYPE_RECEIPT);
		sc.setUserId(userId);
		sc.setState(Constants.CONST_STATE_Y);
		sc.setPagingYn(null);
		sc.getPage().setSidx("seq_no");
		sc.getPage().setSord("ASC");
		List<WorkFlowModel> workFlowList = workFlowService.getWorkFlowList(sc);
		
		List<BillProcModel> billProcList = new ArrayList<BillProcModel>();
		
		if (!isDraft){
			// 检查单据下一处理人
			BillProcModel bpFirstModel = new BillProcModel();
			bpFirstModel.setProcDatId("0");
			bpFirstModel.setEmpId(empId);
			bpFirstModel.setProcTypeCd(Constants.PROC_TYPE_SUBMIT_BILL);
			bpFirstModel.setProcSeqNo("-1");
			bpFirstModel.setBillProcName(Constants.PROC_NAME_BILL_SUBMIT);
			bpFirstModel.setUserName(loginUser.getUserName());
			bpFirstModel.setEmpName(loginUser.getEmpName());
			bpFirstModel.setEmpNameok(loginUser.getEmpName());
			bpFirstModel.setState(Constants.PROC_STATUS_COMPLETED);
			bpFirstModel.setCreateBy(empId);
			bpFirstModel.setUpdateBy(empId);
			bpFirstModel.setUserId(userId);
			bpFirstModel.setUserNo(loginUser.getUserNo());
			bpFirstModel.setUserName(loginUser.getUserName());
			billProcList.add(bpFirstModel);
			
			for(WorkFlowModel workFlow : workFlowList){
				float priceMin = Float.parseFloat(workFlow.getMinCost());
				float priceMax = Float.parseFloat(workFlow.getMaxCost());
				if (totalPrice > priceMin && totalPrice < priceMax){
					List<String> empList = workFlow.getEmpList();
					List<String> empIdList = workFlow.getEmpIdList();
					if (empList.size() > 0){
						//写入单据处理记录-单据提交 insert into bill_proc
						
						for (int i=0;i<empIdList.size();i++){
							BillProcModel bpModel = new BillProcModel();
							bpModel.setProcDatId(workFlow.getWorkFlowId());
							bpModel.setEmpId(empIdList.get(i));
							bpModel.setProcTypeCd(Constants.PROC_TYPE_RECEIPT_FLOW);
							bpModel.setProcSeqNo(workFlow.getSeqNo());
							bpModel.setBillProcName(workFlow.getWorkFlowName());
							bpModel.setQtyYn(workFlow.getQtyYn());
							bpModel.setEmpName(empList.get(i));
							bpModel.setEmpNameok(empList.get(i));
							bpModel.setPriceYn(workFlow.getPriceYn());
							bpModel.setShipPriceYn(workFlow.getShipPriceYn());
							bpModel.setUserId(userId);
							bpModel.setUserNo(loginUser.getUserNo());
							bpModel.setUserName(loginUser.getUserName());
							bpModel.setReadmark(Constants.CONST_N);
							bpModel.setState(Constants.PROC_STATUS_IN_PROCESS);
							bpModel.setCreateBy(empId);
							bpModel.setUpdateBy(empId);
							billProcList.add(bpModel);
							
							if (procMan.equals("")){
								procMan = procMan + empList.get(i);
							}else{
								procMan = procMan + ", " + empList.get(i);
							}
						}
						
						billState = Constants.BILL_STATE_IN_PROCESS;
						billProc = workFlow.getWorkFlowName();
						break;
					}
				}
			}
		}
				
		// BILL HEAD MODEL
		BillHeadModel bhModel = new BillHeadModel();
		bhModel.setBillId(billId);
		bhModel.setBillType(Constants.CONST_BILL_TYPE_RUKU);
		bhModel.setArriveDate(request.getParameter("arriveDate"));
		bhModel.setCustUserId(userId);
		bhModel.setCustUserNo(loginUser.getUserNo());
		bhModel.setCustUserName(loginUser.getUserName());
		bhModel.setCustContactName(loginUser.getContactName());
		bhModel.setCustTelNo(loginUser.getTelNo());
		bhModel.setCustQqNo(loginUser.getQqNo());
		bhModel.setCustMobileNo(loginUser.getMobileNo());
		bhModel.setHostUserId(hostUser.getUserId());
		bhModel.setHostUserNo(hostUser.getUserNo());
		bhModel.setHostUserName(hostUser.getUserName());
		bhModel.setHostContactName(hostUser.getContactName());
		bhModel.setHostTelNo(hostUser.getTelNo());
		bhModel.setHostQqNo(hostUser.getQqNo());
		bhModel.setHostMobileNo(hostUser.getMobileNo());
		bhModel.setState(billState);
		bhModel.setBillProc(billProc);
		bhModel.setProcMan(procMan);
		//bhModel.setBnoUser(userId);
		bhModel.setPaytypeId(payTypeId);
		bhModel.setPaytypeName(payType.getPayTypeName());
		bhModel.setManagerId(empId);
		bhModel.setManagerName(loginUser.getEmpName());
		bhModel.setInputorId(empId);
		bhModel.setInputorName(loginUser.getEmpName());
		bhModel.setItemAmt(Float.toString(totalPrice));
		bhModel.setItemamount2(Float.toString(totalPrice));
		bhModel.setFreight("0");
		bhModel.setFreightamount2("0");
		bhModel.setTotalAmt(Float.toString(totalPrice));
		bhModel.setTotal2(Float.toString(totalPrice));
		bhModel.setStoreId(storeId);
		bhModel.setStoreName(store.getStoreName());
		bhModel.setCreateBy(empId);
		bhModel.setUpdateBy(empId);
		
		String[] itemIdArray = request.getParameterValues("itemId");
		String[] itemPriceArray = request.getParameterValues("price2");
		String[] itemQtyArray = request.getParameterValues("qty");
		String[] lastPriceArray = request.getParameterValues("lastPrice");
		String[] noteArray = request.getParameterValues("note");
		
		List<BillLineModel> blModelList = new ArrayList<BillLineModel>();
		List<ItemStockModel> newItemStockList = new ArrayList<ItemStockModel>();
		List<ItemStockModel> updateItemStockList = new ArrayList<ItemStockModel>();
		List<UserItemModel> updateItemList = new ArrayList<UserItemModel>();
		
		for (int i=0; i < itemIdArray.length; i++){
			if (lastPriceArray[i].equals("")){
				lastPriceArray[i] = "0";
			}
			float subTotal = (float)Math.round(Float.parseFloat(itemQtyArray[i]) * Float.parseFloat(itemPriceArray[i]) * 100) / 100;
			BillLineModel blModel = new BillLineModel();
			blModel.setItemId(itemIdArray[i]);
			blModel.setPrice(itemPriceArray[i]);
			blModel.setPrice2(itemPriceArray[i]);
			blModel.setQty(itemQtyArray[i]);
			blModel.setQty2(itemQtyArray[i]);
			blModel.setTot(Float.toString(subTotal));
			blModel.setTot2(Float.toString(subTotal));
			blModel.setPriceIn(lastPriceArray[i]);
			blModel.setNote(noteArray[i]);
			blModel.setCreateBy(empId);
			blModel.setUpdateBy(empId);
			blModelList.add(blModel);
			
			if (!isDraft){
				if (billState.equals(Constants.BILL_STATE_COMPLETED)){
					ItemStockModel itemStock = itemStockService.getItemStock(storeId, itemIdArray[i]);
					if (itemStock != null){
						// update
						ItemStockModel itemTotal = itemStockService.getItemTotalCost(userId, itemIdArray[i]);
						String cost = "";
						float itemPrice = Float.parseFloat(itemPriceArray[i]);
						float itemQty = Float.parseFloat(itemQtyArray[i]);
						if (itemTotal != null){
							if ("".equals(itemTotal.getTotalCost())){
								cost = itemPriceArray[i];
							}else{
								float totalCost = Float.parseFloat(itemTotal.getTotalCost());
								float totalQty = Float.parseFloat(itemTotal.getTotalQty());
								cost = Float.toString((itemPrice * itemQty + totalCost) / (totalQty + itemQty));
							}
						}else{
							cost = itemPriceArray[i];
						}
						itemStock.setQty(Float.toString(Float.parseFloat(itemStock.getQty()) + itemQty));
						itemStock.setCost(cost);
						itemStock.setUpdateBy(empId);
						updateItemStockList.add(itemStock);
						
						UserItemModel itemModel = new UserItemModel();
						itemModel.setCost(cost);
						itemModel.setPriceIn(itemPriceArray[i]);
						itemModel.setLastVendorId(hostUserId);
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
						itemStock.setQty(itemQtyArray[i]);
						itemStock.setCreateBy(empId);
						itemStock.setUpdateBy(empId);
						newItemStockList.add(itemStock);
						
						UserItemModel itemModel = new UserItemModel();
						itemModel.setCost(itemPriceArray[i]);
						itemModel.setPriceIn(itemPriceArray[i]);
						itemModel.setLastVendorId(hostUserId);
						itemModel.setUserId(userId);
						itemModel.setItemId(itemIdArray[i]);
						itemModel.setUpdateBy(empId);
						updateItemList.add(itemModel);
					}
				}
			}
		}
		
		billService.processReceipt(bhModel, billProcList, blModelList, newItemStockList, updateItemStockList, updateItemList);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("Receipt", "receiptList");
	}
	
	/**
	 * Description	: View Receipt Bill Data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView viewReceipt(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		mv = new ModelAndView( "bill/receiptView" );
		
		String billId = request.getParameter("billId");
		
		BillHeadModel bhModel = billService.getBill(billId);
		
		if (!empId.equals(bhModel.getInputorId()) && !empId.equals(bhModel.getManagerId()) && !empId.equals(bhModel.getCreateBy()))
		{
			return redirect("Receipt", "receiptList");
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
		
		cartGridColName.add("前次入库价");
		cartGridColModel.add(JqGridUtil.getColModel("priceIn", null, null, false, "right", "100", null, null));
		
		JSONObject tmp = new JSONObject();
		tmp.put("colModel", cartGridColModel);
		tmp.put("colNames", cartGridColName);
		
		mv.addObject("cartGridData", tmp);
		
		mv.addObject("bill", bhModel);
		
		pageTitle = "入库单明细";
		initCmd();
		breads.add(new BreadcrumbModel(pageTitle, "#"));
		
		if (bhModel.getState().equals(Constants.BILL_STATE_DRAFT)){
			mv.addObject("isDraft", true);
		}else{
			mv.addObject("isDraft", false);
		}
		
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
	
	public ModelAndView deleteReceiptAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "receiptList");
		}
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String billId = request.getParameter("billId");
		
		if (StringUtils.isEmpty(billId))
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// validation here.
		BillHeadModel bill = billService.getBill( billId );
		if ( bill == null )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// current emp's bill?
		if ( ! empId.equals(bill.getInputorId()) ) 
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		billService.processDeleteReceipt(billId);
		
		rm.setResultMsg( MessageUtil.getMessage("bill.delete.success") );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: View Receipt Bill Data For Mobile.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView viewReceiptForMobile(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		mv = new ModelAndView( "bill/receiptView" );
		
		String billId = request.getParameter("billId");
		
		BillHeadModel bhModel = billService.getBill(billId);
		
		if (!empId.equals(bhModel.getInputorId()) && !empId.equals(bhModel.getManagerId()) && !empId.equals(bhModel.getCreateBy()))
		{
			return redirect("Receipt", "receiptList");
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
		
		cartGridColName.add("前次入库价");
		cartGridColModel.add(JqGridUtil.getColModel("priceIn", null, null));
		
		JSONObject tmp = new JSONObject();
		tmp.put("colModel", cartGridColModel);
		tmp.put("colNames", cartGridColName);
		
		mv.addObject("cartGridData", tmp);
		
		mv.addObject("bill", bhModel);
		
		pageTitle = "入库单明细";
		initCmd();
		breads.add(new BreadcrumbModel(pageTitle, "#"));
		
		if (bhModel.getState().equals(Constants.BILL_STATE_DRAFT)){
			mv.addObject("isDraft", true);
		}else{
			mv.addObject("isDraft", false);
		}
		
		return mv;
	}
}
