
package com.kpc.eos.controller.userPage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.PagingModel;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.CookieUtil;
import com.kpc.eos.core.util.DateUtil;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.HTMLHelper;
import com.kpc.eos.core.util.MathUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.RandomUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.bill.SubPayTypeModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.ConfigPageBannerModel;
import com.kpc.eos.model.bizSetting.ConfigPageDetailModel;
import com.kpc.eos.model.bizSetting.DeliveryAddrModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeSModel;
import com.kpc.eos.model.bizSetting.UserItemCategoryMdoel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.common.AddressModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.common.UploadFile;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.userPage.CommentModel;
import com.kpc.eos.model.userPage.CommentPicModel;
import com.kpc.eos.model.userPage.CommentSModel;
import com.kpc.eos.model.userPage.InfoLinkModel;
import com.kpc.eos.model.userPage.ShopCartModel;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.bizSetting.ConfigPageService;
import com.kpc.eos.service.bizSetting.DeliveryAddrService;
import com.kpc.eos.service.bizSetting.HostCustService;
import com.kpc.eos.service.bizSetting.PayTypeService;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.common.AddressService;
import com.kpc.eos.service.dataMng.UserService;
import com.kpc.eos.service.userPage.CommentService;
import com.kpc.eos.service.userPage.UserPageService;

public class UserPageController extends BaseEOSController 
{
	public final static String SC_KEY_ITEMS_LIST 	= "UP_userItems";
	
	private UserPageService 	userPageService;
	private UserService 		userService;
	private ConfigPageService 	configPageService;
	private UserItemService 	userItemService;
	private BizSettingService 	bizSettingService;
	private BizDataService 		bizDataService;
	private CommentService 		commentService;
	private DeliveryAddrService deliveryAddrService;
	private AddressService 		addrService;
	private HostCustService 	hostCustService;
	private PayTypeService 		payTypeService;
	
	private UserModel			loginUser;
	private String				userId;
	private String				empId;
	
	private HashMap<String, List<UserItemPropertyModel>> itemFieldList = null;
	private HashMap<String, HashMap<String, String>> hostUserBSMap = null;
	
	public void setUserPageService(UserPageService userPageService) {
		this.userPageService = userPageService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setConfigPageService(ConfigPageService configPageService) {
		this.configPageService = configPageService;
	}
	
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	
	public void setUserItemService(UserItemService userItemService) {
		this.userItemService = userItemService;
	}
	
	public void setBizSettingService(BizSettingService bizSettingService) {
		this.bizSettingService = bizSettingService;
	}
	
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	public void setDeliveryAddrService(DeliveryAddrService deliveryAddrService) {
		this.deliveryAddrService = deliveryAddrService;
	}
	
	public void setAddrService(AddressService addrService) {
		this.addrService = addrService;
	}
	
	public void setHostCustService(HostCustService hostCustService) 
	{
		this.hostCustService = hostCustService;
	}
	
	public void setPayTypeService(PayTypeService payTypeService) 
	{
		this.payTypeService = payTypeService;
	}
	
	public UserPageController() {
		super();
		controllerId = "UserPage";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "UserPage";
		
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser != null ) {
			userId = loginUser.getUserId();
			empId = loginUser.getEmpId();
		}
		
		itemFieldList = null;
	}
	
	/**
	 * Description	: Check if buyer is going to shop for the products of his several buyers.
	 * @author 		: RKRK
	 * @param request
	 * @return
	 * 2018
	 */
	private boolean isMultiHostMode(HttpServletRequest request)
	{
		String hostUserId = request.getParameter("hostUserId");
		return isMobileClient() && ( StringUtils.isEmpty(hostUserId) );
	}
	
	public ModelAndView mainPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("userPage/mainPage");
		
		//Added by rmh 20180113
		String hostUserId = request.getParameter("hostUserId");

		UserModel hostUser = userService.getUserById(hostUserId);
		if(hostUser == null) {
			mv.setViewName("redirect:common/errors/notFound.jsp");
			return mv;
		}
		
		hostUser.setUserAddr(addrService.getFullAddressByLocationId(hostUser.getLocationId()));
		
		List<ConfigPageBannerModel> listBanner = configPageService.getPageBannerList(hostUserId);
		List<ConfigPageDetailModel> listDetail = configPageService.getPageDetailList(hostUserId);
		
		String cookieShopCart = getCookieShopCart(request);
		
		// Get Item Property field
		int i=0;		
		String codeField = "";
		
		List<UserItemPropertyModel> itemFieldList = getItemFieldList(hostUserId, null);
		for (i=0; i<itemFieldList.size(); i++) {
			if ( StringUtils.equals(itemFieldList.get(i).getPropertyTypeCd(), Constants.CONST_ITEM_NUM_CODE) ) {
				codeField = itemFieldList.get(i).getPropertyName();
			}			
		}
		
		// Get Product & Info List
		for (i=0; i<listDetail.size(); i++) {
			if ( listDetail.get(i).getDetailType().equals(Constants.PAGE_DETAIL_TYPE_PRODUCT) ) {			
				// get user item
				UserItemModel item = userItemService.getUserItem(hostUserId, listDetail.get(i).getDetailImgPath());
				
				// set user item
				if ( item != null) 
				{
					// set field value
					getItemFieldValue(hostUserId, item);					
					
					// get shop cart qty
					ShopCartModel cs = new ShopCartModel();
					cs.setCookieId(cookieShopCart);
					cs.setHostId(hostUserId);
					cs.setItemId(item.getItemId());
					ShopCartModel shopCart = userPageService.getShopCart(cs);
					if ( shopCart != null )
						item.setCartQty(shopCart.getQty());
					
					listDetail.get(i).setUserItem( item );
				}
			}
			
			if ( listDetail.get(i).getDetailType().equals(Constants.PAGE_DETAIL_TYPE_NEWS) ) {
				// get info link
				InfoLinkModel sm = new InfoLinkModel();
				sm.setHostUserId( hostUserId );
				sm.setBillType( Constants.CONST_BILL_TYPE_NEWS);
				sm.setInfoType( listDetail.get(i).getInfoType() );
				sm.setInfoNum( Integer.parseInt( listDetail.get(i).getInfoRecNum() ) );				
				
				List<InfoLinkModel> infoLinkList = userPageService.getInfoLinkList(sm);
				
				for (int j=0; j<infoLinkList.size(); j++)
				{
					List<String> infoLinkPicList = userPageService.getInfoLinkPicList( infoLinkList.get(j).getBillId() );
					
					for (int k=0; k<infoLinkPicList.size(); k++)
					{
						infoLinkList.get(j).addPicPath( infoLinkPicList.get(k) );
					}
					
					listDetail.get(i).addInfoLink( infoLinkList.get(j) );
				}
			}
		}
		
