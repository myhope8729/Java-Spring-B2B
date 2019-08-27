
package com.kpc.eos.service.bizSetting.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.bizSetting.UserMenuModel;
import com.kpc.eos.model.common.DefaultSModel;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.ActionUrlModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.service.bizSetting.MenuSettingService;
import com.kpc.eos.service.bizSetting.UserMenuService;

/**
 * Filename		: MenuSettingServiceImpl.java
 * Description	: Implementation Class to manage menu name by user
 * 2017
 * @author		: RKRK
 */
@Transactional
public class UserMenuServiceImpl extends BaseService implements UserMenuService {

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserMenuService#getUserMenuList(com.kpc.eos.model.common.DefaultSModel)
	 */
	@Override
	public List<UserMenuModel> getUserMenuList(DefaultSModel sc) throws Exception {
		List<UserMenuModel> userMenuList = (List<UserMenuModel>)baseDAO.queryForList("userMenu.getUserMenuList", sc);
		return userMenuList;
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.bizSetting.UserMenuService#saveUserMenu(java.util.List)
	 */
	@Override
	public void saveUserMenu(List<UserMenuModel> paramList, String userId) throws Exception {
		ArrayList<UserMenuModel> cParamList = new ArrayList<UserMenuModel>();
		ArrayList<UserMenuModel> uParamList = new ArrayList<UserMenuModel>();
		for (UserMenuModel model : paramList) {
			model.setUserId(userId);
			if(!StringUtils.isEmpty(model.getCrud()) && model.getCrud().equals("C")) {
				cParamList.add(model);
			} else {
				uParamList.add(model);
			}
		}
			baseDAO.batchUpdate("userMenu.updateUserMenu", uParamList);
			baseDAO.batchInsert("userMenu.insertUserMenu", cParamList);
	}
	
}
