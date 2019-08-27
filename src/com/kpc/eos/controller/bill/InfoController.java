package com.kpc.eos.controller.bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.DateUtil;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.core.util.HTMLHelper;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.InfoAttachmentModel;
import com.kpc.eos.model.bill.InfoDetailModel;
import com.kpc.eos.model.bill.InfoDetailSModel;
import com.kpc.eos.model.bill.InfoModel;
import com.kpc.eos.model.bill.InfoSModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.common.UploadFile;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.bill.BillService;
import com.kpc.eos.service.bill.InfoService;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.dataMng.UserService;

/**
 * Filename		: InfoController.java
 * Description	: Management class for the user's infos.
 * 2017
 * @author		: RKRK
 */
public class InfoController extends BaseEOSController 
{
	public final static String SC_KEY_INFO_LIST 	= "Info_list";
	public final static String SC_KEY_INFO_ATTACH_LIST 	= "Info_attach_list";
	
	private BillService			billService;
	
	private UserService 		userService;
	private BizDataService  	bizDataService;
	private InfoService  		infoService;
	
	// member vars
	private ModelAndView 		mv = null;
	
	private UserModel 			loginUser;
	private String 				userId;
	private String 				empId;
	
	public InfoController() 
	{
		super();
		controllerId = "Info";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "Info";
		
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
		empId = loginUser.getEmpId();
		
		// get the pre breadcrumbs.
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] urls = new String[]{"infoList", "infoForm", "infoSet", "infoAttachment"};
		
