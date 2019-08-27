
package com.kpc.eos.controller.bizSetting;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.CustTypeModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.bizSetting.CustTypeService;
import com.kpc.eos.service.bizSetting.HostCustService;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.dataMng.UserService;

public class HostCustController extends BaseEOSController {

	private HostCustService hostCustService;
	private CustTypeService custTypeService;
	private UserService userService;
	private UserItemService userItemService;
	private BizDataService bizDataService;
	private BizSettingService bizSettingService;
	
	public void setHostCustService(HostCustService hostCustService) {
		this.hostCustService = hostCustService;
	}
	
	public void setCustTypeService(CustTypeService custTypeService) {
		this.custTypeService = custTypeService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setUserItemService(UserItemService userItemService) {
		this.userItemService = userItemService;
	}
	
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	
	public void setBizSettingService(BizSettingService bizSettingService) {
		this.bizSettingService = bizSettingService;
	}
	
	public HostCustController() {
		super();
		controllerId = "HostCust";
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
	}
	
	/****************************************************************************************************************************
	* Function Name:  			custSettingList
	* Function Description		Call the view for customer setting list according to user id
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView custSettingList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		breads.add(new BreadcrumbModel("订货方管理 ", getCmdUrl("custSettingList"), true));
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		HostCustSModel sc = new HostCustSModel();
		
		String key = "HostCust_custSettingList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (HostCustSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/custSettingList", "sc", sc);
		
		//Get the customer type list by user
		List<CustTypeModel> custTypeList = custTypeService.getCustTypeListByUser(userId);
		mv.addObject("custTypeList", custTypeList);
		
		//Get the employee list by user
		List<UserModel> empList = userService.getEmployerListByUser(userId);
		
		mv.addObject("empList", empList);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			customerSettingGridAjax
	* Function Description		Retrieve the customer type list according to customer type id
	*****************************************************************************************************************************/
	public ModelAndView custSettingGridAjax(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "HostCust_custSettingList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setHostId(userId);
		
		Integer totalCount = hostCustService.getTotalCountCustSettingList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<HostCustModel> list = hostCustService.getCustSettingList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			custSettingForm
	* Function Description		Get the customer setting information according to customer type id
	*****************************************************************************************************************************/
	public ModelAndView custSettingForm(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception {
		initCmd();
		breads.add(new BreadcrumbModel("订货方管理 ", getCmdUrl("custSettingList"), true));
		String hostId = request.getParameter("hostId");
		String custId = request.getParameter("custId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		HostCustModel custSetting = null;
		if (StringUtils.isNotEmpty(hostId) && StringUtils.isNotEmpty(custId))
		{
			breads.add(new BreadcrumbModel("设置订货方", "", false));
			custSetting = hostCustService.getCustSetting(hostId, custId);
			
			if (StringUtils.isEmpty(userId) || custSetting == null || !hostId.equals(userId) || 
				!hostId.equals(custSetting.getHostUserId()) || !custId.equals(custSetting.getCustUserId()))
			{
				return redirect("HostCust", "custSettingList");
			}
		}
		else
		{
			return redirect("HostCust", "custSettingList");
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/custSettingForm");
		
		//Get sale_online_mark of user table
		String strSaleOnlineMark = userService.getUserById(hostId).getSaleOnlineMark();
		mv.addObject("saleOnlineMark", strSaleOnlineMark);
		
		//Get business setting data, check if copymark is 'Y' or 'N'
		String strCopyMark = ((BizSettingModel)bizSettingService.getBizSettingBySysType(hostId, BizSetting.COPYMARK_CODE)).getSysValueName();
		if (StringUtils.isNotEmpty(strCopyMark) && StringUtils.equals(strCopyMark, BizSetting.COPYMARK_VALUE_Y)) {
			mv.addObject("copyMark", strCopyMark);
			List<HostCustModel> custListForCopyMark = hostCustService.getCustListByHostIdForCopy(hostId);
			mv.addObject("custListForCopyMark", custListForCopyMark);
		}
		
		//Get paytype, price and pick list by host id in case sale_online_mark is 'Y'
		if (StringUtils.equals(strSaleOnlineMark, Constants.CONST_Y)){
			List<PayTypeModel> payTypeList = hostCustService.getPayTypeListByUser(hostId, custId);
			mv.addObject("payTypeList", payTypeList);
			
			//Get item price information so that apply the price to customer
			List<UserItemPropertyModel> priceList = userItemService.getItemFieldPropertyByUser(hostId, Constants.CONST_ITEM_PRICE_CODE);
			mv.addObject("priceList", priceList);
			
			//Get picking group list
			List<BizDataModel> pickList = bizDataService.getBizDataByBizCode(hostId, Constants.COSNT_BIZDATA_PICKGROUP_CODE, null);
			mv.addObject("pickList", pickList);
		}
		
		//Get Cust type list
		List<CustTypeModel> custTypeList = custTypeService.getCustTypeListByUser(hostId);
		mv.addObject("custTypeList", custTypeList);
		
		//Get the employee list by user
		List<UserModel> empList = userService.getEmployerListByUser(hostId);
		mv.addObject("empList", empList);
		
		//Get Delivery car list
		List<BizDataModel> carList = bizDataService.getBizDataByBizCode(hostId, Constants.CONST_BIZDATA_CAR_CODE, null);
		mv.addObject("carList", carList);
		
		JSONObject jsonObj = new JSONObject();
		List<HostCustModel> custSettingList = new ArrayList<HostCustModel>();
		custSettingList.add(custSetting);
		jsonObj.element("custData", custSettingList);
		
		mv.addObject("custSetting", custSetting);
		
		mv.addObject("sc", sc);
		mv.addObject("jsonObj", jsonObj.toString());
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveCustSetting
	* Function Description		Save the customer setting information.
	*****************************************************************************************************************************/
	public ModelAndView saveCustSetting(HttpServletRequest request, HttpServletResponse response, HostCustModel hostCustModel) throws Exception {
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		if (!userId.equals(hostCustModel.getHostUserId())) {
			return redirect("HostCust", "custSettingList");
		}
		hostCustService.saveCustSetting(hostCustModel);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("HostCust", "custSettingList");
	}
	
	/****************************************************************************************************************************
	* Function Name:  			hostSettingList
	* Function Description		Call the view for supply setting list according to user id
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView hostSettingList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		breads.add(new BreadcrumbModel("供货方管理 ", getCmdUrl("hostSettingList"), true));
		
		HostCustSModel sc = new HostCustSModel();
		
		String key = "HostCust_hostSettingList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (HostCustSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/hostSettingList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			customerSettingGridAjax
	* Function Description		Retrieve the customer type list according to customer type id
	*****************************************************************************************************************************/
	public ModelAndView hostSettingGridAjax(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception 
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "HostCust_hostSettingList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setCustId(userId);
		
		Integer totalCount = hostCustService.getTotalCountHostSettingList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<HostCustModel> list = hostCustService.gethostSettingList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			hostList
	* Function Description		Retrieve host list form for selecting supply
	*****************************************************************************************************************************/
	public ModelAndView hostList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		initCmd();
		
		breads.add(new BreadcrumbModel("供货方管理 ", getCmdUrl("hostSettingList"), true));
		breads.add(new BreadcrumbModel("新增供货方", "", false));
		
		HostCustSModel sc = new HostCustSModel();
		
		String key = "HostCust_hostSearchList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (HostCustSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/hostList", "sc", sc);
		mv.addObject("custUserId", SessionUtil.getUserId(request, getSystemName()));
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			hostListGridAjax
	* Function Description		Retrieve the host list
	*****************************************************************************************************************************/
	public ModelAndView hostListGridAjax(HttpServletRequest request, HttpServletResponse response, HostCustSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "HostCust_hostSearchList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setCustId(userId);
		
		Integer totalCount = hostCustService.getTotalSupplyList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<HostCustModel> list = hostCustService.getSupplyList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			settingHost
	* Function Description		Add host list to customer
	*****************************************************************************************************************************/
	public ModelAndView settingHost(HttpServletRequest request, HttpServletResponse response, HostCustModel sc) throws Exception{
		String userId = SessionUtil.getUserId(request, getSystemName());
		if (StringUtils.isEmpty(userId) || !userId.equals(sc.getCustUserId()))
		{
			return redirect("HostCust", "hostSettingList");
		}
		
		hostCustService.insertHostList(sc);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("hostcust.supply.addSuccess"), request);
		
		return redirect("HostCust", "hostSettingList");
	}
	
	/****************************************************************************************************************************
	* Function Name:  			deleteHost
	* Function Description		Delete supply to customer
	*****************************************************************************************************************************/
	public ModelAndView deleteHost(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		String hostUserId = request.getParameter("hostId");
		String custUserId = request.getParameter("custId");
		
		if (StringUtils.isEmpty(hostUserId) || StringUtils.isEmpty(custUserId) || !userId.equals(custUserId)){
			return redirect("HostCust", "hostSettingList");
		}
		
		hostCustService.deleteHost(hostUserId, custUserId);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("hostcust.supply.deleteSuccess"), request);
		return redirect("HostCust", "hostSettingList");
	}
}
