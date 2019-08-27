
package com.kpc.eos.controller.bizSetting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.bizSetting.handler.UserItemRowHandler;
import com.kpc.eos.controller.userPage.UserPageController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.core.util.DateUtil;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.BizSettingSModel;
import com.kpc.eos.model.bizSetting.ItemWidgetModel;
import com.kpc.eos.model.bizSetting.StoreModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.common.UploadFile;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.CodeModel;
import com.kpc.eos.model.system.ItemModel;
import com.kpc.eos.model.userPage.CommentSModel;
import com.kpc.eos.model.userPage.ShopCartModel;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.bizSetting.ItemWidgetService;
import com.kpc.eos.service.bizSetting.StoreService;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.dataMng.UserService;
import com.kpc.eos.service.system.ItemService;
import com.kpc.eos.service.userPage.CommentService;
import com.kpc.eos.service.userPage.UserPageService;

public class UserItemController extends BaseEOSController {
	
	private UserPageService userPageService;
	private UserItemService userItemService;
	private BizSettingService bizSettingService;
	private StoreService storeService;
	private ItemService itemService;
	private BizDataService bizDataService;
	private ItemWidgetService itemWidgetService;
	private CommentService commentService;
	
	private UserService userService;
	
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("业务设置", "", false));
		breads.add(new BreadcrumbModel("商品资料", getCmdUrl("UserItem", "userItemList" ), true));
	}
	
	public void setUserItemService(UserItemService userItemService) {
		this.userItemService = userItemService;
	}
	
	public void setBizSettingService(BizSettingService bizSettingService){
		this.bizSettingService = bizSettingService;
	}
		
	public void setStoreService(StoreService storeService){
		this.storeService = storeService;
	}
	
	public void setCommentService(CommentService commentService){
		this.commentService = commentService;
	}
	
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	
	public void setItemWidgetService(ItemWidgetService itemWidgetService) {
		this.itemWidgetService = itemWidgetService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setUserPageService(UserPageService userPageService) 
	{
		this.userPageService = userPageService;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			userItemList
	* Function Description		Call the view for user item list
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView userItemList(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		initCmd();
		
		UserItemSModel sc = new UserItemSModel();
		
		String key = "UserItem_userItemList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(key, sc, request);
				
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(loginUser.getUserId(), "ST0001");
		
		sc.setUserId(loginUser.getUserId());
		
		BizSettingSModel bizSModel = new BizSettingSModel();
		bizSModel.setUserId(loginUser.getUserId());
		bizSModel.setSysCode(BizSetting.THIRD_VENDOR_MARK);
		
		List<BizSettingModel> bizSettingList = bizSettingService.getBizSettingList(bizSModel);
		
		Boolean isThirdVendor = false;
		
		if (bizSettingList != null && bizSettingList.size() > 0 &&  BizSetting.THIRD_VENDOR_MARK_Y.equals(bizSettingList.get(0).getSysValueName())){
			isThirdVendor = true;
		}
		
		List<String> propNamesList = new ArrayList<String>();
		Map<String, Object> colModel;
		List<Map<String,Object>> colModelList = new ArrayList<Map<String, Object>>();
		
		List<String> propTypeCdList = new ArrayList<String>();
		
		propNamesList.add("图片");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "itemSmallImg");
		colModel.put("formatter", "funcImg");
		colModel.put("align", "center");
		colModel.put("width", 120);
		colModel.put("sortable", false);
		colModelList.add(colModel);
		
		String categoryFieldName = null;
		
		if (fieldList == null || fieldList.size() == 0){
			
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("useritem.property.empty"), request);
			return redirect("UserItem", "userItemPropertyList");
		}
		
		for (int i = 0; i < fieldList.size(); i++){
			colModel = new HashMap<String, Object>();
			propNamesList.add(fieldList.get(i).getPropertyDesc());
			propTypeCdList.add(fieldList.get(i).getPropertyTypeCd());
			colModel.put("name", fieldList.get(i).getPropertyName());
			colModel.put("index", fieldList.get(i).getPropertyName());
			colModel.put("align", "center");
			colModel.put("sortable", true);
			colModelList.add(colModel);
			
			if (StringUtils.equals(fieldList.get(i).getPropertyTypeCd(), "PT0003")){
				categoryFieldName = fieldList.get(i).getPropertyName();
			}
		}
		if (isThirdVendor){
			propNamesList.add("供应商");
			colModel = new HashMap<String, Object>();
			colModel.put("name", "vendor");
			colModel.put("index", "vendor");
			colModel.put("align", "center");
			colModel.put("width", "200");
			colModelList.add(colModel);
		}
		propNamesList.add("按库存下单");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "stockMarkName");
		colModel.put("index", "stockMark");
		colModel.put("align", "center");
		colModelList.add(colModel);
		propNamesList.add("状态");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "stateName");
		colModel.put("index", "state");
		colModel.put("align", "center");
		colModelList.add(colModel);
		propNamesList.add("操作");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "control");
		colModel.put("align", "center");
		colModel.put("sortable", false);
		colModelList.add(colModel);
		
		ModelAndView mv = new ModelAndView("bizSetting/userItemList", "sc", sc);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("colNames", propNamesList);
		jsonObj.element("colModel", colModelList);
		jsonObj.element("properties", propTypeCdList);
		mv.addObject("jsonObj", jsonObj.toString());
		
		if (categoryFieldName != null && !StringUtils.equals(categoryFieldName, "")){
			List<String> itemCategoryList = userItemService.getItemCategoryList(loginUser.getUserId(), categoryFieldName);
			mv.addObject("categoryList", itemCategoryList);
		}
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			userItemGridAjax
	* Function Description		Retrieve the user item list according to user id
	*****************************************************************************************************************************/
	public ModelAndView userItemGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		
		request.setAttribute(SC_ID_SESSION, "UserItem_userItemList");
		
		sc.setUserId(loginUser.getUserId());
		
		List<UserItemPropertyModel> itemPropertyList = userItemService.getItemFieldPropertyByUser(loginUser.getUserId(), "PT0003");
		if (itemPropertyList != null && itemPropertyList.size() > 0){
			String categoryFieldName = itemPropertyList.get(0).getPropertyName();
			sc.setCatFieldName(categoryFieldName);
		}
		
		Integer totalCount = userItemService.countUserItemList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> itemList = userItemService.getUserItemList(sc);
		
		mv.addObject("rows", itemList);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			itemList
	* Function Description		Call the view for whole item list
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView itemList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initCmd();
		
		breads.add(new BreadcrumbModel("新增商品资料", "", true));
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		String userId = loginUser.getUserId();
		
		List<UserItemPropertyModel> namePropertyData = userItemService.getItemFieldPropertyByUser(userId, "PT0006");
		if (namePropertyData == null || namePropertyData.size() == 0){
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("useritem.property.noitemname"), request);
			return redirect("UserItem", "userItemPropertyList");
		}
		
		UserItemSModel sc = new UserItemSModel();
		
		String key = "UserItem_itemList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		List<String> itemClassList = itemService.getCategoryList(userId);
		
		ModelAndView mv = new ModelAndView("bizSetting/itemList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		mv.addObject("categoryList", itemClassList);
		
		return mv;
		
	}
	
	/****************************************************************************************************************************
	* Function Name:  			userItemGridAjax
	* Function Description		Retrieve the user item list according to user id
	*****************************************************************************************************************************/
	public ModelAndView itemGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "UserItem_itemList");
		
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		
		sc.setUserId(loginUser.getUserId());
		
		Integer totalCount = itemService.countItemList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<ItemModel> itemList = itemService.getItemList(sc);
		
		mv.addObject("rows", itemList);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveItems
	* Function Description		Save selected items to user items
	*****************************************************************************************************************************/
	public ModelAndView saveItems(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {
		
		String itemIds = request.getParameter("selectedIds");
		
		if (itemIds == null || itemIds.equals("")){
			
			SysMsg.addMsg(SysMsg.WARNING, MessageUtil.getMessage("system.alert.noDataToSave"), request);
			
			return redirect("UserItem", "itemList");
		}
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		String empId = SessionUtil.getEmpId(request, getSystemName());
		
		String cat1 = null, cat2 = null, itemName = null, itemNo = null, unit = null, standard = null, manufacturer = null;
		
		List<UserItemPropertyModel> cat1PropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_TYPE1_CODE, false);
		if (cat1PropertyData != null && cat1PropertyData.size() > 0){
			cat1 = cat1PropertyData.get(0).getPropertyName();
		}
		
		List<UserItemPropertyModel> cat2PropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_TYPE2_CODE, false);
		if (cat2PropertyData != null && cat2PropertyData.size() > 0){
			cat2 = cat2PropertyData.get(0).getPropertyName();
		}
		
		List<UserItemPropertyModel> namePropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NAME_CODE, false);
		if (namePropertyData != null && namePropertyData.size() > 0){
			itemName = namePropertyData.get(0).getPropertyName();
		}
		
		List<UserItemPropertyModel> noPropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NUM_CODE, false);
		if (noPropertyData != null && noPropertyData.size() > 0){
			itemNo = noPropertyData.get(0).getPropertyName();
		}
		
		List<UserItemPropertyModel> unitPropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_SALE_UNIT_CODE, false);
		if (unitPropertyData != null && unitPropertyData.size() > 0){
			unit = unitPropertyData.get(0).getPropertyName();
		}
		
		List<UserItemPropertyModel> standardPropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_STANDARD_CODE, false);
		if (standardPropertyData != null && standardPropertyData.size() > 0){
			standard = standardPropertyData.get(0).getPropertyName();
		}
		
		List<UserItemPropertyModel> manufacturerPropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_MANUFACTURER_CODE, false);
		if (manufacturerPropertyData != null && manufacturerPropertyData.size() > 0){
			manufacturer = manufacturerPropertyData.get(0).getPropertyName();
		}
		 
		String[] itemIdArray = itemIds.split(",");
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("itemIds", itemIdArray);
		map.put("itemName", itemName);
		map.put("cat1", cat1);
		map.put("cat2", cat2);
		map.put("itemNo", itemNo);
		map.put("unit", unit);
		map.put("standard", standard);
		map.put("manufacturer", manufacturer);
		map.put("empId", empId);
		userItemService.saveItems(map);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("UserItem", "userItemList");
	}
	
	/****************************************************************************************************************************
	* Function Name:  			userItemForm
	* Function Description		Get the user item information according to item id
	*****************************************************************************************************************************/
	public ModelAndView userItemForm(HttpServletRequest request, HttpServletResponse response, UserItemModel userItemModel) throws Exception {
		
		initCmd();
		
		ModelAndView mv = new ModelAndView("bizSetting/userItemForm");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(loginUser.getUserId(), "ST0001");
		
		// The part related with third party setting.
		/*
		BizSettingSModel bizSModel = new BizSettingSModel();
		bizSModel.setUserId(loginUser.getUserId());
		bizSModel.setSysCode("1007");
		
		List<BizSettingModel> bizSettingList = bizSettingService.getBizSettingList(bizSModel);
		
		Boolean isThirdVendor = false;
		
		if (bizSettingList != null && bizSettingList.size() > 0 && StringUtils.equals(bizSettingList.get(0).getSysValueName(), "SYSD00000000060")){
			isThirdVendor = true;
		}
		
		mv.addObject("isThirdVendor", isThirdVendor);
		
		if (isThirdVendor){
			List<HostCustModel> hostList = hostCustService.getHostList(loginUser.getUserId());
			mv.addObject("isThirdVendor", true);
			mv.addObject("hostList", hostList);
		}
		
		bizSModel.setSysCode("1008");
		
		bizSettingList = bizSettingService.getBizSettingList(bizSModel);
		
		Boolean isVendorGroup = false;
		
		if (bizSettingList != null && bizSettingList.size() > 0 && StringUtils.equals(bizSettingList.get(0).getSysValueName(), "SYSD00000000062")){
			isVendorGroup = true;
		}
		
		mv.addObject("isVendorGroup", isVendorGroup);
		*/
		
		String itemId = request.getParameter( "itemId" );
		
		UserItemModel userItem = new UserItemModel();
		if (itemId != null) 
		{
			userItem = userItemService.getUserItem(loginUser.getUserId(), itemId);
			
		}else{
			return redirect("UserItem", "userItemList");
		}
		breads.add( new BreadcrumbModel("修改商品资料", "", true) );
		
		List<StoreModel> storeList = storeService.getAllStoreList(loginUser.getUserId());
		
		String width 	= ApplicationSetting.getConfig(FileUtil.UPLOAD_PREFIX + "useritem" + FileUtil.UPLOAD_MINWIDTH);
		String height 	= ApplicationSetting.getConfig(FileUtil.UPLOAD_PREFIX + "useritem" + FileUtil.UPLOAD_MINHEIGHT);
		
		if (userItemModel != null && isPost(request)) {
			userItem = userItemModel;
		}
		
		mv.addObject("userItem", userItem);
		mv.addObject("fieldList", fieldList);
		mv.addObject("storeList", storeList);
		mv.addObject("sc", null);
		mv.addObject("minWidth", width);
		mv.addObject("minHeight", height);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveUserItem
	* Function Description		Save the user item information.
	*****************************************************************************************************************************/
	public ModelAndView saveUserItem(HttpServletRequest request, HttpServletResponse response, UserItemModel userItem) throws Exception {
		
		//Check if the user item no is duplicated or not
		List<UserItemPropertyModel> itemPropertyList = userItemService.getItemFieldPropertyByUser(userItem.getUserId(), Constants.CONST_ITEM_NUM_CODE);
		String itemNoField = "";
		if (itemPropertyList != null && itemPropertyList.size() > 0){
			itemNoField = itemPropertyList.get(0).getPropertyName();
		}
		
		if (StringUtils.isNotEmpty(itemNoField)){
			Integer exists = userItemService.existsUserItemNo(userItem, itemNoField);
			if (exists != null)
			{
				formErrors = userItem.validate();
				ModelAndView mv = new ModelAndView();
				formErrors.rejectValue(itemNoField, "useritem.itemno.duplicated", new Object[]{userItem.get(itemNoField)}, null);
				mv = userItemForm(request, response, userItem);
				return mv;
			}
		}
		
		//ModelAndView mv = new ModelAndView("bizSetting/userItemList");
		String now = DateUtil.getToday("yyyyMMddHHmmss");
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		String empId = loginUser.getEmpId();
		String tempId = empId.replaceFirst("EMPL0+", "").replace("EMPL", "");
		String fileName = tempId.concat(now);
		String tempFileName = fileName.concat("_temp");
		
		int offsetX = StringUtils.isNotEmpty(request.getParameter("cropX"))?Integer.parseInt(request.getParameter("cropX")):0;
		int offsetY = StringUtils.isNotEmpty(request.getParameter("cropY"))?Integer.parseInt(request.getParameter("cropY")):0;
		int cropW = StringUtils.isNotEmpty(request.getParameter("cropW"))?Integer.parseInt(request.getParameter("cropW")):0;
		int cropH = StringUtils.isNotEmpty(request.getParameter("cropH"))?Integer.parseInt(request.getParameter("cropH")):0;
		
		String imgFileExist = request.getParameter("imgFile");
		if (imgFileExist == null){
			UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request
					, "useritem"//request.getParameter("module")
					, "imgFile"
					, ""
					, tempFileName);
			if(uploadedFile != null){
				if (Constants.RESULT_FAILURE.equals(uploadedFile.getResultCode())){
					SysMsg.addMsg(SysMsg.ERROR, uploadedFile.getResultMsg(), request);
					ModelAndView mv = new ModelAndView();
					mv = userItemForm(request, response, userItem);
					return mv;
				}
				
				String originFileName = userItem.getItemImgPath();
				if (StringUtils.isNotEmpty(originFileName)){
					FileUtil.deleteFile("useritem", originFileName);
				}
				
				String uploadedFileName = uploadedFile.getSysFileName();
				String ext = uploadedFileName.substring(uploadedFileName.lastIndexOf(".") + 1).toLowerCase();
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName,"", -1, 500, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName, "medium", 300, 200, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName, "small", 120, 80, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName + "@2x", "small", 120, 80, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.deleteFile("useritem", uploadedFile.getSysFileName());
				
				userItem.setItemImgPath(fileName + FileUtil.EXT_DOT + ext);
			}else{
				String originFileName = userItem.getItemImgPath();
				
				if (StringUtils.isNotEmpty(originFileName)){
					String originFileFullPath = FileUtil.getModulePath("useritem") + "/" + originFileName;
					String fileExceptExt = originFileName.substring(0, originFileName.lastIndexOf("."));
					String ext = originFileName.substring(originFileName.lastIndexOf(".") + 1).toLowerCase();
					String tempName = fileExceptExt + "_temp" + FileUtil.EXT_DOT + ext;
					String destFileFullPath = FileUtil.getModulePath("useritem") + "/" + tempName;
					File srcFile = new File(originFileFullPath);
					FileUtil.copy(srcFile, destFileFullPath);
					
					FileUtil.deleteFile("useritem", originFileName);
					
					FileUtil.resizeWithCrop("useritem", tempName, fileExceptExt,"", -1, 500, offsetX, offsetY, cropW, cropH, 300, 0);
					
					FileUtil.resizeWithCrop("useritem", tempName, fileExceptExt, "medium", 300, 200, offsetX, offsetY, cropW, cropH, 300, 0);
					
					FileUtil.resizeWithCrop("useritem", tempName, fileExceptExt, "small", 120, 80, offsetX, offsetY, cropW, cropH, 300, 0);
					
					FileUtil.resizeWithCrop("useritem", tempName, fileExceptExt + "@2x", "small", 120, 80, offsetX, offsetY, cropW, cropH, 300, 0);
					
					FileUtil.deleteFile("useritem", tempName);
					
					//userItem.setItemImgPath(fileName + FileUtil.EXT_DOT + ext);
				}
			}
		}else{
			String originFileName = userItem.getItemImgPath();
			if (!originFileName.equals("")){
				FileUtil.deleteFile("useritem", originFileName);
			}
			userItem.setItemImgPath("");
		}
		
		userItemService.saveUserItem(userItem);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("UserItem", "userItemList");
	}
	
	/****************************************************************************************************************************
	* Function Name:  			userItemPropertyList 
	* Function Description		Call the view for user item list
	*****************************************************************************************************************************/
	public ModelAndView userItemPropertyList(HttpServletRequest request, HttpServletResponse response, UserItemModel userItem) throws Exception {
		initCmd();
		ModelAndView mv = new ModelAndView("bizSetting/userItemPropertyList");
		breads.add(new BreadcrumbModel("商品资料设置", "", false));
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			userItemPropertyGridAjax
	* Function Description		Get Item Property List using Ajax
	*****************************************************************************************************************************/
	@SuppressWarnings("unchecked")
	public ModelAndView userItemPropertyGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		
		List<UserItemPropertyModel> itemPropertyList = userItemService.getFieldList(loginUser.getUserId(), null);
		
		mv.addObject("rows", itemPropertyList);
		
		List<CodeModel> typeCodeList = CodeUtil.getCodeList("PT0000");
		
		sc.setUserId(loginUser.getUserId());
		Integer totalCount = userItemService.countUserItemList(sc);
		if (totalCount > 0){
			mv.addObject("itemExist", true);
		}
		String strType = ":请选择";
		
		if (typeCodeList != null && typeCodeList.size() > 0){
			for (int i=0; i < typeCodeList.size(); i++){
				if (totalCount > 0){
					if (Constants.CONST_ITEM_PRICE_CODE.equals(typeCodeList.get(i).getCodeId()) 
							|| Constants.CONST_ITEM_PACKAGE_MARK_CODE.equals(typeCodeList.get(i).getCodeId())){
						strType = strType.concat(";");
						strType = strType.concat(typeCodeList.get(i).getCodeId());
						strType = strType.concat(":");
						strType = strType.concat(typeCodeList.get(i).getCodeName());
					}
				}else{
					strType = strType.concat(";");
					strType = strType.concat(typeCodeList.get(i).getCodeId());
					strType = strType.concat(":");
					strType = strType.concat(typeCodeList.get(i).getCodeName());
				}
			}
		}
		
		mv.addObject("typeList", strType);
		
		return mv;
	}
	
	public ModelAndView saveUserItemProperty(HttpServletRequest request, HttpServletResponse response, UserItemPropertyModel sc) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:UserItem.do?cmd=userItemList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		userItemService.saveUserItemProperty(sc);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			groupItemList
	* Function Description		Call the view for group item list
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView groupItemList(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		ModelAndView mv = new ModelAndView("bizSetting/groupItemList");
		
		UserItemSModel sc = new UserItemSModel();
		String key = "UserItem_groupItemList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		String chelp = (String) SessionUtil.getData(request, "chelp");
		sc.setChelp(chelp);
		
		List<BizDataModel> groupList = bizDataService.getBizDataByBizCode(userId, Constants.COSNT_BIZDATA_ITEMGROUP_CODE, null);
		
		if (groupList == null || groupList.size() == 0){
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("useritem.group.empty"), request);
			
			return redirect("UserItem", "userItemList");
		}
		
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(userId, Constants.CONST_STATE_Y);
		
		List<String> propTypeCdList = new ArrayList<String>();
		List<String> propNamesList = new ArrayList<String>();
		Map<String, Object> colModel;
		List<Map<String,Object>> colModelList = new ArrayList<Map<String, Object>>();
		
		propNamesList.add("图片");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "itemSmallImg");
		colModel.put("formatter", "funcImg");
		colModel.put("align", "center");
		colModel.put("width", 100);
		colModel.put("sortable", false);
		colModelList.add(colModel);
		
		for (int i = 0; i < fieldList.size(); i++){
			colModel = new HashMap<String, Object>();
			propTypeCdList.add(fieldList.get(i).getPropertyTypeCd());
			propNamesList.add(fieldList.get(i).getPropertyDesc());
			colModel.put("name", fieldList.get(i).getPropertyName());
			colModel.put("index", fieldList.get(i).getPropertyName());
			colModel.put("align", "center");
			colModel.put("sortable", true);
			colModelList.add(colModel);
		}
		
		propNamesList.add("状态");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "stateName");
		colModel.put("index", "state");
		colModel.put("align", "center");
		colModelList.add(colModel);
		propNamesList.add("操作");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "control");
		colModel.put("align", "center");
		colModel.put("sortable", false);
		colModelList.add(colModel);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("colNames", propNamesList);
		jsonObj.element("colModel", colModelList);
		jsonObj.element("properties", propTypeCdList);
		
		initCmd();
		
		breads.add(new BreadcrumbModel("商品分组", "", false));
		
		mv.addObject("page", sc.getPage());
		mv.addObject("jsonObj", jsonObj.toString());
		mv.addObject("groupList", groupList);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			groupItemGridAjax
	* Function Description		Retrieve the user item list of group
	*****************************************************************************************************************************/
	public ModelAndView groupItemGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "UserItem_groupItemList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		SessionUtil.setData(request, "groupId", sc.getGroupId());
		
		Integer totalCount = userItemService.countUserItemByGroupList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> itemList = userItemService.getUserItemByGroupList(sc);
		
		mv.addObject("rows", itemList);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			searchUserItemList
	* Function Description		Call the view for item list
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView searchUserItemList(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {	
		
		initCmd();
		
		breads.add(new BreadcrumbModel("商品分组", getCmdUrl("UserItem", "groupItemList" ), false));
		breads.add(new BreadcrumbModel("添加分组商品", "", false));
		
		ModelAndView mv = new ModelAndView("bizSetting/searchUserItemList");
		
		String key = "UserItem_searchUserItemList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		String groupId = request.getParameter("group");
		String chelp = request.getParameter("chelp");
		sc.setGroupId(groupId);
		sc.setChelp(chelp);
		
		SessionUtil.setData(request, "chelp", chelp);
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(userId, Constants.CONST_STATE_Y);
		
		List<BizDataModel> groupList = bizDataService.getBizDataByBizCode(userId, Constants.COSNT_BIZDATA_ITEMGROUP_CODE, sc.getGroupId());
		
		List<String> propNamesList = new ArrayList<String>();
		Map<String, Object> colModel;
		List<Map<String,Object>> colModelList = new ArrayList<Map<String, Object>>();
		
		propNamesList.add("图片");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "itemSmallImg");
		colModel.put("formatter", "funcImg");
		colModel.put("align", "center");
		colModel.put("width", 100);
		colModel.put("sortable", false);
		colModelList.add(colModel);
		
		String categoryFieldName = null;
		
		for (int i = 0; i < fieldList.size(); i++){
			colModel = new HashMap<String, Object>();
			propNamesList.add(fieldList.get(i).getPropertyDesc());
			colModel.put("name", fieldList.get(i).getPropertyName());
			colModel.put("index", fieldList.get(i).getPropertyName());
			colModel.put("align", "center");
			colModel.put("sortable", true);
			colModelList.add(colModel);
			
			if (StringUtils.equals(fieldList.get(i).getPropertyTypeCd(), "PT0003")){
				categoryFieldName = fieldList.get(i).getPropertyName();
			}
		}
		
		propNamesList.add("状态");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "stateName");
		colModel.put("index", "state");
		colModel.put("align", "center");
		colModelList.add(colModel);
		
		propNamesList.add("itemId");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "itemId");
		colModel.put("key", true);
		colModel.put("hidden", true);
		colModelList.add(colModel);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("colNames", propNamesList);
		jsonObj.element("colModel", colModelList);
		
		mv.addObject("jsonObj", jsonObj.toString());
		
		List<String> itemCategoryList = userItemService.getItemCategoryList(userId, categoryFieldName);
		
		mv.addObject("categoryList", itemCategoryList);
		mv.addObject("groupName", groupList.get(0).getC1());
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			groupItemGridAjax
	* Function Description		Retrieve the user item list of group
	*****************************************************************************************************************************/
	public ModelAndView searchUserItemGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		request.setAttribute(SC_ID_SESSION, "UserItem_searchUserItemList");
		
		sc.setUserId(userId);
		SessionUtil.setData(request, "chelp", sc.getChelp());
		
		List<UserItemPropertyModel> itemPropertyList = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_TYPE1_CODE);
		String categoryFieldName = itemPropertyList.get(0).getPropertyName();
		
		sc.setCatFieldName(categoryFieldName);
		
		Integer totalCount = userItemService.countUserItemExceptGroupList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> itemList = userItemService.getUserItemExceptGroupList(sc);
		
		mv.addObject("rows", itemList);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveGroupItems
	* Function Description		Add user items to item group
	*****************************************************************************************************************************/
	public ModelAndView saveGroupItems(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {
		
		String itemIds = request.getParameter("selectedIds");
		
		if (itemIds == null || itemIds.equals("")){
			
			SysMsg.addMsg(SysMsg.WARNING, MessageUtil.getMessage("system.alert.noDataToSave"), request);
			
			return redirect("UserItem", "searchUserItemList");
		}
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		String groupId = (String)SessionUtil.getData(request, "groupId");
		
		String[] itemIdArray = itemIds.split(",");
		
		userItemService.saveGroupItems(userId, itemIdArray, groupId);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("UserItem", "groupItemList");
	}
	
	/****************************************************************************************************************************
	* Function Name:  			deleteGroupItemAjax
	* Function Description		Retrieve the user item list of group
	*****************************************************************************************************************************/
	public ModelAndView deleteGroupItemAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		String itemId = request.getParameter("itemId");
		String groupId = request.getParameter("groupId");
		
		userItemService.deleteGroupItem(userId, itemId, groupId);
		
		ResultModel resultModel = new ResultModel();
		resultModel.setResultMsg(MessageUtil.getMessage("system.alert.delete"));
		resultModel.setResultCode(0);
		return new ModelAndView("jsonView", "result", resultModel);
	}
	
	/****************************************************************************************************************************
	* Function Name:  			setItemPage
	* Function Description		Call the view for Item Detail  Setting Page
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView setItemPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("bizSetting/setItemPage");
		
		initCmd();
		breads.add(new BreadcrumbModel("设置商品详情", "", false));
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		String itemId = request.getParameter("itemId");
		String isPreview = request.getParameter("isPreview");
		
		ItemWidgetModel sc = new ItemWidgetModel();
		sc.setUserId(userId);
		sc.setItemId(itemId);
		
		List<ItemWidgetModel> itemWidgetList = itemWidgetService.getUserItemWidgetList(sc);
		mv.addObject("itemWidgetList", itemWidgetList);
		
		sc.setWidgetType("WT0001");
		List<ItemWidgetModel> itemSlideImgList = itemWidgetService.getUserItemWidgetList(sc);
		mv.addObject("itemSlideImgList", itemSlideImgList);
		
		List<UserItemPropertyModel> itemPropertyList = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NUM_CODE);
		String itemNoField = itemPropertyList.get(0).getPropertyName();
		
		List<UserItemPropertyModel> itemPropertyList1 = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NAME_CODE);
		String itemNameField = itemPropertyList1.get(0).getPropertyName();
		
		UserItemModel item = userItemService.getUserItem(userId, itemId);
		
		mv.addObject("itemNo", item.get(itemNoField));
		mv.addObject("itemName", item.get(itemNameField));
		mv.addObject("itemId", itemId);
		if (StringUtils.isNotEmpty(isPreview)){
			mv.addObject("isPreview", true);
		}else{
			mv.addObject("isPreview", false);
		}
		mv.addObject("hostUserId", userId);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveItemPage
	* Function Description		Call the view for Item Detail Page
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView saveItemPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		String itemId = request.getParameter("itemId");
		
		String now = DateUtil.getToday("yyyyMMddHHmmss");
		String empId = SessionUtil.getEmpId(request, getSystemName());
		String tempId = empId.replaceFirst("EMPL0+", "").replace("EMPL", "");
		String fileName = tempId.concat(now);
		
		String[] lineNum = request.getParameterValues("line[]");
		String[] widgetType = request.getParameterValues("type[]");
		
		List<ItemWidgetModel> saveList = new ArrayList<ItemWidgetModel>();
		List<String> saveImgList = new ArrayList<String>();
		
		for (int i=0; i<lineNum.length; i++){
			if (widgetType[i].equals("WT0002")){
				ItemWidgetModel model = new ItemWidgetModel();
				model.setUserId(userId);
				model.setItemId(itemId);
				model.setLineNo(Integer.toString(i));
				model.setWidgetType("WT0002");
				String content = request.getParameter("content[" + i + "]").replaceAll("\r\n", "");
				model.setWidgetContent(content);
				model.setColNo("0");
				model.setState("ST0001");
				saveList.add(model);
			}else{
				int imgLineNo = Integer.parseInt(lineNum[i]);
				for (int imgInx = 0; imgInx < 4; imgInx++){
					String imgFileExist = request.getParameter("imgFile[" + imgLineNo + "][" + imgInx + "]");
					if (imgFileExist == null){
						ItemWidgetModel sc = new ItemWidgetModel();
						sc.setUserId(userId);
						sc.setItemId(itemId);
						sc.setLineNo(lineNum[i]);
						sc.setColNo(Integer.toString(imgInx));
						sc.setWidgetType(widgetType[i]);
						List<ItemWidgetModel> itemWidgetList = itemWidgetService.getUserItemWidgetList(sc);
						
						String imgFileName = fileName + Integer.toString(i) + "_" + Integer.toString(imgInx);
						UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request
								, "itemwidget"//request.getParameter("module")
								, "imgFile[" + imgLineNo + "][" + imgInx + "]"
								, ""
								, imgFileName);
						if(uploadedFile == null){
							if (itemWidgetList != null && itemWidgetList.size() > 0){
								ItemWidgetModel model = new ItemWidgetModel();
								model.setUserId(userId);
								model.setItemId(itemId);
								model.setWidgetType(widgetType[i]);
								model.setLineNo(Integer.toString(i));
								model.setColNo(Integer.toString(imgInx));
								model.setWidgetContent(itemWidgetList.get(0).getWidgetContent());
								saveImgList.add(itemWidgetList.get(0).getWidgetContent());
								model.setState("ST0001");
								saveList.add(model);
							}
						}else{
							if (itemWidgetList != null && itemWidgetList.size() > 0){
								String originFileName = itemWidgetList.get(0).getWidgetContent();
								if (!originFileName.equals("")){
									FileUtil.deleteFile("itemwidget", originFileName);
								}
							}
							ItemWidgetModel model = new ItemWidgetModel();
							model.setUserId(userId);
							model.setItemId(itemId);
							model.setWidgetType(widgetType[i]);
							model.setLineNo(Integer.toString(i));
							model.setColNo(Integer.toString(imgInx));
							model.setState("ST0001");
							model.setWidgetContent(uploadedFile.getSysFileName());
							saveList.add(model);
						}
					}
				}
			}
		}
		
		//  ok process savelist
		List<String> deleteImgList = itemWidgetService.getDeleteImageList(userId, itemId, saveImgList);
		if (deleteImgList != null && deleteImgList.size() > 0){
			for (int i = 0; i<deleteImgList.size(); i++){
				FileUtil.deleteFile("itemwidget", deleteImgList.get(i));
			}
		}
		itemWidgetService.SaveItemWidget(saveList, userId, itemId);
		
		
		ModelAndView mv = new ModelAndView("redirect:UserItem.do?cmd=setItemPage&itemId=" + itemId);
		if (request.getParameter("isPreview").equals("true")){
			mv.addObject("isPreview", true);
			return mv;//redirect("UserItem", "viewItemPage", map);
		}else{
			SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
			return mv;//redirect("UserItem", "setItemPage", map);
		}
	}
	
	/****************************************************************************************************************************
	* Function Name:  			viewItemPage
	* Function Description		Call the view for Item Detail Page
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView viewItemPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("userPage/viewItemPage");
		
		//String userId = SessionUtil.getUserId(request, getSystemName());
		String userId = request.getParameter("hostUserId");
		String itemId = request.getParameter("itemId");
		String isPreview = request.getParameter("isPreview");
		
		ItemWidgetModel sc = new ItemWidgetModel();
		sc.setUserId(userId);
		sc.setItemId(itemId);
		
		List<ItemWidgetModel> itemWidgetList = itemWidgetService.getUserItemWidgetList(sc);
		mv.addObject("itemWidgetList", itemWidgetList);
		
		sc.setWidgetType("WT0001");
		List<ItemWidgetModel> itemSlideImgList = itemWidgetService.getUserItemWidgetList(sc);
		mv.addObject("itemSlideImgList", itemSlideImgList);
		
		/*ItemCommentModel itemSc = new ItemCommentModel();
		itemSc.setUserId(userId);
		itemSc.setItemId(itemId);
		List<ItemCommentModel> itemCommentList = itemCommentService.getUserItemCommentList(itemSc);*/
		
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(userId, "ST0001");
		
		UserItemModel item = userItemService.getUserItem(userId, itemId);
		
		List<UserItemPropertyModel> itemPropertyList1 = userItemService.getItemFieldPropertyByUser(userId, "PT0006");
		
		String itemNameField = null;
		if (itemPropertyList1 != null && itemPropertyList1.size() > 0){
			itemNameField = itemPropertyList1.get(0).getPropertyName();
		}
		
		// Get Comment Count
		CommentSModel cs = new CommentSModel();
		cs.setHostId( userId );
		cs.setItemId( itemId );
		cs.setCommentType( Constants.COMMENT_TYPE_PRODUCT );
		Integer commentCount = commentService.getCommentCount(cs);
		UserModel hostUser = userService.getUserById(userId);
		
		mv.addObject("item", item);
		mv.addObject("itemId", itemId);
		if (StringUtils.isNotEmpty(itemNameField)){
			mv.addObject("itemName", item.get(itemNameField));
		}
		mv.addObject("fieldList", fieldList);
		//mv.addObject("commentList", itemCommentList);
		
		mv.addObject("hostUser", hostUser);
		mv.addObject("commentCount", commentCount);
		mv.addObject("commentType", Constants.COMMENT_TYPE_PRODUCT);
		
		// Shoppingcart section here.
		// set the cookie value.
		String cookieId = UserPageController.getCookieShopCart(request);
		mv.addObject("ckEosCartVal", cookieId);
		
		// --- get the current carts list.
		// get shop cart qty
		ShopCartModel scCartModel = new ShopCartModel();
		scCartModel.setCookieId(cookieId);
		scCartModel.setHostId(hostUser.getUserId());
		scCartModel.setItemId(itemId);
		ShopCartModel shopCart = userPageService.getShopCart(scCartModel);
		
		// get shopping cart summary.
		ShopCartModel cartSc = new ShopCartModel();
		cartSc.setHostId(hostUser.getUserId());
		cartSc.setCookieId( cookieId );
		Integer totalAmt = userPageService.getQtyTotalInCart(cartSc);
		mv.addObject("shopCartNum", totalAmt);
		mv.addObject("qty", shopCart != null? shopCart.getQty() : "");
		
		if (StringUtils.isNotEmpty(isPreview) && StringUtils.equals(isPreview, "true")){
			mv.addObject("pageTitle", "预看商品详情");
		}else{
			mv.addObject("pageTitle", "商品详情");
		}
		
		return mv;
	}	
	
	public ModelAndView downloadUserItemList(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		String userId = loginUser.getUserId();
		List<UserItemPropertyModel> itemPropertyList = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_TYPE1_CODE);
		String categoryFieldName = itemPropertyList.get(0).getPropertyName();
		
		sc.setCatFieldName(categoryFieldName);
		sc.setUserId(userId);
		
		Map<String, String> header = new LinkedHashMap<String, String>();
		
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(loginUser.getUserId(), "ST0001");
		
		for (int i = 0; i < fieldList.size(); i++){
			header.put(fieldList.get(i).getPropertyDesc(), fieldList.get(i).getPropertyName());
		}
		header.put("库存", "stockQty");
		header.put("状态", "stateName");
		
		ModelAndView mv = new ModelAndView("excelView");
		UserItemRowHandler handler = new UserItemRowHandler("商品资料", header);
		userItemService.downloadUserItemList(sc, handler);
		mv.addObject(Constants.REQUEST_WORKBOOK_KEY, handler.getWorkbook());
		mv.addObject("fileName", "商品资料-" + loginUser.getUserNo() + ".xls");
		
		return mv;
	}
	
	public ModelAndView saveUserItemPropertyForMobile(HttpServletRequest request, HttpServletResponse response, UserItemPropertyModel model)throws Exception {
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		model.setUserId(userId);
		model.setUpdateBy(SessionUtil.getEmpId(request, getSystemName()));
		
		if (Constants.CONST_ITEM_PRICE_CODE.equals(model.getPropertyTypeCd()) && Constants.CONST_Y.equals(model.getPropertyValue())
				&& Constants.CONST_STATE_Y.equals(model.getState())){
			List<UserItemPropertyModel> defaultPriceList = userItemService.getDefaultPriceField(model);
			
			if (defaultPriceList != null && defaultPriceList.size() > 0){
				return new ModelAndView("jsonView", "result", new ResultModel(ResultModel.RESULT_FAIL_CODE, MessageUtil.getMessage("useritem.property.duplicateprice")));
			}
		}
		if (!Constants.CONST_ITEM_PRICE_CODE.equals(model.getPropertyTypeCd()) && StringUtils.isNotEmpty(model.getPropertyTypeCd()) ){
			List<UserItemPropertyModel> currentFieldList = userItemService.getItemFieldPropertyByUser(userId, model.getPropertyTypeCd(), false);
			
			if (currentFieldList != null && currentFieldList.size() > 0 && !currentFieldList.get(0).getPropertyName().equals(model.getPropertyName())){
				return new ModelAndView("jsonView", "result", new ResultModel(ResultModel.RESULT_FAIL_CODE, MessageUtil.getMessage("useritem.property.duplicated")));
			}
		}
		
		List<UserItemPropertyModel> itemNoFieldList = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NUM_CODE, false);
		List<UserItemPropertyModel> itemNameFieldList = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NAME_CODE);
		
		if ((itemNoFieldList == null || itemNoFieldList.size() == 0) && !Constants.CONST_ITEM_NUM_CODE.equals(model.getPropertyTypeCd())){
			return new ModelAndView("jsonView", "result", new ResultModel(ResultModel.RESULT_FAIL_CODE, MessageUtil.getMessage("useritem.property.nonameommitted")));
		}
		
		if ((itemNameFieldList == null || itemNameFieldList.size() == 0) 
				&& !(Constants.CONST_ITEM_NAME_CODE.equals(model.getPropertyTypeCd()) 
						&& Constants.CONST_STATE_Y.equals(model.getState()))
			 || (itemNameFieldList != null && itemNameFieldList.size() > 0 
				  && !(Constants.CONST_ITEM_NAME_CODE.equals(model.getPropertyTypeCd()) 
							&& Constants.CONST_STATE_Y.equals(model.getState()))
				  && itemNameFieldList.get(0).getPropertyName().equals(model.getPropertyName())
				)){
			return new ModelAndView("jsonView", "result", new ResultModel(ResultModel.RESULT_FAIL_CODE, MessageUtil.getMessage("useritem.property.nonameommitted")));
		}
		
		userItemService.saveUserItemPropertyModel(model);
		
		return new ModelAndView("jsonView", "result", new ResultModel());
	}
	
	/****************************************************************************************************************************
	* Function Name:  			searchUserItemListForMobile
	* Function Description		Call the view for item list on Mobile
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView searchUserItemListForMobile(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {	
		
		initCmd();
		
		breads.add(new BreadcrumbModel("商品分组", getCmdUrl("UserItem", "groupItemList" ), false));
		breads.add(new BreadcrumbModel("添加分组商品", "", false));
		
		ModelAndView mv = new ModelAndView("bizSetting/searchUserItemList");
		
		String key = "UserItem_searchUserItemList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		String groupId = request.getParameter("group");
		String chelp = request.getParameter("chelp");
		sc.setGroupId(groupId);
		sc.setChelp(chelp);
		
		SessionUtil.setData(request, "chelp", chelp);
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(userId, "ST0001");
				
		List<BizDataModel> groupList = bizDataService.getBizDataByBizCode(userId, "BD0003", groupId);
		
		List<String> propNamesList = new ArrayList<String>();
		Map<String, Object> colModel;
		List<Map<String,Object>> colModelList = new ArrayList<Map<String, Object>>();
		
		propNamesList.add("图片");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "itemSmallImg");
		colModel.put("formatter", "funcImg");
		colModel.put("align", "center");
		colModel.put("width", 120);
		colModel.put("sortable", false);
		colModelList.add(colModel);
		
		String categoryFieldName = null;
		
		for (int i = 0; i < fieldList.size(); i++){
			
			if (Constants.CONST_ITEM_NAME_CODE.equals(fieldList.get(i).getPropertyTypeCd()) || 
				Constants.CONST_ITEM_TYPE1_CODE.equals(fieldList.get(i).getPropertyTypeCd()) ||	
				Constants.CONST_ITEM_TYPE2_CODE.equals(fieldList.get(i).getPropertyTypeCd())	){
				colModel = new HashMap<String, Object>();
				propNamesList.add(fieldList.get(i).getPropertyDesc());
				colModel.put("name", fieldList.get(i).getPropertyName());
				colModel.put("index", fieldList.get(i).getPropertyName());
				colModel.put("align", "center");
				colModel.put("sortable", true);
				if (Constants.CONST_ITEM_TYPE1_CODE.equals(fieldList.get(i).getPropertyTypeCd()) ||
					Constants.CONST_ITEM_TYPE2_CODE.equals(fieldList.get(i).getPropertyTypeCd()))
				{
					colModel.put("width", 80);
				}else{
					colModel.put("width", 150);
				}
				colModelList.add(colModel);
			}
			
			if (StringUtils.equals(fieldList.get(i).getPropertyTypeCd(), "PT0003")){
				categoryFieldName = fieldList.get(i).getPropertyName();
			}
		}
		
		propNamesList.add("状态");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "stateName");
		colModel.put("index", "state");
		colModel.put("align", "center");
		colModel.put("width", 60);
		colModelList.add(colModel);
		
		propNamesList.add("itemId");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "itemId");
		colModel.put("key", true);
		colModel.put("hidden", true);
		colModelList.add(colModel);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("colNames", propNamesList);
		jsonObj.element("colModel", colModelList);
		
		
		mv.addObject("jsonObj", jsonObj.toString());
		
		List<String> itemCategoryList = userItemService.getItemCategoryList(userId, categoryFieldName);
		
		mv.addObject("categoryList", itemCategoryList);
		mv.addObject("groupName", groupList.get(0).getC1());
		mv.addObject("chelp" , chelp);
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveItemPageForMobile
	* Function Description		Call the view for Item Detail Page and view Item Page on Mobile.
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView saveItemPageForMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		String itemId = request.getParameter("itemId");
		
		String now = DateUtil.getToday("yyyyMMddHHmmss");
		String empId = SessionUtil.getEmpId(request, getSystemName());
		String tempId = empId.replaceFirst("EMPL0+", "").replace("EMPL", "");
		String fileName = tempId.concat(now);
		
		String[] lineNum = request.getParameterValues("line[]");
		String[] widgetType = request.getParameterValues("type[]");
		
		List<ItemWidgetModel> saveList = new ArrayList<ItemWidgetModel>();
		List<String> saveImgList = new ArrayList<String>();
		
		for (int i=0; i<lineNum.length; i++){
			if (widgetType[i].equals("WT0002")){
				ItemWidgetModel model = new ItemWidgetModel();
				model.setUserId(userId);
				model.setItemId(itemId);
				model.setLineNo(Integer.toString(i));
				model.setWidgetType("WT0002");
				String content = request.getParameter("content[" + i + "]").replaceAll("\r\n", "");
				model.setWidgetContent(content);
				model.setColNo("0");
				model.setState("ST0001");
				saveList.add(model);
			}else{
				int imgLineNo = Integer.parseInt(lineNum[i]);
				for (int imgInx = 0; imgInx < 4; imgInx++){
					String imgFileExist = request.getParameter("imgFile[" + imgLineNo + "][" + imgInx + "]");
					if (imgFileExist == null){
						ItemWidgetModel sc = new ItemWidgetModel();
						sc.setUserId(userId);
						sc.setItemId(itemId);
						sc.setLineNo(lineNum[i]);
						sc.setColNo(Integer.toString(imgInx));
						sc.setWidgetType(widgetType[i]);
						List<ItemWidgetModel> itemWidgetList = itemWidgetService.getUserItemWidgetList(sc);
						
						String imgFileName = fileName + Integer.toString(i) + "_" + Integer.toString(imgInx);
						UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request
								, "itemwidget"//request.getParameter("module")
								, "imgFile[" + imgLineNo + "][" + imgInx + "]"
								, ""
								, imgFileName);
						if(uploadedFile == null){
							if (itemWidgetList != null && itemWidgetList.size() > 0){
								ItemWidgetModel model = new ItemWidgetModel();
								model.setUserId(userId);
								model.setItemId(itemId);
								model.setWidgetType(widgetType[i]);
								model.setLineNo(Integer.toString(i));
								model.setColNo(Integer.toString(imgInx));
								model.setWidgetContent(itemWidgetList.get(0).getWidgetContent());
								saveImgList.add(itemWidgetList.get(0).getWidgetContent());
								model.setState("ST0001");
								saveList.add(model);
							}
						}else{
							if (itemWidgetList != null && itemWidgetList.size() > 0){
								String originFileName = itemWidgetList.get(0).getWidgetContent();
								if (!originFileName.equals("")){
									FileUtil.deleteFile("itemwidget", originFileName);
								}
							}
							ItemWidgetModel model = new ItemWidgetModel();
							model.setUserId(userId);
							model.setItemId(itemId);
							model.setWidgetType(widgetType[i]);
							model.setLineNo(Integer.toString(i));
							model.setColNo(Integer.toString(imgInx));
							model.setState("ST0001");
							model.setWidgetContent(uploadedFile.getSysFileName());
							saveList.add(model);
						}
					}
				}
			}
		}
		
		//  ok process savelist
		List<String> deleteImgList = itemWidgetService.getDeleteImageList(userId, itemId, saveImgList);
		if (deleteImgList != null && deleteImgList.size() > 0){
			for (int i = 0; i<deleteImgList.size(); i++){
				FileUtil.deleteFile("itemwidget", deleteImgList.get(i));
			}
		}
		itemWidgetService.SaveItemWidget(saveList, userId, itemId);
		
		
		ModelAndView mv = new ModelAndView("redirect:UserItem.do?cmd=setItemPage&itemId=" + itemId);
		if (request.getParameter("isPreview").equals("true")){
			mv.addObject("isPreview", true);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("hostUserId", userId);
			map.put("itemId", itemId);
			return redirect("UserItem", "viewItemPage", map);
		}else{
			SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
			return mv;//redirect("UserItem", "setItemPage", map);
		}
	}
}