
package com.kpc.eos.model.userPage;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.model.bizSetting.UserItemModel;

@Data
public class ShopCartModel extends CommonModel {
	
	private String shopCartId;
	private String cookieId;
	private String hostId;
	private String itemId;
	private String qty;
	
	// item model
	private UserItemModel userItem;
	
	public ShopCartModel() {
		this.userItem = new UserItemModel();
	}
}