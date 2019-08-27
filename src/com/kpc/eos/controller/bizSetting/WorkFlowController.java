
package com.kpc.eos.controller.bizSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.CustomerModel;
import com.kpc.eos.model.bizSetting.EmployeeModel;
import com.kpc.eos.model.bizSetting.WorkFlowGroupModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.model.bizSetting.WorkFlowSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.WorkFlowService;

public class WorkFlowController extends BaseEOSController {

	private WorkFlowService workFlowService;
	private BizDataService bizDataService;
	
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	
	public WorkFlowController() {
		super();
		controllerId = "WorkFlow";
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
		breads.add(new BreadcrumbModel("业务流程 ", getCmdUrl("workFlowList"), true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			workFlowList
	* Function Description		Call the view for workflow list according to user id
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView workFlowList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
				
		WorkFlowSModel sc = new WorkFlowSModel();
		
		String key = "WorkFlow_workFlowList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (WorkFlowSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/workFlowList", "sc", sc);
		
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			workFlowGridAjax
	* Function Description		Retrieve the workflow list according to user id
	*****************************************************************************************************************************/
	public ModelAndView workFlowGridAjax(HttpServletRequest request, HttpServletResponse response, WorkFlowSModel sc) throws Exception
	{
		ModelAndView mv = new ModelAndView("jsonView");
		String formObj = request.getParameter("formObj");
		if (StringUtils.isEmpty(formObj)){
			request.setAttribute(SC_ID_SESSION, "WorkFlow_workFlowList");
		}
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		String pagingYn = request.getParameter("pagingYn");
		sc.setPagingYn(pagingYn);
		sc.setState(null);
		
		Integer totalCount = workFlowService.getTotalCountWorkFlowList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<WorkFlowModel> list = workFlowService.getWorkFlowList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			workFlowForm
	* Function Description		Get the workflow information information according to workFlow ID
	*****************************************************************************************************************************/
	public ModelAndView workFlowForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		String workFlowId = request.getParameter("workFlowId");
		String userId = SessionUtil.getUserId(request, getSystemName());
				
		WorkFlowModel workFlow = null;
		
		if (StringUtils.isNotEmpty(workFlowId)){
			workFlow = workFlowService.getWorkFlow(workFlowId);
			if (StringUtils.isEmpty(userId) || workFlow == null || !userId.equals(workFlow.getUserId()))
			{
				return redirect("WorkFlow", "workFlowList");
			}
			breads.add(new BreadcrumbModel("修改业务流程", "", false));
		}
		else
		{
			breads.add(new BreadcrumbModel("新增业务流程", "", false));
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/workFlowForm");
		
		List<EmployeeModel> empList = workFlowService.getEmployeeList(userId, workFlow==null?null:workFlow.getWorkFlowId());
		
		mv.addObject("workFlow", workFlow);
		mv.addObject("workFlowId", workFlowId);
		mv.addObject("employee", empList);
		return mv;
	}
		
	/****************************************************************************************************************************
	* Function Name:  			saveWorkFlow
	* Function Description		Save workflow data
	*****************************************************************************************************************************/
	public ModelAndView saveWorkFlow(HttpServletRequest request, HttpServletResponse response, WorkFlowModel workFlow) throws Exception {
		String userId = SessionUtil.getUserId(request, getSystemName());
		workFlow.setUserId(userId);
		
		workFlowService.saveWorkFlow(workFlow, isMobileClient());
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("WorkFlow", "workFlowList");
	}
	
	/****************************************************************************************************************************
	* Function Name:  			workFlowGroupList
	* Function Description		Call the view for workflow group list according to workflow id
	*****************************************************************************************************************************/
	public ModelAndView workFlowGroupList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		WorkFlowSModel sc = new WorkFlowSModel();
		String key = "WorkFlow_workFlowGroupList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (WorkFlowSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		String workFlowId = request.getParameter("workFlowId");
		WorkFlowModel workFlow = workFlowService.getWorkFlow(workFlowId);
		
		breads.add(new BreadcrumbModel("审批组", getCmdUrl("workFlowGroupList&workFlowId=" + workFlowId), true));
		
		if (StringUtils.isEmpty(userId) || workFlow == null || !userId.equals(workFlow.getUserId())){
			return redirect("WorkFlow", "workFlowList");
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/workFlowGroupList", "sc", sc);
		mv.addObject("page", sc.getPage());
		mv.addObject("workFlow", workFlow);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			workFlowGroupGridAjax
	* Function Description		Retrieve the workflow group list according to workflow id
	*****************************************************************************************************************************/
	public ModelAndView workFlowGroupGridAjax(HttpServletRequest request, HttpServletResponse response, WorkFlowSModel sc) throws Exception
	{
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "WorkFlow_workFlowGroupList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		String workFlowId = request.getParameter("workFlowId");
		sc.setWorkFlowId(workFlowId);
		sc.setUserId(userId);
		
		Integer totalCount = workFlowService.getTotalCountWorkFlowGroupList(workFlowId);
		sc.getPage().setRecords(totalCount);
		
		List<WorkFlowGroupModel> list = workFlowService.getWorkFlowGroupList(sc);
		
		for (int i = 0; i < list.size(); i++){
			list.get(i).setCustShortNameList(workFlowService.getWorkFlowGroupCustList(list.get(i)));
			list.get(i).setEmpList(workFlowService.getWorkFlowGroupEmpList(list.get(i)));
		}
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
		
	/****************************************************************************************************************************
	* Function Name:  			workFlowGroupCustForm
	* Function Description		Call the view for workflow group customer form according to workFlowId, seqNo
	*****************************************************************************************************************************/
	public ModelAndView workFlowGroupCustForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		
		String workFlowId = request.getParameter("workFlowId");
		String seqNo = request.getParameter("seqNo");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		List<CustomerModel> custList = null;
		WorkFlowGroupModel workFlowGroup = null;
		
		if (StringUtils.isNotEmpty(workFlowId) && StringUtils.isNotEmpty(seqNo))
		{
			workFlowGroup = workFlowService.getWorkFlowGroup(workFlowId, seqNo);
			if (workFlowGroup == null || !userId.equals(workFlowGroup.getUserId()))
			{
				return redirect("WorkFlow", "workFlowGroupList&workFlowId=" + workFlowId);
			}
			custList = workFlowService.getCustomerCheckedList(workFlowId, seqNo, userId);
			
			breads.add(new BreadcrumbModel("审批组", getCmdUrl("workFlowGroupList&workFlowId=" + workFlowId), true));
			breads.add(new BreadcrumbModel("设置审批组订货方", "", false));
		}
		else
		{
			return redirect("WorkFlow", "workFlowList");
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/workFlowGroupCustForm");
		
		mv.addObject("workFlowGroup", workFlowGroup);
		mv.addObject("customerList", custList);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			workFlowGroupEmpForm
	* Function Description		Call the view for workflow group employee form according to workFlowId, seqNo
	*****************************************************************************************************************************/
	public ModelAndView workFlowGroupEmpForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		
		String workFlowId = request.getParameter("workFlowId");
		String seqNo = request.getParameter("seqNo");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		List<EmployeeModel> empList = null;
		WorkFlowGroupModel workFlowGroup = null;
		
		if (StringUtils.isNotEmpty(workFlowId) && StringUtils.isNotEmpty(seqNo))
		{
			workFlowGroup = workFlowService.getWorkFlowGroup(workFlowId, seqNo);
			if (workFlowGroup == null || !userId.equals(workFlowGroup.getUserId()))
			{
				return redirect("WorkFlow", "workFlowGroupList&workFlowId=" + workFlowId);
			}
			empList = workFlowService.getEmployeeCheckedList(workFlowId, seqNo, userId);
			
			breads.add(new BreadcrumbModel("审批组", getCmdUrl("workFlowGroupList&workFlowId=" + workFlowId), true));
			breads.add(new BreadcrumbModel("设置审批组处理人", "", false));
		}
		else
		{
			return redirect("WorkFlow", "workFlowList");
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/workFlowGroupEmpForm");
		
		mv.addObject("workFlowGroup", workFlowGroup);
		mv.addObject("empList", empList);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveWorkFlowGroupCust
	* Function Description		Save workflow group customer data
	*****************************************************************************************************************************/
	public ModelAndView saveWorkFlowGroupCust(HttpServletRequest request, HttpServletResponse respons, WorkFlowGroupModel workFlow) throws Exception
	{
		String userId = SessionUtil.getUserId(request, getSystemName());
		workFlow.setUserId(userId);
		
		workFlowService.saveWorkFlowGroupCust(workFlow);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("WorkFlow", "workFlowGroupList&workFlowId=" + workFlow.getWorkFlowId());
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveWorkFlowGroupEmp
	* Function Description		Save workflow group customer data
	*****************************************************************************************************************************/
	public ModelAndView saveWorkFlowGroupEmp(HttpServletRequest request, HttpServletResponse respons, WorkFlowGroupModel workFlow) throws Exception
	{
		String userId = SessionUtil.getUserId(request, getSystemName());
		workFlow.setUserId(userId);
		
		workFlowService.saveWorkFlowGroupEmp(workFlow);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("WorkFlow", "workFlowGroupList&workFlowId=" + workFlow.getWorkFlowId());
	}
	
	/****************************************************************************************************************************
	* Function Name:  			workFlowGroupForm
	* Function Description		Get the workflow group information information according to workFlow ID and seqNo
	*****************************************************************************************************************************/
	public ModelAndView workFlowGroupForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		String workFlowId = request.getParameter("workFlowId");
		String seqNo = request.getParameter("seqNo");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		WorkFlowGroupModel workFlowGroup = null;

		List<BizDataModel> itemType = bizDataService.getBizDataByBizCode(userId, Constants.CONST_BIZDATA_ITEM_TYPE_CODE, null);
		
		if (itemType == null || itemType.size() == 0){
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("workflow.workflowgroup.noitemtype"), request);
			return redirect("WorkFlow", "workFlowGroupList&workFlowId=" + workFlowId);
		}
		
		if (StringUtils.isNotEmpty(workFlowId) && StringUtils.isNotEmpty(seqNo))
		{
			workFlowGroup = workFlowService.getWorkFlowGroup(workFlowId, seqNo);
			if (StringUtils.isEmpty(userId) || workFlowGroup == null || !userId.equals(workFlowGroup.getUserId()))
			{
				return redirect("WorkFlow", "workFlowGroupList&workFlowId=" + workFlowId);
			}
			breads.add(new BreadcrumbModel("审批组", getCmdUrl("workFlowGroupList&workFlowId=" + workFlowId), true));
			breads.add(new BreadcrumbModel("修改审批组", "", false));
		}
		else
		{
			breads.add(new BreadcrumbModel("审批组", getCmdUrl("workFlowGroupList&workFlowId=" + workFlowId), true));
			breads.add(new BreadcrumbModel("新增审批组", "", false));
		}
		
		ModelAndView mv = new ModelAndView("bizSetting/workFlowGroupForm");
		
		mv.addObject("workFlowId", workFlowId);
		mv.addObject("itemType", itemType);
		mv.addObject("seqNo", seqNo);
		mv.addObject("workFlowGroup", workFlowGroup);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveWorkFlowGroup
	* Function Description		Save the workflow group information
	*****************************************************************************************************************************/
	public ModelAndView saveWorkFlowGroup(HttpServletRequest request, HttpServletResponse response, WorkFlowGroupModel workFlowGroup) throws Exception
	{
		workFlowService.saveWorkFlowGroup(workFlowGroup);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("WorkFlow", "workFlowGroupList&workFlowId=" + workFlowGroup.getWorkFlowId());
	}
}
