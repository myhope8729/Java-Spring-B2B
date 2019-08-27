
package com.kpc.eos.service.bill.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.BizSetting;
import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.ResultModel;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.core.util.FileUtil;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.BillLineModel;
import com.kpc.eos.model.bill.BillLineNoStockModel;
import com.kpc.eos.model.bill.BillWorkflowSModel;
import com.kpc.eos.model.bill.CartUserItemModel;
import com.kpc.eos.model.bill.ItemStockModel;
import com.kpc.eos.model.bill.PayBillDetailModel;
import com.kpc.eos.model.bill.PriceDetailModel;
import com.kpc.eos.model.bill.ProcEmpModel;
import com.kpc.eos.model.bill.ProcGroupModel;
import com.kpc.eos.model.bill.VendorModel;
import com.kpc.eos.model.billProcMng.BillProcModel;
import com.kpc.eos.model.billProcMng.PrepayBillModel;
import com.kpc.eos.model.billProcMng.PrepayBillSModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;
import com.kpc.eos.service.bill.BillService;

public class BillServiceImpl extends BaseService implements BillService 
{
	/*public UserModel hostUser = null;
	public UserModel custUser = null;
	public HostCustModel hcModel = null;*/
	
	private HashMap<String, String> hostUserBSMap = null;
	
	public HostCustModel hcModel = null;
	
	@Override
	public void setHostUserBSMap(HashMap<String, String> hostUserBSMap)
	{
		this.hostUserBSMap = hostUserBSMap;
	}
	