		// Get Item Show Mark
		String itemDetailShow = "N";
		String szShowMark = ((BizSettingModel)bizSettingService.getBizSettingBySysType(hostUserId, BizSetting.ITEMSHOW_CODE)).getSysValueName();
		if (StringUtils.isNotEmpty(szShowMark) && StringUtils.equals(szShowMark, BizSetting.ITEMSHOW_DETAIL)) {
			itemDetailShow = "Y";
		}
		
		// Get Comment Count
		CommentSModel cs = new CommentSModel();
		cs.setHostId( hostUser.getUserId() );
		cs.setCommentType( Constants.COMMENT_TYPE_HOMEPAGE );
		Integer commentCount = commentService.getCommentCount(cs);
		
		mv.addObject("hostUser", hostUser);
		mv.addObject("bannerList", listBanner);
		mv.addObject("detailList", listDetail);
		mv.addObject("itemDetailShow", itemDetailShow);
		mv.addObject("commentCount", commentCount);
		mv.addObject("commentType", Constants.COMMENT_TYPE_HOMEPAGE);		
		
		return mv;
	}
	
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("userPage/register");
		
		// Get Host User
		String hostUserId = request.getParameter("hostUserId");
		UserModel hostUser = userService.getUserById(hostUserId);
		
		List<AddressModel> provList = addrService.findProvinceList();
		List<AddressModel> areaList;
		
		AddressModel firstProv = provList.get(0);
		if (firstProv.getAddressLevel().equals("2")){
			List<AddressModel> cityList = addrService.findChildLocationList(firstProv.getAddressId());
			mv.addObject("cityList", cityList);
			AddressModel firstCity = cityList.get(0);
			areaList = addrService.findChildLocationList(firstCity.getAddressId());
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", true);
		}else{
			areaList = addrService.findChildLocationList(firstProv.getAddressId());
			mv.addObject("areaList", areaList);
			mv.addObject("isProvince", false);
		}
		
		mv.addObject("hostUser", hostUser);
		mv.addObject("provList", provList);
		
		return mv;
	}
	
	/**
	 * Description	: User page - product list by categories.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView itemType(HttpServletRequest request, HttpServletResponse response, UserItemSModel scNew) throws Exception 
	{
		String hostUserId = request.getParameter("hostUserId");
		
		if ( isMultiHostMode(request) )
		{
			if (loginUser == null)
				return redirect("Main", "main");
			
			return items(request, response, scNew);
		}
		
		UserModel hostUser = userService.getUserById(hostUserId);
		if ( hostUser.isBuyer() ) 
		{
			if (loginUser == null)
			{
				return redirect("Main", "main");
			}
			else 
			{
				if (isMobileClient())
				{
					return redirect("UserPage", "itemType&hostUserId=");
				}
				else
				{
					return redirect("Main", "main");
				}
			}
		}
		
		// for search model on session
		request.setAttribute(SC_ID_SESSION, SC_KEY_ITEMS_LIST);
		
		ModelAndView mv = null;
		UserItemSModel sc  =  new UserItemSModel();
		
		String next = request.getParameter("next");
		
		// This is a first visit?
		if (next == null) 
		{
			mv = new ModelAndView("userPage/itemType");
			
			// getting the search model from session
			sc  = (UserItemSModel)SearchModelUtil.getSearchModel(SC_KEY_ITEMS_LIST, sc, request);
			
			// If hostUserId is different, we need to reset the search model information.
			if ( ! hostUserId.equals(sc.getUserId()) )
			{
				sc = new UserItemSModel();
			}
			
			// reset the paging model
			PagingModel pager = sc.getPage();
			pager.setSidx("itemId");
			pager.setPage(1);
			sc.setPage(pager);
		}
		else
		{
			mv = new ModelAndView("userPage/itemTypeInner");
			
			// getting the search model from session
			sc  = (UserItemSModel)SearchModelUtil.getSearchModel(SC_KEY_ITEMS_LIST, sc, request);
			PagingModel pager = scNew.getPage();
			sc.getPage().setPage( pager.getPage() );
			sc.getPage().setSidx( pager.getSidx() );
			sc.getPage().setSord( pager.getSord() );
		}
		
		sc.getPage().setRows(9);
		
		// getting the item properties
		String itemType1 	= "";		// item category
		String itemType2 	= "";		// item sub-category
		String itemPackageCol 	= "";		// item property of items count a package.
		String itemNameCol 	= "";		// item property of item name.
		String itemUnitCol 	= "";		// item property of item unit.
		
		List<UserItemPropertyModel> ipList = getItemFieldList(hostUserId, null);
		for (UserItemPropertyModel item : ipList)
		{
			if ( Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemType1 = item.getPropertyName();
			}
			
			if ( Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemType2 = item.getPropertyName();
			}
			
			if ( Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemPackageCol = item.getPropertyName();
			}
			if ( Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemNameCol = item.getPropertyName();
			}
			if ( Constants.CONST_ITEM_SALE_UNIT_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemUnitCol = item.getPropertyName();
			}
		}
		
		mv.addObject("itemType1", 	itemType1);
		mv.addObject("itemType2", 	itemType2);
		mv.addObject("itemPackage", itemPackageCol);
		mv.addObject("itemNameCol", itemNameCol);
		mv.addObject("itemUnitCol", itemUnitCol);
		
		if (next == null)
		{
			// get the itemType1List
			List<UserItemCategoryMdoel> uiCatList = new ArrayList<UserItemCategoryMdoel>();
			if (StringUtils.isNotEmpty(itemType1))
			{
				List<HashMap> itemType1List = userItemService.getUserItemCategoryWithCountList(hostUserId, itemType1, null);
				
				
				if ( isMobileClient() )
				{
					List<UserItemCategoryMdoel> itemTypeAllList = new ArrayList<UserItemCategoryMdoel>();
					List<UserItemCategoryMdoel> cat1List = new ArrayList<UserItemCategoryMdoel>();
					
					String selParent = sc.getCategory();
					String selCat = sc.getCategory2();
					
					// getting category1List here and default category1.
					for (HashMap m : itemType1List)
					{
						String catName = (String) m.get("catName");
						Long cnt = (Long)m.get("cnt");
						
						// set the default cat1
						if ( StringUtils.isEmpty(selParent) && StringUtils.isNotEmpty(catName) )
						{
							selParent = catName;
						}
						
						UserItemCategoryMdoel uiCat = new UserItemCategoryMdoel(catName, cnt);
						uiCat.setParentCatName(catName);
						cat1List.add(uiCat);
						
						// getting category2 list
						if (StringUtils.isNotEmpty(itemType2))
						{
							List<HashMap> itemType2List = userItemService.getUserItemCategoryWithCountList(hostUserId, itemType2, itemType1, catName,  null);
							
							for (HashMap c : itemType2List)
							{
								String catName2 = (String) c.get("catName");
								Long cnt2 = (Long) c.get("cnt");
								
								UserItemCategoryMdoel temp = new UserItemCategoryMdoel(catName2, cnt2);
								temp.setParentCatName(catName);
								
								itemTypeAllList.add(temp);
								
								// default category2
								if (selParent != null && (selParent.equals(catName) || (selParent.equals("-1") && StringUtils.isEmpty(catName))) )
								{
									if (StringUtils.isEmpty(selCat) && StringUtils.isNotEmpty(catName2))
									{
										selCat = catName2;
									}
								}
							}
						}
					}
					
					if ( StringUtils.isNotEmpty(selParent) ) 
					{
						sc.setCategory(selParent);
					}
						
					if ( StringUtils.isNotEmpty(selCat) ) 
					{
						sc.setCategory2(selCat);
					}
					
					mv.addObject("cat1List", cat1List);
					mv.addObject("itemType1List", JSONArray.fromObject(itemTypeAllList));
				}
				else 
				{
					for (HashMap m : itemType1List)
					{
						String catName = (String) m.get("catName");
						Long cnt = (Long)m.get("cnt");
						
						UserItemCategoryMdoel uiCat = new UserItemCategoryMdoel(catName, cnt);
						
						// get children
						if (StringUtils.isNotEmpty(itemType2))
						{
							List<HashMap> itemType2List = userItemService.getUserItemCategoryWithCountList(hostUserId, itemType2, itemType1, catName,  null);
							
							if (itemType2List.size() > 0)
							{
								uiCat.setEmptyChildren();
								
								for (HashMap c : itemType2List)
								{
									catName = (String) c.get("catName");
									cnt = (Long) c.get("cnt");
									
									uiCat.addChild(new UserItemCategoryMdoel(catName, cnt));
								}
							}
						}
						
						uiCatList.add(uiCat);
					}
					mv.addObject("uiCatListHtml", HTMLHelper.getItemCatHtml(uiCatList, 0));
			
				}
			}
		}
		
		// Get Cookie for shop cart
		String cookieShopCart = getCookieShopCart(request);	
		
		// --- Getting the user items list.
		sc.setUserId(hostUserId);
		
		if (StringUtils.isEmpty(sc.getPage().getSidx()) ){
			sc.getPage().setSidx("itemId");
		}
		
		Integer totalCount = userItemService.countUserItemListInOrder(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> list = userItemService.getUserItemListInOrder(sc);
		
		// --- get the current carts list.
		List<ShopCartModel> shopCartList = userPageService.getShopCartList(cookieShopCart, hostUserId);
		
		// --- set the cart information to a product
		for ( UserItemModel userItem : list ) 
		{
			//Set Item Property Value
			
			getItemFieldValue(hostUserId, userItem);
		
			// get and set a cart qty.
			for (ShopCartModel shopCart : shopCartList)
			{
				if (shopCart.getItemId().equals(userItem.getItemId()) && userItem.getUserId().equals(shopCart.getHostId()) )
				{
					userItem.setCartQty(shopCart.getQty());
					break;
				}
			}
		}
		
		// set the next page var.
		int nextPage = sc.getPage().getNextPageNoWithNoGroup();
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		mv.addObject("nextPage", nextPage);
		
		mv.addObject("hostUserId", hostUserId);
		mv.addObject("ckEosCartVal", cookieShopCart);
		
		if (nextPage == 0) 
		{
			mv.addObject("nextUrl", "");
		}
		else
		{
			mv.addObject("nextUrl", getCmdUrl("itemType&hostUserId=" + hostUserId + "&next=1&page.page=" + nextPage));
		}
		
		return mv;
	}
	
	/**
	 * Description	: get the list by ajax.
	 * NOTE			: Because jQuery ias plugin allows ajax GET method, we need to save the search criteria in session.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView itemTypeSearchAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception 
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		String hostUserId = request.getParameter("hostUserId");
		
		sc.setUserId(hostUserId);
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_ITEMS_LIST);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		// session update
		SearchModelUtil.storeSearchModel(SC_KEY_ITEMS_LIST, sc, request);
		
		return itemType(request, response, sc);
	}
	
	/**
	 * Description	: shopping cart list page.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView shopCart(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mv = new ModelAndView("userPage/shopCart");
		
		if ( isMultiHostMode(request) )
		{
			if (loginUser == null)
				return redirect("Main", "main");
			
			// Get Shop Cart
			List<ShopCartModel> listShopCart = userPageService.getShopCartList(getCookieShopCart(request), null);
			List<ShopCartModel> listRemoved = new ArrayList<ShopCartModel>();
			
			for ( ShopCartModel shopCart : listShopCart )
			{
				String itemId = shopCart.getItemId();
				String hostId = shopCart.getHostId();
				
				if (StringUtils.isEmpty(hostId)) continue;
				
				UserItemModel userItem = userItemService.getUserItem(hostId, itemId); 
				
				HostCustModel hcModel = hostCustService.getCustSetting(hostId, userId);
				
				if ( hcModel == null || ! hcModel.getConnection() ) 
				{ 
					listRemoved.add(shopCart);
					continue;
				}
				
				getItemFieldValue(hostId, userItem, hcModel);
				
				shopCart.setUserItem(userItem);
			}
			
			// remove the cart items which can't be ordered.
			if ( listRemoved.size() > 0 ) 
			{
				for ( ShopCartModel shopCart : listRemoved )
				{
					listShopCart.remove(shopCart);
				}
				userPageService.removeShopCartList(listRemoved);
			}
			
			mv.addObject( "hostUser", null );
			mv.addObject( "listShopCart", listShopCart );
			mv.addObject( "cookieShopCart", getCookieShopCart(request) );
		}
		else
		{
			String hostUserId = request.getParameter("hostUserId");
			UserModel hostUser = userService.getUserById(hostUserId);
			
			// Get Shop Cart
			List<ShopCartModel> listShopCart = userPageService.getShopCartList(getCookieShopCart(request), hostUserId);
			
			for (  int i=0; i<listShopCart.size(); i++) 
			{
				
				String itemId = listShopCart.get(i).getItemId();
				String hostId = listShopCart.get(i).getHostId();
				
				
				UserItemModel userItem = userItemService.getUserItem(hostId, itemId); 
				
				if (hostId == null || userItem == null) continue;
				
				getItemFieldValue(hostId, userItem);
				listShopCart.get(i).setUserItem(userItem);
			}
			
			mv.addObject( "hostUser", hostUser );
			mv.addObject( "listShopCart", listShopCart );
			mv.addObject( "cookieShopCart", getCookieShopCart(request) );
		}
		
		return mv;
	}
	
	/**
	 * Description	: Checkout page.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView shopCartCheck(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{		
		if ( isMultiHostMode(request) )
		{
			if (loginUser == null)
				return redirect("Main", "main");
			return shopCartCheckInMultiHostMode(request, response);
		}
		
		String hostUserId = request.getParameter("hostUserId");
		if ( StringUtils.isEmpty(hostUserId) ) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Main", "main");
		}
		
		ModelAndView mv = new ModelAndView("userPage/shopCartCheck");
		
		// ------ validation here ------------- //
		UserModel loginUser = (UserModel) SessionUtil.getUser(request, getSystemName());
		if (loginUser == null)
		{
			mv = redirect("Login", "loginForm");	
			
			SessionUtil.setData(request, Constants.REQUEST_KEY_RETURN_URL, getCmdUrl("shopCartCheck&hostUserId=" + hostUserId));
			
			String[] itemIds = request.getParameterValues("lineCheck");
			SessionUtil.setData(request, Constants.REQUEST_KEY_RETURN_DATA, itemIds);
			return mv;
		}
		
		UserModel hostUser = userService.getUserById(hostUserId);
		if (hostUser == null)
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Main", "main");
		}
		
		HostCustModel hcModel = hostCustService.getHostCustSetting(hostUserId, userId);
		
		if (hcModel == null)
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("order.host.cust.map.not.saved"), request);
			return redirect("UserPage", "itemType&hostUserId=" + hostUserId);
		}
		
		if ( ! hcModel.getConnection() )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("order.host.cust.map.not.saved"), request);
			return redirect("UserPage", "itemType&hostUserId=" + hostUserId);
		}
		// ------ End of validation here -------//
		
		
		// Get request parameter
		String cookieShopCart = getCookieShopCart(request);
		
		
		// Get Shop Cart
		List<ShopCartModel> listCart = new ArrayList<ShopCartModel>();
		List<ShopCartModel> listCartTemp = userPageService.getShopCartList(cookieShopCart, hostUserId);
		
		// remove the unchecked items.
		String[] itemIds = request.getParameterValues("lineCheck");
		if ( isPost(request) )
		{
			SessionUtil.setData(request, Constants.REQUEST_KEY_RETURN_DATA, itemIds);
		} 
		else
		{
			itemIds = (String[])SessionUtil.getData(request, Constants.REQUEST_KEY_RETURN_DATA);
		}
		
		Map idsMap = new HashMap<String, Boolean>();
		
		if (itemIds != null )
		{
			for (String reqId : itemIds) {
				idsMap.put(reqId, true);
			}
			itemIds = null;
		}
		
		Double totalSum = 0.0;
		for (ShopCartModel shopCart : listCartTemp)
		{
			if ( ! idsMap.containsKey(hostUserId + shopCart.getItemId()) ) 
			{
				// remove the shopping cart information.
				ShopCartModel m = new ShopCartModel();
				m.setHostId(hostUserId);
				m.setItemId(shopCart.getItemId());
				m.setQty("0");
				m.setCookieId(cookieShopCart);
				
				userPageService.saveShopCart(m);
				
				continue;
			}
			
			UserItemModel userItem = userItemService.getUserItem(hostUserId, shopCart.getItemId()); 
			
			getItemFieldValue(hostUserId, userItem, hcModel);
			
			Double totalAmt = MathUtil.getDouble(userItem.getItemPrice(), true);
			totalAmt *= MathUtil.getDouble(shopCart.getQty(), true);
			totalSum += totalAmt;
			userItem.setTotal_amt(totalAmt);
			
			shopCart.setUserItem(userItem);
			
			listCart.add(shopCart);
		}
		
		// getting paytype list
		PayTypeSModel payTypeSC = new PayTypeSModel(hostUserId, userId);
		
		payTypeSC.setPrivYn(null);
		List<PayTypeModel> payTypeList = payTypeService.getUserPayTypeList(payTypeSC);
		mv.addObject("payTypeList", payTypeList);
		
		// sub payment_type list here.
		List<SubPayTypeModel> subPayTypeList = new ArrayList<SubPayTypeModel>();
		mv.addObject("subPayTypeList", subPayTypeList);
		
		// get the delivery address.
		List<DeliveryAddrModel> addrList = deliveryAddrService.getUserDeliveryAddrList(loginUser.getUserId());
		for (int j=0; j < addrList.size(); j++) 
		{
			if ( StringUtils.equals(addrList.get(j).getDefaultYn(), Constants.CONST_Y) ) 
			{
				mv.addObject("deliveryAddr", addrList.get(j));
				mv.addObject("deliveryAddrId", addrList.get(j).getAddrId());
			}
		}
		
		// get the biz setting for a host user.
		HashMap<String, String> hostUserBSMap = bizSettingService.getUserBizSettingMap(hostUserId, null);
		Boolean isArrivalDateRequired = BizSetting.ARRIVAL_DATE_REQUIRED_Y.equals(hostUserBSMap.get(BizSetting.ARRIVAL_DATE_REQUIRED)); 
		mv.addObject("isArrivalDateRequired", isArrivalDateRequired);
		
		Double defaultArrivalDateOffset = 0.0;
		if (StringUtils.isNotEmpty((String)hostUserBSMap.get(BizSetting.ARRIVAL_DATE_OFFSET))) {
			try {
				defaultArrivalDateOffset = MathUtil.getDouble(hostUserBSMap.get(BizSetting.ARRIVAL_DATE_OFFSET), true);
			} catch (Exception e) {}
		}
		long time = new Date().getTime() + (long)(defaultArrivalDateOffset.doubleValue() * 86400000);
		String defaultArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
		
		mv.addObject("defaultArrivalDate", defaultArrivalDate);
		
		
		// Get Item Property field
		String priceField = hcModel.getPriceColName();		
		
		mv.addObject("hostUser", hostUser);
		mv.addObject("shopCartNum", listCart.size());
		mv.addObject("itemCount", listCart.size());
		mv.addObject("listCart", listCart);
		mv.addObject("totalSum", totalSum);
		mv.addObject("priceField", priceField);
		mv.addObject("custtypeId", hcModel.getCustTypeId());
		
		return mv;
	}
	
	/**
	 * Description	: Shopcart checking function for multi host mode.
	 * 				: buyer must be logged in.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	private ModelAndView shopCartCheckInMultiHostMode(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{		
		ModelAndView mv = new ModelAndView("userPage/shopCartCheckInMultiHostMode");
		
		// Get request parameter
		String cookieShopCart = getCookieShopCart(request);
		
		// Get Shop Cart
		List<ShopCartModel> listCart = new ArrayList<ShopCartModel>();
		List<ShopCartModel> listCartTemp = userPageService.getShopCartList(cookieShopCart, null);
		
		// remove the unchecked items.
		String[] itemIds = request.getParameterValues("lineCheck");
		
		Map idsMap = new HashMap<String, Boolean>();
		if (itemIds != null)
		{
			for (String reqId : itemIds) {
				idsMap.put(reqId, true);
			}
			itemIds = null;
		}
		
		Double totalSum = 0.0;
		for ( ShopCartModel shopCart : listCartTemp )
		{
			String itemId = shopCart.getItemId();
			String hostId = shopCart.getHostId();
			if (StringUtils.isEmpty(hostId)) continue;
			
			if ( isPost(request) && ! idsMap.containsKey(hostId + itemId) ) 
			{
				// remove the shopping cart information.
				ShopCartModel m = new ShopCartModel();
				m.setHostId(hostId);
				m.setItemId(itemId);
				m.setQty("0");
				m.setCookieId(cookieShopCart);
				
				userPageService.saveShopCart(m);
				
				continue;
			}
			
			UserItemModel userItem = userItemService.getUserItem(hostId, itemId); 
			
			HostCustModel hcModel = hostCustService.getCustSetting(hostId, userId);
			
			if ( hcModel == null || ! hcModel.getConnection() ) continue; 
			
			getItemFieldValue(hostId, userItem, hcModel);
			
			Double totalAmt = MathUtil.getDouble(userItem.getItemPrice(), true);
			totalAmt *= MathUtil.getDouble(shopCart.getQty(), true);
			
			totalAmt = Math.round(totalAmt * 1000) / 1000.00; 
			
			totalSum += totalAmt;
			userItem.setTotal_amt(totalAmt);
			
			shopCart.setUserItem(userItem);
			
			listCart.add(shopCart);
		}
		totalSum = Math.round(totalSum * 1000) / 1000.00; 
		
		// get the delivery address.
		List<DeliveryAddrModel> addrList = deliveryAddrService.getUserDeliveryAddrList( userId );
		for (DeliveryAddrModel addr : addrList)
		{
			mv.addObject("deliveryAddr", addr);
			mv.addObject("deliveryAddrId", addr.getAddrId());
			
			if ( StringUtils.equals(addr.getDefaultYn(), Constants.CONST_Y) ) 
			{
				break;
			}
		}
		
		// get the biz setting for a host user.
		mv.addObject("isArrivalDateRequired", true);
		String defaultArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		mv.addObject("defaultArrivalDate", defaultArrivalDate);
		
		mv.addObject("shopCartNum", listCart.size());
		mv.addObject("itemCount", listCart.size());
		mv.addObject("listCart", listCart);
		mv.addObject("totalSum", totalSum);
		
		// set the multiHostMode now.
		mv.addObject("multiHostMode", 1);
		
		return mv;
	}
	
	/**
	 * Description	: Save cart information
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView saveShopCartAjax(HttpServletRequest request, HttpServletResponse response, ShopCartModel sc) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_SUCCESS_CODE, "");
		ModelAndView mv = new ModelAndView("jsonView");
		
		// start validation.
		String hostUserId = request.getParameter("hostUserId");
		
		sc.setHostId(hostUserId);
		
		// save the shopping cart.
		userPageService.saveShopCart(sc);		
		
		rm.setResultMsg(MessageUtil.getMessage("up.cart.add.success"));
		
		return ajaxReturn(mv, rm);
	}
	
	/***
	 *  Comment
	 */
	public ModelAndView docComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("userPage/docComment");
		
		String hostUserId = request.getParameter("hostUserId");
		UserModel hostUser = userService.getUserById(hostUserId);
		mv.addObject("hostUser", hostUser);
		
		return mv;
	}
		
	public ModelAndView listComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("userPage/listComment");
		
		String hostUserId = request.getParameter("hostUserId");
		UserModel hostUser = userService.getUserById(hostUserId);
		
		// Get Comment
		CommentSModel cs = new CommentSModel();
		cs.setHostId( hostUserId );
		cs.setCommentType( request.getParameter("commentType") );
		cs.setItemId( request.getParameter("itemId") );
		
		List<CommentModel> listComment = commentService.getCommentList(cs);
		
		for (int i=0; i<listComment.size(); i++) {
			List<CommentPicModel> listCommentPic = commentService.getCommentPicList( listComment.get(i).getCommentId() ); 
			for (int j=0; j<listCommentPic.size(); j++) {
				listComment.get(i).addPic( listCommentPic.get(j) );
			}
		}
		
		mv.addObject("hostUser", hostUser);
		mv.addObject("listComment", listComment);
		
		return mv;
	}
	
	public ModelAndView returnComment(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		
		ModelAndView mv = new ModelAndView("userPage/returnComment");
		
		String commentId = request.getParameter("commentId");
		CommentModel comment = commentService.getCommentById(commentId);
		
		UserModel hostUser = userService.getUserById(comment.getHostId());
		mv.addObject("hostUser", hostUser);
		mv.addObject("comment", comment);
		
		return mv;
	}	
	
	public ModelAndView saveComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UserModel loginUser = (UserModel) SessionUtil.getUser(request, getSystemName());
		
		String 	lastCommentId = commentService.getLastCommentId();
		String 	lastCommentPicId = commentService.getLastCommentPicId();
		
		String 	hostUserId = request.getParameter("hostUserId");
		String 	commentType = request.getParameter("commentType");
		String 	itemId = request.getParameter("itemId");
		
		// Insert Comment
		CommentModel comment = new CommentModel();
		comment.setCreateBy( loginUser.getEmpId() );
		comment.setUpdateBy( loginUser.getEmpId() );
		comment.setHostId( hostUserId );
		comment.setCommentType( commentType );
		comment.setItemId( itemId );
		comment.setCommentName( request.getParameter("cName") );
		comment.setCommentText( request.getParameter("cText") );
		comment.setUpperId( request.getParameter("upperId") );		
		
		commentService.insertComment(comment);
		
		// Insert Comment Pic
		CommentPicModel commentPic = new CommentPicModel();
		commentPic.setCommentId(lastCommentId);
		
		// Save Comment Pic Image
		String now = DateUtil.getToday("yyyyMMddHHmmss");
		String tempId = lastCommentPicId.replaceFirst("CMMP0+", "").replace("CMMP", "");
		String fileName = tempId.concat(now);
		
		UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request, "commentpic", "commentPicFile", "", fileName);
		if(uploadedFile != null){
			commentPic.setCommentImgPath(uploadedFile.getSysFileName());
			commentService.insertCommentPic(commentPic);
		}
		
		// Get Return Url
		String returnType = request.getParameter("returnType");
		String returnUrl = "";
		
		if ( StringUtils.equals(returnType, "list") ) {
			returnUrl = "UserPage.do?cmd=listComment&hostUserId=" + hostUserId + "&commentType=" + commentType + "&itemId=" + itemId;
		} else {
			if ( StringUtils.equals(commentType, Constants.COMMENT_TYPE_HOMEPAGE) ) {
				returnUrl = "UserPage.do?cmd=mainPage&hostUserId=" + hostUserId;
			} else {
				returnUrl = "UserItem.do?cmd=viewItemPage&hostUserId=" + hostUserId + "&itemId=" + itemId;
			}
		}
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:" + returnUrl);
		return mv;
	}
	
	public ModelAndView delComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String commentId = request.getParameter("commentId");
		
		// for return url
		CommentModel comment = commentService.getCommentById(commentId);
		
		// delete comment pic file
		List<CommentPicModel> listCommentPic = commentService.getCommentPicList( commentId ); 
		for (int i=0; i<listCommentPic.size(); i++) {
			FileUtil.deleteFile("commentpic", listCommentPic.get(i).getCommentImgPath());
		}
		
		// delete from database
		commentService.deleteComment(commentId);
		commentService.deleteCommentPic(commentId);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:UserPage.do?cmd=listComment&hostUserId=" + comment.getHostId() + "&commentType=" + comment.getCommentType() + "&itemId=" + comment.getItemId());
		return mv;
	}
	
	// Common Function
	public Object getItemFieldValue(String hostUserId, UserItemModel userItem) throws Exception 
	{
		return getItemFieldValue(hostUserId, userItem, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getItemFieldValue(String hostUserId, UserItemModel userItem, HostCustModel hcModel) throws Exception 
	{
		// Get Item Property field
		String finalPriceField = "";
		
		String codeField = "";
		String priceField = "";
		String nameField = "";
		String unitField = "";
		String itemPackageField = "";
		String defaultPriceField = "";
		
		List<UserItemPropertyModel> itemFieldList = getItemFieldList(hostUserId, null);
		for ( UserItemPropertyModel item : itemFieldList) 
		{
			String typeCode = item.getPropertyTypeCd();
			
			if ( Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(typeCode) )
			{
				itemPackageField = item.getPropertyName();
			}
			if ( Constants.CONST_ITEM_NAME_CODE.equals(typeCode) )
			{
				nameField = item.getPropertyName();
			}
			if ( Constants.CONST_ITEM_SALE_UNIT_CODE.equals(typeCode) )
			{
				unitField = item.getPropertyName();
			}
			if ( Constants.CONST_ITEM_NUM_CODE.equals(typeCode) )
			{
				codeField = item.getPropertyName();
			}
			
			if ( Constants.CONST_ITEM_PRICE_CODE.equals(typeCode) )
			{
				priceField = item.getPropertyName();
				
				// default price
				if ( Constants.CONST_Y.equals(item.getPropertyValue()) )
				{
					defaultPriceField = priceField;
				}
			}
		}
		
		userItem.setItemCode( userItem.get(codeField) );
		
		// if  hostcust setting is defined, we will use the price field from this setting 
		if (hcModel != null)
		{
			finalPriceField = hcModel.getPriceColName();
			
			String custPrice = userItem.getCustPrice();
			if (StringUtils.isNotEmpty(custPrice))
			{
				userItem.setItemPrice( custPrice );
			}
			else 
			{
				userItem.setItemPrice( userItem.get(finalPriceField) );
			}
		}
		else 
		{
			if ( StringUtils.isNotEmpty(defaultPriceField) )
			{
				userItem.setItemPrice( userItem.get(defaultPriceField) );
				finalPriceField = defaultPriceField;
			}
			else 
			{
				// if not exist default price?
				userItem.setItemPrice( userItem.get(priceField) );
				
				finalPriceField = priceField;
			}
			
		}
		
		if ( StringUtils.isNotEmpty(userItem.getPriceCol()) )
		{
			String price = userItem.get(userItem.getPriceCol());
			
			if ( StringUtils.isNotEmpty(price) )
			{
				finalPriceField = userItem.getPriceCol();
				userItem.setItemPrice(price);
			}
		}
		
		// If cust price defined, we will set this price for an item.
		if ( StringUtils.isNotEmpty(userItem.getCustPrice()) )
		{
			userItem.setItemPrice(userItem.getCustPrice());
		}
		
		
		userItem.setItemCode( userItem.get(codeField) );
		userItem.setItemName( userItem.get(nameField) );
		if (StringUtils.isEmpty(userItem.getItemName()))
		{
			userItem.setItemName(userItem.getItemCode());
		}
		userItem.setItemUnit( userItem.get(unitField) );
		userItem.setItemPackageCnt( userItem.get(itemPackageField) );
		
		userItem.setPriceCol(finalPriceField);
		
		HashMap ret = new HashMap();
		ret.put("priceField", finalPriceField);
		
		return ret;
	}
	
	// get the cookieId
	public static String getCookieShopCart(HttpServletRequest request) throws Exception 
	{
		String cookieShopCart = null;
		String szTmp = (String)CookieUtil.getData(request, Constants.COOKIE_KEY_EOS_CART);
		if(  StringUtils.isNotEmpty(szTmp) ) 
		{
			cookieShopCart = szTmp;
		} 
		else 
		{
			cookieShopCart = RandomUtil.getRandomLower(12);
		}
		return cookieShopCart;
	}
	
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mv = super.handleRequestInternal(request, response);
		
		String hostUserId = request.getParameter("hostUserId");
		String isMultiHostMode = request.getParameter("isMultiHostMode");
		if ( StringUtils.isNotEmpty(hostUserId) )
		{
			UserModel hostUser = userService.getUserById(hostUserId);
			if (hostUser != null) 
			{
				mv.addObject("hostUser", hostUser);
			}
		}
		
		// get shopping cart summary.
		ShopCartModel cartSc = new ShopCartModel();
		cartSc.setCookieId( getCookieShopCart(request) );
		
		if ( isMobileClient() )
		{
			if ( ! "true".equals(isMultiHostMode) )
			{
				// if hostUserId is not equal to own user id.
				if (hostUserId != null && loginUser != null && ! hostUserId.equals(loginUser.getUserId())) 
				{
					cartSc.setHostId(hostUserId);
				}
			}
		}
		else
		{
			cartSc.setHostId(hostUserId);
		}
		
		Integer totalAmt = userPageService.getQtyTotalInCart(cartSc);
		mv.addObject("shopCartNum", totalAmt==null? 0 : totalAmt);
		
		// set the cookie value.
		mv.addObject("ckEosCartVal", getCookieShopCart(request));
		
		if (isMobileClient() && ! isPost(request))
		{
			mv.addObject("isMultiHostMode", isMultiHostMode(request));
		}
		
		return mv;
	}
	
	/**
	 * Description	: Get the user item properties list.
	 * 		   NOTE : Singleton implementation per request. This var is setted to a NULL on initCmd function.
	 * @author 		: RKRK
	 * @param hostUserId
	 * @param custUserId
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public List<UserItemPropertyModel> getItemFieldList(String hostUserId, String custUserId) throws Exception
	{
		if ( itemFieldList == null) 
		{
			itemFieldList = new HashMap<String, List<UserItemPropertyModel>>();
		}
		
		if ( ! itemFieldList.containsKey(hostUserId) )
		{
			UserItemService  userItemService = (UserItemService) ApplicationSetting.getWebApplicationContext().getBean("userItemService");
			itemFieldList.put(hostUserId, userItemService.getFieldList(hostUserId, Constants.CONST_STATE_Y));
		}
		
		if ( itemFieldList.containsKey(hostUserId) )
		{
			return itemFieldList.get(hostUserId);
		}
		
		return new ArrayList<UserItemPropertyModel>();
	}
	
	
	/**
	 * Description	: get the host's items of the current buyer.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param scNew
	 * @return
	 * @throws Exception
	 * 2018
	 */
	private ModelAndView items(HttpServletRequest request, HttpServletResponse response, UserItemSModel scNew) throws Exception 
	{
		// for search model on session
		request.setAttribute(SC_ID_SESSION, SC_KEY_ITEMS_LIST);
		
		ModelAndView mv = null;
		UserItemSModel sc  =  new UserItemSModel();
		
		String next = request.getParameter("next");
		
		// This is a first visit?
		if (next == null) 
		{
			mv = new ModelAndView("userPage/itemType");
			
			// getting the search model from session
			sc  = (UserItemSModel)SearchModelUtil.getSearchModel(SC_KEY_ITEMS_LIST, sc, request);
			
			// reset the paging model
			PagingModel pager = sc.getPage();
			pager.setSidx("itemId");
			pager.setPage(1);
			sc.setPage(pager);
		}
		else
		{
			mv = new ModelAndView("userPage/itemTypeInner");
			
			// getting the search model from session
			sc  = (UserItemSModel)SearchModelUtil.getSearchModel(SC_KEY_ITEMS_LIST, sc, request);
			PagingModel pager = scNew.getPage();
			sc.getPage().setPage( pager.getPage() );
			sc.getPage().setSidx( pager.getSidx() );
			sc.getPage().setSord( pager.getSord() );
		}
		
		sc.getPage().setRows(9);
		
		String itemType1 = "class1";
		String itemType2 = "class2";
		
		if ( next == null && !isAjaxRequest(request) )
		{
			// get the itemType1List
			List<UserItemCategoryMdoel> itemType1List = userItemService.getItemClassesWithCountList(userId, itemType1, itemType2, null);
			
			if ( isMobileClient() )
			{
				List<UserItemCategoryMdoel> cat1List = new ArrayList<UserItemCategoryMdoel>();
				
				
				// Getting category1 list
				String selParent = sc.getCategory();
				
				for ( int i = 0; i < itemType1List.size(); )
				{
					UserItemCategoryMdoel m = itemType1List.get(i);
					Long 	totalCnt = Long.parseLong(m.getCnt());
					String 	pCatName = m.getParentCatName();
					
					// set the default cat1
					if ( StringUtils.isEmpty(selParent) && StringUtils.isNotEmpty(pCatName) )
					{
						selParent = pCatName;
					}
					
					cat1List.add(m);
					
					i++;
					int jStart = i;
					for ( int j = jStart; j < itemType1List.size(); j++ )
					{
						UserItemCategoryMdoel child = itemType1List.get(j);
						String cPCatName = child.getParentCatName();
						if (!( pCatName == cPCatName || (pCatName != null && pCatName.equals(cPCatName))) ) 
						{
							break;
						}
						i++;
					}
				}
				
				// Getting category2 list
				String selCat = sc.getCategory2();
				
				if ( selParent != null )
				{
					for ( int i = 0; i < itemType1List.size(); )
					{
						UserItemCategoryMdoel m = itemType1List.get(i);
						Long 	totalCnt = Long.parseLong(m.getCnt());
						String 	pCatName = m.getParentCatName();
						
						if (selParent.equals(pCatName) || (selParent.equals("-1") && StringUtils.isEmpty(pCatName)) )
						{
							int jStart = i;
							for ( int j = jStart; j < itemType1List.size(); j++ )
							{
								UserItemCategoryMdoel child = itemType1List.get(j);
								String cPCatName = child.getParentCatName();
								
								if ( !(selParent.equals(cPCatName) || (selParent.equals("-1") && StringUtils.isEmpty(cPCatName))) )
								{
									break;
								}
								
								//cat2List.add(child);
								
								if (StringUtils.isEmpty(selCat) && StringUtils.isNotEmpty(child.getCatName()))
								{
									selCat = child.getCatName();
									break;
								}
								i++;
							}
							break;
						} else {
							i++;
						}
					}
				}
				
				if ( StringUtils.isNotEmpty(selParent) ) 
				{
					sc.setCategory(selParent);
				}
					
				if ( StringUtils.isNotEmpty(selCat) ) 
				{
					sc.setCategory2(selCat);
				}
				
				mv.addObject("cat1List", cat1List);
				mv.addObject("itemType1List", JSONArray.fromObject(itemType1List));
			} 
			else
			{
				List<UserItemCategoryMdoel> uiCatList = new ArrayList<UserItemCategoryMdoel>();
				
				for ( int i = 0; i < itemType1List.size(); )
				{
					UserItemCategoryMdoel m = itemType1List.get(i);
					Long 	totalCnt = Long.parseLong(m.getCnt());
					String pCatName = m.getParentCatName();
					//if (pCatName == null) pCatName = "";
					
					// parent cat changed.
					m.setEmptyChildren();
					
					if ( StringUtils.isNotEmpty(pCatName) )
						m.addChild( new UserItemCategoryMdoel(m.getCatName(), totalCnt) );
					
					i++;
					int jStart = i;
					for ( int j = jStart; j < itemType1List.size(); j++ )
					{
						UserItemCategoryMdoel child = itemType1List.get(j);
						String cPCatName = child.getParentCatName();
						if (!( pCatName == cPCatName || (pCatName != null && pCatName.equals(cPCatName))) ) 
						{
							break;
						}
						m.addChild(child);
						totalCnt += Long.parseLong(child.getCnt());
						i++;
					}
					
					m.setCnt( String.valueOf(totalCnt) );
					m.setCatName(pCatName);
						
					uiCatList.add(m);
				}
				
				mv.addObject("uiCatListHtml", HTMLHelper.getItemCatHtml(uiCatList, 0));
			}
		}
		
		// Get Cookie for shop cart
		String cookieShopCart = getCookieShopCart(request);	
		
		// --- Getting the user items list.
		sc.setUserId(userId);
		
		if ( StringUtils.isEmpty(sc.getPage().getSidx()) )
		{
			sc.getPage().setSidx("itemId");
		}
		
		Integer totalCount = userItemService.countUserItemListInFront(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> list = userItemService.getUserItemListInFront(sc);
		
		// --- get the current carts list.
		List<ShopCartModel> shopCartList = userPageService.getShopCartList(cookieShopCart, null);
		
		// --- set the cart information to a product
		for ( UserItemModel userItem : list ) 
		{
			//Set Item Property Value
			getItemFieldValue(userItem.getUserId(), userItem);
		
			// get and set a cart qty.
			for (ShopCartModel shopCart : shopCartList)
			{
				if (shopCart.getItemId().equals(userItem.getItemId()) && userItem.getUserId().equals(shopCart.getHostId()) )
				{
					userItem.setCartQty(shopCart.getQty());
					break;
				}
			}
		}
		
		// set the next page var.
		int nextPage = sc.getPage().getNextPageNoWithNoGroup();
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		mv.addObject("nextPage", nextPage);
		
		mv.addObject("ckEosCartVal", cookieShopCart);
		
		if (nextPage == 0) 
		{
			mv.addObject("nextUrl", "");
		}
		else
		{
			mv.addObject("nextUrl", getCmdUrl("itemType&hostUserId=&next=1&page.page=" + nextPage));
		}
		
		return mv;
	}
	
	public ModelAndView viewItemGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		
		ModelAndView mv = new ModelAndView("userPage/viewItemGroup");
		
		String hostUserId = request.getParameter("hostUserId");
		String groupId	= request.getParameter("groupId");
		
		UserItemSModel sc  =  new UserItemSModel();
		sc.setUserId(hostUserId);
		sc.setGroupId(groupId);
		
		PagingModel pager = sc.getPage();
		pager.setSidx("itemId");
		pager.setPage(1);
		sc.setPage(pager);
			
		sc.getPage().setRows(10000);
		
		// getting the item properties
		String itemType1 	= "";		// item category
		String itemType2 	= "";		// item sub-category
		String itemPackageCol 	= "";		// item property of items count a package.
		String itemNameCol 	= "";		// item property of item name.
		String itemUnitCol 	= "";		// item property of item unit.
		
		List<UserItemPropertyModel> ipList = getItemFieldList(hostUserId, null);
		for (UserItemPropertyModel item : ipList)
		{
			if ( Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemType1 = item.getPropertyName();
			}
			
			if ( Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemType2 = item.getPropertyName();
			}
			
			if ( Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemPackageCol = item.getPropertyName();
			}
			if ( Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemNameCol = item.getPropertyName();
			}
			if ( Constants.CONST_ITEM_SALE_UNIT_CODE.equals(item.getPropertyTypeCd()) )
			{
				itemUnitCol = item.getPropertyName();
			}
		}
		
		mv.addObject("itemType1", 	itemType1);
		mv.addObject("itemType2", 	itemType2);
		mv.addObject("itemPackage", itemPackageCol);
		mv.addObject("itemNameCol", itemNameCol);
		mv.addObject("itemUnitCol", itemUnitCol);
		
		// Get Cookie for shop cart
		String cookieShopCart = getCookieShopCart(request);	
		
		Integer totalCount = userItemService.countUserItemByGroupList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> itemList = userItemService.getUserItemByGroupList(sc);
		
		// --- get the current carts list.
		List<ShopCartModel> shopCartList = userPageService.getShopCartList(cookieShopCart, hostUserId);
		
		// --- set the cart information to a product
		for ( UserItemModel userItem : itemList) 
		{
			//Set Item Property Value
			
			getItemFieldValue(hostUserId, userItem);
		
			// get and set a cart qty.
			for (ShopCartModel shopCart : shopCartList)
			{
				if (shopCart.getItemId().equals(userItem.getItemId()) && userItem.getUserId().equals(shopCart.getHostId()) )
				{
					userItem.setCartQty(shopCart.getQty());
					break;
				}
			}
		}
		
		List<BizDataModel> groupList = bizDataService.getBizDataByBizCode(hostUserId, Constants.COSNT_BIZDATA_ITEMGROUP_CODE, groupId);
		
		mv.addObject("rows", itemList);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		mv.addObject("hostUserId", hostUserId);
		mv.addObject("ckEosCartVal", cookieShopCart);
		
		if (groupList != null && groupList.size() > 0){
			mv.addObject("itemGroup", groupList.get(0));
		}
		
		return mv;
	}
	
	public ModelAndView myMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = null;
		if (loginUser == null){
			mv = new ModelAndView("redirect:Login.do?cmd=loginForm");
			return mv;
		}
		mv = new ModelAndView("userPage/myMain");
		return mv;
	}
}
