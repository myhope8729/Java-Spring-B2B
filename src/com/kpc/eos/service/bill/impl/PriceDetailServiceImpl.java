
package com.kpc.eos.service.bill.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bill.PriceDetailModel;
import com.kpc.eos.model.bill.PriceDetailSModel;
import com.kpc.eos.service.bill.PriceDetailService;

public class PriceDetailServiceImpl extends BaseService implements PriceDetailService 
{

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bill.PriceDetailService#getPriceItemList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<PriceDetailModel> getPriceItemList(String userId,
			String billId, String state) throws Exception {
		
		Map param = new HashMap();
		param.put("billId", billId);
		param.put("userId", userId);
		param.put("state", state);
		
		return (List<PriceDetailModel>)baseDAO.queryForList( "priceDetail.getPriceItemList", param );
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bill.PriceDetailService#getAllItemsForPriceList(com.kpc.eos.model.bill.PriceDetailSModel)
	 */
	@Override
	public List<PriceDetailModel> getAllItemsForPriceList(PriceDetailSModel sc) throws Exception {
		
		return (List<PriceDetailModel>)baseDAO.queryForList("priceDetail.getAllItemsForPriceList", sc);
	}
}
