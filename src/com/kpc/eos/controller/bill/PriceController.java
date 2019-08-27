package com.kpc.eos.controller.bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.kpc.eos.controller.BaseEOSController;
import com.kpc.eos.controller.utility.SearchModelUtil;
import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.util.MessageUtil;
import com.kpc.eos.core.util.SessionUtil;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.bill.PriceDetailModel;
import com.kpc.eos.model.bill.PriceDetailSModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.bizSetting.BizSettingModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.UserItemPropertyModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.model.bizSetting.WorkFlowSModel;
import com.kpc.eos.model.common.BreadcrumbModel;
import com.kpc.eos.model.common.SysMsg;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.service.bill.BillService;
import com.kpc.eos.service.bill.PriceDetailService;
import com.kpc.eos.service.billProcMng.BillProcService;
import com.kpc.eos.service.bizSetting.BizSettingService;
import com.kpc.eos.service.bizSetting.UserItemService;
import com.kpc.eos.service.bizSetting.WorkFlowService;
import com.kpc.eos.service.dataMng.UserService;

/**
 * Filename		: PriceController.java
 * Description	: Management class for the user's adjustment of receipt items price.
 * 2017
 * @author		: RKRK
 */
public class PriceController extends BaseEOSController 
{
	
	public final static String SC_KEY_USER_ITEMS = "Price_userItems";
	
	ModelAndView mv = null;
	
	private BillService billService;
	private UserItemService userItemService;
	private UserService userService;
	private BillProcService billProcService;
	private BizSettingService bizSettingService;
	private WorkFlowService workFlowService;
	private PriceDetailService priceDetailService;
		
	private UserModel loginUser;
	private String userId;
	private String empId;
	
	public PriceController() 
	{
		super();
		controllerId = "Price";
	}
	
	
	public void initCmd(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.initCmd();
		
		controllerId = "Price";
		loginUser = (UserModel)SessionUtil.getUser(request, getSystemName());
		if (loginUser == null ) {
			return;
		}
		
		userId = loginUser.getUserId();
		empId = loginUser.getEmpId();
		
		String methodName = getMethodNameResolver().getHandlerMethodName(request);
		
		String[] methodList = new String[]{"priceList", "priceForm", "billList", "priceDirectForm"};
		
		if ( ArrayUtils.contains(methodList, methodName) )
		{
			breads.add(new BreadcrumbModel("填写单据", ""));
			breads.add(new BreadcrumbModel("调价单", getCmdUrl("priceList")));
		}
		
	}
	
	/**
	 * Description	: Set the order service.
	 * @author 		: RKRK
	 * @param orderService
	 * 2017
	 */
	public void setBillService(BillService billService) 
	{
		this.billService = billService;
	}
	
	public void setUserItemService(UserItemService userItemService) 
	{
		this.userItemService = userItemService;
	}
	
	public void setUserService(UserService userService) 
	{
		this.userService = userService;
	}
	
	public void setBillProcService(BillProcService billProcService)
	{
		this.billProcService = billProcService;
	}
	
	public void setBizSettingService(BizSettingService bizSettingService)
	{
		this.bizSettingService = bizSettingService;
	}
	
	public void setWorkFlowService(WorkFlowService workFlowService)
	{
		this.workFlowService = workFlowService;
	}
	
	public void setPriceDetailService(PriceDetailService priceDetailService){
		this.priceDetailService = priceDetailService;
	}

