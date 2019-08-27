
package com.kpc.eos.service.bill;

import java.util.List;

import com.kpc.eos.core.service.BaseServiceInterface;
import com.kpc.eos.model.bill.PriceDetailModel;
import com.kpc.eos.model.bill.PriceDetailSModel;

public interface PriceDetailService  extends BaseServiceInterface
{
	public List<PriceDetailModel> getPriceItemList(String userId, String billId, String state) throws Exception;
	public List<PriceDetailModel> getAllItemsForPriceList(PriceDetailSModel sc) throws Exception;
}
