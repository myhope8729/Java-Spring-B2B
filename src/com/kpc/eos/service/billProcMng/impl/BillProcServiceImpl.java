
package com.kpc.eos.service.billProcMng.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.bill.ItemStockModel;
import com.kpc.eos.model.bill.PriceDetailModel;
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
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.service.billProcMng.BillProcService;

@Transactional
public class BillProcServiceImpl extends BaseService implements BillProcService {
	
	@Override
	public String getMinMaxDate(BillProcSModel sc, boolean flag) throws Exception
	{
		if (flag == true){
			return (String) baseDAO.queryForObject("billproc.getBillMinDate", sc);
		}else{
			return (String) baseDAO.queryForObject("billproc.getBillMaxDate", sc);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillProcModel> getBillProcList(BillProcSModel sc) throws Exception{
		List<BillProcModel> list = baseDAO.queryForList("billproc.getBillProcList", sc);
		return list;
	}
	
	@Override
	public Integer getCountBillProcList(BillProcSModel sc) throws Exception
	{
		return (Integer)baseDAO.queryForObject("billproc.getCountBillProcList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeModel> getProcEmpList(BillProcModel sc) throws Exception
	{
		List<EmployeeModel> list = baseDAO.queryForList("billproc.getProcEmpList", sc);
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BillProcModel getBillProc(String billProcId, String userId) throws Exception
	{
		Map map = new HashMap();
		map.put("billProcId", billProcId);
		map.put("userId", userId);
		return (BillProcModel)baseDAO.queryForObject("billproc.getBillProc", map);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<PrepayBillModel> getPrepayInfo(Map map) throws SQLException
	{
		return baseDAO.queryForList("billproc.getPrePayList", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String checkBillProc(String billId) throws Exception
	{
		List<String> getBillList = baseDAO.queryForList("billproc.getProcYn", billId);
		return getBillList.size() > 2 ? "Y" : "N";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BillProcModel>getBillHistoryList(String billId, String userId) throws Exception
	{
		Map map = new HashMap();
		map.put("billId", billId);
		map.put("userId", userId);
		return baseDAO.queryForList("billproc.getBillProcHistory", map);
	}
	
	@Override
	public String checkDistributedOrder(String billId) throws Exception
	{
		return (String)baseDAO.queryForObject("billproc.checkDistributedOrder", billId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VendorModel> getVendorListForBillLineByBillId(String billId) throws Exception{
		List<VendorModel> list = baseDAO.queryForList("billLine.getVendorListOfBillLineByBillId", billId);
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void saveBillProc(BillProcModel billProc, List<Map> nextWorkFlows) throws Exception
	{
		boolean nextWFExists = false;
		
		if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType()))
		{
			for (PriceDetailModel item : billProc.getPriceDetailList())
			{
				baseDAO.update("priceDetail.updatePriceDetail", item);
			}
		}
		else if (Constants.CONST_BILL_TYPE_PAYMENT.equals(billProc.getBillHead().getBillType()))
		{
			Map map = new HashMap();
			map.put("billId", billProc.getBillId());
			map.put("totalAmt", billProc.getBillHead().getTotal2());
			map.put("total2", billProc.getBillHead().getTotalAmt());
			map.put("updateBy", billProc.getUpdateBy());
			baseDAO.update("bill.updateBillHeadPriceForPayment", billProc.getBillHead());
		}
		else
		{
			//Update the bill line information
			for (BillLineModel item : billProc.getBillLineList())
			{
				baseDAO.update("billLine.updateBillLine", item);
			}
			baseDAO.update("bill.updateBillHeadPriceInfo", billProc.getBillId());
		}
		
		if (Constants.CONST_Y.equals(billProc.getSaveFlag()) || nextWorkFlows == null)
		{
			return;
		}
		
		StringBuilder procManStr = new StringBuilder();
		StringBuilder procNameStr = new StringBuilder();
		
		boolean isManFirst = true;
		boolean isProcNameFirst = true;
		
		for (Map item : nextWorkFlows)
		{
			WorkFlowModel nextWorkFlow = (WorkFlowModel)item.get("workFlow");
			List<EmployeeModel> empList = (List<EmployeeModel>)item.get("empList");
			
			if ( ! isProcNameFirst ) 
			{
				procNameStr.append(", ");
			}
			
			for (EmployeeModel emp : empList)
			{
				if ( ! isManFirst ) 
				{
					procManStr.append(", ");
				}
				
				BillProcModel bp = new BillProcModel();
				bp.setBillId(billProc.getBillId());
				bp.setProcDatId(nextWorkFlow.getWorkFlowId());
				bp.setEmpId(emp.getEmpId());
				
				if (Constants.WF_TYPE_ORDER_REQUEST.equals(nextWorkFlow.getWorkFlowType()))
				{
					bp.setProcTypeCd(Constants.PROC_TYPE_SUBMIT_BILL);
				}
				else if (Constants.WF_TYPE_ORDER_ACCEPT.equals(nextWorkFlow.getWorkFlowType()))
				{
					bp.setProcTypeCd(Constants.PROC_TYPE_SELLER_FLOW);
				}
				else if (Constants.WF_TYPE_RECEIPT.equals(nextWorkFlow.getWorkFlowType()))
				{
					bp.setProcTypeCd(Constants.PROC_TYPE_RECEIPT_FLOW);
				}
				else if (Constants.WF_TYPE_SALE.equals(nextWorkFlow.getWorkFlowType()))
				{
					bp.setProcTypeCd(Constants.PROC_TYPE_SALE_FLOW);
				}
				else if (Constants.WF_TYPE_PRICE.equals(nextWorkFlow.getWorkFlowType()))
				{
					bp.setProcTypeCd(Constants.PROC_TYPE_PRICE_FLOW);
				}
				else if (Constants.WF_TYPE_PAYMENT.equals(nextWorkFlow.getWorkFlowType()))
				{
					bp.setProcTypeCd(Constants.PROC_TYPE_PAYMENT_FLOW);
				}
				
				bp.setProcSeqNo(nextWorkFlow.getSeqNo());
				bp.setBillProcName(nextWorkFlow.getWorkFlowName());
				bp.setQtyYn(nextWorkFlow.getQtyYn());
				bp.setPriceYn(nextWorkFlow.getPriceYn());
				bp.setShipPriceYn(nextWorkFlow.getShipPriceYn());
				bp.setMinCost(nextWorkFlow.getMinCost());
				bp.setMaxCost(nextWorkFlow.getMaxCost());
				bp.setEmpName(emp.getEmpName());
				bp.setEmpNameok(emp.getEmpName());
				bp.setState(Constants.PROC_STATUS_IN_PROCESS);
				bp.setReadmark(Constants.CONST_N);
				bp.setUserId(nextWorkFlow.getUserId());
				bp.setUserName(nextWorkFlow.getUserName());
				bp.setUserNo(nextWorkFlow.getUserNo());
				bp.setCreateBy(billProc.getCreateBy());
				bp.setUpdateBy(billProc.getUpdateBy());
				
				baseDAO.insert("billproc.insertBillProc", bp);
				
				procManStr.append( emp.getEmpName() );
				isManFirst = false;
			}
			nextWFExists = true;
			
			procNameStr.append( nextWorkFlow.getWorkFlowName() );
			isProcNameFirst = false;
		}
		
		Map bh = new HashMap();
		bh.put("procMan", procManStr.toString());
		bh.put("billProc", procNameStr.toString());
		bh.put("billId", billProc.getBillId());
		bh.put("updateBy", billProc.getUpdateBy());
		baseDAO.update("bill.updateBillHeadSomeByBillId", bh);
		
		//Update current bill process status to 'complete'
		BillProcModel bp = new BillProcModel();
		bp.setProcNote(billProc.getProcNote());
		bp.setState(Constants.PROC_STATUS_COMPLETED);
		bp.setUpdateBy(billProc.getUpdateBy());
		bp.setProcDatId(billProc.getWorkFlowId());
		bp.setBillId(billProc.getBillId());
		bp.setEmpId(billProc.getCreateBy());
		
		baseDAO.update("billproc.updateBillProc", bp);
		
		//If the next workFlow does not exists then, the bill will be complete
		if (!nextWFExists)
		{
			BillHeadModel billHead = new BillHeadModel();
			billHead.setBillId(billProc.getBillId());
			billHead.setState(Constants.BILL_STATE_COMPLETED);
			baseDAO.update("bill.updateBillHeadStatusByBillId", billHead);
			
			if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || 
				Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType())
			)
			{
				// insert the paybill details.
				if (Constants.CONST_Y.equals(billProc.getBillHead().getHbmark()))
				{
					Map map = new HashMap();
					map.put("createBy", billProc.getCreateBy());
					map.put("updateBy", billProc.getUpdateBy());
					map.put("billId", billProc.getBillId());
					baseDAO.insert("billproc.insertPayBillDetail", map);
				}
			}
			
			//In case sale, update the eos_custprice table, so that set the customer's special price
			if (Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()) && (Constants.CONST_Y.equals(billProc.getPriceMark())))
			{
				for (BillLineModel item : billProc.getBillLineList())
				{	
					if ((!Double.valueOf(item.getPrice()).equals(Double.valueOf(item.getPrice2()))))
					{
						//Check eos_custprice_new table
						Map map = new HashMap();
						map.put("hostUserId", billProc.getBillHead().getHostUserId());
						map.put("custUserId", billProc.getBillHead().getCustUserId());
						map.put("itemId", item.getItemId());
						map.put("price", item.getPrice2());
						map.put("createBy", billProc.getCreateBy());
						map.put("updateBy", billProc.getUpdateBy());
						
						String existYn = (String)baseDAO.queryForObject("billLine.checkItemPriceExists", map);
						
						if (StringUtils.isNotEmpty(existYn))
						{
							baseDAO.update("billLine.updateCustPrice", map);
						}
						else
						{
							baseDAO.update("billLine.insertCustPrice", map);
						}
					}
				}
			}
			
			if (Constants.CONST_BILL_TYPE_PRICE.equals(billProc.getBillHead().getBillType()))
			{
				String priceCol = billProc.getPriceCols();
				JSONArray priceColArray = new JSONArray(priceCol);
				
				for (PriceDetailModel price : billProc.getPriceDetailList())
				{
					Map itemPriceMap = new HashMap();
					itemPriceMap.clear();
					for (int i = 0; i < priceColArray.length(); i++)
					{
						JSONObject item = (JSONObject)priceColArray.get(i);
						
						if (Constants.CONST_Y.equals(billProc.getPriceMark()))
						{
							Map map = new HashMap();
							map.put("price2", price.get("d" + String.valueOf(i + 1) + "2"));
							map.put("updateBy", price.getUpdateBy());
							map.put("itemId", price.getItemId());
							map.put("c20", (String)item.get("propertyName"));
							map.put("hostUserId", billProc.getBillHead().getHostUserId());
							baseDAO.update("billLine.updateBillLineByPrice", map);
							baseDAO.update("bill.updateBillHeadByPrice", map);
						}
						itemPriceMap.put((String)item.get("propertyName"), price.get("d" + String.valueOf(i + 1) + "2"));
					}
					itemPriceMap.put("updateBy", price.getUpdateBy());
					itemPriceMap.put("userId", billProc.getBillHead().getHostUserId());
					itemPriceMap.put("itemId", price.getItemId());
					baseDAO.update("userItem.updateUserItemInPrice", itemPriceMap);
				}
			}
			else if (!Constants.CONST_BILL_TYPE_PAYMENT.equals(billProc.getBillHead().getBillType()))
			{
				List<ItemStockModel> itemList = baseDAO.queryForList("itemStock.getItemStockByBillLineList", billProc.getBillId());
				
				for (ItemStockModel item : itemList)
				{
					if (StringUtils.isEmpty(item.getStoreId())) continue;
					
					Double cost = (double) 0;
					
					if (StringUtils.isEmpty(item.getStockItemId())){
						ItemStockModel itemStock = new ItemStockModel();
						itemStock.setStoreId(item.getStoreId());
						itemStock.setItemId(item.getItemId());
						if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
						{
							itemStock.setQty(String.valueOf((-1) * Double.valueOf(item.getBillQty())));
						}
						else if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType()))
						{
							itemStock.setQty(item.getBillQty());
							itemStock.setCost(item.getBillPrice());
							cost = Double.valueOf(item.getBillPrice());
						}
						
						itemStock.setUpdateBy(billProc.getUpdateBy());
						itemStock.setCreateBy(billProc.getCreateBy());
						baseDAO.insert("itemStock.insertItemStock", itemStock);
					}
					else
					{
						ItemStockModel itemStock = new ItemStockModel();
						itemStock.setStoreId(item.getStoreId());
						itemStock.setItemId(item.getItemId());
						if (Constants.CONST_BILL_TYPE_DING.equals(billProc.getBillHead().getBillType()) || Constants.CONST_BILL_TYPE_SALE.equals(billProc.getBillHead().getBillType()))
						{
							itemStock.setQty(String.valueOf(Double.valueOf(Double.valueOf(item.getQty()) - Double.valueOf(item.getBillQty()))));
						}
						else if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType()))
						{
							itemStock.setQty(String.valueOf(Double.valueOf(Double.valueOf(item.getQty()) + Double.valueOf(item.getBillQty()))));
							
							Map map = new HashMap();
							map.put("userId", billProc.getBillHead().getCustUserId());
							map.put("itemId", item.getItemId());
							ItemStockModel is = (ItemStockModel)baseDAO.queryForObject("itemStock.getItemTotalCost", map);
							cost = (Double.valueOf(item.getBillPrice()) * Double.valueOf(item.getBillQty()) + Double.valueOf(is.getTotalCost())) / (Double.valueOf(item.getBillQty()) + Double.valueOf(is.getTotalQty()));
							if (cost < 0) cost = (double) 0;
							itemStock.setCost(String.valueOf(cost));
						}
						itemStock.setUpdateBy(billProc.getUpdateBy());
						baseDAO.update("itemStock.updateItemStock", itemStock);
					}
					
					//Update eos_user_item table's cost, price_in, last_vendor_id info
					if (Constants.CONST_BILL_TYPE_RUKU.equals(billProc.getBillHead().getBillType()))
					{
						UserItemModel userItem = new UserItemModel();
						userItem.setCost(String.valueOf(cost));
						userItem.setPriceIn(item.getBillPrice());
						userItem.setLastVendorId(billProc.getBillHead().getHostUserId());
						userItem.setUpdateBy(billProc.getUpdateBy());
						userItem.setItemId(item.getItemId());
						userItem.setUserId(billProc.getBillHead().getCustUserId());
						baseDAO.update("userItem.updateUserItemInReceipt", userItem);
					}
				}
			}else{
				Map map = new HashMap();
				map.put("state", Constants.BILL_STATE_COMPLETED);
				map.put("billId", billProc.getBillId());
				baseDAO.update("billproc.updatePayBillDetailState", map);
			}
		}
	}
	public void updateReadMark(String billProcId) throws Exception
	{
		baseDAO.update("billproc.updateReadMark", billProcId);
	}
	
	public void rejectDocument(BillProcModel sc) throws Exception
	{
		baseDAO.update("billproc.rejectBill", sc);
		baseDAO.update("bill.rejectBill", sc);
		if (Constants.CONST_BILL_TYPE_PAYMENT.equals(sc.getBillHead().getBillType())){
			sc.setState(Constants.BILL_STATE_RETURNED);
			baseDAO.update("billproc.updatePayBillDetailState", sc);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplyStatisticModel> getSupplyStatistics(BillProcSModel sc) throws Exception
	{
		List<SupplyStatisticModel> statList = baseDAO.queryForList("billproc.getSupplyStatistic", sc);
		return statList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BuyStatisticModel> getBuyStatisticItemList(BillProcSModel sc) throws Exception
	{
		return baseDAO.queryForList("billproc.getBuyStatisticItemList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BuyStatisticModel> getBuyStatistic(BillProcSModel sc) throws Exception
	{
		List<BuyStatisticModel> statList = baseDAO.queryForList("billproc.getBuyStatistic", sc);
		return statList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BuyStatisticModel> getBuyStatisticForMoblie(BillProcSModel sc) throws Exception
	{
		List<BuyStatisticModel> statList = baseDAO.queryForList("billproc.getBuyStatisticForMobile", sc);
		return statList;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void saveBuyStatisticInfo(Map map) throws Exception
	{
		baseDAO.update("billproc.saveBuyStat", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BizDataModel> getDistributeList(BillProcSModel sc) throws Exception
	{
		List<BizDataModel> list = baseDAO.queryForList("billproc.getDistributeList", sc);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DistributeStatisticModel> getDistributeStatistic(BillProcSModel sc) throws Exception
	{
		List<DistributeStatisticModel> list = baseDAO.queryForList("billproc.getDistributeStatistic", sc);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillProcModel> getBillProcCheckedList(BillProcSModel sc) throws Exception
	{
		List<BillProcModel> list = baseDAO.queryForList("billproc.getBillProcCheckedList", sc);
		return list;
	}
	
	@Override
	public Integer getCountBillProcCheckedList(BillProcSModel sc) throws Exception
	{
		return (Integer)baseDAO.queryForObject("billproc.getCountBillProcCheckedList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HostCustModel> getCustShortNameList(BillProcSModel sc) throws Exception
	{
		return baseDAO.queryForList("billproc.getDistributeCustomListForPrint", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DistributeStatisticModel> getDistributeStatisticPrint(BillProcSModel sc) throws Exception
	{
		return baseDAO.queryForList("billproc.getDistributeItemListForPrint", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DistributeConfirmModel> getDistributeConfirmList(BillProcSModel sc) throws Exception
	{
		return baseDAO.queryForList("billproc.getDistributeConfirmList", sc);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void changeBillProcPrice(String userData) throws Exception
	{
		JSONArray jsonArr = new JSONArray(userData);
		baseDAO.getSqlMapClient().startBatch();
		for (int i = 0; i < jsonArr.length(); i++){
			JSONObject obj =  (JSONObject)jsonArr.get(i);
			Map map = new HashMap();
			map.put("qty2", obj.getString("qty2"));
			map.put("price2", obj.getString("price2"));
			map.put("billId", obj.getString("billId"));
			map.put("itemId", obj.getString("itemId"));
			baseDAO.update("billproc.changeBillProcPrice", map);
			baseDAO.update("bill.updateBillHeadPriceInfo", map);
		}
		baseDAO.getSqlMapClient().executeBatch();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplyStatisticModel> getLastReceiptList(BillProcSModel sc) throws Exception{
		return baseDAO.queryForList("billproc.getLastReceiptList", sc);
	}
}
