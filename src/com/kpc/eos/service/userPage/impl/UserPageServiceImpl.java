
package com.kpc.eos.service.userPage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.userPage.InfoLinkModel;
import com.kpc.eos.model.userPage.ShopCartModel;
import com.kpc.eos.service.userPage.UserPageService;

public class UserPageServiceImpl extends BaseService implements UserPageService {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopCartModel> getShopCartList(String cookieId, String hostId) throws Exception 
	{
		Map<String, String> data = new HashMap<String, String>();
		data.put("cookieId", cookieId);		
		data.put("hostId", hostId);
		return (List<ShopCartModel>)baseDAO.queryForList("userPage.getShopCartList", data);
	}
	
	@Override
	public ShopCartModel getShopCart(ShopCartModel sc) throws Exception 
	{
		return (ShopCartModel)baseDAO.queryForObject("userPage.getShopCart", sc);
	}
	
	@Override
	public Integer getQtyTotalInCart(ShopCartModel sc) throws Exception 
	{
		return (Integer) baseDAO.queryForObject("userPage.getQtyTotalInCart", sc);
	}
	
	@Override
	public void saveShopCart(ShopCartModel sc) throws Exception {
		
		ShopCartModel shopCart = (ShopCartModel)baseDAO.queryForObject("userPage.getShopCart", sc);
		
		if ( shopCart == null ) 
		{
			if ( !( "0".equals(sc.getQty()) || StringUtils.isEmpty(sc.getQty())) ) 
			{
				baseDAO.insert("userPage.insertShopCart", sc);
			}
		} 
		else 
		{
			if ( "0".equals(sc.getQty()) || StringUtils.isEmpty(sc.getQty()) ) 
			{
				baseDAO.delete("userPage.deleteShopCart", sc);
			} 
			else 
			{
				baseDAO.update("userPage.updateShopCart", sc);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InfoLinkModel> getInfoLinkList(InfoLinkModel sc) throws Exception {
		return (List<InfoLinkModel>)baseDAO.queryForList("userPage.getInfoLinkList", sc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getInfoLinkPicList(String billId) throws Exception {
		return (List<String>)baseDAO.queryForList("userPage.getInfoLinkPicList", billId);
	}

	@Override
	public void removeShopCartList(List<ShopCartModel> removeList) throws Exception
	{
		baseDAO.getSqlMapClient().startBatch();
		for ( ShopCartModel shopCart : removeList )
		{
			baseDAO.getSqlMapClient().delete("userPage.deleteShopCart", shopCart);
		}
		baseDAO.getSqlMapClient().executeBatch();
	}
}