	@Override
	public void setHostCustModel(HostCustModel hcModel)
	{
		this.hcModel = hcModel;
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public List<BillHeadModel> getOrderList() throws Exception 
    {
    	List<BillHeadModel> orderList = baseDAO.queryForList("bill.getOrderList", null);
    	return orderList;
    }
    
    @Override
	public Integer countBillList(BillHeadSModel sModel) throws Exception
	{
		return (Integer)baseDAO.queryForObject("bill.countOrderList", sModel);
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public List<BillHeadModel> getBillList(BillHeadSModel sModel) throws Exception 
    {
    	List<BillHeadModel> orderList = baseDAO.queryForList("bill.getOrderList", sModel);
    	return orderList;
    }
    
    @Override
    public BillHeadModel getBill(String orderId) throws Exception {
    	
    	return (BillHeadModel)baseDAO.queryForObject("bill.getBill", orderId);
    }
    
    @Override
    public void saveBill(BillHeadModel bill) throws Exception 
    {
    	if (StringUtils.isNotEmpty(bill.getBillId()))
		{	
    		baseDAO.update("bill.insertBillHead", bill);
		}
		else	
		{
			baseDAO.update("bill.insertBillHead", bill);
		}
    }
    
    
    @Override
    public void saveBill(BillHeadModel bill, boolean isNew) throws Exception 
    {
    	if ( isNew)
		{	
    		baseDAO.insert("bill.insertBillHead", bill);
		}
		else	
		{
			baseDAO.update("bill.updateBillHead", bill);
		}
    }
    
	@Override
	public String getBillHeadKey(String billType) throws Exception
	{
		return (String)baseDAO.queryForObject("bill.getBillHeadKey", billType);
	}
	
	@Override
	public String getBillHeadKey(String billType, String type) throws Exception
	{
		if (type != null) {
			throw new SQLException();
		}
		return (String)baseDAO.queryForObject("bill.getBillHeadKey", billType);
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<VendorModel> getUserVendorsByBillIdList(String userId, String billId, boolean isSplitProc) throws Exception
	{
		Map params = new HashMap();
		params.put( "userId", userId );
		params.put( "isSplitProc", isSplitProc );
		params.put( "billId", billId );
				
		return (List<VendorModel>)baseDAO.queryForList("bill.getUserVendorsByBillIdList", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkFlowModel> getWorkFlowByBillList(BillWorkflowSModel sModel) throws Exception
	{
		return (List<WorkFlowModel>) baseDAO.queryForList("workflow.getWorkFlowByBillList", sModel);
	}

	@Override
	public void deleteBillProcByBillId(String billId) throws Exception
	{
		baseDAO.delete( "bill.deleteBillProcByBillId", billId );
	}

	@Override
	public void deletePendingBillProcByBillId(String billId) throws Exception
	{
		baseDAO.delete( "bill.deletePendingBillProcByBillId", billId );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateBillHeadStatusByBillId(String billId, String state) throws Exception
	{
		Map param = new HashMap();
		param.put("billId", billId);
		param.put("state", state);
		
		baseDAO.update( "bill.updateBillHeadStatusByBillId", param );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public Object processSubmittedBill(BillHeadModel bill, List<UserItemModel> userItems,
			HashMap<String, CartUserItemModel> cartItems)
			throws Exception
	{
		ResultModel rm = new ResultModel();
		
		baseDAO.getSqlMapClient().startBatch();
		
		String billId = bill.getBillId();
		boolean isNew = StringUtils.isEmpty(billId);
		
		// is create case?
		if (isNew) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_DING);
			bill.setBillId(billId);
		} 
		else 
		{
			// remove the bill lines and bill proc list.
			baseDAO.delete( "billLine.deleteBillLineByBillId", billId );
			
			// baseDAO.delete( "billproc.deleteBillProcByBillId", billId );
			deletePendingBillProcByBillId(billId);
			
			baseDAO.delete( "billLine.deleteNoStockItemsByBillId", billId );
		}
		
		// ================ 1. Stock checking ========================================/
		int correctItemsCnt = 0;
		int noStockItemsCnt = 0;
		double correctTotal = 0.0;
		for (UserItemModel item : userItems)
		{
			String itemId = item.getItemId();
			CartUserItemModel cartItem = cartItems.get(itemId);
			
			boolean noStock = false;
			if (Constants.CONST_Y.equals(item.getStockMark())) 
			{
				double cartQty = cartItem.getQty().doubleValue();
				double stockQty = Double.valueOf(item.getStockQty()).doubleValue();
				if ( cartQty > stockQty )
				{
					BillLineNoStockModel blNS = new BillLineNoStockModel();
					blNS.setBillId( bill.getBillId() );
					blNS.setRbillId( bill.getBillId() );
					blNS.setUserId( item.getVendorId() );
					blNS.setItemId( itemId );
					blNS.setStoreId( item.getStoreId() );
					blNS.setCost( item.getCost() );
					blNS.setPrice( String.valueOf(cartItem.getPrice()) );
					blNS.setPrice2( String.valueOf(cartItem.getPrice()) );
					blNS.setQty( String.valueOf(cartItem.getQty()) );
					blNS.setQty2( String.valueOf(cartItem.getQty()) );
					blNS.setTot( String.valueOf(cartItem.getTotal()) );
					blNS.setTot2( String.valueOf(cartItem.getTotal()) );
					blNS.setNote(cartItem.getNote());
					blNS.setJsYn(item.getJsYn());
					blNS.setJsQty(String.valueOf(cartItem.getJsQty()));
					
					blNS.setUpdateBy(bill.getInputorId());
					blNS.setCreateBy(bill.getInputorId());
					
					baseDAO.insert("billLine.insertBillLineNoStock", blNS);
					
					noStock = true;
					noStockItemsCnt ++;
				}
			}
			
			// Stock check is ok?
			if (! noStock) 
			{
				BillLineModel bl = new BillLineModel();
				
				bl.setBillId( bill.getBillId() );
				bl.setRbillId( bill.getBillId() );
				bl.setVendorId( bill.getHostUserId() );
				bl.setItemId( itemId );
				bl.setStoreId( item.getStoreId() );
				bl.setCost( item.getCost() );
				bl.setPrice( String.valueOf(cartItem.getPrice()) );
				bl.setPrice2( String.valueOf(cartItem.getPrice()) );
				bl.setQty( String.valueOf(cartItem.getQty()) );
				bl.setQty2( String.valueOf(cartItem.getQty()) );
				bl.setTot( String.valueOf(cartItem.getTotal()) );
				bl.setTot2( String.valueOf(cartItem.getTotal()) );
				bl.setNote( cartItem.getNote() );
				bl.setJsQty(String.valueOf(cartItem.getJsQty()));
				bl.setJsDisplay( cartItem.getJsDisplay() );
				
				bl.setUpdateBy( bill.getInputorId() );
				bl.setCreateBy( bill.getInputorId() );
				
				baseDAO.insert("billLine.insertBillLine", bl);
				
				correctItemsCnt ++;
				correctTotal += cartItem.getTotal();
			}
		}
		
		if (correctItemsCnt == 0)
		{
			rm.setResultCode( EC_NO_ITEMS );
			return rm;
		}
		
		// ================ 2. Add a Bill Head ========================================/
		// Set extra information of a billHead and add it.
		bill.setTotalAmt( String.valueOf(correctTotal) );
		bill.setTotal2( String.valueOf(correctTotal) );
		bill.setItemAmt( String.valueOf(correctTotal) );
		bill.setItemamount2(String.valueOf(correctTotal));
		bill.setWebbno( billId );
		
		bill.setNostockmark( correctItemsCnt == userItems.size()? Constants.CONST_N : Constants.CONST_Y );
		
		
		// save bill first.
		saveBill( bill, isNew );
		
		
		// ================ 3. Add a bill proc of a submitter ========================================/
		// add a bill proc
		BillProcModel bp = new BillProcModel();
		bp.setBillId( billId );
		bp.setProcTypeCd( Constants.PROC_TYPE_SUBMIT_BILL );
		bp.setUserId( hcModel.getCustUserId() );
		bp.setUserName( hcModel.getCustUserName() );
		bp.setUserNo( hcModel.getCustUserNo() );
		bp.setBillProcName( Constants.PROC_NAME_ORDER_SUBMIT );
		bp.setState( Constants.PROC_STATUS_COMPLETED );
		bp.setProcSeqNo( Constants.PROC_SEQ_NO_DEFAULT );
		bp.setProcDatId( Constants.PROC_DAT_ID_DEFAULT );
		bp.setEmpId( bill.getInputorId() );
		bp.setEmpName( bill.getInputorName() );
		bp.setEmpNameok( bill.getInputorName() );
		bp.setCreateBy( bill.getInputorId() );
		bp.setUpdateBy( bill.getInputorId() );
		
		baseDAO.insert( "billproc.insertBillProc", bp );
		
		baseDAO.getSqlMapClient().executeBatch();
		
		// ================ 4. Add bill procs from workflows defined ========================================/
		boolean isSplitProc = BizSetting.SPLIT_ORDER_PROC_IN_SUBMISSION_Y.equals(hostUserBSMap.get(BizSetting.SPLIT_ORDER_PROC_IN_SUBMISSION)); 
		List<VendorModel> vendorList = getUserVendorsByBillIdList(hcModel.getHostUserId(), billId, isSplitProc);
		
		if (vendorList.isEmpty())
		{
			// Later we will throw an exception manullly to rollback the above queries.
			// But this logic might not be run.
			rm.setResultCode( EC_NO_WORKFLOW );
			return rm;
		}
		
		for ( VendorModel vendor: vendorList )
		{
			BillWorkflowSModel sm = new BillWorkflowSModel(hcModel.getCustUserId(), Constants.WF_TYPE_ORDER_REQUEST, 
					vendor.getUserId(), Constants.WF_TYPE_ORDER_ACCEPT);
			List<WorkFlowModel> wfList = getWorkFlowByBillList(sm);
			
			if ( wfList == null || wfList.isEmpty() )
			{
				// delete the bill proc added before and change the bill status as a DRAFT.
				deletePendingBillProcByBillId(billId);
				
				updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
				
				rm.setResultCode( EC_NO_WORKFLOW );
				return rm;
			}
			
			for ( WorkFlowModel wf : wfList )
			{
				String wfId = wf.getWorkFlowId();
				
				if ( wf.isValidWorkflowForBill(bill) )
				{
					List<ProcEmpModel> procEmpList = new ArrayList<ProcEmpModel>();
					
					if ( wf.isGroupWorkflow() )
					{
						Map param = new HashMap();
						param.put( "userId", hcModel.getCustUserId() );	// logged in user's ID.
						param.put( "procDatId", wfId );
						List<ProcGroupModel> procGroups = (List<ProcGroupModel>) baseDAO.queryForList("bill.getProcGroupListByWfId", param);
						
						if ( procGroups != null && !procGroups.isEmpty() )
						{
							for (ProcGroupModel pg : procGroups)
							{
								Map p1 = new HashMap();
								p1.put("billId", billId );
								p1.put("condition", pg.getCondition() );
								Integer exist = (Integer) baseDAO.queryForObject("bill.existBillHeadByCondition", p1);
								
								if (exist != null)
								{
									p1.clear();
									p1.put( "procDatId", wfId );
									p1.put( "seqNo", pg.getSeqNo() );
									
									List<ProcEmpModel> procEmpListTemp = (List<ProcEmpModel>) baseDAO.queryForList("bill.getProcGroupEmpListByWfId", p1);
									if ( ! procEmpListTemp.isEmpty() )
									{
										procEmpList.addAll(procEmpListTemp);
									}
									//procEmpList = (List<ProcEmpModel>) baseDAO.queryForList("bill.getProcGroupEmpListByWfId", p1);
								}
							}
						}
					}
					else
					{
						procEmpList = getProcEmpListByWfId(wfId);
					}
					
					// not exists if proc employees?
					if ( procEmpList.isEmpty() )
					{
						// delete the bill proc added before and change the bill status as a DRAFT.
						deletePendingBillProcByBillId(billId);
						
						updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
						
						rm.setResultCode( EC_NO_EMP );
						return rm;
					}
					
					StringBuilder procManStr = new StringBuilder();
					boolean isFirst = true;
					
					for ( ProcEmpModel procEmp : procEmpList )
					{
						if ( ! isFirst ) 
						{
							procManStr.append(", ");
						}
						
						bp = new BillProcModel();
						
						bp.setBillId( billId );
						
						bp.setProcTypeCd(Constants.PROC_TYPE_SELLER_FLOW);
						
						bp.setUserId(wf.getUserId());
						bp.setUserNo(wf.getUserNo());
						bp.setUserName(wf.getUserName());
						
						bp.setState( Constants.PROC_STATUS_IN_PROCESS );
						bp.setProcDatId( wfId );
						bp.setProcSeqNo( wf.getSeqNo() );
						bp.setBillProcName( wf.getWorkFlowName() ); //bp.setBillProcName( Constants.PROC_NAME_ORDER_SUBMIT );
						
						bp.setEmpId( procEmp.getEmpId() );
						bp.setEmpName( procEmp.getEmpName() );
						bp.setEmpNameok( procEmp.getEmpName() );
						
						bp.setQtyYn( wf.getQtyYn() );
						bp.setPriceYn( wf.getPriceYn() );
						bp.setShipPriceYn( wf.getShipPriceYn() );
						bp.setReadmark( Constants.CONST_N );
						
						bp.setCreateBy( bill.getInputorId() );
						bp.setUpdateBy( bill.getInputorId() );
						
						baseDAO.insert( "billproc.insertBillProc", bp );
						
						procManStr.append( procEmp.getEmpName() );
						isFirst = false;
					}
					bill.setProcMan(procManStr.toString());
					bill.setBillProc( wf.getWorkFlowName() );
					
					baseDAO.update( "bill.updateBillHeadSomeByBillId", bill );
					
					break;
				}
			}
		}
		
		rm.setResultCode( EC_SUCCESS );
		rm.setResultData( noStockItemsCnt );
		return rm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcEmpModel> getProcEmpListByWfId(String wfId)
			throws Exception
	{
		return (List<ProcEmpModel>) baseDAO.queryForList("bill.getProcEmpListByWfId", wfId);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bill.BillService#processReceipt(com.kpc.eos.model.bill.BillHeadModel, java.util.List, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void processReceipt(BillHeadModel bill, List<BillProcModel> bpList,
			List<BillLineModel> blList, List<ItemStockModel> newItemStockList,
			List<ItemStockModel> updateItemStockList,
			List<UserItemModel> updateItemList) throws Exception {
		
		baseDAO.getSqlMapClient().startBatch();
		
		String billId = bill.getBillId();

		if (StringUtils.isEmpty(billId)) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_RUKU);
			bill.setBillId(billId);
		}else{
			baseDAO.delete("bill.deleteBillHead", billId);
		}
		baseDAO.insert("bill.insertBillHead", bill);
		
		for (BillProcModel billProc : bpList){
			billProc.setBillId(billId);
			baseDAO.insert("billproc.insertBillProc", billProc);
		}
		
		baseDAO.delete("billLine.deleteBillLineByBillId", billId);
		
		for (BillLineModel billLine : blList){
			billLine.setBillId(billId);
			billLine.setRbillId(billId);
			baseDAO.insert("billLine.insertBillLine", billLine);
		}
		
		for (ItemStockModel newItemStock : newItemStockList){
			baseDAO.insert("itemStock.insertItemStock", newItemStock);
		}
		
		for(ItemStockModel updateItemStock : updateItemStockList){
			baseDAO.update("itemStock.updateItemStock", updateItemStock);
		}
		
		for (UserItemModel updateUserItem : updateItemList){
			baseDAO.update("userItem.updateUserItemInReceipt", updateUserItem);
		}		
		baseDAO.getSqlMapClient().executeBatch();
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BillLineModel> getBillItemList(Map map) throws Exception
	{
		return baseDAO.queryForList("billLine.getBillItemList", map);
	}
	
	@Override
	@Transactional
	public Object processSaveBill(BillHeadModel bill, List<UserItemModel> userItems, HashMap<String, CartUserItemModel> cartItems) throws Exception
	{
		ResultModel rm = new ResultModel();
		
		String billId = bill.getBillId();
		boolean isNew = StringUtils.isEmpty(billId);
		
		// is create case?
		if (isNew) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_DING);
			bill.setBillId(billId);
		} 
		else
		{
			baseDAO.delete( "billLine.deleteBillLineByBillId", billId );
		}
		// ================ 2. Adding items in bill_line table ========================================/
		double correctTotal = 0.0;
		for (UserItemModel item : userItems)
		{
			String itemId = item.getItemId();
			CartUserItemModel cartItem = cartItems.get(itemId);
			
			BillLineModel bl = new BillLineModel();
			
			bl.setBillId( bill.getBillId() );
			bl.setRbillId( bill.getBillId() );
			bl.setVendorId( item.getVendorId() );
			bl.setItemId( itemId );
			bl.setStoreId( item.getStoreId() );
			bl.setCost( item.getCost() );
			bl.setPrice( String.valueOf(cartItem.getPrice()) );
			bl.setPrice2( String.valueOf(cartItem.getPrice()) );
			bl.setQty( String.valueOf(cartItem.getQty()) );
			bl.setQty2( String.valueOf(cartItem.getQty()) );
			bl.setTot( String.valueOf(cartItem.getTotal()) );
			bl.setTot2( String.valueOf(cartItem.getTotal()) );
			bl.setNote( cartItem.getNote() );
			bl.setJsQty(String.valueOf(cartItem.getJsQty()));
			bl.setJsDisplay( cartItem.getJsDisplay() );
			
			bl.setUpdateBy( bill.getInputorId() );
			bl.setCreateBy( bill.getInputorId() );
			
			baseDAO.insert( "billLine.insertBillLine", bl );
				
			correctTotal += cartItem.getTotal();
		}
		
		// ================ 3. Add a Bill Head ========================================/
		// Set extra information of a billHead and add it.
		bill.setTotalAmt( String.valueOf(correctTotal) );
		bill.setTotal2( String.valueOf(correctTotal) );
		bill.setItemAmt( String.valueOf(correctTotal) );
		bill.setItemamount2(String.valueOf(correctTotal));
		bill.setNostockmark( Constants.CONST_N );
		
		// save bill first.
		saveBill( bill, isNew );
		
		return rm;
	}

	@Override
	@Transactional
	public void deleteBill(String billId) throws Exception
	{
		baseDAO.getSqlMapClient().startBatch();
		
		baseDAO.delete("bill.deleteBillHead", billId);
		
		baseDAO.delete( "billLine.deleteBillLineByBillId", billId );
		
		deleteBillProcByBillId(billId);
		
		baseDAO.getSqlMapClient().executeBatch();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void deleteBill(BillHeadModel bill) throws Exception
	{
		baseDAO.getSqlMapClient().startBatch();
		
		String billId = bill.getBillId();
		// if the current bill is an info bill, we need to remove all files in an info. 
		if ( Constants.CONST_BILL_TYPE_NEWS.equals(bill.getBillType()) )
		{
			List<String> images = baseDAO.queryForList("info.getInfoDetailFilesList", billId);
			for (String str : images)
			{
				FileUtil.deleteFile("infoImg", str);
			}
			List<String> files = baseDAO.queryForList("info.getInfoAttachmentFilesList", billId);
			for (String str : files)
			{
				FileUtil.deleteFile("infoAttachment", str);
			}
			
			baseDAO.delete("info.deleteInfoDetailByBillId", billId);
			
			baseDAO.delete("info.deleteInfoByBillId", billId);
			
			baseDAO.delete("info.deleteInfoAttachmentByBillId", billId);
		}
		
		if ( Constants.CONST_BILL_TYPE_PAYMENT.equals(bill.getBillType()) )
		{
			baseDAO.delete("paybillDetail.deletePaybillDetailByBillId", billId);
		}
		
		baseDAO.delete("bill.deleteBillHead", billId);
		
		baseDAO.delete( "billLine.deleteBillLineByBillId", billId );
		
		deleteBillProcByBillId(billId);
		
		baseDAO.getSqlMapClient().executeBatch();
	}

	@Override
	public void processDeleteReceipt(String billId) throws Exception {
		deleteBill( billId );
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BillProcModel> getOrderBillProcHistory(String billId, Map param)
			throws Exception
	{
		if (param == null)
			param = new HashMap();
		
		param.put("billId", billId);
		
		return baseDAO.queryForList( "billproc.getBillProcHistory", param );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BillProcModel> getBillProcHistory(BillHeadModel bill) throws Exception
	{
		Map param = new HashMap();
		
		param.put("billId", bill.getBillId());
		
		return baseDAO.queryForList( "billproc.getBillProcHistory", param );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PrepayBillModel> getPrePayTotalAmtList(PrepayBillSModel sc)
			throws Exception
	{
		return (List<PrepayBillModel> )baseDAO.queryForList("bill.getPrePayTotalAmtList", sc);
	}

	@Override
	public Integer countBillForReturnList(BillHeadSModel sc) throws Exception {
		return (Integer)baseDAO.queryForObject("bill.countBillForReturnList", sc);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bill.BillService#getBillForReturnList(com.kpc.eos.model.bill.BillHeadSModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillHeadModel> getBillForReturnList(BillHeadSModel sc) throws Exception {
		return (List<BillHeadModel>)baseDAO.queryForList("bill.getBillForReturnList", sc);
	}
	
	@Override
	public Integer countPaidBillHeadList(PrepayBillSModel sc)
	throws Exception
	{
		return (Integer) baseDAO.queryForObject("bill.countPaidBillHeadList", sc);
	}
	
	@Override
	public List<PayBillDetailModel> getPaidBillHeadList(PrepayBillSModel sc)
	throws Exception
	{
		return (List<PayBillDetailModel> )baseDAO.queryForList("bill.getPaidBillHeadList", sc);
	}
	

	@Override
	@Transactional
	public Object processSubmittedSaleBill(BillHeadModel bill, List<UserItemModel> userItems,
			HashMap<String, CartUserItemModel> cartItems)
			throws Exception
	{
		ResultModel rm = new ResultModel();
		
		baseDAO.getSqlMapClient().startBatch();
		
		String billId = bill.getBillId();
		boolean isNew = StringUtils.isEmpty(billId);
		
		// is crate case?
		if (isNew) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_SALE);
			bill.setBillId(billId);
		} 
		else 
		{
			// remove the bill lines and bill proc list.
			baseDAO.delete( "billLine.deleteBillLineByBillId", billId );
			
			deletePendingBillProcByBillId(billId);
			
			baseDAO.delete( "billLine.deleteNoStockItemsByBillId", billId );
		}
		
		// ================ 1. Stock checking ========================================/
		int correctItemsCnt = 0;
		int noStockItemsCnt = 0;
		double correctTotal = 0.0;
		for (UserItemModel item : userItems)
		{
			String itemId = item.getItemId();
			CartUserItemModel cartItem = cartItems.get(itemId);
			
			boolean noStock = false;
			if (Constants.CONST_Y.equals(item.getStockMark())) 
			{
				double cartQty = cartItem.getQty().doubleValue();
				double stockQty = Double.valueOf(item.getStockQty()).doubleValue();
				if ( cartQty > stockQty )
				{
					BillLineNoStockModel blNS = new BillLineNoStockModel();
					blNS.setBillId( bill.getBillId() );
					blNS.setRbillId( bill.getBillId() );
					blNS.setUserId( item.getVendorId() );
					blNS.setItemId( itemId );
					blNS.setStoreId( item.getStoreId() );
					blNS.setCost( item.getCost() );
					blNS.setPrice( String.valueOf(cartItem.getPrice()) );
					blNS.setPrice2( String.valueOf(cartItem.getPrice()) );
					blNS.setQty( String.valueOf(cartItem.getQty()) );
					blNS.setQty2( String.valueOf(cartItem.getQty()) );
					blNS.setTot( String.valueOf(cartItem.getTotal()) );
					blNS.setTot2( String.valueOf(cartItem.getTotal()) );
					blNS.setNote(cartItem.getNote());
					blNS.setJsYn(item.getJsYn());
					blNS.setJsQty(String.valueOf(cartItem.getJsQty()));
					
					blNS.setUpdateBy(bill.getInputorId());
					blNS.setCreateBy(bill.getInputorId());
					
					baseDAO.insert("billLine.insertBillLineNoStock", blNS);
					
					noStock = true;
					noStockItemsCnt ++;
				}
			}
			
			// Stock check is ok?
			if (! noStock) 
			{
				BillLineModel bl = new BillLineModel();
				
				bl.setBillId( bill.getBillId() );
				bl.setRbillId( bill.getBillId() );
				bl.setVendorId( bill.getHostUserId() );
				bl.setItemId( itemId );
				bl.setStoreId( item.getStoreId() );
				bl.setCost( item.getCost() );
				bl.setPrice( String.valueOf(cartItem.getPrice()) );
				bl.setPrice2( String.valueOf(cartItem.getPrice()) );
				bl.setQty( String.valueOf(cartItem.getQty()) );
				bl.setQty2( String.valueOf(cartItem.getQty()) );
				bl.setTot( String.valueOf(cartItem.getTotal()) );
				bl.setTot2( String.valueOf(cartItem.getTotal()) );
				bl.setNote( cartItem.getNote() );
				
				bl.setJsQty(String.valueOf(cartItem.getJsQty()));
				bl.setJsDisplay( cartItem.getJsDisplay() );
				
				bl.setUpdateBy( bill.getInputorId() );
				bl.setCreateBy( bill.getInputorId() );
				
				baseDAO.insert("billLine.insertBillLine", bl);
				
				correctItemsCnt ++;
				correctTotal += cartItem.getTotal();
			}
		}
		
		if (correctItemsCnt == 0)
		{
			rm.setResultCode( EC_NO_ITEMS );
			return rm;
		}
		
		// ================ 2. Add a Bill Head ========================================/
		// Set extra information of a billHead and add it.
		bill.setTotalAmt( String.valueOf(correctTotal) );
		bill.setTotal2( String.valueOf(correctTotal) );
		bill.setItemAmt( String.valueOf(correctTotal) );
		bill.setItemamount2(String.valueOf(correctTotal));
		bill.setWebbno( billId );
		
		bill.setNostockmark( correctItemsCnt == userItems.size()? Constants.CONST_N : Constants.CONST_Y );
		
		// save bill first.
		saveBill( bill, isNew );
		
		baseDAO.getSqlMapClient().executeBatch();
		
		// ================ 3. Add bill procs from workflows defined ========================================/
		BillWorkflowSModel sm = new BillWorkflowSModel(null, null, hcModel.getHostUserId(), Constants.WF_TYPE_SALE);
		List<WorkFlowModel> wfList = getWorkFlowByBillList(sm);
		
		if ( wfList == null || wfList.isEmpty() )
		{
			// delete the bill proc added before and change the bill status as a DRAFT.
			deletePendingBillProcByBillId(billId);
			
			updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
			
			rm.setResultCode( EC_NO_WORKFLOW );
			return rm;
		}
		
		boolean existsEmp = false;
		for ( WorkFlowModel wf : wfList )
		{
			String wfId = wf.getWorkFlowId();
			
			if ( wf.isValidWorkflowForBill(bill) )
			{
				List<ProcEmpModel> procEmpList = getProcEmpListByWfId(wfId);
				
				// not exists if proc employees?
				if ( procEmpList.isEmpty() )
				{
					// delete the bill proc added before and change the bill status as a DRAFT.
					deletePendingBillProcByBillId(billId);
					
					updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
					
					rm.setResultCode( EC_NO_EMP );
					return rm;
				}
				
				// ================ 3.1 Add a bill proc of a submitter ========================================/
				BillProcModel bp = new BillProcModel();
				bp.setBillId( billId );
				bp.setProcTypeCd( Constants.PROC_TYPE_SUBMIT_BILL );
				bp.setUserId( hcModel.getHostUserId() );
				bp.setUserName( hcModel.getHostUserName() );
				bp.setUserNo( hcModel.getHostUserNo() );
				bp.setState( Constants.PROC_STATUS_COMPLETED );
				
				bp.setBillProcName( Constants.PROC_NAME_SALE_SUBMIT );
				bp.setProcSeqNo( Constants.PROC_SEQ_NO_DEFAULT );
				bp.setProcDatId( Constants.PROC_DAT_ID_DEFAULT );
				
				bp.setEmpId( bill.getInputorId() );
				bp.setEmpName( bill.getInputorName() );
				bp.setEmpNameok( bill.getInputorName() );
				bp.setCreateBy( bill.getInputorId() );
				bp.setUpdateBy( bill.getInputorId() );
				
				baseDAO.insert( "billproc.insertBillProc", bp );
				
				// ================ 3.2 Add bill procs for the next emps ========================================/
				StringBuilder procManStr = new StringBuilder();
				boolean isFirst = true;
				
				for ( ProcEmpModel procEmp : procEmpList )
				{
					if ( ! isFirst ) 
					{
						procManStr.append(", ");
					}
					
					bp = new BillProcModel();
					
					bp.setBillId( billId );
					bp.setProcTypeCd(Constants.PROC_TYPE_SALE_FLOW);
					bp.setUserId(wf.getUserId());
					bp.setUserNo(wf.getUserNo());
					bp.setUserName(wf.getUserName());
					bp.setState(Constants.PROC_STATUS_IN_PROCESS );
					bp.setProcDatId( wfId );
					bp.setProcSeqNo( wf.getSeqNo() );
					bp.setBillProcName( wf.getWorkFlowName() ); 
					
					bp.setEmpId( procEmp.getEmpId() );
					bp.setEmpName( procEmp.getEmpName() );
					bp.setEmpNameok( procEmp.getEmpName() );
					
					bp.setQtyYn( wf.getQtyYn() );
					bp.setPriceYn( wf.getPriceYn() );
					bp.setShipPriceYn( wf.getShipPriceYn() );
					bp.setReadmark( Constants.CONST_N );
					
					bp.setCreateBy( bill.getInputorId() );
					bp.setUpdateBy( bill.getInputorId() );
					
					baseDAO.insert( "billproc.insertBillProc", bp );
					
					procManStr.append( procEmp.getEmpName() );
					isFirst = false;
				}
				bill.setProcMan(procManStr.toString());
				bill.setBillProc( wf.getWorkFlowName() );
				
				baseDAO.update( "bill.updateBillHeadSomeByBillId", bill );
				
				existsEmp = true;
				break;
			}
		}
		
		if (! existsEmp)
		{
			// delete the bill proc added before and change the bill status as a DRAFT.
			deletePendingBillProcByBillId(billId);
			updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
			
			rm.setResultCode( EC_NO_EMP );
			return rm;
		}
		
		rm.setResultCode( EC_SUCCESS );
		rm.setResultData( noStockItemsCnt );
		return rm;
	}

	
	@Override
	@Transactional
	public Object processSaveSaleBill(BillHeadModel bill, List<UserItemModel> userItems, HashMap<String, CartUserItemModel> cartItems) throws Exception
	{
		ResultModel rm = new ResultModel();
		
		String billId = bill.getBillId();
		boolean isNew = StringUtils.isEmpty(billId);
		
		// is create case?
		if (isNew) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_SALE);
			bill.setBillId(billId);
		} 
		else
		{
			baseDAO.delete( "billLine.deleteBillLineByBillId", billId );
		}
		// ================ 2. Adding items in bill_line table ========================================/
		double correctTotal = 0.0;
		for (UserItemModel item : userItems)
		{
			String itemId = item.getItemId();
			CartUserItemModel cartItem = cartItems.get(itemId);
			
			BillLineModel bl = new BillLineModel();
			
			bl.setBillId( bill.getBillId() );
			bl.setRbillId( bill.getBillId() );
			bl.setVendorId( item.getVendorId() );
			bl.setItemId( itemId );
			bl.setStoreId( item.getStoreId() );
			bl.setCost( item.getCost() );
			bl.setPrice( String.valueOf(cartItem.getPrice()) );
			bl.setPrice2( String.valueOf(cartItem.getPrice()) );
			bl.setQty( String.valueOf(cartItem.getQty()) );
			bl.setQty2( String.valueOf(cartItem.getQty()) );
			bl.setTot( String.valueOf(cartItem.getTotal()) );
			bl.setTot2( String.valueOf(cartItem.getTotal()) );
			bl.setNote( cartItem.getNote() );
			bl.setJsQty(String.valueOf(cartItem.getJsQty()));
			bl.setJsDisplay( cartItem.getJsDisplay() );
			
			bl.setUpdateBy( bill.getInputorId() );
			bl.setCreateBy( bill.getInputorId() );
			
			baseDAO.insert( "billLine.insertBillLine", bl );
				
			correctTotal += cartItem.getTotal();
		}
		
		// ================ 3. Add a Bill Head ========================================/
		// Set extra information of a billHead and add it.
		bill.setTotalAmt( String.valueOf(correctTotal) );
		bill.setTotal2( String.valueOf(correctTotal) );
		bill.setItemAmt( String.valueOf(correctTotal) );
		bill.setItemamount2(String.valueOf(correctTotal));
		bill.setNostockmark( Constants.CONST_N );
		
		// save bill first.
		saveBill( bill, isNew );
		
		return rm;
	}

	@Override
	@Transactional
	public void processReturn(BillHeadModel bill, BillProcModel bpModel,
			List<BillLineModel> blList, List<ItemStockModel> newItemStockList,
			List<ItemStockModel> updateItemStockList,
			List<UserItemModel> updateItemList) throws Exception {
		
		baseDAO.getSqlMapClient().startBatch();
		
		String billId = bill.getBillId();

		if (StringUtils.isEmpty(billId)) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_TUI);
			bill.setBillId(billId);
		}else{
			baseDAO.delete("bill.deleteBillHead", billId);
		}
		baseDAO.insert("bill.insertBillHead", bill);
		
		bpModel.setBillId(billId);
		baseDAO.insert("billproc.insertBillProc", bpModel);
		
		baseDAO.delete("billLine.deleteBillLineByBillId", billId);
		
		for (BillLineModel billLine : blList){
			billLine.setBillId(billId);
			billLine.setRbillId(billId);
			baseDAO.insert("billLine.insertBillLine", billLine);
		}
		
		for (ItemStockModel newItemStock : newItemStockList){
			baseDAO.insert("itemStock.insertItemStock", newItemStock);
		}
		
		for(ItemStockModel updateItemStock : updateItemStockList){
			baseDAO.update("itemStock.updateItemStock", updateItemStock);
		}
		
		for (UserItemModel updateUserItem : updateItemList){
			baseDAO.update("userItem.updateUserItemInReceipt", updateUserItem);
		}		
		baseDAO.getSqlMapClient().executeBatch();
		
		// insert the paybill details.
		if (Constants.CONST_Y.equals(bill.getHbmark()))
		{
			Map map = new HashMap();
			map.put("createBy", bill.getCreateBy());
			map.put("updateBy", bill.getUpdateBy());
			map.put("billId", bill.getBillId());
			baseDAO.insert("billproc.insertPayBillDetail", map);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Integer checkExistBillHeadForWFGroupCondition(Map map) throws Exception
	{
		return (Integer) baseDAO.queryForObject("bill.existBillHeadByCondition", map);
	}

	@Override
	public Integer countReceiptBillForPriceList(BillHeadSModel sc)
			throws Exception {
		return (Integer) baseDAO.queryForObject("bill.countReceiptBillForPriceList", sc);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bill.BillService#getReceiptBillForPriceList(com.kpc.eos.model.bill.BillHeadSModel)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillHeadModel> getReceiptBillForPriceList(BillHeadSModel sc)
			throws Exception {
		return (List<BillHeadModel>)baseDAO.queryForList("bill.getReceiptBillForPriceList", sc);
	}

	@Override
	@Transactional
	public Object processSubmittedInfoBill(BillHeadModel info) throws Exception
	{
		ResultModel rm = new ResultModel();
		/*
		String billId = info.getBillId();
		boolean isNew = StringUtils.isEmpty(billId);
		
		// is crate case?
		if ( isNew ) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_SALE);
			info.setBillId(billId);
		} 
		else 
		{
			baseDAO.delete( "billproc.deleteBillProcByBillId", billId );
		}
		
		saveBill( info, isNew );
		
		
		// ================ 3. Add bill procs from workflows defined ========================================/
		BillWorkflowSModel sm = new BillWorkflowSModel(null, null, info.getHostUserId(), Constants.WF_TYPE_NEWS);
		List<WorkFlowModel> wfList = getWorkFlowByBillList(sm);
		
		if ( wfList == null || wfList.isEmpty() )
		{
			// delete the bill proc added before and change the bill status as a DRAFT.
			deleteBillProcByBillId( billId );
			
			updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
			
			rm.setResultCode( EC_NO_WORKFLOW );
			return rm;
		}
		
		boolean existsEmp = false;
		for ( WorkFlowModel wf : wfList )
		{
			String wfId = wf.getWorkFlowId();
			
			if ( wf.isValidWorkflowForBill(info) )
			{
				List<ProcEmpModel> procEmpList = getProcEmpListByWfId(wfId);
				
				// not exists if proc employees?
				if ( procEmpList.isEmpty() )
				{
					// delete the bill proc added before and change the bill status as a DRAFT.
					deleteBillProcByBillId( billId );
					updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
					
					rm.setResultCode( EC_NO_EMP );
					return rm;
				}
				
				// ================ 3.1 Add a bill proc of a submitter ========================================/
				BillProcModel bp = new BillProcModel();
				bp.setBillId( billId );
				bp.setProcTypeCd( Constants.PROC_TYPE_SUBMIT_BILL );
				bp.setUserId( hcModel.getHostUserId() );
				bp.setUserName( hcModel.getHostUserName() );
				bp.setUserNo( hcModel.getHostUserNo() );
				bp.setState( Constants.PROC_STATUS_COMPLETED );
				
				bp.setBillProcName( Constants.PROC_NAME_SALE_SUBMIT );
				bp.setProcSeqNo( Constants.PROC_SEQ_NO_DEFAULT );
				bp.setProcDatId( Constants.PROC_DAT_ID_DEFAULT );
				
				bp.setEmpId( info.getInputorId() );
				bp.setEmpName( info.getInputorName() );
				bp.setEmpNameok( info.getInputorName() );
				bp.setCreateBy( info.getInputorId() );
				bp.setUpdateBy( info.getInputorId() );
				
				baseDAO.insert( "billproc.insertBillProc", bp );
				
				// ================ 3.2 Add bill procs for the next emps ========================================/
				StringBuilder procManStr = new StringBuilder();
				boolean isFirst = true;
				
				for ( ProcEmpModel procEmp : procEmpList )
				{
					if ( ! isFirst ) 
					{
						procManStr.append(",");
					}
					
					bp = new BillProcModel();
					
					bp.setBillId( billId );
					bp.setProcTypeCd(Constants.PROC_TYPE_SALE_FLOW);
					bp.setUserId(wf.getUserId());
					bp.setUserNo(wf.getUserNo());
					bp.setUserName(wf.getUserName());
					bp.setState(Constants.PROC_STATUS_IN_PROCESS );
					bp.setProcDatId( wfId );
					bp.setProcSeqNo( wf.getSeqNo() );
					bp.setBillProcName( wf.getWorkFlowName() ); 
					
					bp.setEmpId( procEmp.getEmpId() );
					bp.setEmpName( procEmp.getEmpName() );
					bp.setEmpNameok( procEmp.getEmpName() );
					
					bp.setQtyYn( wf.getQtyYn() );
					bp.setPriceYn( wf.getPriceYn() );
					bp.setShipPriceYn( wf.getShipPriceYn() );
					bp.setReadmark( Constants.CONST_N );
					
					bp.setCreateBy( info.getInputorId() );
					bp.setUpdateBy( info.getInputorId() );
					
					baseDAO.insert( "billproc.insertBillProc", bp );
					
					procManStr.append( procEmp.getEmpName() );
					isFirst = false;
				}
				info.setProcMan(procManStr.toString());
				info.setBillProc( wf.getWorkFlowName() );
				
				baseDAO.update( "bill.updateBillHeadSomeByBillId", info );
				
				existsEmp = true;
				break;
			}
		}
		
		if (! existsEmp)
		{
			// delete the bill proc added before and change the bill status as a DRAFT.
			deleteBillProcByBillId( billId );
			updateBillHeadStatusByBillId(billId, Constants.BILL_STATE_DRAFT);
			
			rm.setResultCode( EC_NO_EMP );
			return rm;
		}
		
		rm.setResultCode( EC_SUCCESS );
		*/
		return rm;
	}

	@Override
	@Transactional
	public Object processSaveInfoBill(BillHeadModel info) throws Exception
	{
		ResultModel rm = new ResultModel();
		
		String billId = info.getBillId();
		boolean isNew = StringUtils.isEmpty(billId);
		
		// is create case?
		if (isNew) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_NEWS);
			info.setBillId(billId);
		} 
		
		// save bill.
		saveBill( info, isNew );
		
		return rm;
	}

	
	@Override
	@Transactional
	public Object processSubmittedPaymentBill(BillHeadModel bill, List<PayBillDetailModel> detailList) throws Exception
	{
		ResultModel rm = new ResultModel();
		
		String billId = bill.getBillId();
		boolean isNew = StringUtils.isEmpty(billId);
		
		// is crate case?
		// In fact, create case would exists only.
		if (isNew) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_PAYMENT);
			bill.setBillId(billId);
		} 
		/*else 
		{
			// remove the bill lines and bill proc list.
			baseDAO.delete( "billproc.deleteBillProcByBillId", billId );
		}*/
		
		// ================ 1. Add bill procs from workflows defined ========================================/
		BillWorkflowSModel sm = new BillWorkflowSModel(null, null, bill.getHostUserId(), Constants.WF_TYPE_PAYMENT);
		List<WorkFlowModel> wfList = getWorkFlowByBillList(sm);
		
		boolean existsEmp = false;
		if ( ! wfList.isEmpty() )
		{
			for ( WorkFlowModel wf : wfList )
			{
				String wfId = wf.getWorkFlowId();
				
				if ( wf.isValidWorkflowForBill(bill) )
				{
					List<ProcEmpModel> procEmpList = getProcEmpListByWfId(wfId);
					
					// not exists if proc employees?
					if ( procEmpList.isEmpty() )
					{
						// delete the bill proc added before.
						deletePendingBillProcByBillId(billId);
						
						bill.setBillId(null);
						
						rm.setResultCode( EC_NO_EMP );
						return rm;
					}
					
					// ================ 1.1 Add a bill proc of a submitter ========================================/
					BillProcModel bp = new BillProcModel();
					bp.setBillId( billId );
					bp.setProcTypeCd( Constants.PROC_TYPE_SUBMIT_BILL );
					bp.setUserId( bill.getHostUserId() );
					bp.setUserName( bill.getHostUserName() );
					bp.setUserNo( bill.getHostUserNo() );
					bp.setState( Constants.PROC_STATUS_COMPLETED );
					
					bp.setBillProcName( Constants.PROC_NAME_PAYMENT_BILL_SUBMIT );
					bp.setProcSeqNo( Constants.PROC_SEQ_NO_DEFAULT );
					bp.setProcDatId( Constants.PROC_DAT_ID_DEFAULT );
					
					bp.setEmpId( bill.getInputorId() );
					bp.setEmpName( bill.getInputorName() );
					bp.setEmpNameok( bill.getInputorName() );
					bp.setCreateBy( bill.getInputorId() );
					bp.setUpdateBy( bill.getInputorId() );
					
					baseDAO.insert( "billproc.insertBillProc", bp );
					
					// ================ 1.2 Add bill procs for the next emps ========================================/
					StringBuilder procManStr = new StringBuilder();
					boolean isFirst = true;
					
					for ( ProcEmpModel procEmp : procEmpList )
					{
						if ( ! isFirst ) 
						{
							procManStr.append(", ");
						}
						
						bp = new BillProcModel();
						
						bp.setBillId( billId );
						bp.setProcTypeCd(Constants.PROC_TYPE_PAYMENT_FLOW);
						bp.setUserId(wf.getUserId());
						bp.setUserNo(wf.getUserNo());
						bp.setUserName(wf.getUserName());
						bp.setState(Constants.PROC_STATUS_IN_PROCESS );
						bp.setProcDatId( wfId );
						bp.setProcSeqNo( wf.getSeqNo() );
						bp.setBillProcName( wf.getWorkFlowName() ); 
						
						bp.setEmpId( procEmp.getEmpId() );
						bp.setEmpName( procEmp.getEmpName() );
						bp.setEmpNameok( procEmp.getEmpName() );
						
						bp.setReadmark( Constants.CONST_N );
						
						bp.setCreateBy( bill.getInputorId() );
						bp.setUpdateBy( bill.getInputorId() );
						
						baseDAO.insert( "billproc.insertBillProc", bp );
						
						procManStr.append( procEmp.getEmpName() );
						isFirst = false;
					}
					
					bill.setProcMan(procManStr.toString());
					bill.setBillProc( wf.getWorkFlowName() );
					bill.setState( Constants.BILL_STATE_IN_PROCESS );
					
					existsEmp = true;
					break;
				}
			}
		}
		
		// ================ 2. Add a Bill Head ========================================/
		
		if ( Constants.BILL_STATE_COMPLETED.equals(bill.getState()) )
		{
			bill.setPayState(Constants.CONST_Y);
		}
		else
		{
			bill.setPayState(Constants.CONST_N);
		}
		
		saveBill( bill, isNew );
		
		// We need to record the paybill details.
		if ( ! detailList.isEmpty() )
		{
			baseDAO.getSqlMapClient().startBatch();
			
			baseDAO.getSqlMapClient().delete( "paybillDetail.deletePaybillDetailByBillId", billId );
			
			for (PayBillDetailModel detail : detailList)
			{
				detail.setBillId(billId);
				detail.setState(bill.getState());
				baseDAO.getSqlMapClient().insert( "paybillDetail.insertPaybillDetail", detail );
			}
			baseDAO.getSqlMapClient().executeBatch();
		}
		
		rm.setResultCode( EC_SUCCESS );
		return rm;
	}
	
	
	@Override
	@Transactional
	public void processPrice(BillHeadModel bill, List<BillProcModel> bpList,
			List<PriceDetailModel> pdList, List<BillLineModel> blList,
			List<UserItemModel> uiList, BillHeadModel updateBill)
			throws Exception {
		
		baseDAO.getSqlMapClient().startBatch();
		
		String billId = bill.getBillId();

		if (StringUtils.isEmpty(billId)) 
		{
			billId = getBillHeadKey(Constants.CONST_BILL_TYPE_PRICE);
			bill.setBillId(billId);
		}else{
			baseDAO.delete("bill.deleteBillHead", billId);
		}
		baseDAO.insert("bill.insertBillHead", bill);
		
		for (BillProcModel billProc : bpList){
			billProc.setBillId(billId);
			baseDAO.insert("billproc.insertBillProc", billProc);
		}
		
		baseDAO.delete("priceDetail.deletePriceDetailByBillId", billId);
		
		for (PriceDetailModel priceDetail : pdList){
			priceDetail.setBillId(billId);
			baseDAO.insert("priceDetail.insertPriceDetail", priceDetail);
		}
		
		for (BillLineModel billLine : blList){
			baseDAO.update("billLine.updateBillLineByPrice", billLine);
		}
		
		for (UserItemModel updateUserItem : uiList){
			baseDAO.update("userItem.updateUserItemInPrice", updateUserItem);
		}		
		
		if (updateBill != null){
			baseDAO.update("bill.updateBillHeadByPrice", updateBill);
		}
		baseDAO.getSqlMapClient().executeBatch();
	}

	@Override
	@Transactional
	public void processDeletePrice(String billId) throws Exception {
		
		baseDAO.getSqlMapClient().startBatch();
		baseDAO.delete("bill.deleteBillHead", billId);
		deleteBillProcByBillId(billId);
		baseDAO.delete("priceDetail.deletePriceDetail", billId);
		baseDAO.getSqlMapClient().executeBatch();
	}
	
	@Override
	public List<PayBillDetailModel> getPaybillDetailListByBillId(String billId) throws Exception
	{
		return baseDAO.queryForList("paybillDetail.getPaybillDetailList", billId);
	}
	
	@Override
	public BillLineModel getBillItemsTotal(Map<String, String> map) throws Exception
	{
		return (BillLineModel)baseDAO.queryForObject("billLine.getBillItemsTotal", map);
	}
	
}
