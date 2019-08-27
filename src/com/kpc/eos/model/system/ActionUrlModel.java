
package com.kpc.eos.model.system;

import org.apache.commons.lang.StringUtils;

import lombok.Data;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.core.util.CodeUtil;

@Data
public class ActionUrlModel extends CommonModel 
{
	private static final long serialVersionUID = -5654777278692175807L;

	private String id;
	private String menuId;
	private String menuName;
	private String menuActionUrl;
	private String urlDesc;
	private String state;
	private String stateName;
	
	private String crud;	


	public String getStateName(){
		if(StringUtils.isEmpty(this.state)) {
			return this.stateName;
		}else {
			return CodeUtil.getCodeName(this.state);
		}
	}
	
	public ActionUrlModel() 
	{
		
	}
}
