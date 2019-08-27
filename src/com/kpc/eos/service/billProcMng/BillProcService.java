
package com.kpc.eos.service.billProcMng;

import java.util.List;
import java.util.Map;

import com.kpc.eos.model.bill.VendorModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.billProcMng.BillProcSModel;
import com.kpc.eos.model.billProcMng.BuyStatisticModel;
import com.kpc.eos.model.billProcMng.DistributeConfirmModel;
import com.kpc.eos.model.billProcMng.DistributeStatisticModel;
import com.kpc.eos.model.billProcMng.PrepayBillModel;
import com.kpc.eos.model.billProcMng.SupplyStatisticModel;
import com.kpc.eos.model.bizSetting.BizDataModel;
import com.kpc.eos.model.bizSetting.EmployeeModel;
import com.kpc.eos.model.bizSetting.HostCustModel;


public interface BillProcService {
	public static int EC_SUCCESS 				= 0;
	public static int EC_NO_ITEMS 				= 1;
	public static int EC_NO_EMP 				= 2;
	
	public String getMinMaxDate(BillProcSModel sc, boolean flag) throws Exception;
	
	public List<BillProcModel> getBillProcList(BillProcSModel sc) throws Exception;
	
	public Integer getCountBillProcList(BillProcSModel sc) throws Exception;
	
	public List<EmployeeModel> getProcEmpList(BillProcModel sc) throws Exception;
	
	public BillProcModel getBillProc(String billProcId, String userId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<PrepayBillModel> getPrepayInfo(Map map) throws Exception;
	
	public String checkBillProc(String billId) throws Exception;
	
	public List<BillProcModel>getBillHistoryList(String billId, String userId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public void saveBillProc(BillProcModel billProc, List<Map> nextWorkFlows) throws Exception;
	
	public String checkDistributedOrder(String billId) throws Exception;
	
	public List<VendorModel> getVendorListForBillLineByBillId(String billId) throws Exception;
	
	public void updateReadMark(String billProcId) throws Exception;
	
	public List<SupplyStatisticModel> getSupplyStatistics(BillProcSModel sc) throws Exception;
	
	public List<BuyStatisticModel> getBuyStatisticItemList(BillProcSModel sc) throws Exception;
	
	public List<BuyStatisticModel> getBuyStatistic(BillProcSModel sc) throws Exception;
	
	public List<BuyStatisticModel> getBuyStatisticForMoblie(BillProcSModel sc) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public void saveBuyStatisticInfo(Map map) throws Exception;
	
	public List<BizDataModel> getDistributeList(BillProcSModel sc) throws Exception;
	
	public List<DistributeStatisticModel> getDistributeStatistic(BillProcSModel sc) throws Exception;
	
	public List<BillProcModel> getBillProcCheckedList(BillProcSModel sc) throws Exception;
	
	public Integer getCountBillProcCheckedList(BillProcSModel sc) throws Exception;
	
	public void rejectDocument(BillProcModel sc) throws Exception;
		
	public List<DistributeConfirmModel> getDistributeConfirmList(BillProcSModel sc) throws Exception;
	
	public void changeBillProcPrice(String userData) throws Exception;
	
	public List<HostCustModel> getCustShortNameList(BillProcSModel sc) throws Exception;
	
	public List<DistributeStatisticModel> getDistributeStatisticPrint(BillProcSModel sc) throws Exception;
	
	public List<SupplyStatisticModel> getLastReceiptList(BillProcSModel sc) throws Exception;

}
