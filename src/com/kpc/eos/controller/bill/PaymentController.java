package com.kpc.eos.controller.bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.MathUtil;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.core.validation.FormErrors;
import com.kpc.eos.core.validation.FormValidationUtils;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.PayBillDetailModel;
import com.kpc.eos.model.bill.SubPayTypeModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.billProcMng.PrepayBillSModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.HostCustSModel;
import com.kpc.eos.model.bizSetting.PayTypeModel;
import com.kpc.eos.model.bizSetting.PayTypeSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.bill.BillService;
import com.kpc.eos.service.bizSetting.BizDataService;
import com.kpc.eos.service.bizSetting.HostCustService;
import com.kpc.eos.service.bizSetting.PayTypeService;
import com.kpc.eos.service.common.AddressService;
import com.kpc.eos.service.dataMng.UserService;

/**
 * Filename		: PaymentController.java
 * Description	: Management class for the user's payments.
 * 2017
 * @author		: RKRK
 */
public class PaymentController extends BaseEOSController 
{
	public final static String CMD_PAYMENT_LIST 	= "paymentList";
	public final static String CMD_PAYMENT_VIEW 	= "paymentView";
	public final static String CMD_PAYMENT_FORM 	= "paymentForm";
	
	public final static String SC_KEY_PAYMENT_LIST 	= "Paymnet_list";
	
	
	// services.
	private BillService 		billService;
	private HostCustService 	hostCustService;
	private UserService 		userService;
	private AddressService 		addrService;
	private PayTypeService  	payTypeService;
	private BizDataService  	bizDataService;
	
	// member vars
	private ModelAndView 		mv = null;
	
	private UserModel 			loginUser;
	private String 				userId;
	private String 				empId;
	
	public PaymentController() 
	{
		super();
		controllerId = "Payment";
	}
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "Payment";
		
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
		empId = loginUser.getEmpId();
		
