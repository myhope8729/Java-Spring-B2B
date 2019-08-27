
package com.kpc.eos.controller.bizSetting;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.CustTypeModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.CustTypeService;
import com.kpc.eos.service.bizSetting.HostCustService;
import com.kpc.eos.service.bizSetting.PayTypeService;

public class BizDataController extends BaseEOSController 
{

	private BizDataService 		bizDataService;
	private HostCustService 	hostCustService;
	private PayTypeService 		payTypeService;
	private CustTypeService 	custTypeService;
	
	public void setBizDataService(BizDataService bizDataService) {
		this.bizDataService = bizDataService;
	}
	
	public void setHostCustService(HostCustService hostCustService) 
	{
		this.hostCustService = hostCustService;
	}
	
	public void setPayTypeService(PayTypeService payTypeService) 
	{
		this.payTypeService = payTypeService;
	}
	
	
	public void setCustTypeService(CustTypeService custTypeService) 
	{
		this.custTypeService = custTypeService;
	}
	
	public BizDataController() {
		super();
		controllerId = "BizData";
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
		breads.add(new BreadcrumbModel("业务资料  ", getCmdUrl("bizDataList"), true));
	}
	
	/****************************************************************************************************************************
	* Function Name:  			bizDataList
	* Function Description		Call the view for business data list
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView bizDataForm(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		initCmd();
		ModelAndView mv = new ModelAndView("bizSetting/bizDataForm");
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			bizDataFormGridAjax
	* Function Description		Retrieve the business main data
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView bizDataFormGridAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<BizDataModel> list = bizDataService.getBizDataMainList();
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("rows", list);
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			bizDataContentAjax
	* Function Description		Retrieve the business main data
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView bizDataContentAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String userId = SessionUtil.getUserId(request, getSystemName());
		String codeId = request.getParameter("codeId");
		
		List<BizDataModel> list = bizDataService.getBizDataList(userId, codeId);
		
		//Construct header model for jqgrid
		BizDataModel header = list.get(0);
		
		List<String> propNamesList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(header.getC1())){
			propNamesList.add(header.getC1());
		}
		if (StringUtils.isNotEmpty(header.getC2())){
			propNamesList.add(header.getC2());
		}
		if (StringUtils.isNotEmpty(header.getC3())){
			propNamesList.add(header.getC3());
		}
		if (StringUtils.isNotEmpty(header.getC4())){
			propNamesList.add(header.getC4());
		}
		if (StringUtils.isNotEmpty(header.getC5())){
			propNamesList.add(header.getC5());
		}
		
		list.remove(0);
		
		list.add(new BizDataModel());
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("colNames", propNamesList);
		jsonObj.element("rowData", list);
				
		ModelAndView mv = new ModelAndView("jsonView");
		
		mv.addObject("jsonObj", jsonObj);
		mv.addObject("rows", list);
		
		if ( Constants.BIZDATA_PREPAY.equals(codeId) )
		{
			// cust type list.
			List<CustTypeModel> custTypeList = custTypeService.getCustTypeListByUser( userId );
			mv.addObject("custList", custTypeList);
			
			for (CustTypeModel ct : custTypeList)
			{
				List<PayTypeModel> paytypeList = new ArrayList<PayTypeModel>();
				
				//String custTypeId= header.getC2();
				String custTypeId= ct.getCustTypeId();
				
				if ( StringUtils.isNotEmpty(custTypeId) )
				{
					PayTypeSModel ptSc = new PayTypeSModel(userId, null);
					ptSc.setCusttypeId(custTypeId);
					ptSc.setIsGroup( "1" );
					
					paytypeList = payTypeService.getUserPrePayTypeForCustList(ptSc);
				}
				ct.setPpList(paytypeList);
			}
		}
		
		return mv;
	}
	
	/****************************************************************************************************************************
	* Function Name:  			saveBizDataAjax
	* Function Description		Save the business data by user id
	 * @throws Exception 
	*****************************************************************************************************************************/
	public ModelAndView saveBizDataAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String[] c1 = request.getParameterValues("c1");
		String[] c2 = request.getParameterValues("c2");
		String[] c3 = request.getParameterValues("c3");
		String[] c4 = request.getParameterValues("c4");
		String[] c5 = request.getParameterValues("c5");
		String codeId = request.getParameter("codeId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		String empId = SessionUtil.getEmpId(request, getSystemName());
		
		bizDataService.saveBizData(userId, empId, codeId, c1, c2, c3, c4, c5);
		
		return new ModelAndView("jsonView", "result", new ResultModel());
	}
	
	public ModelAndView deleteBizDataAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String[] seqNo = request.getParameter("seqNo").split(",");
		String codeId = request.getParameter("codeId");
		String userId = SessionUtil.getUserId(request, getSystemName());
		
		bizDataService.deleteBizData(userId, codeId, seqNo);
		return new ModelAndView("jsonView", "result", new ResultModel());
	}
	
	/**
	 * Description	: get the prepay type list by userId and custtype.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView loadPrePayTypeAjax(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mv = new ModelAndView("jsonView");
		ResultModel rm = new ResultModel(ResultModel.RESULT_SUCCESS_CODE, "");
		
		String custTypeId = request.getParameter("custTypeId");
		String rUserId = SessionUtil.getUserId(request, getSystemName());
		
		// prepay list
		List<PayTypeModel> paytypeList = new ArrayList<PayTypeModel>();
		
		if ( StringUtils.isNotEmpty(custTypeId) && StringUtils.isNotEmpty(rUserId) )
		{
			PayTypeSModel ptSc = new PayTypeSModel(rUserId, null);
			ptSc.setCusttypeId(custTypeId);
			ptSc.setIsGroup( "1" );
			
			paytypeList = payTypeService.getUserPrePayTypeForCustList(ptSc);
		}
		mv.addObject("ppList", paytypeList);
		return ajaxReturn(mv, rm);
	}
}