		if ( ArrayUtils.contains(urls, methodName) )
		{
			breads.add(new BreadcrumbModel("填写单据", ""));
			breads.add(new BreadcrumbModel("栏目信息  ", getCmdUrl("infoList")));
		}
	}

	/**
	 * Description	: Set the info service.
	 * @author 		: RKRK
	 * @param billService
	 * 2017
	 */
	public void setBillService(BillService billService) 
	{
		this.billService = billService;
	}
	
	public void setUserService(UserService userService) 
	{
		this.userService = userService;
	}
	public void setBizDataService(BizDataService bizDataService) 
	{
		this.bizDataService = bizDataService;
	}
	 
	public void setInfoService(InfoService infoService) 
	{
		this.infoService = infoService;
	}
	
	/* Description	: Show the infos list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView infoList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = SC_KEY_INFO_LIST;
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/infoList", "sc", sc );
		
		// file type list.
		List<BizDataModel> fileTypeList = bizDataService.getBizDataByBizCode(userId, Constants.BIZDATA_FILE_TYPE, null);
		mv.addObject("fileTypeList", fileTypeList);
		
		pageTitle = " 栏目信息";
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the infos list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView infoGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_INFO_LIST);
		
		sc.setBillType(Constants.CONST_BILL_TYPE_NEWS);
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
	 * Description	: List the infos of the current user.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView infoForm(HttpServletRequest request, HttpServletResponse response, BillHeadModel infoForm) throws Exception 
	{
		mv = new ModelAndView( "bill/infoForm" );
		
		String billId = request.getParameter( "billId" );
		BillHeadModel info = new BillHeadModel();
		
		if ( isPost(request) )
		{
			// If user has already submitted the info form, keep the info model.
			if ( infoForm != null ) {
				info = infoForm;
			}
		} 
		else
		{
			// edit action?
			if ( StringUtils.isNotEmpty(billId) ) 
			{
				info = billService.getBill(billId);
				if ( ! loginUser.getEmpId().equals(info.getInputorId()) )
				{
					SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
					return redirect(controllerId, "infoList");
				}
			} 
		}
		
		// set the page title
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			pageTitle = "修改栏目信息";
		}
		else
		{
			pageTitle = "新增栏目信息";
		}
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		// file type list.
		List<BizDataModel> fileTypeList = bizDataService.getBizDataByBizCode(userId, Constants.BIZDATA_FILE_TYPE, null);
		if (fileTypeList == null || fileTypeList.size() == 0){
			SysMsg.addMsg(SysMsg.INFO, MessageUtil.getMessage("info.set.document.type"), request);
			return redirect(controllerId, "infoList");
		}
		
		mv.addObject("fileTypeList", fileTypeList);
		
		mv.addObject("hbList", HTMLHelper.getYnList());
		
		mv.addObject("info", info);
		
		return mv;
	}
	
	/**
	 * Description	: Save the info data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveInfo(HttpServletRequest request, HttpServletResponse response, BillHeadModel info) throws Exception 
	{
		if ( ! isPost(request) )
		{
			return redirect(controllerId, "infoList");
		}
		
		String action = request.getParameter("action");
		boolean isSubmit = "submit".equals(action);
		boolean isNew = StringUtils.isEmpty( info.getBillId() );
		
		// ---- start to validate the info form now ------------- //
		formErrors = new FormErrors(info, "target");
		FormValidationUtils fv = new FormValidationUtils(formErrors);
		
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "itype", "system.common.valid.error.required", new Object[]{"类别"});
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "info", "system.common.valid.error.required", new Object[]{"标题"});
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "note", "system.common.valid.error.required", new Object[]{"内容"});
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "hbmark", "system.common.valid.error.required", new Object[]{"是否报名"});
		
		if ( formErrors.hasErrors() )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.save.erorr"), request);
			mv = infoForm(request, response, info);
			return mv;
		}
		// ---- End of validation -------------------- //
		
		// Setting the info data
		info.setBillType(Constants.CONST_BILL_TYPE_NEWS);
		info.setHostUserId(loginUser.getUserId());
		info.setHostUserNo(loginUser.getUserNo());
		info.setHostUserName(loginUser.getUserName());
		info.setInputorId(loginUser.getEmpId());
		info.setInputorName(loginUser.getEmpName());
		info.setArriveDate( DateUtil.getToday() );
		info.setUpdateBy( loginUser.getEmpId() );
		
		if (isNew)
		{
			info.setCreateBy(loginUser.getEmpId());
		}
		
		// submitting bill here...
		if ( isSubmit )
		{
			info.setState( Constants.BILL_STATE_COMPLETED );
			ResultModel ret = (ResultModel) billService.processSaveInfoBill( info );
			
			SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("info.submit.ok"), request);
		}
		else 
		{
			info.setState( Constants.BILL_STATE_DRAFT );
			
			
			billService.processSaveInfoBill( info );
			
			SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("info.save.ok"), request);
		}
			
		return redirect(controllerId, "infoList");
	}
	
	/**
	 * Description	: Delete the info bill.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView deleteInfoAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel info) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "infoList");
		}
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String billId = info.getBillId();
		if (StringUtils.isEmpty(billId))
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// validation here.
		info = billService.getBill( billId );
		if ( info == null )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// current emp's bill?
		if ( ! empId.equals(info.getInputorId()) ) 
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// get the images.
		billService.deleteBill( info );
		
		rm.setResultMsg(MessageUtil.getMessage("info.delete.success") );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	:
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView infoSet(HttpServletRequest request, HttpServletResponse response, BillHeadModel info) throws Exception 
	{
		mv = new ModelAndView( "bill/infoSet" );
		
		String billId = request.getParameter( "billId" );
		
		if ( StringUtils.isEmpty(billId) ) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "infoList");
		}
		
		
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			info = billService.getBill(billId);
			if ( ! loginUser.getEmpId().equals(info.getInputorId()) )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "infoList");
			}
		} 
		
		pageTitle = "编辑详情";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		// getting the list here.
		InfoSModel scInfo = new InfoSModel();
		scInfo.setUserId(userId);
		scInfo.setBillId(billId);
		List<InfoModel> infoList = infoService.getUserInfoList(scInfo);
		
		for (InfoModel obj : infoList) 
		{
			if (Constants.INFO_ITEM_TYPE_IMG.equals(obj.getDtype()))
			{
				InfoDetailSModel scDetail = new InfoDetailSModel(obj);
				obj.setDetailList( infoService.getInfoDetailList(scDetail) );
			}
		}
		
		mv.addObject("infoList", infoList);
		mv.addObject("info", info);
		
		return mv;
	}
	
	/**
	 * Description	: Save the pictures in an info. If there are no images, there will be no details.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView saveInfoPic(HttpServletRequest request, HttpServletResponse response, BillHeadModel info) throws Exception 
	{
		if ( ! isPost(request) )
		{
			return redirect(controllerId, "infoList");
		}
		
		String billId = info.getBillId();
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			info = billService.getBill(billId);
			if ( ! loginUser.getEmpId().equals(info.getInputorId()) )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "infoList");
			}
		} 
		
		String now = DateUtil.getToday("yyyyMMddHHmmss");
		String tempId = empId.replaceFirst("EMPL0+", "").replace("EMPL", "");
		String fileName = tempId.concat(now);
		
		String[] lineNum = request.getParameterValues("line[]");
		String[] widgetType = request.getParameterValues("type[]");
		
		List<InfoModel> saveList = new ArrayList<InfoModel>();
		List<String> saveImgList = new ArrayList<String>();
		List<InfoDetailModel> detailList = new ArrayList<InfoDetailModel>();
		
		for (int i=0; lineNum !=null && i < lineNum.length; i++)
		{
			String newLineId = Integer.toString(i+1);
			
			InfoModel model = new InfoModel();
			
			model.setUserId(userId);
			model.setBillId(billId);
			model.setLineId( newLineId );
			model.setDno(newLineId);
			model.setCreateBy(empId);
			model.setUpdateBy(empId);
			
			if ( Constants.INFO_ITEM_TYPE_TEXT.equals(widgetType[i]) ) 
			{
				model.setDtype( Constants.INFO_ITEM_TYPE_TEXT );
				
				String content = request.getParameter("content[" + newLineId + "]");
				model.setPnote(content);
				
				String showMark = request.getParameter("showMark[" + Integer.parseInt(lineNum[i]) + "]");
				model.setShowmark( showMark == null? Constants.CONST_N: Constants.CONST_Y );
				
				saveList.add(model);
			}
			else
			{
				model.setDtype( Constants.INFO_ITEM_TYPE_IMG );
				model.setShowmark( Constants.CONST_N );	// no meaning here.
				
				saveList.add(model);
				
				int imgLineNo = Integer.parseInt(lineNum[i]);
				
				// get the detail images.
				InfoDetailSModel scDetail = new InfoDetailSModel();
				scDetail.setBillId(billId);
				scDetail.setUserId(userId);
				scDetail.setLineId( Integer.toString(imgLineNo) );
				
				List<InfoDetailModel> details = infoService.getInfoDetailList(scDetail);
				HashMap<String, InfoDetailModel> detailMap = new HashMap<String, InfoDetailModel>();
				for (InfoDetailModel tmp : details) 
				{
					detailMap.put(tmp.getDno(), tmp);
				}
				
				for ( int imgInx = 0; imgInx < 4; imgInx++ )
				{
					String newImgInd = Integer.toString(imgInx);
					InfoDetailModel detailModel = new InfoDetailModel();
					
					detailModel.setUserId(userId);
					detailModel.setBillId(billId);
					detailModel.setLineId( newLineId );
					detailModel.setDno(Integer.toString(imgInx + 1));
					String showMark = request.getParameter("showMark[" + imgLineNo + "][" + imgInx + "]");
					detailModel.setShowYn( showMark == null? Constants.CONST_N: Constants.CONST_Y );
					
					String htmlFileFieldName = "imgFile[" + imgLineNo + "][" + imgInx + "]";
					
					String imgFileName = fileName + newLineId + "_" + newImgInd;
					UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request
							, "infoImg"//request.getParameter("module")
							, htmlFileFieldName
							, ""
							, imgFileName);
					
					String mapKey = Integer.toString(imgInx+1);
					if ( uploadedFile == null )
					{
						if (detailMap.containsKey(mapKey))
						{
							String url = ((InfoDetailModel)detailMap.get(mapKey)).getUrl();
							
							// checking unattached image.
							String imgFileExist = request.getParameter("imgFile[" + imgLineNo + "][" + imgInx + "]");
							if (imgFileExist == null) {
								detailModel.setUrl(url);
								detailList.add(detailModel);
								
								saveImgList.add(url);
							}
						} 
					}
					else
					{
						detailModel.setUrl(uploadedFile.getSysFileName());
						detailList.add(detailModel);
					}
				}
			}
		}
		
		// remove the unnecessary images here.
		List<String> deleteImgList = infoService.getDeleteImageList(userId, billId, saveImgList);
		if (deleteImgList != null && deleteImgList.size() > 0) 
		{
			for (int i = 0; i<deleteImgList.size(); i++)
			{
				FileUtil.deleteFile("infoImg", deleteImgList.get(i));
			}
		}
		
		// save the info picture information here.
		infoService.saveInfoPic(saveList, detailList, info);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("info.save.ok"), request);
		return redirect(controllerId, "infoList");
	}
	
	/**
	 * Description	: Show the detail page.
	 * 					NOTE : under development now.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView infoDetail(HttpServletRequest request, HttpServletResponse response, BillHeadModel info) throws Exception 
	{
		mv = new ModelAndView( "bill/infoDetail" );
		
		String billId = request.getParameter( "billId" );
		
		if ( StringUtils.isEmpty(billId) ) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "infoList");
		}
		
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			info = billService.getBill(billId);
			if ( ! loginUser.getEmpId().equals(info.getInputorId()) )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "infoList");
			}
		} 
		
		pageTitle = "编辑详情";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		// getting the list here.
		InfoSModel scInfo = new InfoSModel();
		scInfo.setUserId(userId);
		scInfo.setBillId(billId);
		List<InfoModel> infoList = infoService.getUserInfoList(scInfo);
		
		for (InfoModel obj : infoList) 
		{
			if (Constants.INFO_ITEM_TYPE_IMG.equals(obj.getDtype()))
			{
				InfoDetailSModel scDetail = new InfoDetailSModel(obj);
				obj.setDetailList( infoService.getInfoDetailList(scDetail) );
			}
		}
		
		mv.addObject("infoList", infoList);
		mv.addObject("info", info);
		
		return mv;
	}
	
	/**
	 * Description	: 
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView infoAttachment(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView( "bill/infoAttachment" );
		
		String billId = sc.getBillId();
		
		if ( StringUtils.isEmpty(billId) ) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "infoList");
		}
		
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			BillHeadModel infoObj = billService.getBill(billId);
			if ( ! loginUser.getEmpId().equals(infoObj.getInputorId()) )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "infoList");
			}
		} 
		
		// getting the search model from session
		String key = SC_KEY_INFO_ATTACH_LIST;
		request.setAttribute(SC_ID_SESSION, key);
		//modified by rmh 2018.02.11
		/*sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);*/
		
		mv.addObject("sc", sc);
		
		pageTitle = "上传附件";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
	
		return mv;
	}
	
	/**
	 * Description	:
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView infoAttachmentGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_INFO_ATTACH_LIST);
		
		// getting the list here.
		Integer totalCount = (Integer) infoService.countInfoAttachmentList(sc);
		
		sc.getPage().setRecords(totalCount);
		
		List<InfoAttachmentModel> list = infoService.getInfoAttachmentList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: Delete the attachment row.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param attach
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView deleteInfoAttachmentAjax(HttpServletRequest request, HttpServletResponse response, InfoAttachmentModel attach) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "infoAttachment");
		}
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String id = attach.getId();
		if (StringUtils.isEmpty(id))
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// validation here.
		InfoAttachmentModel temp = infoService.getInfoAttachment( id );
		if ( temp == null )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		String billId = temp.getBillId();
		BillHeadModel info = billService.getBill(billId);
		
		if ( ! empId.equals(info.getInputorId()) ) 
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		infoService.deleteInfoAttachment(id);
		
		SysMsg.addMsg( SysMsg.SUCCESS, MessageUtil.getMessage("info.delete.success"), request );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Upload the attachment file.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView saveInfoAttachment(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		if ( ! isPost(request) )
		{
			return redirect(controllerId, "infoAttachment");
		} 
		
		String billId = sc.getBillId();
		
		if ( StringUtils.isEmpty(billId) ) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "infoList");
		}
		
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			BillHeadModel infoObj = billService.getBill(billId);
			if ( ! loginUser.getEmpId().equals(infoObj.getInputorId()) )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "infoList");
			}
		} 
		
		UploadFile uploadedFile = FileUtil.uploadImageFileOnLocal(request
				, "infoAttachment"//request.getParameter("module")
				, "attachment"
				, ""
				, null);
		
		// if upload is done?
		if ( uploadedFile != null )
		{
			InfoAttachmentModel m = new InfoAttachmentModel();
			m.setBillId(billId);
			m.setAttachName(uploadedFile.getSysFileName());
			
			Object ret = infoService.saveInfoAttachment(m);
			
			SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("info.attachment.save.ok"), request);
		}
		
		return redirect(controllerId, "infoAttachment&billId=" + billId);
	}
	
	/**
	 * Description	: Show the info page on the user's layout.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView viewInfoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("userPage/viewInfoPage");
		
		String hostId = request.getParameter("hostUserId");
		String billId = request.getParameter( "billId" );
		
		UserModel hostUser = userService.getUserById(hostId);
		
		// Get Info List
		BillHeadModel info = new BillHeadModel();
		
		if ( StringUtils.isEmpty(billId) ) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "infoList");
		}
				
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			info = billService.getBill(billId);
			
			if (info == null)
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, "infoList");
			}
		}
				
		// getting the list here.
		InfoSModel scInfo = new InfoSModel();
		scInfo.setUserId(hostId);
		scInfo.setBillId(billId);
		List<InfoModel> infoList = infoService.getUserInfoList(scInfo);
		
		for (InfoModel obj : infoList) 
		{
			if (Constants.INFO_ITEM_TYPE_IMG.equals(obj.getDtype()))
			{
				InfoDetailSModel scDetail = new InfoDetailSModel(obj);
				scDetail.setShowYn(Constants.CONST_Y);
				List<InfoDetailModel> detail = infoService.getInfoDetailList(scDetail);
				obj.setDetailList( detail );
				obj.setTotalCount(detail.size()== 0? 0 :  (int)12/detail.size());
			}
		}
		
		mv.addObject("infoList", infoList);
		mv.addObject("info", info);
		mv.addObject("hostUser", hostUser);
		
		return mv;
	}
}
