
package com.kpc.eos.service.bill;

import java.util.HashMap;
import java.util.List;

import com.kpc.eos.core.service.BaseServiceInterface;
import com.kpc.eos.model.bill.BillHeadModel;
import com.kpc.eos.model.bill.BillHeadSModel;
import com.kpc.eos.model.bill.BillWorkflowSModel;
import com.kpc.eos.model.bill.CartUserItemModel;
import com.kpc.eos.model.bill.ItemStockModel;
import com.kpc.eos.model.bill.VendorModel;
import com.kpc.eos.model.bizSetting.HostCustModel;
import com.kpc.eos.model.bizSetting.UserItemModel;
import com.kpc.eos.model.bizSetting.WorkFlowModel;

public interface ItemStockService  extends BaseServiceInterface
{
	public ItemStockModel getItemStock(String storeId, String itemId) throws Exception;
	
	public ItemStockModel getItemTotalCost(String userId, String itemId) throws Exception;
}