		// get the pre breadcrumbs.
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] methodNameList = new String[]{"paymentList", "paymentForm", "paymentView", "paymentBillList"};
		
		if ( ArrayUtils.contains(methodNameList, methodName) )
		{
			breads.add(new BreadcrumbModel("填写单据", ""));
			breads.add(new BreadcrumbModel("收款单  ", getCmdUrl("paymentList")));
		}
	}

	/**
	 * Description	: Set the sale service.
	 * @author 		: RKRK
	 * @param saleService
	 * 2017
	 */
	public void setBillService(BillService billService) 
	{
		this.billService = billService;
	}
	
	/**
	 * Description	: Set the host cust service.
	 * @author 		: RKRK
	 * @param hostCustService
	 * 2017
	 */
	public void setHostCustService(HostCustService hostCustService) 
	{
		this.hostCustService = hostCustService;
	}
	
	public void setUserService(UserService userService) 
	{
		this.userService = userService;
	}
	
	public void setAddrService(AddressService addrService) 
	{
		this.addrService = addrService;
	}
	
	public void setPayTypeService(PayTypeService payTypeService) 
	{
		this.payTypeService = payTypeService;
	}
	
	public void setBizDataService(BizDataService bizDataService) 
	{
		this.bizDataService = bizDataService;
	}
	
	/**
	 * Description	: Show the payments list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView paymentList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = SC_KEY_PAYMENT_LIST;
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/paymentList", "sc", sc );
		
		pageTitle = "收款单";
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	/**
	 * Description	: show the payments list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView paymentGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, SC_KEY_PAYMENT_LIST);
		
		sc.setBillType(Constants.CONST_BILL_TYPE_PAYMENT);
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
	 * Description	: List the payments of the current user.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView paymentForm(HttpServletRequest request, HttpServletResponse response, BillHeadModel paymentForm) throws Exception 
	{
		mv = new ModelAndView( "bill/paymentForm" );
		
		String billId = request.getParameter( "billId" );
		BillHeadModel payment = new BillHeadModel();
		
		if (isPost(request))
		{
			// if user has alredy submitted the emp form, keep the emp model.
			if (paymentForm != null && isPost(request)) {
				payment = paymentForm;
			}
		} 
		if ( StringUtils.isNotEmpty(billId) ) 
		{
			payment = billService.getBill(billId);
			if ( ! empId.equals(payment.getInputorId()) ) 
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, "paymentList");
			}
			pageTitle = "修改收款单";
			
			// we need to re-bind the bill to override the attributes in the current order.
			ServletRequestDataBinder binder = new ServletRequestDataBinder(payment);
			binder.bind(request);
		} 
		else 
		{
			pageTitle = "新增收款单";
		}
		
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		// cust list.
		HostCustSModel hcSc = new HostCustSModel();
		hcSc.setHostId(userId);
		hcSc.getPage().setSidx("custUserName");
		hcSc.getPage().setRows(1000);
		List<HostCustModel> custList = hostCustService.getCustSettingList(hcSc);
		mv.addObject("custList", custList);
		
		// prepay list
		List<PayTypeModel> paytypeList = new ArrayList<PayTypeModel>();
		if (StringUtils.isNotEmpty(payment.getCusttypeId()) && StringUtils.isNotEmpty(payment.getCustUserId()))
		{
			PayTypeSModel ptSc = new PayTypeSModel(userId, payment.getCustUserId());
			ptSc.setCusttypeId(payment.getCusttypeId());
			//ptSc.setIsGroup( "1" );
			
			paytypeList = payTypeService.getUserPrePayTypeList(ptSc);
		}
		
		
		mv.addObject("paytypeList", paytypeList);
		
		mv.addObject("payment", payment);
		
		return mv;
	}
	
	/**
	 * Description	: Save the payment data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param payment
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView savePayment(HttpServletRequest request, HttpServletResponse response, BillHeadModel payment) throws Exception 
	{
		if ( ! isPost(request) )
		{
			return redirect(controllerId, "paymentList");
		}
		
		System.out.println("\n >>>>>>>>>>> Save Payment >>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
		ModelAndView mv = new ModelAndView();
		
		// ---- start to validate the payment form now.
		formErrors = new FormErrors(payment, "target");
		
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "custUserId", "system.common.valid.error.required", new Object[]{"客户名称"});
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "paymentType", "system.common.valid.error.required", new Object[]{"预付款名称"});
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "arriveDate", "system.common.valid.error.required", new Object[]{"到账日期"});
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "totalAmt", "system.common.valid.error.required", new Object[]{"金额(元)"});
		double totalAmt = MathUtil.getDouble(payment.getTotalAmt(), true);
		if (totalAmt <=0) 
		{
			formErrors.rejectValue("totalAmt", "payment.invalid.amount", new Object[]{"金额"}, null);
		}
		
		try
		{
			new SimpleDateFormat("yyyy-MM-dd").parse(payment.getArriveDate());
		} catch(Exception e){
			formErrors.rejectValue("arriveDate", "payment.invalid.arriveDate", new Object[]{"金额"}, null);
		}
		
		
		FormValidationUtils.rejectIfEmptyOrWhitespace(formErrors, "paytypeId", "system.common.valid.error.required", new Object[]{"预付款名称"});
		if (formErrors.hasErrors())
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.save.erorr"), request);
			mv = paymentForm(request, response, payment);
			return mv;
		}
		
		String paytypeId = payment.getPaytypeId();
		
		// host cust setting.
		HostCustModel hcModel = (HostCustModel)hostCustService.getHostCustSetting(userId, payment.getCustUserId());
		if (hcModel == null || ! Constants.CONST_STATE_Y.equals( hcModel.getState() ) || ! hcModel.getConnection() )
		{
			formErrors.rejectValue("custUserId", "payment.invalid.hostcust");
		}
		
		// paytypeId checking
		PayTypeModel ptModel = (PayTypeModel) payTypeService.getPayType(paytypeId);
		if ( ptModel == null || ! Constants.CONST_Y.equals(ptModel.getPrePayYn()) )
		{
			formErrors.rejectValue("paytypeId", "payment.invalid.prepay.setting");
		}
		
		if (formErrors.hasErrors())
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.save.erorr"), request);
			mv = paymentForm(request, response, payment);
			return mv;
		}
		
		// get prepay setting from bizdata setting.
		SubPayTypeModel subPayType = payTypeService.getActiveUserSubPayType(userId, hcModel.getCustTypeId(), payment.getPaytypeId(), payment.getPaymentType());
		
		// this is an error.
		if ( subPayType == null )
		{
			formErrors.rejectValue("paytypeId", "payment.invalid.prepay.setting");
			
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.save.erorr"), request);
			mv = paymentForm(request, response, payment);
			return mv;
		}
		
		// ---- End of validation --------------------
		
		// setting the payment information.
		payment.setBillType(Constants.CONST_BILL_TYPE_PAYMENT);
		payment.setState( Constants.BILL_STATE_COMPLETED );
		payment.setTotal2( payment.getTotalAmt() );
		
		// Note : request val will be replaced. Because paymentType parameter is seqNo now.
		payment.setPaymentType( subPayType.getName() );
		
		payment.setCusttypeId( hcModel.getCustTypeId());
		payment.setCusttypeName( hcModel.getCustTypeName());
		
		// submitter.
		payment.setManagerId(loginUser.getEmpId());
		payment.setManagerName(loginUser.getEmpName());
		payment.setInputorId(loginUser.getEmpId());
		payment.setInputorName(loginUser.getEmpName());
		
		// paytype setting
		payment.setPaytype( ptModel );
		
		// cust user setting.
		payment.setCustUserNo(hcModel.getCustUserNo());
		payment.setCustUserName(hcModel.getCustUserName() );
		payment.setCustContactName(hcModel.getCustContactName());
		payment.setCustShortName( hcModel.getCustShortName() );
		payment.setCustTelNo(hcModel.getCustTelNo());
		payment.setCustMobileNo(hcModel.getCustMobileNo());
		
		// host user setting.
		payment.setHostUserId(userId);
		payment.setHostUserNo(hcModel.getHostUserNo());
		payment.setHostUserName(hcModel.getHostUserName());
		payment.setHostContactName( hcModel.getHostContactName() );
		payment.setHostTelNo( hcModel.getHostTelNo() );
		payment.setHostMobileNo( loginUser.getMobileNo() );
		payment.setHostAddress( loginUser.getAddress() );
		
		
		// Get paybill detail list.
		List<PayBillDetailModel> detailList = new ArrayList<PayBillDetailModel>();
		
		PayBillDetailModel detail = new PayBillDetailModel(payment, subPayType, false);
		
		detailList.add(detail);
		
		PayBillDetailModel detailBonus = new PayBillDetailModel(payment, subPayType, true);
		detailList.add(detailBonus);
		
		// Process the form submission to create a payment bill.
		try
		{
			ResultModel sRet = (ResultModel) billService.processSubmittedPaymentBill( payment, detailList );
			
			// analyze the result.
			int ecCode = sRet.getResultCode();
			switch ( ecCode )
			{
			case BillService.EC_NO_EMP:
				// this is invalid order.
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("payment.submit.ok.no.emp"), request);
				mv = paymentForm(request, response, payment);
				return mv;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			payment.setBillId(null);
			request.setAttribute("billId", null);
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.save.erorr"), request);
			mv = paymentForm(request, response, payment);
			return mv;
		}
		// success case!!!
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.common.success"), request);
		
		return redirect("Payment", "paymentList");
	}
		
	/**
	 * Description	: Payment View page.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView paymentView(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/paymentView" );
		BillHeadModel payment = new BillHeadModel();
		
		String billId = request.getParameter("billId");
		
		// ------ Validation ----------------------- //
		if ( StringUtils.isEmpty(billId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect(controllerId, CMD_PAYMENT_LIST);
		}
		
		if ( StringUtils.isNotEmpty(billId) )
		{
			// validation here.
			payment = billService.getBill( billId );
			if ( payment == null )
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect(controllerId, CMD_PAYMENT_LIST);
			}
			
			// current emp's bill?
			if ( ! payment.isViewable(loginUser) ) 
			{
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
				return redirect(controllerId, CMD_PAYMENT_LIST);
			}
		}
		// ------ End of validation ----------------------- //
		
		mv.addObject("payment", payment);
		
		// 1. getting the paybill detail list.
		List<PayBillDetailModel> detailList = billService.getPaybillDetailListByBillId(billId);
		
		// 2. getting processor's list.
		List<BillProcModel> bpList = billService.getBillProcHistory(payment);
		
		mv.addObject("bpList", JSONArray.fromObject(bpList));
		mv.addObject("detailList", JSONArray.fromObject(detailList));
		
		pageTitle = "收款单明细";
		
		breads.add(new BreadcrumbModel(pageTitle, "#"));
			
		return mv;
	}
	
	/**
	 * Description	: Delete the payment bill and details.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param payment
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView deletePaymentAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel payment) throws Exception 
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, CMD_PAYMENT_LIST);
		}
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String billId = payment.getBillId();
		if (StringUtils.isEmpty(billId))
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// validation here.
		payment = billService.getBill( billId );
		if ( payment == null )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// current emp's bill?
		if ( ! empId.equals(payment.getInputorId()) ) 
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		if ( ! payment.isDeletable() )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		billService.deleteBill( payment );
		
		SysMsg.addMsg( SysMsg.SUCCESS, MessageUtil.getMessage("payment.delete.success"), request );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Load the prepay type list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param payment
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView loadListDataAjax(HttpServletRequest request, HttpServletResponse response, BillHeadModel payment) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		ResultModel rm = new ResultModel(ResultModel.RESULT_SUCCESS_CODE, "");
		
		// prepay list
		List<PayTypeModel> paytypeList = new ArrayList<PayTypeModel>();
		if (StringUtils.isNotEmpty(payment.getCusttypeId()) && StringUtils.isNotEmpty(payment.getCustUserId()))
		{
			
			PayTypeSModel ptSc = new PayTypeSModel(userId, payment.getCustUserId());
			ptSc.setCusttypeId(payment.getCusttypeId());
			//ptSc.setIsGroup( "1" );
			
			paytypeList = payTypeService.getUserPrePayTypeList(ptSc);
		}
		mv.addObject("paytypeList", paytypeList);
		return ajaxReturn(mv, rm);
	}
	
	
	/**
	 * Description	: Get the payment detail with subpaytype.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView paymentBillList(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String hostUserId = request.getParameter("hostUserId");
		String custUserId = request.getParameter("custUserId");
		
		// Validation here.
		if (StringUtils.isEmpty(hostUserId) || StringUtils.isEmpty(custUserId) )
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
			return redirect("Main", "main");
		}
		
		// current emp's bill?
		if (!( custUserId.equals(loginUser.getUserId()) || hostUserId.equals(loginUser.getUserId()))) 
		{
			SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.no.permission"), request);
			return redirect("Main", "main");
		}
		
		PrepayBillSModel ppSc = new PrepayBillSModel(hostUserId, custUserId, null, null);
		
		pageTitle = "货款明细";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		mv = new ModelAndView( "bill/paymentBillList" );
		mv.addObject("sc", ppSc);
		
		return mv;
	}
	
	/**
	 * Description	: Get Pre Payment List through Ajax
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param ppSc
	 * @return
	 * @throws Exception
	 * 2018
	 */
	public ModelAndView paymentBillListAjax(HttpServletRequest request, HttpServletResponse response, PrepayBillSModel ppSc) throws Exception 
	{
		ResultModel rm = new ResultModel(ResultModel.RESULT_SUCCESS_CODE, "");
		mv = new ModelAndView("jsonView");
		
		Integer totalCount = billService.countPaidBillHeadList(ppSc);
		
		ppSc.getPage().setRecords(totalCount);
		
		List<PayBillDetailModel> pbList = billService.getPaidBillHeadList(ppSc);
		
		mv.addObject("rows", pbList);
		mv.addObject("page", ppSc.getPage());
		mv.addObject("sc", ppSc);
		
		return ajaxReturn(mv, rm);
	}
	
}
