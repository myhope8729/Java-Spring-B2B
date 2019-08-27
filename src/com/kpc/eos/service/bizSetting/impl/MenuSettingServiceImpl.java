
package com.kpc.eos.service.bizSetting.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.service.bizSetting.MenuSettingService;

/**
 * Filename		: MenuSettingServiceImpl.java
 * Description	: Implementation Class to manage menu name by user
 * 2017
 * @author		: RKRK
 */
@Transactional
public class MenuSettingServiceImpl extends BaseService implements MenuSettingService {
	
	@SuppressWarnings("unchecked")
	public List<MenuModel> getMenuSettingList(UserModel user) throws Exception {
		List<MenuModel> menuSetModel = baseDAO.queryForList("menu.getMenuSetListByUser", user);
		return menuSetModel;
	}

	@Override
	public void saveMenuSetting(MenuModel menuSet)
			throws Exception {
		if (StringUtils.isEmpty(menuSet.getSeqNo())) {
			baseDAO.insert("menu.insertMenuSet", menuSet);
		}else {
			baseDAO.update("menu.updateMenuSet", menuSet);
		}
	}
}
