
package com.kpc.eos.model.bizSetting;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;

/**
 * Filename		: MenuModel.java
 * Description	: menu model
 * 2017
 * @author		: RKRK
 */
@Data
public class UserMenuModel extends CommonModel {

	private static final long serialVersionUID = 3368964991367485827L;
    
	private String userId;
	private String menuName;
	private String menuUrl;
	private String seqNo;
	private String state;
	private String stateName;
	private String crud;

	private String id;
	
	public UserMenuModel() {}
	
	public String getStateName(){
		if(StringUtils.isEmpty(this.state)) {
			return this.stateName;
		}else {
			return CodeUtil.getCodeName(this.state);
		}
	}
	
}