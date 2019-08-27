
package com.kpc.eos.core.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.kpc.eos.controller.userPage.UserPageController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.exception.BaseException;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.model.SearchModel;
import com.kpc.eos.core.util.MenuUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.userPage.ShopCartModel;
import com.kpc.eos.service.dataMng.DepartmentService;
import com.kpc.eos.service.userPage.UserPageService;

public abstract class BaseController extends MultiActionController 
{
	public static final String SC_ID_SESSION = "sessionKey";
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected List<BreadcrumbModel> breads = null;
	
	protected String controllerId = null;
	
	protected String pageTitle = null;
	
	protected FormErrors formErrors = null;
	
	public abstract String getSystemName();
	
	private boolean isMobileClient = false;
	
	/**
	 * Description	: command initialization function.
	 * 				  When getting a request, this function will be called 
	 * 				  before running a cmd's method automatically.
	 * NOTE			: No need to call this method in the controller's method.
	 * @author 		: RKRK
	 * 2017
	 */
	public abstract void initCmd();

	public abstract void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
	public BaseController() {
		super();
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		String 			methodName = null;
		ModelAndView 	mv;

		try {
			
			System.out.println("current Menu :" + SessionUtil.getData(request, Constants.SESSION_KEY_CURRENT_MENU));
			
			request.setAttribute(Constants.REQUEST_KEY_SYSTEMNAME, getSystemName());
			request.setAttribute("SYSTEM_MILLISECONDS", new Date().getTime());

			methodName = getMethodNameResolver().getHandlerMethodName(request);
			mv = super.handleRequestInternal(request, response);

			request.setAttribute(Constants.SESSION_KEY_LOGIN_USER, 	getLoginUser(request));
			request.setAttribute(Constants.SESSION_KEY_CURRENT_MENU, getCurrentMenuId(request));
			
			String viewName =  mv.getViewName();
			// Created by rmh.
			if(mv!= null && isMobileClient) {
				if(!viewName.equals("jsonView") && !viewName.startsWith("redirect")) {
					viewName = Constants.STR_MOBILE + "/" + viewName;
					mv.setViewName(viewName);
				}
			}
			
			if(mv != null)
				request.setAttribute(Constants.REQUEST_VIEW_NAME, 		mv.getViewName());

			if (!isAjaxRequest(request)) 
			{
				mv.addObject("breadcrumb", breads);
				request.setAttribute("pageTitle", pageTitle);
			}
			
			// ============ Set the formErrors var in request.
			if (mv != null)
			{
				// if GET method, we don't need the formErrors.
				if (! isPost(request)) 
				{
					formErrors = new FormErrors(new CommonModel(), "target");
				}
			}
			request.setAttribute("formErrors", formErrors);
			
			// ============ Set the SearchModel in session.
			
			Object obj = null;
			ModelMap mm = mv.getModelMap();
			if (mm != null)
			{
				obj = mm.get("sc");
			}
			
			if (obj instanceof DefaultSModel) 
			{
				DefaultSModel model = (DefaultSModel) obj;
				
				if (model != null /*&& model.getSessionVar()==true*/)
				{
					String key = (String)request.getAttribute(SC_ID_SESSION);
					SearchModelUtil.storeSearchModel(key, obj, request);
				}
			}
			
			// shoppingcart information here.
			if ( isMobileClient() )
			{
				if (! mv.getModel().containsKey("shopCartNum") ) {
					ShopCartModel cartSc = new ShopCartModel();
					cartSc.setCookieId( UserPageController.getCookieShopCart(request) );
					
					UserPageService  userPageService = (UserPageService) ApplicationSetting.getWebApplicationContext().getBean("userPageService");
					Integer totalAmt = userPageService.getQtyTotalInCart(cartSc);
					
					mv.addObject("shopCartNum", totalAmt==null? 0 : totalAmt);
				}
			}
			
			return mv;

		} catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("BaseController.handleRequestInternal() 500 ", e);
			}

