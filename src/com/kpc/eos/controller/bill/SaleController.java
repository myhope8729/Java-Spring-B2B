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
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.CartUserItemModel;
import com.kpc.eos.model.bill.PayBillDetailModel;
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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Filename		: SaleController.java
 * Description	: Management class for the user's sales.
 * 2017
 * @author		: RKRK
 */
public class SaleController extends BaseEOSController 
{
	public final static String SC_KEY_USER_ITEMS 	= "Sale_userItems";
	public final static String SC_KEY_SALE_CUST 	= "Sale_custList";
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
	private DepartmentService 	departmentService;
	
	// member vars
	private ModelAndView 		mv = null;
	
	private UserModel 			loginUser;
	private String 				userId;
	private String 				empId;
	
	public SaleController() 
	{
		super();
		controllerId = "Sale";
	}
	
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "Sale";
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
		empId = loginUser.getEmpId();
		
		// get the pre breadcrumbs.
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] salesList = new String[]{"saleList", "saleForm", "vendorsList", "paymentBillList", "custList"};
		
		if ( ArrayUtils.contains(salesList, methodName) )
		{
			breads.add(new BreadcrumbModel("填写单据", ""));
			breads.add(new BreadcrumbModel("销售单 ", getCmdUrl("saleList")));
		}
	}

	/**
	 * Description	: Set the sale service.
	 * @author 		: RKRK
	 * @param saleService
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
	 * Description	: Show the sales list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = "Sale_saleList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/saleList", "sc", sc );
		
		pageTitle = "销售单";
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the sales list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Sale_saleList");
		
		sc.setBillType(Constants.CONST_BILL_TYPE_SALE);
		sc.setInputorId(empId);
		sc.setHostUserId(loginUser.getUserId());
		sc.setCustUserId(null);
		
		
		Integer totalCount = billService.countBillList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<BillHeadModel> list = billService.getBillList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Show the customers list to select a customer
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView custList(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/custList");
		
		HostCustSModel sc = new HostCustSModel();
		
		// getting the search model from session
		String key = SC_KEY_SALE_CUST;
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (HostCustSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		pageTitle = "选择客户";
		
		breads.add(new BreadcrumbModel(pageTitle, "#"));
		
		mv.addObject("returnUrl", getCmdUrl("Sale", "saleList"));
		mv.addObject("gridAjaxUrl", getCmdUrl("Sale", "custListGridAjax"));
		mv.addObject("selectActionUrl", getCmdUrl("Sale", "saleForm"));
		
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
	public ModelAndView custListGridAjax(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_SALE_CUST);
		
		// get the list
		sc.setHostId(userId);
		
		Integer totalCount = hostCustService.countHostCustList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<HostCustModel> list = hostCustService.getHostCustList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	
	/**
	 * Description	: Show the sale form. This is the first step to create an sale.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saleForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/saleForm" );
		BillHeadModel sale = new BillHeadModel();
		
		String custUserId = request.getParameter("custUserId");
		String billId = request.getParameter("billId");
		boolean isCopy = COPY_MARK.equals(request.getParameter("copymark"));
		
		String hostUserId = userId;
		UserModel custUser = null;
		// ------ Validation ----------------------- //
		// edit action?
		if ( StringUtils.isNotEmpty(billId) )
		{
			// validation here.
			sale = billService.getBill( billId );
			if ( sale == null )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "saleList");
			}
			
			// current emp's bill?
			if ( ! empId.equals(sale.getInputorId()) ) 
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "saleList");
			}
			
			// if copy function, we don't need this check.
			if ( ! isCopy && ! sale.isEditableByState() )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "saleList");
			}
			
			custUserId = sale.getCustUserId();
		}
		
		HostCustModel hcModel = hostCustService.getHostCustSetting(hostUserId, custUserId);
		
		if (hcModel == null)
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Sale", "saleList");
		}
		
		if ( ! hcModel.getConnection() )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("sale.host.cust.map.not.saved"), request);
			return redirect("Sale", "saleList");
		}
		
		custUser = userService.getUserById(custUserId) ;
		if (custUser == null)
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "saleList");
		}
		// ------ End of validation ----------------------- //
		
		String fullAddress = addrService.getFullAddressByLocationId(custUser.getLocationId());
		custUser.setAddress(fullAddress + custUser.getAddress());
		
		
		// ------ Getting the biz settings  ----------------------- //
		// get the biz setting for a host user.
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
			
		// getting brand list.
		Boolean isBrandmark = BizSetting.BRANDMARK_Y.equals(hostUserBSMap.get(BizSetting.BRANDMARK)); 
		if (isBrandmark)
		{
			List<BizDataModel> brandList = bizDataService.getBizDataByBizCode(hostUserId, Constants.CONST_BIZDATA_ITEM_BRAND, null);
			mv.addObject("brandList", brandList);
		}
		mv.addObject("isBrandmark", isBrandmark);
		Boolean isJsMark = BizSetting.JS_IN_SALE_Y.equals(hostUserBSMap.get(BizSetting.JS_IN_SALE)); 
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
		Boolean isArrivalDateRequired = BizSetting.ARRIVAL_DATE_REQUIRED_IN_SALE_Y.equals(hostUserBSMap.get(BizSetting.ARRIVAL_DATE_REQUIRED_IN_SALE)); 
		mv.addObject("isArrivalDateRequired", isArrivalDateRequired);
		
		Double defaultArrivalDateOffset = 0.0;
		if (StringUtils.isNotEmpty((String)hostUserBSMap.get(BizSetting.ARRIVAL_DATE_OFFSET_IN_SALE))) 
		{
			defaultArrivalDateOffset = MathUtil.getDouble(hostUserBSMap.get(BizSetting.ARRIVAL_DATE_OFFSET_IN_SALE), true);
		}
		long time = new Date().getTime() + (long)(defaultArrivalDateOffset.doubleValue() * 86400000);
		String defaultArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
		if ( StringUtils.isEmpty(sale.getArriveDate()) )
		{
			sale.setArriveDate(defaultArrivalDate);
		}
		
		// ------ End of getting the biz settings  ----------------------- //
		
		
		// getting paytype list
		PayTypeSModel payTypeSC = new PayTypeSModel(hostUserId, custUserId);
		
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
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null, false, Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd())? "right" : null);
			
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
			billedItems = userItemService.getBilledUserItemList( sale );
		}
		tmp.put( "data", billedItems );
		mv.addObject("cartGridData", tmp);
		// ------ End of jqGrid configuration
		
		// ------ Address jqGrid configuration
		List<DeliveryAddrModel> addrList = deliveryAddrService.getUserDeliveryAddrList(custUserId);
		mv.addObject("addrList", JSONArray.fromObject( addrList ));
		// ------ End of Address jqGrid configuration
		
		mv.addObject("itemType1Property", itemType1Property);
		mv.addObject("itemType2Property", itemType2Property);
		mv.addObject("itemPackageProperty", itemPackageProperty);
		
		pageTitle = "新增销售单";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		// getting the search model from session
		request.setAttribute(SC_ID_SESSION, SC_KEY_USER_ITEMS);
		UserItemSModel sc = new UserItemSModel();
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(SC_KEY_USER_ITEMS, sc, request);
		
		mv.addObject("hcModel", hcModel);
		mv.addObject("hc", new JSONObject(hcModel));
		mv.addObject("custUser", custUser);
		mv.addObject("hostUserBSMap", hostUserBSMap);
		
		// ======== Copy function ==========
		if ( isCopy )
		{
			sale.setBillId(null);
		}
		mv.addObject("sale", sale);
		mv.addObject( "sc", sc );
		
		return mv;
	}
	
	/**
	 * Description	: Get user Items on saleForm page.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView userItemsGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		mv = new ModelAndView("jsonView");
		
		String hostUserId = request.getParameter("hostUserId");
		String custUserId = request.getParameter("custUserId");
		HostCustModel hcModel = hostCustService.getHostCustSetting(hostUserId, custUserId);
		
		if (hcModel == null)
		{
			rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
			return ajaxReturn(mv, rm);
		}
		
		UserModel hostUser = userService.getUserById(hostUserId);
		
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
		
		Boolean isBrandmark = BizSetting.BRANDMARK_Y.equals(hostUserBSMap.get(BizSetting.BRANDMARK)); 
		
		
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
	 * Description	: Save the sale data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sale
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveSaleAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel sale) throws Exception 
	{
		// only accept the request on Ajax.
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "saleList");
		}
		
		System.out.println("\n >>>>>>>>>>> Save Sale >>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String action = request.getParameter("action");
		boolean isSubmit = "submit".equals(action);
		
		String hostUserId = request.getParameter("hostUserId");
		String custUserId = request.getParameter("custUserId");
		HostCustModel hcModel = hostCustService.getHostCustSetting(hostUserId, custUserId);
		
		// ---- Validation here
		if (hcModel == null)
		{
			rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
			return ajaxReturn(mv, rm);
		}
		
		String billId = sale.getBillId();
		
		// if trying to save a draft bill, need to do the extra validation.
		if ( ! isSubmit )
		{
			if ( ! StringUtils.isEmpty(billId) )
			{
				sale = billService.getBill( billId );
				if ( sale == null )
				{
					rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
					return ajaxReturn(mv, rm);
				}
				
				// current emp's bill?
				if ( ! empId.equals(sale.getInputorId()) ) 
				{
					rm.setResultMsg(MessageUtil.getMessage("system.common.no.permission"));
					return ajaxReturn(mv, rm);
				}
				
				if ( ! sale.isEditableByState() )
				{
					rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
					return ajaxReturn(mv, rm);
				}
			}
			
			// we need to re-bind the sale to override the attributes in the current sale.
			ServletRequestDataBinder binder = new ServletRequestDataBinder(sale);
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
		params.put("billType", Constants.CONST_BILL_TYPE_SALE);
		params.put("state", Constants.BILL_STATE_IN_PROCESS);
		
		List<UserItemModel> userItems = userItemService.getUserItemListByIds(hostUserId, itemIds, params);
		
		// ----------- Validation Again ------- //
		// we check the js display and pre paid amount by payment type.
		if ( isSubmit )
		{
			// if  pre-payment selected? Later it should be improved from paytype model.
			if ( StringUtils.isNotEmpty(sale.getPaymentType()) ) 
			{
				// Get the remaining prepay balance
				PrepayBillSModel ppSc = new PrepayBillSModel(hostUserId, custUserId, sale.getPaytypeId(), sale.getPaymentType());
				
				List<PrepayBillModel> ppList = billService.getPrePayTotalAmtList(ppSc);
				if ( ppList.isEmpty() || cartTotal > MathUtil.getDouble(ppList.get(0).getTotalAmt(), true)  ) 
				{
					rm.setResultMsg(MessageUtil.getMessage("order.insufficient_prepayment"));
					
					return ajaxReturn(mv, rm);
				}
			}
			
		}
		// ----------- End of Validation Again  //
		
		// ----------- Setting the sale information ----------------------- /
		UserModel custUser = userService.getUserById(custUserId) ;
		UserModel hostUser = loginUser;
		
		// general setting
		sale.setHostUserId(hostUserId);
		sale.setCustUserId(custUserId);
		sale.setBillType(Constants.CONST_BILL_TYPE_SALE);
		
		if ( isSubmit ) 
		{
			sale.setState(Constants.BILL_STATE_IN_PROCESS);
		} 
		else
		{
			sale.setState( Constants.BILL_STATE_DRAFT );
		}
		sale.setItemtype(Constants.ITEM_TYPE_WANG_LU);
		sale.setCusttypeId( hcModel.getCustTypeId());
		sale.setCusttypeName( hcModel.getCustTypeName());
		sale.setPricecol( hcModel.getPriceColName() );
		sale.setPricedesc( hcModel.getPriceDesc() );
		sale.setManagerId(loginUser.getEmpId());
		sale.setManagerName(loginUser.getEmpName());
		sale.setInputorId(loginUser.getEmpId());
		sale.setInputorName(loginUser.getEmpName());
		sale.setWebstate(Constants.CONST_N);
		sale.setWebno(hostUserId);
		
		sale.setFreightamount2("0");
		sale.setFreightAmt("0");
		
		// paytype setting
		sale.setPaytype( payTypeService.getPayType(sale.getPaytypeId()) );
		
		// delivery address setting.
		sale.setDeliveryAddress( deliveryAddrService.getDeliveryAddr(sale.getAddrId()) );
		
		// cust user setting.
		sale.setCustUserNo(hcModel.getCustUserNo());
		sale.setCustUserName(hcModel.getCustUserName() );
		sale.setCustContactName(hcModel.getCustContactName());
		sale.setCustShortName( hcModel.getCustShortName() );
		sale.setCustTelNo(hcModel.getCustTelNo());
		sale.setCustQqNo(custUser.getQqNo());
		sale.setCustMobileNo(hcModel.getCustMobileNo());
		
		// host user setting.
		sale.setHostUserNo(hcModel.getHostUserNo());
		sale.setHostUserName(hcModel.getHostUserName());
		sale.setHostContactName( hcModel.getHostContactName() );
		sale.setHostTelNo( hcModel.getHostTelNo() );
		sale.setHostQqNo( hostUser.getQqNo() );
		sale.setHostMobileNo( hostUser.getMobileNo() );
		sale.setHostcustpsql(hcModel.getPsql());
		sale.setHostAddress( hostUser.getAddress() );
		
		// dept setting.
		sale.setDept( departmentService.getDepartment(custUser.getDeptId()) );
		// ----------- End of Setting the sale information ----------------------- /
		
		// Saving a draft bill here...
		if ( ! isSubmit ) 
		{
			sale.setState( Constants.BILL_STATE_DRAFT );
			
			ResultModel sRet = (ResultModel) billService.processSaveSaleBill( sale, userItems, cartItems );
			
			SysMsg.addMsg( SysMsg.SUCCESS, MessageUtil.getMessage("sale.save.ok"), request );
			rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
			
			return ajaxReturn(mv, rm);
		}
		
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
		billService.setHostUserBSMap( hostUserBSMap );
		billService.setHostCustModel( hcModel );
		
		// Process the form submission to create an sale.
		ResultModel sRet = (ResultModel) billService.processSubmittedSaleBill( sale, userItems, cartItems );
		
		int ecCode = sRet.getResultCode();
		switch ( ecCode )
		{
		case BillService.EC_NO_ITEMS:
			// this is invalid sale.
			rm.setResultMsg(MessageUtil.getMessage("sale.submit.ok.no.items"));
			break;
		
		case BillService.EC_NO_EMP:
		case BillService.EC_NO_WORKFLOW:
			// this is invalid sale.
			rm.setResultMsg(MessageUtil.getMessage("sale.submit.ok.no.emp"));
			break;
			
		case BillService.EC_SUCCESS:
			Integer noStockItemsCnt = (Integer) sRet.getResultData();
			
			if (noStockItemsCnt == null || noStockItemsCnt == 0)
			{
				rm.setResultMsg(MessageUtil.getMessage("sale.submit.ok"));
			}
			else 
			{
				rm.setResultMsg(MessageUtil.getMessage("sale.submit.ok.items.ommitted", new Object[]{noStockItemsCnt} ));
			}
			break;
		}
		
		SysMsg.addMsg( SysMsg.SUCCESS, rm.getResultMsg(), request );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Delete an sale. Ajax call only.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sale
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView deleteSaleAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel sale) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "saleList");
		}
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String billId = sale.getBillId();
		if (StringUtils.isEmpty(billId))
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// validation here.
		sale = billService.getBill( billId );
		if ( sale == null )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// current emp's bill?
		if ( ! empId.equals(sale.getInputorId()) ) 
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		if ( ! sale.isDeletable() )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		billService.deleteBill( billId );
		
		rm.setResultMsg( MessageUtil.getMessage("sale.delete.success") );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Show the sale detail.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView saleView(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/saleView" );
		BillHeadModel sale = new BillHeadModel();
		
		String billId = request.getParameter("billId");
		
		// ------ Validation ----------------------- //
		if ( StringUtils.isEmpty(billId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "saleList");
		}
		
		if ( StringUtils.isNotEmpty(billId) )
		{
			// validation here.
			sale = billService.getBill( billId );
			if ( sale == null )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "saleList");
			}
			
			// current emp's bill?
			if ( ! sale.isViewable(loginUser) )  
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "saleList");
			}
		}
		// ------ End of validation ----------------------- //
		
		String hostUserId = sale.getHostUserId();
		
		String itemUserId = hostUserId;
		if (Constants.ITEM_TYPE_BY_GUEST.equals(sale.getItemtype()))
		{
			itemUserId = sale.getCustUserId();
		}
		else
		{
			if (StringUtils.isNotEmpty(sale.getWebno()))
			{
				itemUserId = sale.getWebno();
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
		
		
		// 2. getting processor's list.
		if ( ! Constants.BILL_STATE_DRAFT.equals(sale.getState()))
		{
			Map param = new HashMap();
			param.put("stateComplete", 	Constants.PROC_STATUS_COMPLETED);
			param.put("stateRet", 		Constants.PROC_STATUS_RETURNED);
			param.put("stateInProcess", Constants.PROC_STATUS_IN_PROCESS);
			param.put("submitProcName", Constants.PROC_NAME_SALE_SUBMIT);
			param.put("isThirdVendor", 	isThirdVendor);
			param.put("userId", 		hostUserId);
			param.put("custUserId", 	sale.getCustUserId());
			
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
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null);
			
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
		
		// get user items in a bill.
		List<UserItemModel> billedItems = new ArrayList<UserItemModel>();
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			billedItems = userItemService.getBilledUserItemList( sale );
		}
		tmp.put( "data", billedItems );
		mv.addObject("cartGridData", tmp);
		// ------ End of jqGrid configuration
		
		pageTitle = "销售单明细";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		mv.addObject("sale", sale);
		
		return mv;
	}
	
	/**
	 * Description	: Get the payment detail with subpaytype.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView paymentBillList(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String hostUserId = request.getParameter("hostUserId");
		String custUserId = request.getParameter("custUserId");
		
		// Validation here.
		if (StringUtils.isEmpty(hostUserId) || StringUtils.isEmpty(custUserId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Main", "main");
		}
		
		// current emp's bill?
		if ( ! custUserId.equals(loginUser.getUserId()) ) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
			return redirect("Main", "main");
		}
		
		PrepayBillSModel ppSc = new PrepayBillSModel(hostUserId, custUserId, null, null);
		
		pageTitle = "货款明细";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		mv = new ModelAndView( "bill/paymentBillList" );
		mv.addObject("sc", ppSc);
		
		return mv;
	}
	
	/**
	 * Description	: Get Pre Payment List through Ajax
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param ppSc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView paymentBillListAjax(HttpServletRequest request, HttpServletResponse response, PrepayBillSModel ppSc) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_SUCCESS_CODE, "");
		mv = new ModelAndView("jsonView");
		
		Integer totalCount = billService.countPaidBillHeadList(ppSc);
		
		ppSc.getPage().setRecords(totalCount);
		
		List<PayBillDetailModel> pbList = billService.getPaidBillHeadList(ppSc);
		
		mv.addObject("rows", pbList);
		mv.addObject("page", ppSc.getPage());
		mv.addObject("sc", ppSc);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Show the sale detail For Mobile.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView saleViewForMobile(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/saleView" );
		BillHeadModel sale = new BillHeadModel();
		
		String billId = request.getParameter("billId");
		
		// ------ Validation ----------------------- //
		if ( StringUtils.isEmpty(billId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "saleList");
		}
		
		if ( StringUtils.isNotEmpty(billId) )
		{
			// validation here.
			sale = billService.getBill( billId );
			if ( sale == null )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "saleList");
			}
			
			// current emp's bill?
			if ( ! sale.isViewable(loginUser) )  
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "saleList");
			}
		}
		// ------ End of validation ----------------------- //
		
		String hostUserId = sale.getHostUserId();
		
		String itemUserId = hostUserId;
		if (Constants.ITEM_TYPE_BY_GUEST.equals(sale.getItemtype()))
		{
			itemUserId = sale.getCustUserId();
		}
		else
		{
			if (StringUtils.isNotEmpty(sale.getWebno()))
			{
				itemUserId = sale.getWebno();
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
		
		
		// 2. getting processor's list.
		if ( ! Constants.BILL_STATE_DRAFT.equals(sale.getState()))
		{
			Map param = new HashMap();
			param.put("stateComplete", 	Constants.PROC_STATUS_COMPLETED);
			param.put("stateRet", 		Constants.PROC_STATUS_RETURNED);
			param.put("stateInProcess", Constants.PROC_STATUS_IN_PROCESS);
			param.put("submitProcName", Constants.PROC_NAME_SALE_SUBMIT);
			param.put("isThirdVendor", 	isThirdVendor);
			param.put("userId", 		hostUserId);
			param.put("custUserId", 	sale.getCustUserId());
			
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
			JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null);
			
			if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd()))
			{
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
			billedItems = userItemService.getBilledUserItemList( sale );
		}
		tmp.put( "data", billedItems );
		mv.addObject("cartGridData", tmp);
		// ------ End of jqGrid configuration
		
		pageTitle = "销售单明细";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		mv.addObject("sale", sale);
		
		return mv;
	}
}