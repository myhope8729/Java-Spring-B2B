package com.kpc.eos.controller.dataMng;

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
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.web.context.ApplicationSetting;
import com.kpc.eos.model.common.AddressModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.DepartmentModel;
import com.kpc.eos.model.dataMng.EmpMenuRightModel;
import com.kpc.eos.model.dataMng.EmpSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.common.AddressService;
import com.kpc.eos.service.dataMng.DepartmentService;
import com.kpc.eos.service.dataMng.UserService;

public class UserController extends BaseEOSController 
{
	
	private UserService userService;
	
	public String userId = null;
	
	public UserController()
	{
		super();
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void initCmd() {
		super.initCmd();
		
		controllerId = "User";
		
		breads.add(new BreadcrumbModel("资料管理", ""));
		breads.add(new BreadcrumbModel("员工资料 ", getCmdUrl("employeeList")));
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "User";
		
		// get the pre breadcrumbs.
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] empUrls = new String[]{"employeeList", "employeeForm"};
		String[] privUrls = new String[]{"employerPrivList", "employerPrivForm"};
		
		if ( ArrayUtils.contains(empUrls, methodName) )
		{
			breads.add(new BreadcrumbModel("资料管理", ""));
			breads.add(new BreadcrumbModel("员工资料 ", getCmdUrl("employeeList")));
		}
		else if ( ArrayUtils.contains(privUrls, methodName) )
		{
			breads.add(new BreadcrumbModel("资料管理", ""));
			breads.add(new BreadcrumbModel("员工授权 ", getCmdUrl("employerPrivList")));
		}
	}

