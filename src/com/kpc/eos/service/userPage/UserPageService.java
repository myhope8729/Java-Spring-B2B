
package com.kpc.eos.service.userPage;

import java.util.List;

import com.kpc.eos.model.userPage.InfoLinkModel;
import com.kpc.eos.model.userPage.ShopCartModel;

public interface UserPageService {
	
	public List<ShopCartModel> getShopCartList(String cookieId, String hostId) throws Exception;
	public ShopCartModel getShopCart(ShopCartModel sc) throws Exception;
	
	public Integer getQtyTotalInCart(ShopCartModel sc) throws Exception;
	
	public void saveShopCart(ShopCartModel sc) throws Exception;
	
	public void removeShopCartList(List<ShopCartModel> removeList) throws Exception;
		
	public List<InfoLinkModel> getInfoLinkList(InfoLinkModel sc) throws Exception;
	
	public List<String> getInfoLinkPicList(String billId) throws Exception; 
}
