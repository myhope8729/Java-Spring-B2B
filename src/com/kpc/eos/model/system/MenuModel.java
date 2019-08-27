
package com.kpc.eos.model.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;

/**
 * Filename		: MenuModel.java
 * Description	: menu model
 * 2017
 * @author		: RKRK
 */
@Data
public class MenuModel extends CommonModel {

	private static final long serialVersionUID = 3368964991367485827L;
    
	private String menuId;
	private String menuName;
	//menu description
	private String menuDesc;
	//menu name to set by a user
	private String menuUserName;
	private Integer sortNo;
	private String connUrl;
	private String upperMenuId;
	private String upperMenuName;

	//attribute for use tree grid 
	//depth
	private Integer level;
	// leaf node(true/false)
	private String isLeaf;
	private String expanded;

	private List<MenuModel> subMenus;

	private String seqNo;
	
	//added by rmh
	private String menuIcon;
	private String mobileYn;
	private String mobileYnName;
	
	//added by rmh 2018.01.25 , for mobile update
	private String id;
	
	public String getMobileYnName() {
		if(StringUtils.isNotEmpty(this.mobileYn) && this.mobileYn.equals(Constants.CONST_Y)) return Constants.CONST_Y_STR;
		else return Constants.CONST_N_STR;
	}
	
	public MenuModel() {}
	
	public void addSubMenu(MenuModel menu) {
		if (this.subMenus == null)
			this.subMenus = new ArrayList<MenuModel>();

		this.subMenus.add(menu);
	}
	
}