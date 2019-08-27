package com.kpc.eos.controller.bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestDataBinder;
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
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.CartUserItemModel;
import com.kpc.eos.model.bill.SubPayTypeModel;
import com.kpc.eos.model.bill.SubPayTypeSModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.billProcMng.PrepayBillModel;
import com.kpc.eos.model.billProcMng.PrepayBillSModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.DeliveryAddrModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeSModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.bill.BillService;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.bizSetting.DeliveryAddrService;
import com.kpc.eos.service.bizSetting.HostCustService;
import com.kpc.eos.service.bizSetting.PayTypeService;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.common.AddressService;
import com.kpc.eos.service.dataMng.DepartmentService;
import com.kpc.eos.service.dataMng.UserService;

/**
 * Filename		: OrderController.java
 * Description	: Management class for the user's orders.
 * 2017
 * @author		: RKRK
 */
public class OrderController extends BaseEOSController 
{
	public final static String SC_KEY_USER_ITEMS 	= "Order_userItems";
	public final static String COPY_MARK 			= "1";
	
	// services.
	private BillService 		billService;
	private HostCustService 	hostCustService;
	private UserService 		userService;
	private UserItemService 	userItemService;
	private AddressService 		addrService;
	private BizSettingService 	bizSettingService;
	private BizDataService  	bizDataService;
	private PayTypeService  	payTypeService;
	private DeliveryAddrService deliveryAddrService;
	private DepartmentService	departmentService;
	
	// member vars
	private ModelAndView 		mv = null;
	
	private UserModel 			loginUser;
	private String 				userId;
	private String 				empId;
	
	public OrderController() 
	{
		super();
		controllerId = "Order";
	}
	
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "Order";
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
		empId = loginUser.getEmpId();
		
