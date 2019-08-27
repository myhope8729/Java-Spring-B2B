
package com.kpc.eos.service.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpc.eos.core.service.BaseServiceInterface;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.bill.BillWorkflowSModel;
import com.kpc.eos.model.bill.CartUserItemModel;
import com.kpc.eos.model.bill.ItemStockModel;
import com.kpc.eos.model.bill.PayBillDetailModel;
import com.kpc.eos.model.bill.PriceDetailModel;
import com.kpc.eos.model.bill.ProcEmpModel;
import com.kpc.eos.model.bill.VendorModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.billProcMng.PrepayBillModel;
import com.kpc.eos.model.billProcMng.PrepayBillSModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;

public interface BillService  extends BaseServiceInterface
{
	public static int EC_SUCCESS 				= 0;
	public static int EC_SUCCESS_ITEMS_OMITTED 	= 1;
	public static int EC_NO_ITEMS 				= 2;
	public static int EC_NO_EMP 				= 3;
	public static int EC_NO_WORKFLOW 			= 4;
	
	
	public void setHostUserBSMap(HashMap<String, String> hostUserBSMap);
	public void setHostCustModel(HostCustModel hcModel);
	
	public List<BillHeadModel> getOrderList() throws Exception;
	
    public Integer countBillList(BillHeadSModel sModel) throws Exception;
	public List<BillHeadModel> getBillList(BillHeadSModel sModel) throws Exception;
	
	
    public BillHeadModel getBill(String orderId) throws Exception;
	
    public void saveBill(BillHeadModel order) throws Exception;
    
    public void saveBill(BillHeadModel order, boolean isNew) throws Exception;
    
    public void deleteBill(String billId) throws Exception;
    
    public void deleteBill(BillHeadModel bill) throws Exception;
    
    public void processReceipt(
    		BillHeadModel bill, List<BillProcModel> bpList, List<BillLineModel> blList, List<ItemStockModel> newItemStockList, 
    		List<ItemStockModel> updateItemStockList, List<UserItemModel> updateItemList) throws Exception;
    
    public void processReturn(
    		BillHeadModel bill, BillProcModel bpModel, List<BillLineModel> blList, List<ItemStockModel> newItemStockList, 
    		List<ItemStockModel> updateItemStockList, List<UserItemModel> updateItemList) throws Exception;
    
    public void processPrice(
    		BillHeadModel bill, List<BillProcModel> bpList, List<PriceDetailModel> pdList, List<BillLineModel> blList, 
    		List<UserItemModel> uiList, BillHeadModel updateBill) throws Exception;
    
    public String getBillHeadKey(String billType) throws Exception;
    public String getBillHeadKey(String billType, String type) throws Exception;
        
    /**
	 * Description	: Handles the submitted bill.
	 * @author 		: RKRK
	 * @param userId
	 * @param userItems
	 * @param cartItems
	 * @return
	 * @throws Exception
	 * 2017
	 */
	public Object processSubmittedBill(BillHeadModel order, List<UserItemModel> userItems, HashMap<String, CartUserItemModel> cartItems) throws Exception;
	public Object processSaveBill(BillHeadModel order, List<UserItemModel> userItems, HashMap<String, CartUserItemModel> cartItems) throws Exception;
	
	public Object processSubmittedSaleBill(BillHeadModel order, List<UserItemModel> userItems, HashMap<String, CartUserItemModel> cartItems) throws Exception;
	public Object processSaveSaleBill(BillHeadModel order, List<UserItemModel> userItems, HashMap<String, CartUserItemModel> cartItems) throws Exception;
	
	public Object processSubmittedInfoBill(BillHeadModel info) throws Exception;
	public Object processSaveInfoBill(BillHeadModel info) throws Exception;
	
	public Object processSubmittedPaymentBill(BillHeadModel payment, List<PayBillDetailModel> detailList) throws Exception;
	
	public List<VendorModel> getUserVendorsByBillIdList(String userId, String billId, boolean isSplitProc) throws Exception;
	
	public List<WorkFlowModel> getWorkFlowByBillList(BillWorkflowSModel sModel) throws Exception;
	
	
	// Basic services
	public void deleteBillProcByBillId(String billId) throws Exception;
	
	public void deletePendingBillProcByBillId(String billId) throws Exception;
	
	public void updateBillHeadStatusByBillId(String billId, String state) throws Exception;
	
	public List<ProcEmpModel> getProcEmpListByWfId (String wfId)  throws Exception;
	
	// PaybillDetail List
	public List<PayBillDetailModel> getPaybillDetailListByBillId(String billId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<BillLineModel> getBillItemList(Map map) throws Exception;
	
	public void processDeleteReceipt(String billId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<BillProcModel> getOrderBillProcHistory(String billId, Map param) throws Exception;
	
	public List<BillProcModel> getBillProcHistory(BillHeadModel bill) throws Exception;
	
	// get sub pay total amt list
	public List<PrepayBillModel> getPrePayTotalAmtList(PrepayBillSModel sc) throws Exception;
	
	public Integer countPaidBillHeadList(PrepayBillSModel sc) throws Exception;
	
	public List<PayBillDetailModel> getPaidBillHeadList(PrepayBillSModel sc) throws Exception;
	
	public Integer countBillForReturnList(BillHeadSModel sc) throws Exception;
	
	public List<BillHeadModel> getBillForReturnList(BillHeadSModel sc) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Integer checkExistBillHeadForWFGroupCondition(Map map) throws Exception;
	
	public Integer countReceiptBillForPriceList(BillHeadSModel sc) throws Exception;
	
	public List<BillHeadModel> getReceiptBillForPriceList(BillHeadSModel sc) throws Exception;
	
	public void processDeletePrice(String billId) throws Exception;
	
	public BillLineModel getBillItemsTotal(Map<String, String> map) throws Exception;
	
}
