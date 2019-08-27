
package com.kpc.eos.controller.system;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.DateUtil;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.bizSetting.UserItemSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.common.UploadFile;
import com.kpc.eos.model.system.ItemModel;
import com.kpc.eos.service.system.ItemService;

public class ItemController extends BaseEOSController {
	private ItemService itemService;
	
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public ItemController() {
		super();
		controllerId = "Item";
	}
	
	/**
	 * command initialization function.
	 * When getting a request, this function will be called before running a cmd's method.
	 * Define Breadcrumb model.
	 */
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("商品管理", getCmdUrl("itemList"), true));
	}
	
	public ModelAndView itemList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initCmd();
		
		UserItemSModel sc = new UserItemSModel();
		
		String key = "Item_itemList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (UserItemSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("system/itemList", "sc", sc);
		
		List<String> itemCategoryList = itemService.getCategoryList(null);
		
		mv.addObject("categoryList", itemCategoryList);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	public ModelAndView itemGridAjax(HttpServletRequest request, HttpServletResponse response, UserItemSModel sc) throws Exception {

		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Item_itemList");
		
		Integer totalCount = itemService.countItemList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<ItemModel> list = itemService.getItemList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	public ModelAndView itemForm(HttpServletRequest request, HttpServletResponse response, ItemModel itemModel) throws Exception {
		initCmd();
		String itemId = request.getParameter("itemId");
		
		ItemModel item = null;
		
		if (StringUtils.isNotEmpty(itemId))
		{
			breads.add(new BreadcrumbModel("修改商品", "", false));
			item = itemService.getItem(itemId);
		}
		else
		{
			breads.add(new BreadcrumbModel("新增商品", "", false));
		}
		
		ModelAndView mv = new ModelAndView("system/itemForm");
		
		String width 	= ApplicationSetting.getConfig(FileUtil.UPLOAD_PREFIX + "useritem" + FileUtil.UPLOAD_MINWIDTH);
		String height 	= ApplicationSetting.getConfig(FileUtil.UPLOAD_PREFIX + "useritem" + FileUtil.UPLOAD_MINHEIGHT);
		
		mv.addObject("minWidth", width);
		mv.addObject("minHeight", height);
		
		if (itemModel != null && isPost(request)){
			item = itemModel;
		}
		
		mv.addObject("item", item);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveItem
	* Function Description		Save the item information.
	*****************************************************************************************************************************/
	public ModelAndView saveItem(HttpServletRequest request, HttpServletResponse response, ItemModel item) throws Exception {
				
		Integer exists = itemService.checkItemNoExists(item);
		if ((exists != null) || !isPost(request))
		{
			formErrors = item.validate();
			ModelAndView mv = new ModelAndView();
			formErrors.rejectValue("itemNo", "system.common.duplicated", new Object[]{"商品编码"}, null);
			mv = itemForm(request, response, item);
			return mv;
		}
		
		String now = DateUtil.getToday("yyyyMMddHHmmss");
		String fileName = "000" + now;
		
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
					mv = itemForm(request, response, item);
					return mv;
				}
				String originFileName = item.getItemImg();
				if (!originFileName.equals("")){
					FileUtil.deleteFile("useritem", originFileName);
				}
				
				String uploadedFileName = uploadedFile.getSysFileName();
				String ext = uploadedFileName.substring(uploadedFileName.lastIndexOf(".") + 1).toLowerCase();
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName,"", -1, 500, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName, "medium", 300, 200, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName, "small", 120, 80, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.resizeWithCrop("useritem", uploadedFileName, fileName + "@2x", "small", 120, 80, offsetX, offsetY, cropW, cropH, 300, 0);
				
				FileUtil.deleteFile("useritem", uploadedFile.getSysFileName());
				
				item.setItemImg(fileName + FileUtil.EXT_DOT + ext);
			}else{
				String originFileName = item.getItemImg();
				
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
			String originFileName = item.getItemImg();
			if (!originFileName.equals("")){
				FileUtil.deleteFile("useritem", originFileName);
			}
			item.setItemImg("");
		}
		
		itemService.saveItem(item);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("Item", "itemList");
	}
}