		// get the pre breadcrumbs.
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] ordersList = new String[]{"orderList", "orderForm", "vendorsList", "paymentBillList", "vendorsList"};
		String[] privUrls = new String[]{"orderList", "orderForm"};
		
		
		if ( ArrayUtils.contains(ordersList, methodName) )
		{
			breads.add(new BreadcrumbModel("填写单据", ""));
			breads.add(new BreadcrumbModel("订货单 ", getCmdUrl("orderList")));
		}
		else if ( ArrayUtils.contains(privUrls, methodName) )
		{
			
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
	
	/**
	 * Description	: Set the host cust service.
	 * @author 		: RKRK
	 * @param hostCustService
	 * 2017
	 */
	public void setHostCustService(HostCustService hostCustService) 
	{
		this.hostCustService = hostCustService;
	}
	
	public void setUserService(UserService userService) 
	{
		this.userService = userService;
	}
	
	public void setUserItemService(UserItemService userItemService) 
	{
		this.userItemService = userItemService;
	}
	
	public void setAddrService(AddressService addrService) 
	{
		this.addrService = addrService;
	}
	
	public void setBizSettingService(BizSettingService bizSettingService) 
	{
		this.bizSettingService = bizSettingService;
	}
	
	public void setBizDataService(BizDataService bizDataService) 
	{
		this.bizDataService = bizDataService;
	}
	
	public void setPayTypeService(PayTypeService payTypeService) 
	{
		this.payTypeService = payTypeService;
	}
	
	public void setDeliveryAddrService(DeliveryAddrService deliveryAddrService) 
	{
		this.deliveryAddrService = deliveryAddrService;
	}

	public void setDepartmentService(DepartmentService departmentService) 
	{
		this.departmentService = departmentService;
	}
	
	
	/**
	 * Description	: Show the orders list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView orderList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = "Order_orderList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		sc.setUserId(loginUser.getUserId());
		
		mv = new ModelAndView( "bill/orderList", "sc", sc );
		
		pageTitle = "订货单";
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the orders list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView orderGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Order_orderList");
		
		sc.setBillType(Constants.CONST_BILL_TYPE_DING);
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
		String key = "Order_vendorsList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (HostCustSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		pageTitle = "供货方目录";
		
		breads.add(new BreadcrumbModel(pageTitle, "#"));
		
		mv.addObject("returnUrl", getCmdUrl("Order", "orderList"));
		mv.addObject("gridAjaxUrl", getCmdUrl("Order", "vendorsListGridAjax"));
		mv.addObject("selectActionUrl", getCmdUrl("Order", "orderForm"));
		
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
		
		request.setAttribute(SC_ID_SESSION, "Order_vendorsList");
		
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
	 * Description	: Show the order form. This is the first step to create an order.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView orderForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/orderForm" );
		BillHeadModel order = new BillHeadModel();
		
		String hostUserId = request.getParameter("hostUserId");
		String billId = request.getParameter("billId");
		boolean isCopy = COPY_MARK.equals(request.getParameter("copymark"));
		
		// ------ Validation ----------------------- //
		// edit action?
		if ( StringUtils.isNotEmpty(billId) )
		{
			// validation here.
			order = billService.getBill( billId );
			if ( order == null )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "orderList");
			}
			
			// current emp's bill?
			if ( ! empId.equals(order.getInputorId()) ) 
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "orderList");
			}
			
			// if copy function, we don't need this check.
			if ( ! isCopy && ! order.isEditableByState() )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "orderList");
			}
			
			hostUserId = order.getHostUserId();
		}
		
		HostCustModel hcModel = hostCustService.getHostCustSetting(hostUserId, userId);
		
		if (hcModel == null)
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Order", "orderList");
		}
		
		if ( ! hcModel.getConnection() )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("order.host.cust.map.not.saved"), request);
			return redirect("Order", "vendorsList");
		}
		
		// ------ End of validation ----------------------- //
		
		UserModel hostUser = userService.getUserById(hostUserId) ;
		
		String fullAddress = addrService.getFullAddressByLocationId(hostUser.getLocationId());
		hostUser.setAddress(fullAddress + hostUser.getAddress());
		
		// get the biz setting for a host user.
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
		
		Boolean isJsMark = BizSetting.JS_Y.equals(hostUserBSMap.get(BizSetting.JS)); 
		mv.addObject("isJsMark", isJsMark);
		
		// getting itype list. Don't confuse it with the itemtype in the table. itemtype is a bill item type actaully.
		Boolean isItemTypeDisp = BizSetting.ITEM_TYPE_Y.equals(hostUserBSMap.get(BizSetting.ITEM_TYPE)); 
		if (isItemTypeDisp)
		{
			List<BizDataModel> itemTypeList = bizDataService.getBizDataByBizCode(hostUserId, Constants.CONST_BIZDATA_ITEM_TYPE_CODE, null);
			mv.addObject("itemTypeList", itemTypeList);
		}
		mv.addObject("isItemTypeDisp", isItemTypeDisp);
		
		// get the setting for an arrival date.
		Boolean isArrivalDateRequired = BizSetting.ARRIVAL_DATE_REQUIRED_Y.equals(hostUserBSMap.get(BizSetting.ARRIVAL_DATE_REQUIRED)); 
		mv.addObject("isArrivalDateRequired", isArrivalDateRequired);
		Double defaultArrivalDateOffset = 0.0;
		if (StringUtils.isNotEmpty((String)hostUserBSMap.get(BizSetting.ARRIVAL_DATE_OFFSET))) {
			try {
				defaultArrivalDateOffset = MathUtil.getDouble(hostUserBSMap.get(BizSetting.ARRIVAL_DATE_OFFSET), true);
			} catch (Exception e) {}
		}
		long time = new Date().getTime();
		time += (long)(defaultArrivalDateOffset.doubleValue() * 86400000);
		String defaultArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
		
		if ( StringUtils.isEmpty(order.getArriveDate()) )
		{
			order.setArriveDate(defaultArrivalDate);
		}
		
		// getting paytype list
		PayTypeSModel payTypeSC = new PayTypeSModel(hostUserId, userId);
		
		payTypeSC.setPrivYn(Constants.CONST_N);
		List<PayTypeModel> payTypeList = payTypeService.getUserPayTypeList(payTypeSC);
		mv.addObject("payTypeList", payTypeList);
		
		// sub payment_type list here.
		List<SubPayTypeModel> subPayTypeList = new ArrayList<SubPayTypeModel>();
		mv.addObject("subPayTypeList", subPayTypeList);
		
		
		UserItemPropertyModel itemType1Property = null;	//item category
		UserItemPropertyModel itemType2Property = null;	//item sub-category
		UserItemPropertyModel itemPackageProperty = null;	//item sub-category
		
		// getting user items.
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getUserNNItemPropertyList(hostUserId);
		
		List<JSONObject> colModelJSON = new ArrayList<JSONObject>();
		JSONArray colNameJSON = new JSONArray();
		
		List<JSONObject> cartGridColModel = new ArrayList<JSONObject>();
		JSONArray cartGridColName = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, null);
			
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
			
			if ( Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemPackageProperty = item;
			}
		}
		
		// add a price col.
		colNameJSON.add(hcModel.getPriceDesc());
		cartGridColName.add(hcModel.getPriceDesc());
		
		JSONObject col = JqGridUtil.getColModel(hcModel.getPriceColName(), null, null, false, "right", "100", null, null);
		colModelJSON.add(col);
		cartGridColModel.add(col);
		
		JSONObject tmp = new JSONObject();
		tmp.put("colModel", colModelJSON);
		tmp.put("colNames", colNameJSON);
		
		mv.addObject("gridData", tmp);
		
		tmp = new JSONObject();
		tmp.put("colModel", cartGridColModel);
		tmp.put("colNames", cartGridColName);
		
		// get user items in a bill.
		List<UserItemModel> billedItems = new ArrayList<UserItemModel>();
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			billedItems = userItemService.getBilledUserItemList( order );
		}
		tmp.put( "data", billedItems );
		mv.addObject("cartGridData", tmp);
		// ------ End of jqGrid configuration
		
		// ------ Address jqGrid configuration
		List<DeliveryAddrModel> addrList = deliveryAddrService.getUserDeliveryAddrList(userId);
		mv.addObject("addrList", JSONArray.fromObject( addrList ));
		// ------ End of Address jqGrid configuration
		
		mv.addObject("itemType1Property", itemType1Property);
		mv.addObject("itemType2Property", itemType2Property);
		mv.addObject("itemPackageProperty", itemPackageProperty);
		
		pageTitle = "新增订货单";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		// getting the search model from session
		request.setAttribute(SC_ID_SESSION, SC_KEY_USER_ITEMS);
		UserItemSModel sc = new UserItemSModel();
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(SC_KEY_USER_ITEMS, sc, request);
		
		mv.addObject("isItemTypeDisp", isItemTypeDisp);
		
		mv.addObject("hcModel", hcModel);
		mv.addObject("hc", new JSONObject(hcModel));
		mv.addObject("hostUser", hostUser);
		mv.addObject("hostUserBSMap", hostUserBSMap);
		
		// ======== Copy function ==========
		if ( isCopy )
		{
			order.setBillId(null);
		}
		mv.addObject("order", order);
		mv.addObject( "sc", sc );
		
		return mv;
	}
	
	/**
	 * Description	: Get the pre payment list for a paymnet.
	 *  userId 
		custtypeId 
		paytypeId
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView loadSubpaymentList(HttpServletRequest request, HttpServletResponse response, SubPayTypeSModel sc) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_SUCCESS_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String billType = request.getParameter("billType");
		
		List<SubPayTypeModel> subPayTypeList = null;
		PrepayBillSModel ppSc = null;
		
		
		if (Constants.CONST_BILL_TYPE_DING.equals(billType))
		{
			// sub payment_type list here.
			subPayTypeList = payTypeService.getActiveUserSubPayTypeList(sc.getUserId(), sc.getCusttypeId(), sc.getPaytypeId() );
			
			ppSc = new PrepayBillSModel(sc.getUserId(), loginUser.getUserId(), sc.getPaytypeId(), sc.getName());
		}
		else
		{
			subPayTypeList = payTypeService.getActiveUserSubPayTypeList(loginUser.getUserId(), sc.getCusttypeId(), sc.getPaytypeId() );
			ppSc = new PrepayBillSModel(loginUser.getUserId(), sc.getUserId(), sc.getPaytypeId(), sc.getName());
		}
		
		// ---- Get prepay total amount list
		List<PrepayBillModel> ppList = billService.getPrePayTotalAmtList(ppSc);
		mv.addObject("ppList", ppList);
		mv.addObject("subPayTypeList", subPayTypeList);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Get user Items on orderForm page.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView userItemsGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		mv = new ModelAndView("jsonView");
		
		String hostUserId = request.getParameter("hostUserId");
		HostCustModel hcModel = hostCustService.getHostCustSetting(hostUserId, userId);
		
		if (hcModel == null)
		{
			rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
			return ajaxReturn(mv, rm);
		}
		
		// Getting the psqls.
		String psql = "";
		if ( StringUtils.isNotEmpty(hcModel.getPsql()) )
		{
			psql += " " + hcModel.getPsql();
		}
		
		if ( StringUtils.isNotEmpty(hcModel.getCusttypePsql()) )
		{
			psql += " " + hcModel.getCusttypePsql();
		}
				
		sc.setChelp(request.getParameter("searchKey"));
		
		List<HashMap> itemType1List = new ArrayList<HashMap>();
		if (StringUtils.isNotEmpty(sc.getCatFieldName()))
		{
			Map map = new HashMap();
			map.put("psql", psql);
			map.put("chelp", sc.getChelp());
			itemType1List = (List<HashMap>) userItemService.getUserItemCategoryWithCountList(hostUserId, sc.getCatFieldName(), map);
		}
		mv.addObject("itemType1List", itemType1List);
		
		// -- get category2 list.
		List<HashMap> itemType2List = new ArrayList<HashMap>();
		if ( StringUtils.isNotEmpty(sc.getCatFieldName()) && StringUtils.isNotEmpty(sc.getCategory()) && StringUtils.isNotEmpty(sc.getCatFieldName2()) )
		{
			Map map = new HashMap();
			map.put("psql", psql);
			map.put("chelp", sc.getChelp());
			itemType2List = (List<HashMap>) userItemService.getUserItemCategoryWithCountList(hostUserId, sc.getCatFieldName2(), sc.getCatFieldName(), sc.getCategory(),  map);
		}
		mv.addObject("itemType2List", itemType2List);
		
		// get the user item list.
		sc.setUserId(hostUserId);
		sc.setPsql(psql);
		sc.setCustUserId( hcModel.getCustUserId() ); 
		
		Integer totalCount = userItemService.countUserItemListInOrder(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> list = userItemService.getUserItemListInOrder(sc);
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_USER_ITEMS);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Save the order data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveOrderAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel order) throws Exception 
	{
		// only accept the request on Ajax.
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "orderList");
		}
		
		// check multiHostMode
		String multiHostMode = request.getParameter("multiHostMode");
		if (StringUtils.isNotEmpty(multiHostMode) && "1".equals(multiHostMode))
		{
			return saveOrdeInMultiHostModerAjax(request, response, order);
		}
		
		System.out.println("\n >>>>>>>>>>> Save Order >>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String action = request.getParameter("action");
		boolean isSubmit = "submit".equals(action);
		
		String hostUserId = request.getParameter("hostUserId");
		HostCustModel hcModel = hostCustService.getHostCustSetting(hostUserId, userId);
		
		// ---- Validation here
		if (hcModel == null)
		{
			rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
			return ajaxReturn(mv, rm);
		}
		
		String billId = order.getBillId();
		
		// if trying to save a draft bill, need to do the extra validation.
		if ( ! isSubmit )
		{
			if ( ! StringUtils.isEmpty(billId) )
			{
				order = billService.getBill( billId );
				if ( order == null )
				{
					rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
					return ajaxReturn(mv, rm);
				}
				
				// current emp's bill?
				if ( ! empId.equals(order.getInputorId()) ) 
				{
					rm.setResultMsg(MessageUtil.getMessage("system.common.no.permission"));
					return ajaxReturn(mv, rm);
				}
				
				if ( ! order.isEditableByState() )
				{
					rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
					return ajaxReturn(mv, rm);
				}
			}
			
			// we need to re-bind the order to override the attributes in the current order.
			ServletRequestDataBinder binder = new ServletRequestDataBinder(order);
			binder.bind(request);
		}
		// ---- End of validation
		
		// getting the cartlist from json string.
		String itemsStr = request.getParameter("itemsStr");
		org.json.JSONArray jsonArray = new org.json.JSONArray(itemsStr);
		
		HashMap<String, CartUserItemModel> cartItems = new HashMap<String, CartUserItemModel>();
		List<String> itemIds = new ArrayList<String>();
		
		double cartTotal = 0.0;
		for (int i=0; i<jsonArray.length(); i++)
		{
			JSONObject item = (JSONObject)jsonArray.get(i);
	
			String itemId = (String)item.get("itemId");
			itemIds.add( itemId );
			
			CartUserItemModel temp = new CartUserItemModel();
			
			temp.setItemId(itemId);
			temp.setPrice( MathUtil.getDouble(item.get(hcModel.getPriceColName()), true) );
			temp.setQty( MathUtil.getDouble(item.get("qty"), true) );
			temp.setTotal( temp.getQty() * temp.getPrice() );
			temp.setNote( (String)item.get("item_note") );
			
			temp.setJsDisplay( item.has("js_display")? String.valueOf( item.getString("js_display")).trim() : "" );
			temp.setJsQty( item.has("js_qty")? MathUtil.getDouble(item.getDouble("js_qty"), true) : 0 );
		
			cartItems.put(itemId, temp);
			
			cartTotal += temp.getTotal();
		}
		System.out.println(cartItems);
		
		
		// get the user items by itemsId.
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("includeStockQty", "1");
		params.put("billType", Constants.CONST_BILL_TYPE_DING);
		params.put("state", Constants.BILL_STATE_IN_PROCESS);
		
		List<UserItemModel> userItems = userItemService.getUserItemListByIds(hostUserId, itemIds, params);
		
		// ----------- Validation Again ------- //
		// we check the js display and pre paid amount by payment type.
		if ( isSubmit )
		{
			// if  pre-payment selected? Later it should be improved from paytype model.
			if ( StringUtils.isNotEmpty(order.getPaymentType()) ) 
			{
				// Get the remaining prepay balance
				PrepayBillSModel ppSc = new PrepayBillSModel(hostUserId, loginUser.getUserId(), order.getPaytypeId(), order.getPaymentType());
				List<PrepayBillModel> ppList = billService.getPrePayTotalAmtList(ppSc);
				if ( ppList.isEmpty() || cartTotal > MathUtil.getDouble(ppList.get(0).getTotalAmt(), true)  ) 
				{
					rm.setResultMsg(MessageUtil.getMessage("order.insufficient_prepayment"));
					return ajaxReturn(mv, rm);
				}
			}
			
		}
		// ----------- End of Validation Again  ---------------------------- //
		
		
		// ----------- Setting the order information ----------------------- /
		UserModel hostUser = userService.getUserById(hostUserId) ;
		UserModel custUser = loginUser;
		
		// general setting
		order.setHostUserId(hostUserId);
		order.setCustUserId(userId);
		order.setBillType(Constants.CONST_BILL_TYPE_DING);
		
		if ( isSubmit ) 
		{
			order.setState(Constants.BILL_STATE_IN_PROCESS);
		} 
		else
		{
			order.setState( Constants.BILL_STATE_DRAFT );
		}
		order.setItemtype(Constants.ITEM_TYPE_WANG_LU);
		order.setCusttypeId( hcModel.getCustTypeId());
		order.setCusttypeName( hcModel.getCustTypeName());
		order.setPricecol( hcModel.getPriceColName() );
		order.setPricedesc( hcModel.getPriceDesc() );
		order.setManagerId(loginUser.getEmpId());
		order.setManagerName(loginUser.getEmpName());
		order.setInputorId(loginUser.getEmpId());
		order.setInputorName(loginUser.getEmpName());
		order.setWebstate(Constants.CONST_N);
		order.setWebno(hostUserId);
		
		order.setFreightamount2("0");
		order.setFreightAmt("0");
		
		// paytype setting
		order.setPaytype( payTypeService.getPayType(order.getPaytypeId()) );
		
		// delivery address setting.
		order.setDeliveryAddress( deliveryAddrService.getDeliveryAddr(order.getAddrId()) );
		
		// cust user setting.
		order.setCustUserNo(hcModel.getCustUserNo());
		order.setCustUserName(hcModel.getCustUserName() );
		order.setCustContactName(hcModel.getCustContactName());
		order.setCustShortName( hcModel.getCustShortName() );
		order.setCustTelNo(hcModel.getCustTelNo());
		order.setCustQqNo(custUser.getQqNo());
		order.setCustMobileNo(hcModel.getCustMobileNo());
		
		// host user setting.
		order.setHostUserNo(hcModel.getHostUserNo());
		order.setHostUserName(hcModel.getHostUserName());
		order.setHostContactName( hcModel.getHostContactName() );
		order.setHostTelNo( hcModel.getHostTelNo() );
		order.setHostQqNo( hostUser.getQqNo() );
		order.setHostMobileNo( hostUser.getMobileNo() );
		order.setHostcustpsql(hcModel.getPsql());
		order.setHostAddress( hostUser.getAddress() );
		
		// dept setting.
		order.setDept( departmentService.getDepartment(hostUser.getDeptId()) );
		
		// ----------- End of Setting the order information ----------------------- /
		
		// Saving a draft bill here...
		if ( ! isSubmit ) 
		{
			order.setState( Constants.BILL_STATE_DRAFT );
			
			ResultModel sRet = (ResultModel) billService.processSaveBill( order, userItems, cartItems );
			
			SysMsg.addMsg( SysMsg.SUCCESS, MessageUtil.getMessage("order.save.ok"), request );
			rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
			
			return ajaxReturn(mv, rm);
		}
		
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
		billService.setHostUserBSMap( hostUserBSMap );
		billService.setHostCustModel( hcModel );
		
		// Process the form submission to create an order.
		ResultModel sRet = (ResultModel) billService.processSubmittedBill( order, userItems, cartItems );
		
		int ecCode = sRet.getResultCode();
		switch ( ecCode )
		{
		case BillService.EC_NO_ITEMS:
			// this is invalid order.
			rm.setResultMsg(MessageUtil.getMessage("order.submit.ok.no.items"));
			break;
		
		case BillService.EC_NO_EMP:
		case BillService.EC_NO_WORKFLOW:
			// this is invalid order.
			rm.setResultMsg(MessageUtil.getMessage("order.submit.ok.no.emp"));
			break;
			
		case BillService.EC_SUCCESS:
			Integer noStockItemsCnt = (Integer) sRet.getResultData();
			
			if (noStockItemsCnt == null || noStockItemsCnt == 0)
			{
				rm.setResultMsg(MessageUtil.getMessage("order.submit.ok"));
			}
			else 
			{
				rm.setResultMsg(MessageUtil.getMessage("order.submit.ok.items.ommitted", new Object[]{noStockItemsCnt} ));
			}
			break;
		}
		
		SysMsg.addMsg( SysMsg.SUCCESS, rm.getResultMsg(), request );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Process the order from user page as multi host mode.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 * @throws Exception
	 * 2018
	 */
	private ModelAndView saveOrdeInMultiHostModerAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel orderParam) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		// getting the cartlist from json string.
		// prepare the cart information 
		// check the validation (host-cust setting)
		String itemsStr = request.getParameter("itemsStr");
		org.json.JSONArray jsonArray = new org.json.JSONArray(itemsStr);
		
		HashMap<String, HashMap<String, CartUserItemModel>> cartItemsMap = new HashMap<String, HashMap<String, CartUserItemModel>>();
		HashMap<String, List<String>> itemIdsMap = new HashMap<String, List<String>>();
		HashMap<String, HostCustModel> hcModelMap = new HashMap<String, HostCustModel>();
		HashMap<String, Double> totalMap = new HashMap<String, Double>();
		
		HashMap<String, CartUserItemModel> cartItems = new HashMap<String, CartUserItemModel>();
		List<String> itemIds = new ArrayList<String>();
		HostCustModel hcModel = null;
		double cartTotal = 0.0;
		
		for ( int i = 0; i < jsonArray.length(); i++ )
		{
			JSONObject item = (JSONObject)jsonArray.get(i);
			
			String itemUserId = (String)item.get("userId");
			
			// get host-cust setting.
			if ( hcModelMap.containsKey(itemUserId) )
			{
				hcModel = hcModelMap.get(itemUserId);
			}
			else
			{
				hcModel = hostCustService.getHostCustSetting(itemUserId, userId);
				hcModelMap.put(itemUserId, hcModel);
			}
			
			// if host cust doesn't exist, we will skip the ordered products.
			if (hcModel == null) 
			{
				continue;
			}
			
			if ( cartItemsMap.containsKey(itemUserId) )
			{
				cartItems = cartItemsMap.get(itemUserId);
				itemIds = itemIdsMap.get(itemUserId);
			} 
			else 
			{
				cartItems = new HashMap<String, CartUserItemModel>();
				itemIds = new ArrayList<String>();
				
				cartTotal = 0.0;
			}
			
			String itemId = (String)item.get("itemId");
			itemIds.add( itemId );
			
			CartUserItemModel temp = new CartUserItemModel();
			
			temp.setItemId(itemId);
			temp.setPrice( MathUtil.getDouble(item.get(hcModel.getPriceColName()), true) );
			temp.setQty( MathUtil.getDouble(item.get("qty"), true) );
			temp.setTotal( temp.getQty() * temp.getPrice() );
			temp.setNote( (String)item.get("item_note") );
			
			temp.setJsDisplay( item.has("js_display")? String.valueOf( item.getString("js_display")).trim() : "" );
			temp.setJsQty( item.has("js_qty")? MathUtil.getDouble(item.getDouble("js_qty"), true) : 0 );
			
			cartItems.put(itemId, temp);
			
			cartTotal += temp.getTotal();
			
			// put the cart information, item's ids and cart total by user id.
			cartItemsMap.put(itemUserId, cartItems);
			itemIdsMap.put(itemUserId, itemIds);
			totalMap.put(itemUserId, cartTotal);
		}
		
		// loop for the hosts who order the products.
		for ( String hostUserId : cartItemsMap.keySet() )
		{
			hcModel = hcModelMap.get(hostUserId);
			
			if (hcModel == null) continue;
			
			cartItems = cartItemsMap.get(hostUserId);
			itemIds = itemIdsMap.get(hostUserId);
			
			
			// get the user items by itemsId.
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("includeStockQty", "1");
			params.put("billType", Constants.CONST_BILL_TYPE_DING);
			params.put("state", Constants.BILL_STATE_IN_PROCESS);
			
			List<UserItemModel> userItems = userItemService.getUserItemListByIds(hostUserId, itemIds, params);
			
			// ----------- Setting the order information ----------------------- /
			UserModel hostUser = userService.getUserById(hostUserId);
			
			if (hostUser == null) continue;
			
			UserModel custUser = loginUser;
			
			BillHeadModel order = orderParam;
			order.setBillId(null);
			
			// general setting
			order.setHostUserId(hostUserId);
			order.setCustUserId(userId);
			order.setBillType(Constants.CONST_BILL_TYPE_DING);
			
			order.setState(Constants.BILL_STATE_IN_PROCESS);
			
			order.setItemtype(Constants.ITEM_TYPE_WANG_LU);
			order.setCusttypeId( hcModel.getCustTypeId());
			order.setCusttypeName( hcModel.getCustTypeName());
			order.setPricecol( hcModel.getPriceColName() );
			order.setPricedesc( hcModel.getPriceDesc() );
			order.setManagerId(loginUser.getEmpId());
			order.setManagerName(loginUser.getEmpName());
			order.setInputorId(loginUser.getEmpId());
			order.setInputorName(loginUser.getEmpName());
			order.setWebstate(Constants.CONST_N);
			order.setWebno(hostUserId);
			
			order.setFreightamount2("0");
			order.setFreightAmt("0");
			
			// paytype setting : We set the null in multi host mode.
			// order.setPaytype( payTypeService.getPayType(order.getPaytypeId()) );
			
			// delivery address setting.
			order.setDeliveryAddress( deliveryAddrService.getDeliveryAddr(order.getAddrId()) );
			
			// cust user setting.
			order.setCustUserNo(hcModel.getCustUserNo());
			order.setCustUserName(hcModel.getCustUserName() );
			order.setCustContactName(hcModel.getCustContactName());
			order.setCustShortName( hcModel.getCustShortName() );
			order.setCustTelNo(hcModel.getCustTelNo());
			order.setCustQqNo(custUser.getQqNo());
			order.setCustMobileNo(hcModel.getCustMobileNo());
			
			// host user setting.
			order.setHostUserNo(hcModel.getHostUserNo());
			order.setHostUserName(hcModel.getHostUserName());
			order.setHostContactName( hcModel.getHostContactName() );
			order.setHostTelNo( hcModel.getHostTelNo() );
			order.setHostQqNo( hostUser.getQqNo() );
			order.setHostMobileNo( hostUser.getMobileNo() );
			order.setHostcustpsql(hcModel.getPsql());
			order.setHostAddress( hostUser.getAddress() );
			
			// dept setting.
			order.setDept( departmentService.getDepartment(hostUser.getDeptId()) );
			
			order.setNote(request.getParameter("note_" + hostUserId));
			
			// ----------- End of Setting the order information ----------------------- /
			
			HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
			billService.setHostUserBSMap( hostUserBSMap );
			billService.setHostCustModel( hcModel );
			
			// Process the form submission to create an order.
			ResultModel sRet = (ResultModel) billService.processSubmittedBill( order, userItems, cartItems );
			
			int ecCode = sRet.getResultCode();
			switch ( ecCode )
			{
			case BillService.EC_NO_ITEMS:
				// this is invalid order.
				rm.setResultMsg(MessageUtil.getMessage("order.submit.ok.no.items"));
				break;
				
			case BillService.EC_NO_EMP:
			case BillService.EC_NO_WORKFLOW:
				// this is invalid order.
				rm.setResultMsg(MessageUtil.getMessage("order.submit.ok.no.emp"));
				break;
				
			case BillService.EC_SUCCESS:
				Integer noStockItemsCnt = (Integer) sRet.getResultData();
				
				if (noStockItemsCnt == null || noStockItemsCnt == 0)
				{
					rm.setResultMsg(MessageUtil.getMessage("order.submit.ok"));
				}
				else 
				{
					rm.setResultMsg(MessageUtil.getMessage("order.submit.ok.items.ommitted", new Object[]{noStockItemsCnt} ));
				}
				break;
			}
			
			String msg =  rm.getResultMsg();
			
			if (hcModelMap.size() > 1) 
			{
				msg = "<b>" + hcModel.getHostUserName() +  "</b> : " +  msg;
			}
			
			SysMsg.addMsg( SysMsg.SUCCESS, msg, request );
		}
		
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		return ajaxReturn(mv, rm);
	}

	
	/**
	 * Description	: Delete an order. Ajax call only.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView deleteOrderAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel order) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "orderList");
		}
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String billId = order.getBillId();
		if (StringUtils.isEmpty(billId))
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// validation here.
		order = billService.getBill( billId );
		if ( order == null )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// current emp's bill?
		if ( ! empId.equals(order.getInputorId()) ) 
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		if ( ! order.isDeletable() )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		billService.deleteBill( billId );
		
		rm.setResultMsg( MessageUtil.getMessage("order.delete.success") );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Show the order detail.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView orderView(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/orderView" );
		BillHeadModel order = new BillHeadModel();
		
		String billId = request.getParameter("billId");
		
		// ------ Validation ----------------------- //
		if ( StringUtils.isEmpty(billId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "orderList");
		}
		
		if ( StringUtils.isNotEmpty(billId) )
		{
			// validation here.
			order = billService.getBill( billId );
			if ( order == null )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "orderList");
			}
			
			// current emp's bill?
			if ( ! order.isViewable(loginUser) ) 
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "orderList");
			}
		}
		// ------ End of validation ----------------------- //
		
		String hostUserId = order.getHostUserId();
		
		String itemUserId = hostUserId;
		if (Constants.ITEM_TYPE_BY_GUEST.equals(order.getItemtype()))
		{
			itemUserId = order.getCustUserId();
		}
		else
		{
			if (StringUtils.isNotEmpty(order.getWebno()))
			{
				itemUserId = order.getWebno();
			}
		}
		
		// get the biz setting for a host user.
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
		
		//Boolean isThirdVendor = BizSetting.THIRD_VENDOR_MARK_Y.equals(hostUserBSMap.get(BizSetting.THIRD_VENDOR_MARK)); 
		Boolean isThirdVendor = true;
		mv.addObject("isThirdVendor", isThirdVendor);
		
		//Boolean isThirdVendorDisp = BizSetting.THIRD_VENDOR_DISPLAY_Y.equals(hostUserBSMap.get(BizSetting.THIRD_VENDOR_DISPLAY)); 
		Boolean isThirdVendorDisp = true;
		mv.addObject("isThirdVendorDisp", isThirdVendorDisp);
		
		String uiDetailMark = (String)hostUserBSMap.get(BizSetting.ITEM_DETAIL_MODE); 
		mv.addObject("uiDetailMark", uiDetailMark);
		
		if ( ! Constants.BILL_STATE_DRAFT.equals(order.getState()))
		{
			Map param = new HashMap();
			param.put("stateComplete", 	Constants.PROC_STATUS_COMPLETED);
			param.put("stateRet", 		Constants.PROC_STATUS_RETURNED);
			param.put("stateInProcess", Constants.PROC_STATUS_IN_PROCESS);
			param.put("submitProcName", Constants.PROC_NAME_ORDER_SUBMIT);
			param.put("isThirdVendor", 	isThirdVendor);
			param.put("userId", 		hostUserId);
			param.put("custUserId", 	order.getCustUserId());
			
			List<BillProcModel> bpList = billService.getOrderBillProcHistory(billId, param);
			
			mv.addObject("bpList", JSONArray.fromObject(bpList));
		}
		else 
		{
			mv.addObject("bpList", JSONArray.fromObject(new ArrayList()));
		}
		
		// 3. getting user items.
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getUserNNItemPropertyList(hostUserId);
		
		List<JSONObject> cartGridColModel = new ArrayList<JSONObject>();
		JSONArray cartGridColName = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, null);
			
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
		
		// get user items in a bill.
		List<UserItemModel> billedItems = new ArrayList<UserItemModel>();
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			billedItems = userItemService.getBilledUserItemList( order );
		}
		tmp.put( "data", billedItems );
		mv.addObject("cartGridData", tmp);
		// ------ End of jqGrid configuration
		
		pageTitle = "订货单明细";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		mv.addObject("order", order);
		
		return mv;
	}
	
	/**
	 * Description	: Show the order detail For Mobile.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelAndView orderViewForMobile(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/orderView" );
		BillHeadModel order = new BillHeadModel();
		
		String billId = request.getParameter("billId");
		
		// ------ Validation ----------------------- //
		if ( StringUtils.isEmpty(billId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "orderList");
		}
		
		if ( StringUtils.isNotEmpty(billId) )
		{
			// validation here.
			order = billService.getBill( billId );
			if ( order == null )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "orderList");
			}
			
			// current emp's bill?
			if ( ! order.isViewable(loginUser) ) 
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "orderList");
			}
		}
		// ------ End of validation ----------------------- //
		
		String hostUserId = order.getHostUserId();
		
		String itemUserId = hostUserId;
		if (Constants.ITEM_TYPE_BY_GUEST.equals(order.getItemtype()))
		{
			itemUserId = order.getCustUserId();
		}
		else
		{
			if (StringUtils.isNotEmpty(order.getWebno()))
			{
				itemUserId = order.getWebno();
			}
		}
		
		// get the biz setting for a host user.
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
		
		//Boolean isThirdVendor = BizSetting.THIRD_VENDOR_MARK_Y.equals(hostUserBSMap.get(BizSetting.THIRD_VENDOR_MARK)); 
		Boolean isThirdVendor = true;
		mv.addObject("isThirdVendor", isThirdVendor);
		
		//Boolean isThirdVendorDisp = BizSetting.THIRD_VENDOR_DISPLAY_Y.equals(hostUserBSMap.get(BizSetting.THIRD_VENDOR_DISPLAY)); 
		Boolean isThirdVendorDisp = true;
		mv.addObject("isThirdVendorDisp", isThirdVendorDisp);
		
		String uiDetailMark = (String)hostUserBSMap.get(BizSetting.ITEM_DETAIL_MODE); 
		mv.addObject("uiDetailMark", uiDetailMark);
		
		if ( ! Constants.BILL_STATE_DRAFT.equals(order.getState()))
		{
			Map param = new HashMap();
			param.put("stateComplete", 	Constants.PROC_STATUS_COMPLETED);
			param.put("stateRet", 		Constants.PROC_STATUS_RETURNED);
			param.put("stateInProcess", Constants.PROC_STATUS_IN_PROCESS);
			param.put("submitProcName", Constants.PROC_NAME_ORDER_SUBMIT);
			param.put("isThirdVendor", 	isThirdVendor);
			param.put("userId", 		hostUserId);
			param.put("custUserId", 	order.getCustUserId());
			
			List<BillProcModel> bpList = billService.getOrderBillProcHistory(billId, param);
			
			mv.addObject("bpList", bpList);
		}
						
		// 3. getting user items.
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getUserNNItemPropertyList(hostUserId);
		
		for (UserItemPropertyModel item : ipList)
		{
			if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd()))
			{
				mv.addObject("itemNameCol", item.getPropertyName());
			}
			else if (Constants.CONST_ITEM_SALE_UNIT_CODE.equals(item.getPropertyTypeCd()))
			{
				mv.addObject("itemUnitCol", item.getPropertyName());
			}
		}
		
		// get user items in a bill.
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			List<UserItemModel> billedItems = userItemService.getBilledUserItemList( order );
			mv.addObject("itemList", billedItems);
		}
		mv.addObject("order", order);
		
		if (order.getHbmark().equals("Y"))
		{
			
			PrepayBillSModel ppSc = new PrepayBillSModel(order.getHostUserId(), order.getCustUserId(), null, null);
			List<PrepayBillModel> ppList = billService.getPrePayTotalAmtList(ppSc);
			mv.addObject("ppList", ppList);
		}
		
		return mv;
	}
}