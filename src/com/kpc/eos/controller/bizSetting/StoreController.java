
package com.kpc.eos.controller.bizSetting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bizSetting.StoreModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.DepartmentModel;
import com.kpc.eos.service.bizSetting.StoreService;
import com.kpc.eos.service.dataMng.DepartmentService;

public class StoreController extends BaseEOSController {

	private StoreService storeService;
	private DepartmentService departmentService;
	
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public StoreController() {
		super();
		controllerId = "Store";
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
		breads.add(new BreadcrumbModel("仓库资料", getCmdUrl("storeList"), true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			storeList
	* Function Description		Call the view for store list according to user id
	*****************************************************************************************************************************/
	public ModelAndView storeList(HttpServletRequest request, HttpServletResponse response)
	{
		initCmd();
		
		DefaultSModel sc = new DefaultSModel();
		
		String key = "Store_storeList";
		request.setAttribute(SC_ID_SESSION, key);
		
		sc  = (DefaultSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		ModelAndView mv = new ModelAndView("bizSetting/storeList", "sc", sc);
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			custTypeGridAjax
	* Function Description		Retrieve the customer type list according to user id
	*****************************************************************************************************************************/
	public ModelAndView storeGridAjax(HttpServletRequest request, HttpServletResponse response, DefaultSModel sc) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Store_storeList");
		
		String userId = SessionUtil.getUserId(request, getSystemName());
		sc.setUserId(userId);
		
		Integer totalCount = storeService.getTotalCountStoreList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<StoreModel> list = storeService.getStoreList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("sc", sc);
		mv.addObject("page", sc.getPage());
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			storeForm
	* Function Description		Get the store information according to user id
	*****************************************************************************************************************************/
	public ModelAndView storeForm(HttpServletRequest request, HttpServletResponse response, StoreModel storeModel) throws Exception {
		initCmd();
		String storeId = request.getParameter("storeId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		StoreModel store = null;
		
		if (StringUtils.isNotEmpty(storeId))
		{
			breads.add(new BreadcrumbModel("修改仓库资料", "", false));
			store = storeService.getStore(storeId);
			if (StringUtils.isEmpty(userId) || store == null || !userId.equals(store.getUserId()))
			{
				return redirect("Store", "storeList");
			}
		}
		else
		{
			breads.add(new BreadcrumbModel("新增仓库资料", "", false));
		}
		
		if (storeModel != null && isPost(request))
		{
			store = storeModel;
		}
		
		List<DepartmentModel> deptList = departmentService.getDepartmentList(userId);
		ModelAndView mv = new ModelAndView("bizSetting/storeForm");
		
		mv.addObject("store", store);
		mv.addObject("deptList", deptList);
		return mv;
	}
	/****************************************************************************************************************************
	* Function Name:  			saveStore
	* Function Description		Save the store information.
	*****************************************************************************************************************************/
	public ModelAndView saveStore(HttpServletRequest request, HttpServletResponse response, StoreModel store) throws Exception {
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		if (StringUtils.isEmpty(userId)){
			return redirect("Store", "storeList");
		}
		store.setUserId(userId);
		
		ModelAndView mv = new ModelAndView();
		
		formErrors = store.validate();
		
		Integer exists = storeService.existStoreNo(store);
		if (exists != null)
		{
			formErrors.rejectValue("storeNo", "system.common.duplicated", new Object[]{"仓库编号"}, null);
			mv = storeForm(request, response, store);
			return mv;
		}
		
		storeService.saveStore(store);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("Store", "storeList");
	}
}