	/**
	 * Description	: Show the Price bill list.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView priceList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = "Price_priceList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/priceList", "sc", sc );
		
		pageTitle = "调价单";
		
		
		String receiptMark = "N";
		BizSettingModel bsModel = bizSettingService.getBizSettingBySysType(userId, BizSetting.PRICE_BILL_BY_RECEIPT);
		receiptMark = BizSetting.PRICE_BILL_BY_RECEIPT_Y.equals(bsModel.getSysValueName()) ? "Y" : "N";
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		mv.addObject("receiptMark" , receiptMark);
		
		return mv;
	}
	
	/**
	 * Description	: show the return list by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView priceGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Price_priceList");
		
		sc.setBillType(Constants.CONST_BILL_TYPE_PRICE);
		sc.setInputorId(empId);
		sc.setHostUserId(userId);
		
		Integer totalCount = billService.countBillList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<BillHeadModel> list = billService.getBillList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Show the receipt bill list for Price bill.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView billList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		BillHeadSModel sc = new BillHeadSModel();
		
		// getting the search model from session
		String key = "Price_billList";
		request.setAttribute(SC_ID_SESSION, key);
		sc  = (BillHeadSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/priceReceiptList", "sc", sc );
		
		pageTitle = "选择入库单";
		
		//this.initCmd();
		breads.add(new BreadcrumbModel("选择入库单", "#"));
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		mv.addObject("selectActionUrl", getCmdUrl("Price", "priceForm"));
		
		return mv;
	}
	
	/**
	 * Description	: show the receipt bill list for Price bill by Ajax.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView billGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		request.setAttribute(SC_ID_SESSION, "Price_billList");
		
		sc.setUserId(userId);
		sc.setCustUserId(userId);
		
		Integer totalCount = billService.countReceiptBillForPriceList(sc);
		sc.getPage().setRecords(totalCount);
		
		List<BillHeadModel> list = billService.getReceiptBillForPriceList(sc);
		
		mv.addObject("rows", list);
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: Show the price form.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView priceForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		mv = new ModelAndView( "bill/priceForm" );
		
		String rbillId = request.getParameter("rbillId");
		
		String billId = null;
		
		if (rbillId == null || rbillId.equals("")){
			billId = request.getParameter("billId");
			if (billId == null || billId.equals("")){
				SysMsg.addMsg(SysMsg.ERROR, MessageUtil.getMessage("system.common.invalid.request"), request);
				return redirect("Price", "billList");
			}
		}
		
		BillHeadModel bill = null;
		
		if (rbillId != null && !rbillId.equals("")){
			bill = billService.getBill(rbillId);
			Map<String, String> map = new HashMap();
			map.put("userId", userId);
			map.put("billId", rbillId);
			List<BillLineModel> billItemList = billService.getBillItemList(map);
			mv.addObject("billItemList", JSONArray.fromObject(billItemList));
			mv.addObject("originBill", bill);
			String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			mv.addObject("price_note", today + "调价");
			mv.addObject("isDraft", false);
		}else if (billId != null && !billId.equals("")){
			
			bill = billService.getBill(billId);
			rbillId = bill.getRbillId();
			BillHeadModel rBill = billService.getBill(rbillId);
			List<PriceDetailModel> itemList = priceDetailService.getPriceItemList(userId, billId, Constants.CONST_STATE_Y);
			
			mv.addObject("billItemList", JSONArray.fromObject(itemList));
			mv.addObject("originBill", rBill);
			mv.addObject("newBill", bill);
			mv.addObject("isDraft", true);
		}
		
		// getting goods items.
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getAllUserItemPropertyList(userId);
		
		JSONArray ncpColNames = new JSONArray();
		JSONArray ncpColDesc = new JSONArray();
		
		JSONArray priceColNames = new JSONArray();
		JSONArray priceColDesc = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			//JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null);
			
			if (! Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) 
					&& !Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd())
					&& !Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())
				)
			{
				if (Constants.CONST_Y.equals(item.getDisplayYn())){
					ncpColNames.add(item.getPropertyName());
					ncpColDesc.add(item.getPropertyDesc());
				}
			}
			if (Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())){
				priceColNames.add(item.getPropertyName());
				priceColDesc.add(item.getPropertyDesc());
			}
		}
		
		JSONObject tmp = new JSONObject();
		
		tmp.put("ncpColNames", ncpColNames);
		tmp.put("ncpColDesc", ncpColDesc);
		tmp.put("priceColNames", priceColNames);
		tmp.put("priceColDesc", priceColDesc);
		
		pageTitle = "新增调价单";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		
		mv.addObject("gridData", tmp);
		// ------ End of jqGrid configuration
		
		return mv;
	}
	
	/**
	 * Description	: Save the price data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView savePrice(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String billId = "";
		if (request.getParameter("billId") != null && !request.getParameter("billId").equals("")){
			billId = request.getParameter("billId");
		}
		String originBillId = request.getParameter("originBillId");
		boolean isDraft = request.getParameter("draftFlg").equals("Y")?true:false;
		String priceNote = request.getParameter("price_note");
		String arriveDate = request.getParameter("arriveDate");
		
		String billProc = "";
		String billState = isDraft?Constants.BILL_STATE_DRAFT:Constants.BILL_STATE_COMPLETED;
		String procMan = "";
		
		List<UserItemPropertyModel> ipList = userItemService.getAllUserItemPropertyList(userId);
		
		WorkFlowSModel sc = new WorkFlowSModel();
		
		sc.setWorkFlowType(Constants.WF_TYPE_PRICE);
		sc.setUserId(userId);
		sc.setState(Constants.CONST_STATE_Y);
		sc.setPagingYn(null);
		sc.getPage().setSidx("seq_no");
		sc.getPage().setSord("ASC");
		List<WorkFlowModel> workFlowList = workFlowService.getWorkFlowList(sc);
		
		List<BillProcModel> billProcList = new ArrayList<BillProcModel>();
		
		if (!isDraft){
			BillProcModel bpFirstModel = new BillProcModel();
			bpFirstModel.setProcDatId("0");
			bpFirstModel.setEmpId(empId);
			bpFirstModel.setProcTypeCd(Constants.PROC_TYPE_SUBMIT_BILL);
			bpFirstModel.setProcSeqNo("-1");
			bpFirstModel.setBillProcName(Constants.PROC_NAME_BILL_SUBMIT);
			bpFirstModel.setUserName(loginUser.getUserName());
			bpFirstModel.setEmpName(loginUser.getEmpName());
			bpFirstModel.setEmpNameok(loginUser.getEmpName());
			bpFirstModel.setState(Constants.PROC_STATUS_COMPLETED);
			bpFirstModel.setCreateBy(empId);
			bpFirstModel.setUpdateBy(empId);
			bpFirstModel.setUserId(userId);
			bpFirstModel.setUserNo(loginUser.getUserNo());
			bpFirstModel.setUserName(loginUser.getUserName());
			billProcList.add(bpFirstModel);
			
			for(WorkFlowModel workFlow : workFlowList){
				List<String> empList = workFlow.getEmpList();
				List<String> empIdList = workFlow.getEmpIdList();
				
				if (empList.size() > 0){
					for (int i=0;i<empIdList.size();i++){
						BillProcModel bpModel = new BillProcModel();
						bpModel.setProcDatId(workFlow.getWorkFlowId());
						bpModel.setEmpId(empIdList.get(i));
						bpModel.setProcTypeCd(Constants.PROC_TYPE_PRICE_FLOW);
						bpModel.setProcSeqNo(workFlow.getSeqNo());
						bpModel.setBillProcName(workFlow.getWorkFlowName());
						bpModel.setQtyYn(workFlow.getQtyYn());
						bpModel.setEmpName(empList.get(i));
						bpModel.setEmpNameok(empList.get(i));
						bpModel.setPriceYn(workFlow.getPriceYn());
						bpModel.setShipPriceYn(workFlow.getShipPriceYn());
						bpModel.setUserId(userId);
						bpModel.setUserNo(loginUser.getUserNo());
						bpModel.setUserName(loginUser.getUserName());
						bpModel.setReadmark(Constants.CONST_N);
						bpModel.setState(Constants.PROC_STATUS_IN_PROCESS);
						bpModel.setCreateBy(empId);
						bpModel.setUpdateBy(empId);
						billProcList.add(bpModel);
						
						if (procMan.equals("")){
							procMan = procMan + empList.get(i);
						}else{
							procMan = procMan + ", " + empList.get(i);
						}
					}
					
					billState = Constants.BILL_STATE_IN_PROCESS;
					billProc = workFlow.getWorkFlowName();
					break;
				}
			}
		}
		
		String priceDesc = "";
		
		List<UserItemPropertyModel> priceList = new ArrayList<UserItemPropertyModel>();
		
		for (UserItemPropertyModel item : ipList)
		{
			if (Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())){
				if (priceDesc.equals("")){
					priceDesc = item.getPropertyDesc();
				}else{
					priceDesc = priceDesc + "," + item.getPropertyDesc();
				}
				priceList.add(item);
			}
		}
		
		// BILL HEAD MODEL
		BillHeadModel bhModel = new BillHeadModel();
		bhModel.setBillId(billId);
		bhModel.setRbillId(originBillId);
		bhModel.setBillType(Constants.CONST_BILL_TYPE_PRICE);
		bhModel.setBillProc(billProc);
		bhModel.setProcMan(procMan);
		bhModel.setCustUserName(priceNote);
		bhModel.setHostUserId(loginUser.getUserId());
		bhModel.setHostUserNo(loginUser.getUserNo());
		bhModel.setHostUserName(loginUser.getUserName());
		bhModel.setHostContactName(loginUser.getContactName());
		bhModel.setHostTelNo(loginUser.getTelNo());
		bhModel.setHostQqNo(loginUser.getQqNo());
		bhModel.setHostMobileNo(loginUser.getMobileNo());
		bhModel.setPricedesc(priceDesc);
		bhModel.setArriveDate(arriveDate);
		bhModel.setState(billState);
		bhModel.setManagerId(empId);
		bhModel.setManagerName(loginUser.getEmpName());
		bhModel.setInputorId(empId);
		bhModel.setInputorName(loginUser.getEmpName());
		bhModel.setCreateBy(empId);
		bhModel.setUpdateBy(empId);
		
		String priceMark = "N";
		BizSettingModel bsModel = bizSettingService.getBizSettingBySysType(userId, BizSetting.AUTO_PRICE_BY_BILL);
		priceMark = BizSetting.AUTO_PRICE_BY_BILL_Y.equals(bsModel.getSysValueName()) ? "Y" : "N";
		
		String[] itemIdArray = request.getParameterValues("itemId");
		String[] itemPriceArray = request.getParameterValues("price");
		String[] itemQtyArray = request.getParameterValues("qty");
		String[] itemCostArray = request.getParameterValues("cost");
		String[] itemTotArray = request.getParameterValues("tot");
		String[] itemNoteArray = request.getParameterValues("note");
		String[] itemPriceInArray = request.getParameterValues("priceIn");
		
		List<PriceDetailModel> priceDetailList = new ArrayList<PriceDetailModel>();
		List<BillLineModel> updateBillLineList = new ArrayList<BillLineModel>();
		List<UserItemModel> updateUserItemList = new ArrayList<UserItemModel>();
		BillHeadModel updateBill = null;
		
		for (int i=0; i < itemIdArray.length; i++){
			boolean modMark = false;
			PriceDetailModel priceDetailModel = new PriceDetailModel();
			priceDetailModel.setItemId(itemIdArray[i]);
			priceDetailModel.setUserId(userId);
			priceDetailModel.setPrice(itemPriceArray[i]);
			priceDetailModel.setCost(itemCostArray[i]);
			priceDetailModel.setQty(itemQtyArray[i]);
			priceDetailModel.setTotal(itemTotArray[i]);
			priceDetailModel.setPriceIn(itemPriceInArray[i]);
			
			UserItemModel userItemModel = new UserItemModel();
			
			for (int priceNo = 0; priceNo < priceList.size(); priceNo++)
			{
				modMark = false;
				String[] oldPriceArray = request.getParameterValues("old_price" + priceNo);
				String[] newPriceArray = request.getParameterValues("new_price" + priceNo);
				
				priceDetailModel.set("d" + (priceNo + 1) + "1", oldPriceArray[i]);
				priceDetailModel.set("d" + (priceNo + 1) + "2", newPriceArray[i]);
				
				if (oldPriceArray[i] != newPriceArray[i]){
					modMark = true;
				}
				
				if (Constants.BILL_STATE_COMPLETED.equals(billState) && modMark && priceMark.equals(Constants.CONST_Y) && !isDraft){
					BillLineModel blModel = new BillLineModel();
					blModel.setPrice2(newPriceArray[i]);
					blModel.setItemId(itemIdArray[i]);
					blModel.setC20(priceList.get(priceNo).getPropertyName());
					blModel.setUpdateBy(empId);
					updateBillLineList.add(blModel);
				}
				userItemModel.set(priceList.get(priceNo).getPropertyName(), newPriceArray[i]);
			}
			if (Constants.BILL_STATE_COMPLETED.equals(billState) && modMark && !isDraft){
				userItemModel.setUserId(userId);
				userItemModel.setItemId(itemIdArray[i]);
				userItemModel.setUpdateBy(empId);
				updateUserItemList.add(userItemModel);
			}
			
			priceDetailModel.setNote(itemNoteArray[i]);
			priceDetailModel.setCreateBy(empId);
			priceDetailModel.setUpdateBy(empId);
			priceDetailList.add(priceDetailModel);
		}
		
		if (priceMark.equals(Constants.CONST_Y) && Constants.BILL_STATE_COMPLETED.equals(billState) && !isDraft){
			updateBill = new BillHeadModel();
			updateBill.setHostUserId(userId);
			updateBill.setUpdateBy(empId);
		}
		
		billService.processPrice(bhModel, billProcList, priceDetailList, updateBillLineList, updateUserItemList, updateBill);
		
		SysMsg.addMsg(SysMsg.SUCCESS, MessageUtil.getMessage("system.alert.save"), request);
		
		return redirect("Price", "priceList");
	}
	
	/**
	 * Description	: View Price Bill Data.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView viewPrice(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		mv = new ModelAndView( "bill/priceView" );
		
		String billId = request.getParameter("billId");
		
		BillHeadModel bhModel = billService.getBill(billId);
		
		if (!empId.equals(bhModel.getInputorId()) && !empId.equals(bhModel.getManagerId()) && !empId.equals(bhModel.getCreateBy()))
		{
			return redirect("Price", "priceList");
		}
		
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getAllUserItemPropertyList(userId);
		
		JSONArray ncpColNames = new JSONArray();
		JSONArray ncpColDesc = new JSONArray();
		
		JSONArray priceColNames = new JSONArray();
		JSONArray priceColDesc = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			//JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null);
			
			if (! Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) 
					&& !Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd())
					&& !Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())
				)
			{
				if (Constants.CONST_Y.equals(item.getDisplayYn())){
					ncpColNames.add(item.getPropertyName());
					ncpColDesc.add(item.getPropertyDesc());
				}
			}
			if (Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())){
				priceColNames.add(item.getPropertyName());
				priceColDesc.add(item.getPropertyDesc());
			}
		}
		
		JSONObject tmp = new JSONObject();
		
		tmp.put("ncpColNames", ncpColNames);
		tmp.put("ncpColDesc", ncpColDesc);
		tmp.put("priceColNames", priceColNames);
		tmp.put("priceColDesc", priceColDesc);
		
		mv.addObject("gridData", tmp);
		
		pageTitle = "调价单明细";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		// ------ End of jqGrid configuration
		
		List<PriceDetailModel> itemList = priceDetailService.getPriceItemList(userId, billId, Constants.CONST_STATE_Y);
		mv.addObject("itemList", JSONArray.fromObject(itemList));
		mv.addObject("bill", bhModel);
		
		return mv;
	}
	
	public ModelAndView billProcGridAjax(HttpServletRequest request, HttpServletResponse response, BillHeadSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");
		
		String billId = request.getParameter("billId");
		
		// get the user item list.
		List<BillProcModel> billProcList = billProcService.getBillHistoryList(billId, userId);
		
		mv.addObject("rows", billProcList);
		return mv;
	}
	
	public ModelAndView deletePriceAjax(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if ( ! isAjaxRequest(request) )
		{
			return redirect(controllerId, "priceList");
		}
		
		ResultModel rm = new ResultModel(ResultModel.RESULT_FAIL_CODE, "");
		ModelAndView mv =  new ModelAndView("jsonView");
		
		String billId = request.getParameter("billId");
		
		if (StringUtils.isEmpty(billId))
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// validation here.
		BillHeadModel bill = billService.getBill( billId );
		if ( bill == null )
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.invalid.request") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		// current emp's bill?
		if ( ! empId.equals(bill.getInputorId()) ) 
		{
			rm.setResultMsg( MessageUtil.getMessage("system.common.no.permission") );
			rm.setResultCode(ResultModel.RESULT_FAIL_CODE);
			return ajaxReturn(mv, rm);
		}
		
		billService.processDeletePrice(billId);
		
		rm.setResultMsg( MessageUtil.getMessage("bill.delete.success") );
		rm.setResultCode(ResultModel.RESULT_SUCCESS_CODE);
		
		return ajaxReturn(mv, rm);
	}
	
	/**
	 * Description	: Show the price form without receipt bill.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @param sc
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView priceDirectForm(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String key = "Price_itemDirectList";
		request.setAttribute(SC_ID_SESSION, key);
		PriceDetailSModel sc = new PriceDetailSModel();
		sc  = (PriceDetailSModel)SearchModelUtil.getSearchModel(key, sc, request);
		
		mv = new ModelAndView( "bill/priceDirectForm", "sc", sc );
		
		List<UserItemPropertyModel> catPropertyData = userItemService.getItemFieldPropertyByUser(userId, "PT0003");
		String catFieldName = catPropertyData.get(0).getPropertyName();
		
		List<String> categoryList = userItemService.getItemCategoryList(userId, catFieldName);
		
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getAllUserItemPropertyList(userId);
		
		JSONArray ncpColNames = new JSONArray();
		JSONArray ncpColDesc = new JSONArray();
		
		JSONArray priceColNames = new JSONArray();
		JSONArray priceColDesc = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			//JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null);
			
			if (! Constants.CONST_ITEM_TYPE1_CODE.equals(item.getPropertyTypeCd()) 
					&& !Constants.CONST_ITEM_TYPE2_CODE.equals(item.getPropertyTypeCd())
					&& !Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())
				)
			{
				if (Constants.CONST_Y.equals(item.getDisplayYn())){
					ncpColNames.add(item.getPropertyName());
					ncpColDesc.add(item.getPropertyDesc());
				}
			}
			if (Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())){
				priceColNames.add(item.getPropertyName());
				priceColDesc.add(item.getPropertyDesc());
			}
		}
		
		JSONObject tmp = new JSONObject();
		
		tmp.put("ncpColNames", ncpColNames);
		tmp.put("ncpColDesc", ncpColDesc);
		tmp.put("priceColNames", priceColNames);
		tmp.put("priceColDesc", priceColDesc);
		
		pageTitle = "新增调价单";
		breads.add(new BreadcrumbModel(pageTitle, "#"));
		
		mv.addObject("gridData", tmp);
		// ------ End of jqGrid configuration
		mv.addObject("catFieldName", catFieldName);
		mv.addObject("categoryList", categoryList);
		
		// getting the search model from session
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		if (StringUtils.isEmpty(sc.getCreateDate())){
			sc.setCreateDate(today);
		}
		mv.addObject("price_note", today + "调价");
		
		mv.addObject("page", sc.getPage());
		mv.addObject("sc", sc);
		
		return mv;
	}
	
	public ModelAndView itemListGridAjax(HttpServletRequest request, HttpServletResponse response, PriceDetailSModel sc) throws Exception 
	{
		mv = new ModelAndView("jsonView");

		request.setAttribute(SC_ID_SESSION, "Price_itemDirectList");
		
		sc.setUserId(userId);
		sc.setState(Constants.CONST_STATE_Y);
		// get the user item list.
		List<PriceDetailModel> itemsList = priceDetailService.getAllItemsForPriceList(sc);
		
		mv.addObject("rows", itemsList);
		mv.addObject("sc", sc);
		return mv;
	}
	
	/**
	 * Description	: View Price Bill Data For Mobile.
	 * @author 		: RKRK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public ModelAndView viewPriceForMobile(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		mv = new ModelAndView( "bill/priceView" );
		
		String billId = request.getParameter("billId");
		
		BillHeadModel bhModel = billService.getBill(billId);
		
		if (!empId.equals(bhModel.getInputorId()) && !empId.equals(bhModel.getManagerId()) && !empId.equals(bhModel.getCreateBy()))
		{
			return redirect("Price", "priceList");
		}
		
		// ------ jqGrid configuration
		List<UserItemPropertyModel> ipList = userItemService.getAllUserItemPropertyList(userId);
		
		JSONArray ncpColNames = new JSONArray();
		JSONArray ncpColDesc = new JSONArray();
		
		JSONArray priceColNames = new JSONArray();
		JSONArray priceColDesc = new JSONArray();
		
		for (UserItemPropertyModel item : ipList)
		{
			//JSONObject col = JqGridUtil.getColModel(item.getPropertyName(), null, null);
			
			if (Constants.CONST_ITEM_NAME_CODE.equals(item.getPropertyTypeCd())){
				ncpColNames.add(item.getPropertyName());
				ncpColDesc.add(item.getPropertyDesc());
			}
			
			if (Constants.CONST_ITEM_PRICE_CODE.equals(item.getPropertyTypeCd())){
				priceColNames.add(item.getPropertyName());
				priceColDesc.add(item.getPropertyDesc());
			}
		}
		
		JSONObject tmp = new JSONObject();
		
		tmp.put("ncpColNames", ncpColNames);
		tmp.put("ncpColDesc", ncpColDesc);
		tmp.put("priceColNames", priceColNames);
		tmp.put("priceColDesc", priceColDesc);
		
		mv.addObject("gridData", tmp);
		
		pageTitle = "调价单明细";
		breads.add( new BreadcrumbModel(pageTitle, "#", true) );
		// ------ End of jqGrid configuration
		
		List<PriceDetailModel> itemList = priceDetailService.getPriceItemList(userId, billId, Constants.CONST_STATE_Y);
		mv.addObject("itemList", JSONArray.fromObject(itemList));
		mv.addObject("bill", bhModel);
		
		return mv;
	}
}
