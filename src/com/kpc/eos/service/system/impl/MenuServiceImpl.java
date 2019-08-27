
package com.kpc.eos.service.system.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.service.BaseService;
import com.kpc.eos.model.dataMng.UserModel;
import com.kpc.eos.model.system.ActionUrlModel;
import com.kpc.eos.model.system.CodeModel;
import com.kpc.eos.model.system.MenuModel;
import com.kpc.eos.model.system.MenuSModel;
import com.kpc.eos.service.system.MenuService;

@Transactional
public class MenuServiceImpl extends BaseService implements MenuService {

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuModel> findMenuList(MenuSModel sc) throws Exception {
		return baseDAO.queryForList("menu.findMenuList", sc);
	}
	
	@Override
	public MenuModel getMenu(String menuId) throws Exception {
		return (MenuModel)baseDAO.queryForObject("menu.getMenu", menuId);
	}

	/* (non-Javadoc)
	 * @see com.kpc.eos.service.system.MenuService#getUpperMenuList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HashMap<String,String>> getUpperMenuList() throws Exception {
		return (List<HashMap<String, String>>)baseDAO.queryForList("menu.getUpperMenuList", null);
	}
	
	@Override
	public void saveMenu(List<MenuModel> paramList) throws Exception {
			baseDAO.batchUpdate("menu.updateMenu", paramList);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Object> findAccessibleMenuList(UserModel user) throws Exception {
		Map<String, MenuModel> higherMenu = new LinkedHashMap<String, MenuModel>();
		
		List<MenuModel> list;
		if (Constants.CONST_Y.equals(user.getUserYn()))
		{
			list = baseDAO.queryForList("menu.findUserAccessibleMenuList", user);
		}
		else
		{
			list = baseDAO.queryForList("menu.findAccessibleMenuList", user);
		}
		
		for (MenuModel menu : list) {
			if (StringUtils.isBlank(menu.getUpperMenuId())) {
				higherMenu.put(menu.getMenuId(), menu);
			} else {
				if (higherMenu.containsKey(menu.getUpperMenuId())) {
					higherMenu.get(menu.getUpperMenuId()).addSubMenu(menu);
				}
			}
		}
		for (MenuModel menu : list) {
			if (StringUtils.isBlank(menu.getUpperMenuId())) {
				
			} else {
				if (higherMenu.containsKey(menu.getUpperMenuId())) {
					
				}else{
					for (MenuModel menu1 : higherMenu.values()){
						if (menu1.getSubMenus() != null && menu1.getSubMenus().size() > 0){
							for (int i = 0; i < menu1.getSubMenus().size(); i++){
								if ( menu1.getSubMenus().get(i).getMenuId().equals(menu.getUpperMenuId())){
									menu1.getSubMenus().get(i).addSubMenu(menu);
								}
							}
						}
					}
				}
			}
		}
		return Arrays.asList(higherMenu.values().toArray());
		//return null;
	}


	@Override
	public boolean hasAuthorityOfAction(UserModel user, String actionId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("adminId", user.getEmpId());
		map.put("actionId", actionId);
		
		Integer result = (Integer) baseDAO.queryForObject("menu.hasAuthorityOfAction", map);
		return result > 0;
	}
	
	@Override
	public String hasAuthorityOfUrl(UserModel user, String actionUrl) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("empId", user.getEmpId());
		map.put("actionUrl", actionUrl);
		map.put("userId", user.getUserId());
		String result = "";
		
		//For logging action url
		//baseDAO.insert("menu.insertActionUrlForLogging", map);
		//return Constants.LOGIN_MAIN_PAGE_NO;
		
		if (Constants.CONST_Y.equals(user.getUserYn())) {
			//allow all user to can set 'MenuSetting' function
			if(StringUtils.isNotEmpty(actionUrl) && actionUrl.equals("MenuSetting.do?cmd=menuSettingList")) { 
				result = "MENU00000000029";
			}else {
				result = (String) baseDAO.queryForObject("menu.hasAuthorityOfUrlByUser", map);
			}
			
		}else {
			result = (String) baseDAO.queryForObject("menu.hasAuthorityOfUrlByEmp", map);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActionUrlModel> findActionUrlList(MenuSModel sc) throws Exception {
		return baseDAO.queryForList("menu.findActionUrlList", sc);
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuModel> getActionMenuList(MenuSModel sc) throws Exception {
		return baseDAO.queryForList("menu.getActionMenuList", sc);
	}
	
	@Override
	public void saveActinUrls(List<ActionUrlModel> paramList) throws Exception {
		ArrayList<ActionUrlModel> cParamList = new ArrayList<ActionUrlModel>();
		ArrayList<ActionUrlModel> uParamList = new ArrayList<ActionUrlModel>();
		for (ActionUrlModel model : paramList) {
			if(!StringUtils.isEmpty(model.getCrud()) && model.getCrud().equals("C")) {
				cParamList.add(model);
			} else {
				uParamList.add(model);
			}
		}
			baseDAO.batchUpdate("menu.updateActionUrls", uParamList);
			baseDAO.batchInsert("menu.insertActionUrls", cParamList);
	}
}
