
package com.kpc.eos.service.system;

import java.util.HashMap;
import java.util.List;

import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.ActionUrlModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.model.system.MenuSModel;

public interface MenuService {

	public List<MenuModel> findMenuList(MenuSModel sc) throws Exception;
	
	public List<HashMap<String,String>> getUpperMenuList() throws Exception;
	
	public MenuModel getMenu(String menuId) throws Exception;
	
	public void saveMenu(List<MenuModel> paramList) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List findAccessibleMenuList(UserModel user) throws Exception;
	
	
	public boolean hasAuthorityOfAction(UserModel user, String actionId) throws Exception;
	
	public String hasAuthorityOfUrl(UserModel user, String actionUrl) throws Exception;	
	
	public List<ActionUrlModel> findActionUrlList(MenuSModel sc) throws Exception;
	
	public List<MenuModel> getActionMenuList(MenuSModel sc) throws Exception;	
	
	public void saveActinUrls(List<ActionUrlModel> paramList) throws Exception;
	
}
