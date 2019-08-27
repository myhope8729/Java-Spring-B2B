
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.MenuModel;


public interface MenuSettingService {
	
	public List<MenuModel> getMenuSettingList(UserModel user) throws Exception;
	
	public void saveMenuSetting(MenuModel userId) throws Exception;
}
