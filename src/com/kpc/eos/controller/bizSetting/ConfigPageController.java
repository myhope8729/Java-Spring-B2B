
package com.kpc.eos.controller.bizSetting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.common.UploadFile;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.InfoAttachmentModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.BizSettingSModel;
import com.kpc.eos.model.bizSetting.ConfigPageBannerModel;
import com.kpc.eos.model.bizSetting.ConfigPageDetailModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.ConfigPageService;
import com.kpc.eos.service.bizSetting.UserItemService;

import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.DateUtil;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;

public class ConfigPageController extends BaseEOSController {
	
	private ConfigPageService configPageService;
	private UserItemService userItemService;
	private BizDataService bizDataService;
	
	public void setConfigPageService(ConfigPageService configPageService) {
		this.configPageService = configPageService;
	}
	
	public void setUserItemService(UserItemService userItemService) {
		this.userItemService = userItemService;
	}
	
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	
	public ConfigPageController() {
		super();
		controllerId = "ConfigPage";
	}
	
	/**
	 * command initialization function.
	 * When getting a request, this function will be called before running a cmd's method.
	 * Define Breadcrumb model.
	 */
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("业务设置", "", false));
		breads.add(new BreadcrumbModel("首页设置", getCmdUrl("configPageMain"), true));
	}
	
	public ModelAndView configPageMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initCmd();
		ModelAndView mv = new ModelAndView("bizSetting/configPageMain");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		List<ConfigPageBannerModel> listBanner = configPageService.getAllPageBannerList(userId);
		List<ConfigPageDetailModel> listDetail = configPageService.getAllPageDetailList(userId);
		
		// Get Product
		for (int i=0; i<listDetail.size(); i++) {
			if ( listDetail.get(i).getDetailType().equals(Constants.PAGE_DETAIL_TYPE_PRODUCT) ) {
				// get Num_Code category field
				// List<UserItemPropertyModel> codePropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NUM_CODE);
				String itemId = listDetail.get(i).getDetailImgPath();
				
				// get user item
				UserItemModel itemList = userItemService.getUserItem(userId, itemId);
				
				// set user item
				if(itemList != null){
					List<UserItemPropertyModel> namePropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NAME_CODE);
					if (namePropertyData != null && namePropertyData.size() > 0){
						String nameField = namePropertyData.get(0).getPropertyName();
						itemList.setItemName(itemList.get(nameField));
					}
					listDetail.get(i).setUserItem( itemList);
				}
			}			
		}
		
		mv.addObject("bannerList", listBanner);
		mv.addObject("detailList", listDetail);
		
		return mv;
	}
	
	public ModelAndView configPageBanner(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initCmd();
		breads.add(new BreadcrumbModel("设置幻灯片", "", true));
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		ModelAndView mv = new ModelAndView("bizSetting/configPageBanner");
		
		String bannerId = request.getParameter("bannerId");
		ConfigPageBannerModel banner = configPageService.getPageBannerById(bannerId);
		
		List<BizDataModel> groupList = bizDataService.getBizDataByBizCode(userId, Constants.COSNT_BIZDATA_ITEMGROUP_CODE, null);
		
		if (groupList != null && groupList.size() > 0){
			mv.addObject("productGroupList", groupList);
		}
		
		mv.addObject("banner", banner);
		
		return mv;
	}
	
	public ModelAndView configPageDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initCmd();
		breads.add(new BreadcrumbModel("内容设置", "", true));
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		String detailId = request.getParameter("detailId");
		ConfigPageDetailModel detail = configPageService.getPageDetailById(detailId);
		
		if (detail == null){
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("configpage.pagedetail.invaliddetail"), request);
			return redirect("ConfigPage", "configPageMain");
		}
		if (detail.getDetailType().equals(Constants.PAGE_DETAIL_TYPE_PRODUCT) ) {
			String itemId = detail.getDetailImgPath();
			
			// get user item
			UserItemModel item = userItemService.getUserItem(userId, itemId);
			
			// set user item
			if(item != null){
				List<UserItemPropertyModel> namePropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NAME_CODE);
				if (namePropertyData != null && namePropertyData.size() > 0){
					String nameField = namePropertyData.get(0).getPropertyName();
					item.setItemName(item.get(nameField));
				}
				List<UserItemPropertyModel> numPropertyData = userItemService.getItemFieldPropertyByUser(userId, Constants.CONST_ITEM_NUM_CODE);
				if (numPropertyData != null && numPropertyData.size() > 0){
					String numField = numPropertyData.get(0).getPropertyName();
					item.setItemNo(item.get(numField));
				}
				detail.setUserItem( item );
			}
		}
		
		List<BizDataModel> docTypeList = bizDataService.getBizDataByBizCode(userId, Constants.BIZDATA_FILE_TYPE, null);
		
		UserItemSModel sc = new UserItemSModel();
		
		String key = "ConfigPage_userItemList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		
		ModelAndView mv = new ModelAndView("bizSetting/configPageDetail", "sc", sc);
		
		mv.addObject("detail", detail);
		mv.addObject("docTypeList", docTypeList);
		
		List<BizDataModel> groupList = bizDataService.getBizDataByBizCode(userId, Constants.COSNT_BIZDATA_ITEMGROUP_CODE, null);
		
		if (groupList != null && groupList.size() > 0){
			mv.addObject("productGroupList", groupList);
		}
		
		List<UserItemPropertyModel> fieldList = userItemService.getFieldList(userId, "ST0001");
		
		sc.setUserId(userId);
		
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
			if (Constants.CONST_ITEM_NAME_CODE.equals(fieldList.get(i).getPropertyTypeCd())
					|| Constants.CONST_ITEM_NUM_CODE.equals(fieldList.get(i).getPropertyTypeCd())
					|| Constants.CONST_ITEM_TYPE1_CODE.equals(fieldList.get(i).getPropertyTypeCd())
					|| Constants.CONST_ITEM_TYPE2_CODE.equals(fieldList.get(i).getPropertyTypeCd())
					|| (Constants.CONST_ITEM_PRICE_CODE.equals(fieldList.get(i).getPropertyTypeCd()) && Constants.CONST_Y.equals(fieldList.get(i).getPropertyValue()))){
				colModel = new HashMap<String, Object>();
				propNamesList.add(fieldList.get(i).getPropertyDesc());
				propTypeCdList.add(fieldList.get(i).getPropertyTypeCd());
				colModel.put("name", fieldList.get(i).getPropertyName());
				colModel.put("index", fieldList.get(i).getPropertyName());
				if (Constants.CONST_ITEM_PRICE_CODE.equals(fieldList.get(i).getPropertyTypeCd()) && Constants.CONST_Y.equals(fieldList.get(i).getPropertyValue())){
					colModel.put("align", "right");
				}else{
					colModel.put("align", "left");
				}
				
				colModel.put("sortable", true);
				if (Constants.CONST_ITEM_NAME_CODE.equals(fieldList.get(i).getPropertyTypeCd())){
					colModel.put("width", "250");
				}
				colModelList.add(colModel);
				
				if (StringUtils.equals(fieldList.get(i).getPropertyTypeCd(), Constants.CONST_ITEM_TYPE1_CODE)){
					categoryFieldName = fieldList.get(i).getPropertyName();
				}
			}
		}
		propNamesList.add("状态");
		colModel = new HashMap<String, Object>();
		colModel.put("name", "stateName");
		colModel.put("index", "state");
		colModel.put("align", "center");
		colModelList.add(colModel);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("colNames", propNamesList);
		jsonObj.element("colModel", colModelList);
		jsonObj.element("properties", propTypeCdList);
		mv.addObject("jsonObj", jsonObj.toString());
		
		if (categoryFieldName != null && !StringUtils.equals(categoryFieldName, "")){
			List<String> itemCategoryList = userItemService.getItemCategoryList(userId, categoryFieldName);
			mv.addObject("categoryList", itemCategoryList);
		}
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	public ModelAndView savePageMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Save logo Image
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());		
		String fileName = loginUser.getUserNo().concat(DateUtil.getToday("yyyyMMddHHmmss"));
		
		String tempName = fileName.concat("_temp");
		
		String imgFileExist = request.getParameter("logoImgFile");
		if (imgFileExist == null){
			UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request, "userlogo", "logoImgFile", "", tempName);
			if(uploadedFile != null){
				String originFileName = loginUser.getLogoImgPath();
				if (!originFileName.equals("")){
					String originExt = originFileName.substring(originFileName.lastIndexOf(".") + 1).toLowerCase();
					String fileNameNoExt = originFileName.substring(0, originFileName.lastIndexOf("."));
					FileUtil.deleteFile("userlogo", originFileName);
					FileUtil.deleteFile("userlogo", fileNameNoExt + "@2x" + FileUtil.EXT_DOT + originExt);
				}
				
				String uploadedFileName = uploadedFile.getSysFileName();
				String ext = uploadedFileName.substring(uploadedFileName.lastIndexOf(".") + 1).toLowerCase();
				
				String tempFileFullPath = FileUtil.makeFileFullName("userlogo", uploadedFileName);
				String realFileFullPath = FileUtil.makeFileFullName("userlogo", fileName + FileUtil.EXT_DOT + ext);
				String real2XFileFullPath = FileUtil.makeFileFullName("userlogo", fileName + "@2x" + FileUtil.EXT_DOT + ext);
				
				File tempFile = new File(tempFileFullPath);
				File realFile = new File(realFileFullPath);
				File real2XFile = new File(real2XFileFullPath);
				
				FileUtil.resize(tempFile, realFile, FileUtil.RATIO, 80);
				FileUtil.resize(tempFile, real2XFile, FileUtil.RATIO, 160);
				
				FileUtil.deleteFile("userlogo", tempName + FileUtil.EXT_DOT + ext);
				
				loginUser.setLogoImgPath(fileName + FileUtil.EXT_DOT + ext);
			}
		}else{
			String originFileName = loginUser.getLogoImgPath();
			if (!originFileName.equals("")){
				String originExt = originFileName.substring(originFileName.lastIndexOf(".") + 1).toLowerCase();
				String fileNameNoExt = originFileName.substring(0, originFileName.lastIndexOf("."));
				FileUtil.deleteFile("userlogo", originFileName);
				FileUtil.deleteFile("userlogo", fileNameNoExt + "@2x" + FileUtil.EXT_DOT + originExt);
			}
			loginUser.setLogoImgPath("");
		}
		
		// Save Database
		loginUser.setTopic1(request.getParameter("topic1"));
		loginUser.setTopic2(request.getParameter("topic2"));
		loginUser.setMainColor(request.getParameter("mainColor"));
		loginUser.setBkColor(request.getParameter("bkColor"));
		configPageService.savePageMain(loginUser);
		
		// Save loginSession
		SessionUtil.setUser(request, loginUser, getSystemName());
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("ConfigPage", "configPageMain");
	}
	
	public ModelAndView savePageBanner(HttpServletRequest request, HttpServletResponse response, ConfigPageBannerModel pageBanner) throws Exception {
		
		// Save banner Image
		String now = DateUtil.getToday("yyyyMMddHHmmss");		
		String bannerId = pageBanner.getBannerId();
		String tempId = bannerId.replaceFirst("PGBN0+", "").replace("PGBN", "");
		String fileName = tempId.concat(now);		
		
		String imgFileExist = request.getParameter("bannerImgFile");
		if (imgFileExist == null){
			UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request, "pagebanner", "bannerImgFile", "", fileName);
			if(uploadedFile != null){
				String originFileName = pageBanner.getBannerImgPath();
				if (!originFileName.equals("")){
					FileUtil.deleteFile("pagebanner", originFileName);
				}
				pageBanner.setBannerImgPath(uploadedFile.getSysFileName());
			}
		}else{
			String originFileName = pageBanner.getBannerImgPath();
			if (!originFileName.equals("")){
				FileUtil.deleteFile("pagebanner", originFileName);
			}
			pageBanner.setBannerImgPath("");
		}
		
		if (StringUtils.equals(pageBanner.getUrlType(), Constants.CONST_URL_TYPE_PRODUCT)){
			pageBanner.setUrl(pageBanner.getProductGroup());
		}
		
		// Save Database
		configPageService.savePageBanner(pageBanner);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("ConfigPage", "configPageMain");
	}
	
	public ModelAndView savePageDetail(HttpServletRequest request, HttpServletResponse response, ConfigPageDetailModel pageDetail) throws Exception {
		
		// Save banner Image
		String now = DateUtil.getToday("yyyyMMddHHmmss");
		String detailId = pageDetail.getDetailId();
		String tempId = detailId.replaceFirst("PGDT0+", "").replace("PGDT", "");
		String fileName = tempId.concat(now);
		
		String imgFileExist = request.getParameter("detailImgFile");
		if (imgFileExist == null){
			UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request, "pagedetail", "detailImgFile", "", fileName);
			if(uploadedFile != null){
				String originFileName = pageDetail.getDetailImgPath();
				if (!originFileName.equals("")){
					FileUtil.deleteFile("pagedetail", originFileName);
				}
				pageDetail.setDetailImgPath(uploadedFile.getSysFileName());
			}
		}else{
			String originFileName = pageDetail.getDetailImgPath();
			if (!originFileName.equals("")){
				FileUtil.deleteFile("pagedetail", originFileName);
			}
			pageDetail.setDetailImgPath("");
		}
		
		if (StringUtils.equals(pageDetail.getUrlType(), Constants.CONST_URL_TYPE_PRODUCT)){
			pageDetail.setUrl(pageDetail.getProductGroup());
		}
		
		if (StringUtils.equals(pageDetail.getDetailType(), Constants.PAGE_DETAIL_TYPE_PRODUCT)){
			pageDetail.setDetailImgPath(pageDetail.getProductId());
		}
		
		if (StringUtils.equals(pageDetail.getDetailType(), Constants.PAGE_DETAIL_TYPE_NOSHOW)){
			pageDetail.setDetailImgPath("");
			pageDetail.setUrl("");
			pageDetail.setInfoType("");
		}
		//mob_mark
		//li_mark
		//mob_only
		// Save Database
		configPageService.savePageDetail(pageDetail);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("ConfigPage", "configPageMain");
	}
	
	public ModelAndView deletePageDetailAjax(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect("ConfigPage", "configPageMain");
		}
		
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String rowId = request.getParameter("rowId");
		String colId = request.getParameter("colId");
		String cellId = request.getParameter("cellId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		if (StringUtils.isNotEmpty(rowId)){
			map.put("rowId", rowId);
		}
		if (StringUtils.isNotEmpty(colId)){
			map.put("colId", colId);
		}
		if (StringUtils.isNotEmpty(cellId)){
			map.put("cellId", cellId);
		}
		
		configPageService.deletePageDetail(map);
		return mv;
	}
	
	public ModelAndView addPageDetailAjax(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect("ConfigPage", "configPageMain");
		}
		
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String rowId = request.getParameter("rowId");
		String colId = request.getParameter("colId");
		String cellId = request.getParameter("cellId");
		String widthPc = request.getParameter("width");
		String widthMob = request.getParameter("width_mob");
		String userId = SessionUtil.getUserId(request, getSystemName());
		String empId = SessionUtil.getEmpId(request, getSystemName());
		
		ConfigPageDetailModel detail = new ConfigPageDetailModel();
		detail.setUserId(userId);
		detail.setCreateBy(empId);
		detail.setUpdateBy(empId);		
		detail.setRowNum(rowId);
		detail.setColNum(colId);
		detail.setCellNum(cellId);
		detail.setWidthPc(widthPc);
		detail.setWidthMob(widthMob);
		String detailId = configPageService.addPageDetail(detail);
		mv.addObject("detailId", detailId);
		return mv;
	}
	
	public ModelAndView setPageDetailWidthAjax(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect("ConfigPage", "configPageMain");
		}
		
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String rowId = request.getParameter("rowId");
		String colId = request.getParameter("colId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		String empId = SessionUtil.getEmpId(request, getSystemName());
		String width = request.getParameter("width");
		String widthMob = request.getParameter("width_mob");
		
		ConfigPageDetailModel detail = new ConfigPageDetailModel();
		detail.setUserId(userId);
		detail.setUpdateBy(empId);
		detail.setRowNum(rowId);
		detail.setColNum(colId);
		if (StringUtils.isNotEmpty(width)){
			detail.setWidthPc(width);
		}
		if (StringUtils.isNotEmpty(widthMob))
		{
			detail.setWidthMob(widthMob);
		}
		configPageService.setPageDetailWidth(detail);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			userItemGridAjax
	* Function Description		Retrieve the user item list according to user id
	*****************************************************************************************************************************/
	public ModelAndView userItemGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		
		request.setAttribute(SC_ID_SESSION, "ConfigPage_userItemList");
		
		sc.setUserId(loginUser.getUserId());
		
		List<UserItemPropertyModel> itemPropertyList = userItemService.getItemFieldPropertyByUser(loginUser.getUserId(), "PT0003");
		if (itemPropertyList != null && itemPropertyList.size() > 0){
			String categoryFieldName = itemPropertyList.get(0).getPropertyName();
			sc.setCatFieldName(categoryFieldName);
		}
		
		Integer totalCount = userItemService.countUserItemListOnConfigPage(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<UserItemModel> itemList = userItemService.getUserItemListOnConfigPage(sc);
		
		mv.addObject("rows", itemList);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
}