	/**
	 * Description	: show the form to edit the user information.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView userForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mv = new ModelAndView("dataMng/userForm");
		
		UserModel loginUser = (UserModel) SessionUtil.getUser(request, getSystemName());
		String userId = loginUser.getUserId();
		
		UserModel user = new UserModel();
		
		if (userId != null) 
		{
			user = userService.getUserById(userId);
			user.setUserYn(loginUser.getUserYn());
			
			if (user == null || ! userId.equals(user.getUserId()))
			{
				SysMsg.addMsg(SysMsg.ERROR, "No permission  2!", request);
				
				return redirect("Main", "main");
			}
		}
		
		mv.addObject("user", user);
		
		AddressService  addrService = (AddressService) ApplicationSetting.getWebApplicationContext().getBean("addrService");
		AddressModel userLocationsTmp = addrService.getAddressByLocationId(user.getLocationId());
		
		AddressModel userLocations = null; 
		if (userLocationsTmp != null) 
		{
			userLocations = userLocationsTmp.reArrange();
		}
		
		List<AddressModel> provList = addrService.findProvinceList();
		mv.addObject("provList", provList);
		
		if (StringUtils.isNotEmpty(userLocations.getLevel2Id())) 
		{
			List<AddressModel> cityList = addrService.findChildLocationList(userLocations.getLevel2Id());
			mv.addObject("cityList", cityList);
			
			if (StringUtils.isNotEmpty(userLocations.getLevel1Id()))
			{
				List<AddressModel> areaList = addrService.findChildLocationList(userLocations.getLevel1Id());
				mv.addObject("areaList", areaList);
			}
		}
		
		mv.addObject("userLocations", userLocations);
		
		breads.clear();
		breads.add(new BreadcrumbModel("资料管理", ""));
		breads.add(new BreadcrumbModel("会员资料 ", getCmdUrl( "userForm" )));
		
		return mv;
	}
	
	
	/**
	 * Description	: Save the user information by ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveUserAjax(HttpServletRequest request, HttpServletResponse response, UserModel user) throws Exception 
	{
		String userId = SessionUtil.getUserId(request, getSystemName());
		String userIdReq = request.getParameter("userId");
		
		ResultModel rm = new ResultModel();
		ModelAndView mv =  new ModelAndView("jsonView", "result", rm);
		
		if (!userId.equals(userIdReq)) 
		{
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
			mv.addObject(rm);
			return mv;
		}
		
		if ( userService.existOtherUser(user) != null ) 
		{
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			rm.setResultMsg(MessageUtil.getMessage("user.empno.duplicated", new Object[]{user.getUserNo()}));
			mv.addObject(rm);
			return mv;
		}
		
		UserModel oldUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		user.setEmpId(oldUser.getEmpId());
		
		// save the user information
		userService.saveUser(user);
		
		// if userKind changed?
		if ( ! oldUser.getUserKind().equals(user.getUserKind()) )
		{
			userService.saveUserByUserKind(user, oldUser.getUserKind());
			
			SysMsg.addMsg(SysMsg.SUCCESS, ResultModel.RESULT_SUCCESS_MSG, request);
		}
		
		// reset the session.
		oldUser.copyFromUser(user);
		SessionUtil.setUser(request, oldUser, getSystemName());
		
		return mv;
	}
	
	/**
	 * Description	: Show the employee List.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView employeeList(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		EmpSModel sc = new EmpSModel();
		
		ModelAndView mv = new ModelAndView("dataMng/employeeList");
		
		// getting the search model from session
		String key = "User_employeeList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (EmpSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		pageTitle = "员工资料";
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the employees list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView employeeGridAjax(HttpServletRequest request, HttpServletResponse response, EmpSModel sc) throws Exception 
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "User_employeeList");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		userId = loginUser.getUserId();
		
		sc.setUserId(userId);
		if (! Constants.CONST_Y.equals(loginUser.getUserYn()))
		{
			sc.setUserYn(Constants.CONST_N);
		}
		
		Integer totalCount = userService.countEmployerListCountByUserPerm(sc);
		sc.getPage().setRecords(totalCount);
		List<UserModel> list = userService.getEmployerListByUserPerm(sc);

		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: show the employee form to create or edit the employee's data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView employeeForm(HttpServletRequest request, HttpServletResponse response, UserModel empForm) throws Exception 
	{
		initCmd();
		
		ModelAndView mv = new ModelAndView("dataMng/employeeForm");
		
		String empId = request.getParameter("empId");
		userId = SessionUtil.getUserId(request, getSystemName());
		
		UserModel emp = new UserModel();
		
		// if user has alredy submitted the emp form, keep the emp model.
		if (empForm != null && isPost(request)) {
			emp = empForm;
		}
		
		if (StringUtils.isNotEmpty(empId)) 
		{
			if (empForm != null && isPost(request)){
				emp = empForm;
			}else{
				emp = userService.getEmployerByEmpId(empId);
			}
			
			if (emp == null)
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				
				return redirect("User", "employeeList");
			}
			pageTitle = "修改员工资料";
		} 
		else
		{
			pageTitle = "新增员工资料";
		}
		
		DepartmentService  deptService = (DepartmentService) ApplicationSetting.getWebApplicationContext().getBean("departmentService");
		List<DepartmentModel> deptList = deptService.getDepartmentList(userId);
		
		emp.setUserId(userId);
		
		mv.addObject("emp", emp);
		mv.addObject("deptList", deptList);
		
		breads.add(new BreadcrumbModel(pageTitle, "", false));
		
		return mv;
	}
	
	public ModelAndView employeeQRCodeForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		initCmd();
		
		ModelAndView mv = new ModelAndView("dataMng/employeeQRCodeForm");
		
		String empId = request.getParameter("empId");
		userId = SessionUtil.getUserId(request, getSystemName());
		
		UserModel emp = new UserModel();
		
		if (StringUtils.isNotEmpty(empId)) 
		{
			emp = userService.getEmployerByEmpId(empId);
			if (emp == null)
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				
				return redirect("User", "employeeList");
			}
			pageTitle = "推广二维码 ";
		} 
		else
		{
			return redirect("User", "employeeList");
		}
		
		emp.setUserId(userId);
		
		mv.addObject("emp", emp);
		
		breads.add(new BreadcrumbModel(pageTitle, "", false));
		
		return mv;
	}
	
	/**
	 * Description	: Save the employee form.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param emp
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveEmployee(HttpServletRequest request, HttpServletResponse response, UserModel emp) throws Exception 
	{
		// if request is not post, redirect to the employee form.
		if (!isPost(request)) 
		{
			return redirect("User", "employeeForm");
		}
		
		ModelAndView mv = new ModelAndView();
		
		// start to validate the emp form now.
		formErrors = emp.validate(); 
		
		if (formErrors.hasErrors())
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.save.erorr"), request);
			mv = employeeForm(request, response, emp);
			return mv;
		}
		
		// check if same emp no exists?
		Integer exist = userService.existEmployer(emp);
		if (exist != null)
		{
			formErrors.rejectValue("empNo", "user.empno.duplicated", new Object[]{emp.getEmpNo()}, null);
			mv = employeeForm(request, response, emp);
			return mv;
		}
		
		userService.saveEmployer(emp);
		
		if (Constants.CONST_Y.equals(emp.getFirstMark())){
			UserModel user = (UserModel) SessionUtil.getUser(request, getSystemName());
			user.setEmpName(emp.getEmpName());
			SessionUtil.setUser(request, user, getSystemName());
		}
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.common.success"), request);
		
		return redirect("User", "employeeList");
	}
	
	/**
	 * Description	: show the employers list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView employerPrivList(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mv = new ModelAndView("dataMng/employerPrivList");
		
		// search model
		EmpSModel sc = new EmpSModel();

		// getting the search model from session
		String key = "User_employeePrivList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (EmpSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		pageTitle = "员工授权";
		
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the employer list by ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView employerPrivGridAjax(HttpServletRequest request, HttpServletResponse response, EmpSModel sc) throws Exception 
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "User_employeePrivList");
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		userId = loginUser.getUserId();
		
		sc.setUserId(userId);
		if (! Constants.CONST_Y.equals(loginUser.getUserYn()))
		{
			sc.setUserYn(Constants.CONST_N);
		}
		
		Integer totalCount = userService.countEmployerListCountByUserPerm(sc);
		sc.getPage().setRecords(totalCount);
		
		List<UserModel> list = userService.getEmployerListByUserPerm(sc);

		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Show the emp's privilege form.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView employerPrivForm(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception 
	{
		ModelAndView mv = new ModelAndView("dataMng/employerPrivForm");
		pageTitle = "设置权限  ";
		
		String empId = request.getParameter("empId");
		
		UserModel emp = userService.getEmployerByEmpId(empId);
		if (emp == null )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "employerPrivList");
		}
		
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		userId = loginUser.getUserId();
		
		// If the logged in user has an emp, this must be an invalid request.
		if (! userId.equals(emp.getUserId()))
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "employerPrivList");
		}
		
		// set the search param to get the list from database.
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("empId", empId);
		map.put("userKind", loginUser.getUserKind());
		
		List<EmpMenuRightModel> empMenuRightList = userService.getEmployerPrivList(map);
		
		mv.addObject("empMenuRightList", empMenuRightList);
		
		breads.add(new BreadcrumbModel(pageTitle, "#"));
		
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Save the user(emp exactly) menu rights.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView saveEmployerPriv(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserModel loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		userId = loginUser.getUserId();
		
		// validation
		String empId = request.getParameter("empId");
		
		UserModel emp = userService.getEmployerByEmpId(empId);
		if (emp == null )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "employerPrivList");
		}
		
		// If the logged in user has an emp, this must be an invalid request.
		if (! userId.equals(emp.getUserId()))
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, "employerPrivList");
		}
		
		// emp can't change his own permission until he has an administrative privilege.
		if ( ! loginUser.isHostYn() && loginUser.getEmpId().equals(empId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
			return redirect(controllerId, "employerPrivList");
		}
		
		
		String[] menuIds = request.getParameterValues("menuId[]");
		
		userService.saveEmployerPriv(empId, menuIds, userId);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.common.success"), request);
		
		return redirect("User", "employerPrivList");
	}
	
	/**
	 * Description	: Show the password change form.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView changePwdForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mv = new ModelAndView("dataMng/changePwdForm");
		UserModel user = (UserModel)SessionUtil.getUser(request, getSystemName());
		mv.addObject("user", user);
		
		breads.clear();
		breads.add(new BreadcrumbModel("资料管理", ""));
		breads.add(new BreadcrumbModel("修改密码 ", getCmdUrl( "changePwdForm" )));
		
		return mv;
	}
	
	/**
	 * Description	: Change the user's pasword.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView changePasswordAjax(HttpServletRequest request, HttpServletResponse response, UserModel user) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		// get params
		String oldPwd 		= request.getParameter("oldPwd");
		String pwd 			= request.getParameter("pwd");
		String confirmPwd 	= request.getParameter("confirmPwd");
		
		// validation
		String empId = SessionUtil.getEmpId(request, getSystemName());
		String currentPwd = userService.getEmpPwdById(empId);
		
		if (! oldPwd.equals(currentPwd)) 
		{
			rm.setResultMsg(MessageUtil.getMessage("user.current.password.wrong"));
			mv.addObject("result", rm);
			return mv;
		}
		
		if (! empId.equals(user.getEmpId())) 
		{
			rm.setResultMsg(MessageUtil.getMessage("system.common.invalid.request"));
			mv.addObject("result", rm);
			return mv;
		}
		
		if (! pwd.equals(confirmPwd)) 
		{
			rm.setResultMsg(MessageUtil.getMessage("user.mismatch.confirmPassword"));
			mv.addObject("result", rm);
			return mv;
		}
		
		// change the password
		userService.changePassword(user);
		
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		rm.setResultMsg(MessageUtil.getMessage("user.password.changed")	);
		
		mv.addObject("result", rm);
		return mv;
	}
}
