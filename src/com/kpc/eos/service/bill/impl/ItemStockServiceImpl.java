
package com.kpc.eos.service.bill.impl;

import java.util.HashMap;
import java.util.Map;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bill.ItemStockModel;
import com.kpc.eos.service.bill.ItemStockService;


public class ItemStockServiceImpl extends BaseService implements ItemStockService 
{

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bill.ItemStockService#getItemStock(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ItemStockModel getItemStock(String storeId, String itemId) throws Exception {
		Map map = new HashMap();
		map.put("storeId", storeId);
		map.put("itemId", itemId);
		ItemStockModel itemStock = (ItemStockModel) baseDAO.queryForObject("itemStock.getItemStock", map);
		return itemStock;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bill.ItemStockService#getItemTotalCost(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ItemStockModel getItemTotalCost(String userId, String itemId) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("itemId", itemId);
		ItemStockModel itemStock = (ItemStockModel) baseDAO.queryForObject("itemStock.getItemTotalCost", map);
		return itemStock;
	}
	
}
