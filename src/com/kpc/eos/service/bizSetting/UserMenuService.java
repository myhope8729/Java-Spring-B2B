
package com.kpc.eos.service.bizSetting;

import java.util.List;

import com.kpc.eos.model.bizSetting.UserMenuModel;
import com.kpc.eos.model.common.DefaultSModel;

public interface UserMenuService {

	/**
	 * Description	:
	 * @author 		: RKRK
	 * @param sc
	 * @return
	 * 2018
	 */
	public List<UserMenuModel> getUserMenuList(DefaultSModel sc) throws Exception;

	/**
	 * Description	:
	 * @author 		: RKRK
	 * @param menu
	 * 2018
	 */
	public void saveUserMenu(List<UserMenuModel> paramList, String userId) throws Exception;
	
}
