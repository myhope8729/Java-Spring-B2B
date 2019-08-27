package com.kpc.eos.controller.dataMng;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.DepartmentModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.dataMng.DepartmentService;

/**
 * Filename		: DepartmentController.java
 * Description	: Management class for the user's departments.
 * 2017
 * @author		: RKRK
 */
public class DepartmentController extends BaseEOSController 
{
	private DepartmentService departmentService;
	ModelAndView mv = null;
	
	public DepartmentController() 
	{
		super();
		controllerId = "Dept";
	}
		
	/* 
	 * @see com.kpc.eos.controller.BaseEOSController#initCmd()
	 */
	public void initCmd()
	{
		super.initCmd();
		breads.add(new BreadcrumbModel("资料管理", "", false));
		breads.add(new BreadcrumbModel("部门资料", getCmdUrl( "departmentList" ), true));
	}
	
	/**
	 * Description	: Set the department service.
	 * @author 		: RKRK
	 * @param departmentService
	 * 2017
	 */
	public void setDepartmentService(DepartmentService departmentService) 
	{
		this.departmentService = departmentService;
	}
	
	/**
	 * Description	: Show the departments list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView departmentList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		
		DefaultSModel sc = new DefaultSModel();
		
		String key = "Department_departmentList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (DefaultSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		
		mv = new ModelAndView( "dataMng/departmentList", "sc", sc );
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/**
	 * Description	: show the departments list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView departmentGridAjax(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Department_departmentList");
		
		UserModel user = (UserModel)SessionUtil.getUser(request, getSystemName());
		sc.setUserId(user.getUserId());
		
		Integer totalCount = departmentService.getTotalCountDeptList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<DepartmentModel> list = departmentService.getDepartmentListGridAjax(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/**
	 * Description	: List the departments of the current user.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView departmentForm(HttpServletRequest request, HttpServletResponse response, DepartmentModel deptModel) throws Exception 
	{
		initCmd();
		
		mv = new ModelAndView( "dataMng/departmentForm" );
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		String deptId = request.getParameter( "deptId" );
		
		DepartmentModel dept = null;
		
		if (StringUtils.isNotEmpty(deptId)) 
		{
			dept = departmentService.getDepartment(deptId);
			if (userId == null || ! userId.equals(dept.getUserId()))
			{
				return redirect("Dept", "departmentList");
			}
			pageTitle = "修改部门资料";
		} 
		else 
		{
			pageTitle = "新增部门资料";
		}
		
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		List<DepartmentModel> deptList = departmentService.getDepartmentList(userId);
		
		if (deptModel != null && isPost(request))
		{
			dept = deptModel;
		}
		
		mv.addObject("dept", dept);
		mv.addObject("deptList", deptList);
		
		return mv;
	}
	
	/**
	 * Description	: Save the department data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param dept
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView saveDepartment(HttpServletRequest request, HttpServletResponse response, DepartmentModel dept) throws Exception 
	{
		
		dept.setUserId(SessionUtil.getUserId(request, getSystemName()));
		
		formErrors = dept.validate();
		
		Integer exists = departmentService.existDepartmentNo(dept);
		if (exists != null)
		{
			formErrors.rejectValue("deptNo", "system.common.duplicated", new Object[]{"部门编号"}, null);
			mv = departmentForm(request, response, dept);
			return mv;
		}
		
		departmentService.saveDepartment(dept);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.common.success"), request);
		
		return redirect("Dept", "departmentList");
	}
	
}