			//json view로 return 하는 method naming
			if (isAjaxRequest(request) ) {
	            String message = null;

	            if (e instanceof BaseException) {
	                BaseException be = (BaseException) e;
	                message = be.getMessage();
	            } else {
	                message = e.getClass().getName() + ": " + e.getMessage();
	            }

				return new ModelAndView("jsonView", "result", new ResultModel(ResultModel.RESULT_FAIL_CODE, message));
			}

			throw e;
		}
	}

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {

		UserModel user = getLoginUser(request);

		if (user != null) {
			Object command = binder.getTarget();

			if (command instanceof CommonModel) {
				CommonModel model = (CommonModel) command;
				
				model.setCreateBy(user.getEmpId());
				model.setUpdateBy(user.getEmpId());
				model.setClientIp(getClientAddr(request));
				
			} else if (command instanceof SearchModel) {
				SearchModel model = (SearchModel) command;

				model.setLoginUserNo(user.getEmpId());
				model.setSystemName(getSystemName());
				model.setClientIp(getClientAddr(request));
			}
		}else{
			Object command = binder.getTarget();
			if (command instanceof CommonModel) {
				CommonModel model = (CommonModel) command;
				model.setClientIp(getClientAddr(request));
			} else if (command instanceof SearchModel) {
				SearchModel model = (SearchModel) command;
				model.setClientIp(getClientAddr(request));
			}
		}
		
		super.initBinder(request, binder);
		
	}
	
	@Override
	protected void bind(HttpServletRequest request, Object obj) throws Exception
	{
		super.bind(request, obj);
	}

	
	/**
	 * getLoginUser
	 * ===================
	 * @param request
	 * @return
	 */
	public UserModel getLoginUser(HttpServletRequest request) {
		return (UserModel) SessionUtil.getUser(request, getSystemName());
	}

	public String getClientAddr(HttpServletRequest request) {
		return request.getRemoteAddr()==null?"":request.getRemoteAddr();
	}

	// ----------------------- By Liki -------------------------------- /
	public static ModelAndView redirect(String controller, String cmd, HashMap<String, String> params)
	{
		String url = BaseController.getCmdUrl(controller, cmd, params);
		
		return new ModelAndView("redirect:" + url);
	}
	
	public static ModelAndView redirect(String controller, String cmd) 
	{
		return redirect(controller, cmd, null);
	}
	
	public String getCmdUrl( String cmd )
	{
		return BaseController.getCmdUrl(controllerId , cmd, null);
	}
	
	public static String getCmdUrl(String controller, String cmd)
	{
		return BaseController.getCmdUrl(controller, cmd, null);
	}
	
	public static String getCmdUrl(String controller, String cmd, HashMap<String, String> params)
	{
		String url = String.format("%s.do?cmd=%s", controller, cmd);
		
		if (params != null && !params.isEmpty()) 
		{
			Iterator<String> keysItr = params.keySet().iterator();
			while (keysItr.hasNext()) 
			{
				String key = keysItr.next();
				String value = params.get(key);
				url += "&" + key + "=" + value;
			}
		}
		
		return url;
	}
	
	public boolean isAjaxRequest(HttpServletRequest request)
	{
		return "XMLHttpRequest".equals( request.getHeader("X-Requested-With") );
	}
	
	/**
	 * isPost
	 * ---------------------------------------------
	 * @param request
	 * @return
	 * check if the request is by POST method.
	 */
	public boolean isPost(HttpServletRequest request)
	{
		return METHOD_POST .equals(request.getMethod());
	}
	
	public ModelAndView ajaxReturn(ModelAndView mv, ResultModel rm)
	{
		if (mv == null)
		{
			mv = new ModelAndView("jsonView");
		}
		
		mv.addObject("result", rm);
		
		return mv;
	}
	
	/**
	 * @param isMobileClient the isMobileClient to set
	 */
	public void setMobileClient(Boolean isMobileClient) {
		this.isMobileClient = isMobileClient.booleanValue();
	}

	/**
	 * @return the isMobileClient
	 */
	public boolean isMobileClient() {
		return isMobileClient;
	}	
	
	public String getCurrentMenuId(HttpServletRequest request) {
		return (String) SessionUtil.getData(request, Constants.SESSION_KEY_CURRENT_MENU);
	}
}
